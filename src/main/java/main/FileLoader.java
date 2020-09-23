package main;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public final class FileLoader {
    public static Document LoadXSDorXMLDocument(String Directory) throws Exception {
        DocumentBuilder XMLBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document XMLFile = XMLBuilder.parse(Directory);

        return  XMLFile;
    }

    public static Document CreateXSLDocument() throws Exception {
        DocumentBuilder XSLBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document XSLFile = XSLBuilder.newDocument();

        return XSLFile;
    }
}
