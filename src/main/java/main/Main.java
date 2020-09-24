package main;

import xsd.XSDFactory;
import xsl.XSLFactory;

public class Main {
    public static void main(String[] args) {
        XSDFactory SchemaFactory = new XSDFactory("C:\\Users\\nmyznikov\\Documents\\ideaprojects\\xslCreator\\src\\main\\resources\\XSD");
        XSLFactory Factory = new XSLFactory(SchemaFactory);
        Factory.BuildXSLDocument();
    }
}
