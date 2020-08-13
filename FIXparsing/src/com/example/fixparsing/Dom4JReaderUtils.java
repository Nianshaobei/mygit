package com.example.fixparsing;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import java.io.File;

class Dom4JReaderUtils {

    Dom4JReaderUtils(){}

    private static Logger logger = Logger.getLogger(Dom4JReaderUtils.class);

    static Document getDocument(String resource) throws SAXException {

        Document document = null;
        SAXReader reader = new SAXReader();
        reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        try {
            document = reader.read(new File(resource));

        } catch (DocumentException e) {
            logger.error("Read file failed.", e);
        }
        return document;
    }
}
