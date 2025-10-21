package com.ohgiraffers.jdbc.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class QueryUtil {
    private static final Map<String, String> queries = new HashMap();

    static {
        System.out.println("✅here");
        loadQueries();
        System.out.println("✅here");
    }

    /**
     * queries.xml에서
     */
    private static void loadQueries() {
        try {
            InputStream is = QueryUtil.class.getClassLoader().getResourceAsStream("queries.xml");

            if (is == null) {
                throw new RuntimeException("queries.xml not found");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(is);

            NodeList nodeList = document.getElementsByTagName("query");

            for(int i = 0; i < nodeList.getLength(); i++){
                Element queryElement = (Element) nodeList.item(i);

                String key = queryElement.getAttribute("key");
                String sql = queryElement.getAttribute("value");
                System.out.println(key + ": " + sql);

                queries.put(key, sql);
            }
        } catch (Exception e) {
            throw new RuntimeException("쿼리 로딩 중 오류 발생", e);
        }
    }

    public static String getQuery(String key) {
        return queries.get(key);
    }
}
