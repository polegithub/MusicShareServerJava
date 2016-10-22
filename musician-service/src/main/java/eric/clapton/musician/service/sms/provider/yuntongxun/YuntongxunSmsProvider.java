package eric.clapton.musician.service.sms.provider.yuntongxun;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.google.common.base.Charsets;

import eric.clapton.infrastructure.util.CloseableUtils;
import eric.clapton.infrastructure.util.net.WebClient;
import eric.clapton.infrastructure.util.net.WebClientImpl;
import eric.clapton.infrastructure.util.net.WebException;

public class YuntongxunSmsProvider {
	private URL baseUrl;
	private String accountSid;
	private String authToken;
	private String appId;
	private WebClient webClient;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public URL getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(URL baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getAccountSid() {
		return accountSid;
	}

	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public WebClient getWebClient() {
		return webClient;
	}

	public void setWebClient(WebClient webClient) {
		this.webClient = webClient;
	}

	public String sendMessage(List<String> phoneNumbers, String messageTemplateId, List<String> args)
			throws YuntongxunException {
		final Date now = new Date();
		final String timestamp = getTimestampString(now);

		// step 1: build request url
		URL requestUrl = buildRequestUrl(timestamp);
		// step 2: build request headers
		Map<String, String> headers = buildRequestHeaders(timestamp);
		// step 3: build request body
		String requestBody = buildRequestBody(phoneNumbers, messageTemplateId, args);
		// step 4: make request
		String responseText;
		try {
			responseText = webClient.uploadString(requestUrl.toString(), requestBody, "application/xml", headers);
		} catch (WebException e) {
			throw new YuntongxunException("在调用 API 时遇到错误。", e);
		}
		// step 5: parse response
		String id = parseResponse(responseText);

		return id;
	}

	private static final String STATUS_CODE_OK = "000000";

	private String parseResponse(String responseText) throws YuntongxunException, YuntongxunUnexpectedStatusException {
		StringReader reader = null;
		try {
			reader = new StringReader(responseText);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(new InputSource(reader));
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			String statusCode = (String) xpath.evaluate("/Response/statusCode/text()", document, XPathConstants.STRING);

			if (STATUS_CODE_OK.equals(statusCode)) {
				return (String) xpath.evaluate("/Response/TemplateSMS/smsMessageSid/text()", document,
						XPathConstants.STRING);
			}

			String statusMsg = (String) xpath.evaluate("/Response/statusMsg/text()", document, XPathConstants.STRING);
			throw new YuntongxunUnexpectedStatusException(statusCode, statusMsg);
		} catch (YuntongxunException e) {
			throw e;
		} catch (Throwable t) {
			throw new YuntongxunException("在尝试解析 API 响应时遇到错误。", t);
		} finally {
			CloseableUtils.safeClose(reader);
		}
	}

	private Map<String, String> buildRequestHeaders(String timestamp) {
		Map<String, String> headers = new HashMap<String, String>(2);
		headers.put("Authorization", getAuthorization(timestamp));
		// headers.put("Content-Type", "application/xml");
		headers.put("Accept", "application/xml");

		return headers;
	}

	private String buildRequestBody(List<String> phoneNumbers, String messageTemplateId, List<String> args)
			throws YuntongxunException {
		try {
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			StringWriter w = new StringWriter();
			XMLStreamWriter writer = factory.createXMLStreamWriter(w);
			writer.writeStartDocument();
			writer.writeStartElement("TemplateSMS");
			writer.writeStartElement("to");
			for (int i = 0, s = phoneNumbers.size(); i < s; i++) {
				if (i > 0) {
					writer.writeCharacters(",");
				}
				writer.writeCharacters(phoneNumbers.get(i));
			}
			writer.writeEndElement();
			writer.writeStartElement("appId");
			writer.writeCharacters(getAppId());
			writer.writeEndElement();
			writer.writeStartElement("templateId");
			writer.writeCharacters(messageTemplateId);
			writer.writeEndElement();
			writer.writeStartElement("datas");
			if (args != null) {
				for (String arg : args) {
					writer.writeStartElement("data");
					writer.writeCharacters(arg);
					writer.writeEndElement();
				}
			}
			writer.writeEndElement();
			writer.writeEndDocument();

			writer.flush();
			writer.close();
			return w.toString();
		} catch (Throwable t) {
			throw new YuntongxunException("在尝试构建 API 报文时遇到错误。", t);
		}
	}

	private String calculateSigParameter(String timestamp) throws YuntongxunException {
		String h = getAccountSid() + getAuthToken() + timestamp;
		return computeMd5Hash(h);
	}

	private String getAuthorization(String timestamp) {
		return Base64.getEncoder().encodeToString((getAccountSid() + ':' + timestamp).getBytes(Charsets.UTF_8));
	}

	private URL buildRequestUrl(String timestamp) throws YuntongxunException {
		// /2013-12-26/Accounts/{accountSid}/SMS/TemplateSMS?sig={SigParameter}
		StringBuilder relativeUrl = new StringBuilder();
		relativeUrl.append("/2013-12-26/Accounts/");
		relativeUrl.append(accountSid);
		relativeUrl.append("/SMS/TemplateSMS?sig=");
		relativeUrl.append(calculateSigParameter(timestamp));

		try {
			return new URL(baseUrl, relativeUrl.toString());
		} catch (MalformedURLException e) {
			throw new YuntongxunException("在尝试构建 API 请求地址时遇到错误：该地址不是有效的 URL。", e);
		}
	}

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	private static String toHexString(byte[] bytes) {
		int len = bytes.length;
		StringBuilder out = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			out.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]).append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return out.toString();
	}

	private static String computeMd5Hash(String input) throws YuntongxunException {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new YuntongxunException("Error computing MD5 Hash: Cryptographic algorithm 'MD5' is unavailable.", e);
		}

		md5.update(input.getBytes(Charsets.UTF_8));
		return toHexString(md5.digest());
	}

	private static String getTimestampString(Date date) {
		final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ROOT);
		return dateFormat.format(date);
	}

	@Test
	public void main() throws Throwable {
		YuntongxunSmsProvider provider = new YuntongxunSmsProvider();
		provider.setAccountSid("8a48b55151906d030151951d54c6050a");
		provider.setAppId("8a48b55151906d030151955559fc055f");
		provider.setAuthToken("c7450e2c62554f8da505b29c42c03245");
		provider.setBaseUrl(new URL("https://sandboxapp.cloopen.com:8883/"));

		provider.webClient = new WebClientImpl();

		List<String> phoneNumbers = new ArrayList<>();
		phoneNumbers.add("18950051328");

		List<String> arguments = new ArrayList<>();
		arguments.add("2586");
		arguments.add("15");
		String id = provider.sendMessage(phoneNumbers, "1", arguments);

		System.out.println(id);
	}
}
