package eric.clapton.musician.program;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import eric.clapton.infrastructure.util.StringUtils;
import eric.clapton.musician.service.address.impl.DivisionImportHelper;
import eric.clapton.musician.service.address.impl.DivisionImportHelper.DivisionInfo;

@Component
public class DivisionInitProgram {
	public static void main(String[] args) throws Throwable {
		final String paths[] = { "classpath*:/.conf/applicationContext-without-shiro.xml" };

		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(paths)) {
			final DivisionImportHelper helper = context.getBean(DivisionImportHelper.class);

			final String path = ".data/QQ-LocList.xml";
			Document document = getLocListXmlDocument(path);

			Element root = document.getDocumentElement();
			NodeList countryRegionNodes = root.getElementsByTagName("CountryRegion");

			List<DivisionInfo> divisions = new ArrayList<DivisionInfo>();
			for (int i = 0, l = countryRegionNodes.getLength(); i < l; i++) {
				Element e = (Element) countryRegionNodes.item(i);

				DivisionInfo d = new DivisionInfo();
				d.name = e.getAttribute("Name");
				d.code = e.getAttribute("Code");
				d.level = 0;
				d.parent = null;
				d.displayOrder = (i + 1) * 10000;
				divisions.add(d);

				NodeList stateNodes = e.getElementsByTagName("State");
				for (int j = 0, lj = stateNodes.getLength(); j < lj; j++) {
					Element e2 = (Element) stateNodes.item(j);

					String name2 = e2.getAttribute("Name");
					if (StringUtils.isNullOrEmpty(name2)) {
						name2 = d.name;
					}
					String code2 = e2.getAttribute("Code");
					if (StringUtils.isNullOrEmpty(code2)) {
						code2 = d.code;
					}

					DivisionInfo d2 = new DivisionInfo();
					d2.name = name2;
					d2.code = d.code + "-" + code2;
					d2.level = 1;
					d2.parent = d;
					d2.displayOrder = d.displayOrder + (j + 1) * 1000;

					divisions.add(d2);

					NodeList cityNodes = e2.getElementsByTagName("City");
					for (int k = 0, lk = cityNodes.getLength(); k < lk; k++) {
						Element e3 = (Element) cityNodes.item(k);

						DivisionInfo d3 = new DivisionInfo();
						d3.name = e3.getAttribute("Name");
						d3.code = d2.code + "-" + e3.getAttribute("Code");
						d3.level = 2;
						d3.parent = d2;
						d3.displayOrder = d2.displayOrder + (k + 1) * 100;

						divisions.add(d3);

						NodeList regionNodes = e3.getElementsByTagName("Region");
						for (int m = 0, lm = regionNodes.getLength(); m < lm; m++) {
							Element e4 = (Element) regionNodes.item(m);

							DivisionInfo d4 = new DivisionInfo();
							d4.name = e4.getAttribute("Name");
							d4.code = d3.code + "-" + e4.getAttribute("Code");
							d4.level = 3;
							d4.parent = d3;
							d4.displayOrder = d3.displayOrder + (k + 1) * 10;

							divisions.add(d4);
						}
					}

				}
			}
			helper.importDivisions(divisions);
		}
	}

	private static Document getLocListXmlDocument(final String path) throws Throwable {
		ClassLoader classLoader = DivisionInitProgram.class.getClassLoader();
		try (InputStream in = classLoader.getResourceAsStream(path)) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(in);
		}
	}
}
