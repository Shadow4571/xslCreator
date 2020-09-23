package main;

import org.w3c.dom.Document;
import xsl.XSLFactory;

public class Main {
    public static void main(String[] args) {
        Document XSDSchema = null;
        XSLFactory Factory = null;

        try {
            XSDSchema = FileLoader.LoadXSDorXMLDocument("C:\\Users\\nmyznikov\\Documents\\ideaprojects\\xslCreator\\src\\main\\resources\\input.xsd");
        } catch (Exception Exp) {
            Exp.printStackTrace();
        }

        Factory = new XSLFactory(XSDSchema);
        Factory.BuildXSLDocument();
    }
}
