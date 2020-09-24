package xsd;

import main.FileLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xsl.XSLElement;

public class XSDFactory {
    public Document AppliedTypesXSD;
    public Document BaseTypesXSD;
    public Document CommonXSD;
    public Document EntitiesXSD;
    public Document FormularsXSD;
    public Document CoreSchemaXSD;

    public XSDFactory(String Directory) {
        this(Directory, new String[] {"\\appliedTypes.xsd", "\\baseTypes.xsd", "\\common.xsd", "\\entities.xsd", "\\formulars.xsd", "\\xmldsig-core-schema.xsd"});
    }

    public XSDFactory(String Directory, String[] FilesNames) {
        try {
            this.AppliedTypesXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[0]);
            this.BaseTypesXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[1]);
            this.CommonXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[2]);
            this.EntitiesXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[3]);
            this.FormularsXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[4]);
            this.CoreSchemaXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[5]);
        } catch (Exception Exp) {
            Exp.printStackTrace();
        }
    }

    public XSLElement GetXSLElementIfHasType(NodeList ElementsInRoot, String Type, String Name) {
        for(int i = 0; i < ElementsInRoot.getLength(); i++) {
            Node Temp = ElementsInRoot.item(i);

            if(Temp.getNodeType() != Node.TEXT_NODE) {
                if(Temp.getNodeName().equalsIgnoreCase("simpleType") || Temp.getNodeName().equalsIgnoreCase("complexType")) {
                    if(Temp.getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase(Type)) {
                        XSLElement Result = new XSLElement(Name);
                        Result.Type = Type;
                        if(Temp.getNodeName().equalsIgnoreCase("complexType")) {
                            NodeList ResultInner = Temp.getChildNodes();
                            for(int j = 0; j < ResultInner.getLength(); j++) {
                                Node InnerNode = ResultInner.item(j);

                                if(InnerNode != null)
                                    if(InnerNode.getNodeType() != Node.TEXT_NODE)
                                        if(InnerNode.getNodeName().equalsIgnoreCase("attribute"))
                                            Result.AddElementAttribute(InnerNode.getAttributes().getNamedItem("name").getNodeValue(), InnerNode.getAttributes().getNamedItem("minOcurus") == null);
                            }
                        }

                        return Result;
                    }
                }
            }
        }

        return null;
    }

    public XSLElement FindTypeByPrefix(String Prefix, String Type, String Name) {
        XSLElement Result = null;

        if(Prefix.equalsIgnoreCase("app") || Prefix.equalsIgnoreCase("app:"))
            Result = this.FindTypeInAppliedTypes(Type, Name);

        if(Prefix.equalsIgnoreCase("base") || Prefix.equalsIgnoreCase("base:"))
            Result = this.FindTypeInBaseTypes(Type, Name);

        return Result;
    }

    public XSLElement FindTypeInAppliedTypes(String Type, String Name) {
        NodeList ElementsInRoot = this.AppliedTypesXSD.getDocumentElement().getChildNodes();
        return this.GetXSLElementIfHasType(ElementsInRoot, Type, Name);
    }

    public XSLElement FindTypeInBaseTypes(String Type, String Name) {
        NodeList ElementsInRoot = this.BaseTypesXSD.getDocumentElement().getChildNodes();
        return this.GetXSLElementIfHasType(ElementsInRoot, Type, Name);
    }
}
