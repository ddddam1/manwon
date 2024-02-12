package com.example.manwon;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ManwonApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void GetProduct() {
		ArrayList<Product> arr = new ArrayList<>();

		Product product = new Product();
		product.id = 46;
		product.price = 1110;
		product.name = "서울우유 흰우유(1L)";

		arr.add(product);
	}

	@Test
	void CallNameAPI() {

		try {
			// Given
//			System.out.println(new File(".").getAbsolutePath());
			FileInputStream fileInputStream = new FileInputStream("src/test/java/test-data/getNameAPITest.xml");
			Document documentInfo = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileInputStream);
			documentInfo.getDocumentElement().normalize();
			Element root = documentInfo.getDocumentElement();
			NodeList nNodes = root.getElementsByTagName("result").item(0).getChildNodes();

			Map<Integer, String> map = new HashMap<>();
			for (int i = 1; i < nNodes.getLength(); i += 2) {
				Node nNode = nNodes.item(i);
				Element item = (Element) nNode;
				int id = Integer.parseInt(getTagValue("goodId", item));
				String name = getTagValue("goodName", item);
				map.put(id, name);
			}

			fileInputStream.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Test
	void CallPriceAPI() {

		try {
			// Given
//			System.out.println(new File(".").getAbsolutePath());
			FileInputStream fileInputStream = new FileInputStream("src/test/java/test-data/getPriceAPITest.xml");
			Document documentInfo = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileInputStream);
			documentInfo.getDocumentElement().normalize();
			Element root = documentInfo.getDocumentElement();
			NodeList nNodes = root.getElementsByTagName("result").item(0).getChildNodes();

			List<Product> list = new ArrayList<>();
			for (int i = 1; i < nNodes.getLength(); i += 2) {
				Product product = new Product();
				Node nNode = nNodes.item(i);
				Element item = (Element) nNode;

				int price = Integer.parseInt(getTagValue("goodPrice", item));
				int id = Integer.parseInt(getTagValue("goodId", item));

				product.id = id;
				product.price = price;
				list.add(product);
			}

			fileInputStream.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private static String getTagValue(String tag, Element eElement) {
		NodeList nList = null;
		Node nValue = null;
		try {
			nList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
			nValue = (Node) nList.item(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}

}
