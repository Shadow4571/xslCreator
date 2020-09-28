package main;

import xsd.XSDFactory;
import xsl.XSLFactory;

public class Main {
    public static void main(String[] args) {
        XSDFactory OutputSchema = new XSDFactory("C:\\Users\\nmyznikov\\Documents\\ideaprojects\\xslCreator\\src\\main\\resources\\XSDTest", false);
        //XSDFactory InputSchema = new XSDFactory("C:\\Users\\nmyznikov\\Documents\\ideaprojects\\xslCreator\\src\\main\\resources\\XSDInput", true);

        XSLFactory Factory = new XSLFactory(OutputSchema, null);
        Factory.BuildXSLDocument();
    }
}
