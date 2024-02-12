package com.example.manwon;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WebService {

    @Value("${shoppingDay}")
    private String goodInspectDay;
    @Value("${shopCode}")
    private String entpId;
    @Value("${serviceKey}")
    private String serviceKey;

//    private final WebClient productPriceWebClient;
    private Map<Integer, String> CallNameApi() {
        String BASE_URL = "http://openapi.price.go.kr/openApiImpl/ProductPriceInfoService/getProductInfoSvc.do?serviceKey=L287ZhVphoAlHm6Qs1C7f3H%2FpwT6%2BYlr4TK7t7AxhoMKsUtThnlwPHpnnYfbpwLomNF6wxNm%2FQh0N8EoRH4Rpw%3D%3D";
        Map<Integer, String> map = new HashMap<>();
        try {
            Document documentInfo = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(BASE_URL);
            documentInfo.getDocumentElement().normalize();
            Element root = documentInfo.getDocumentElement();
            NodeList nNodes = root.getElementsByTagName("result").item(0).getChildNodes();

            for (int i = 1; i < nNodes.getLength(); i += 2) {
                Node nNode = nNodes.item(i);
                Element item = (Element) nNode;
                int id = Integer.parseInt(getTagValue("goodId", item));
                String name = getTagValue("goodName", item);
                System.out.println(name);
                map.put(id, name);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return map;
    }

    public void CallPriceApi() {
        Map<Integer, String> productNames = CallNameApi();
        String BASE_URL = "http://openapi.price.go.kr/openApiImpl/ProductPriceInfoService/getProductPriceInfoSvc.do?entpId=46&serviceKey=L287ZhVphoAlHm6Qs1C7f3H%2FpwT6%2BYlr4TK7t7AxhoMKsUtThnlwPHpnnYfbpwLomNF6wxNm%2FQh0N8EoRH4Rpw%3D%3D&goodInspectDay=20231222";
        try {
            Document documentInfo = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(BASE_URL);
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
                product.name = productNames.get(id);

                list.add(product);
            }


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
