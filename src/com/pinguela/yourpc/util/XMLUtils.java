package com.pinguela.yourpc.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLUtils {
	
	private static Logger logger = LogManager.getLogger(XMLUtils.class);
	
	private static final DocumentBuilderFactory DOCUMENT_FACTORY = DocumentBuilderFactory.newInstance();
	private static final XPathFactory XPATH_FACTORY = XPathFactory.newInstance();
	
	private static final Map<String, Document> DOCUMENTS = new HashMap<>();
	
	public static final Document getXMLResource(String resourcePath) {
		return DOCUMENTS.computeIfAbsent(resourcePath, (key) -> {
			return parseDocument(resourcePath);
		});
	}
	
	private static final Document parseDocument(String resourcePath) {
		try {
			DocumentBuilder docBuilder = DOCUMENT_FACTORY.newDocumentBuilder();
			Document newDocument = docBuilder.parse(XMLUtils.class.getClassLoader().getResourceAsStream("sql_mapping.xml"));
			newDocument.getDocumentElement().normalize();
			return newDocument;
		} catch (ParserConfigurationException | IOException | SAXException e) {
			logger.fatal(e.getMessage(), e);
			throw new IllegalArgumentException(String.format("Cannot find document %s in classpath.", resourcePath));
		}
	}
	
	public static final Node getNode(Node parent, String xPathStr) {
		return (Node) get(parent, xPathStr, XPathConstants.NODE);
	}
	
	public static final NodeList getNodeList(Node parent, String xPathStr) {
		return (NodeList) get(parent, xPathStr, XPathConstants.NODESET);
	}
	
	public static final String getTextNode(Node parent, String xPathStr) {
		return (String) get(parent, xPathStr, XPathConstants.STRING);
	}
	
	private static final Object get(Node parent, String xPathStr, QName returnType) {
		XPath xPath = XPATH_FACTORY.newXPath();
		try {
			return xPath.compile(xPathStr).evaluate(parent, returnType);
		} catch (XPathExpressionException e) {
			logger.error("An error occured while evaluating the following XPath: {}", xPathStr);
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

}
