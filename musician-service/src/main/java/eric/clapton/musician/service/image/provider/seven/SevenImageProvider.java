package eric.clapton.musician.service.image.provider.seven;

import com.qiniu.util.Auth;

public class SevenImageProvider {
	private String accessKey;
	private String secretKey;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String token() {
		// final Date now = new Date();
		// final String timestamp = getTimestampString(now);
		//
		// // step 1: build request url
		// URL requestUrl = buildRequestUrl(timestamp);
		// // step 2: build request headers
		// Map<String, String> headers = buildRequestHeaders(timestamp);
		// // step 3: build request body
		// String requestBody = buildRequestBody(phoneNumbers,
		// messageTemplateId, args);
		// // step 4: make request
		// String responseText;
		// try {
		// responseText = webClient.uploadString(requestUrl.toString(),
		// requestBody, "application/xml", headers);
		// } catch (WebException e) {
		// throw new YuntongxunException("在调用 API 时遇到错误。", e);
		// }
		// // step 5: parse response
		// String id = parseResponse(responseText);
		//
		// return id;
		Auth auth = Auth.create(accessKey, secretKey);
		return auth.uploadToken("bucket");
	}

}
