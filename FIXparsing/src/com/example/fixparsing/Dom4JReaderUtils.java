package com.example.fixparsing;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;

class Dom4JReaderUtils {

    private static Logger logger = Logger.getLogger(Dom4JReaderUtils.class);

    static Document getDocument(String resource) {

        Document document = null;
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(new File(resource));

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            logger.error("Read file failed.", e);
        }
        return document;
    }
}
