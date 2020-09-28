package xsd.types;

import javafx.util.Pair;
import main.FileLoader;
import org.w3c.dom.*;
import xsl.XSLAttribute;
import xsl.XSLElement;

import java.util.ArrayList;

public class OutputXSD extends XSDType {
    public Document AppliedTypesXSD;
    public Document BaseTypesXSD;
    public Document CommonXSD;
    public Document EntitiesXSD;
    public Document FormularsXSD;

    private Node FormularsRootNode;

    private String FormularsXSDName;
    private String FormularsXSDType;

    public OutputXSD(String Directory) {
        this(Directory, new String[] {"\\appliedTypes.xsd", "\\baseTypes.xsd", "\\common.xsd", "\\entities.xsd", "\\formulars.xsd"});
    }

    public OutputXSD(String Directory, String[] FilesNames) {
        try {
            this.AppliedTypesXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[0]);
            this.BaseTypesXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[1]);
            this.CommonXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[2]);
            this.EntitiesXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[3]);
            this.FormularsXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[4]);
        } catch (Exception Exp) {
            Exp.printStackTrace();
        }

        this.FindXSDRootNameAndType();
    }

    @Override
    public void FindXSDRootNameAndType() {
        for(int i = 0; i < this.FormularsXSD.getDocumentElement().getChildNodes().getLength(); i++) {
            Node Temp = this.FormularsXSD.getDocumentElement().getChildNodes().item(i);

            if(Temp != null) {
                if(Temp.getNodeType() != Node.TEXT_NODE) {
                    if(Temp.getNodeName().equalsIgnoreCase("element")) {
                        this.FormularsXSDName = Temp.getAttributes().getNamedItem("name").getNodeValue();
                        this.FormularsXSDType = Temp.getAttributes().getNamedItem("type").getNodeValue().replace("self:", "").trim();
                    }

                    if(Temp.getNodeName().equalsIgnoreCase("complexType")) {
                        if(this.FormularsXSDType != null) {
                            if(Temp.getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase(this.FormularsXSDType)) {
                                this.FormularsRootNode = Temp;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String GetRootName() { return this.FormularsXSDName; }

    @Override
    public String GetRootType() { return this.FormularsXSDType; }

    @Override
    public boolean CheckNodeIsCorrect(Node Check) {
        if(Check != null) {
            return Check.getNodeType() != Node.TEXT_NODE;
        }

        return false;
    }

    @Override
    public Node GetRootFromFormular() {
        return this.FormularsRootNode;
    }

    @Override
    public NodeList GetRootContent() {
        NodeList ElementsInRoot = this.FormularsRootNode.getChildNodes();

        for(int i = 0; i < ElementsInRoot.getLength(); i++) {
            Node Temp = ElementsInRoot.item(i);

            if(this.CheckNodeIsCorrect(Temp)) {
                if(Temp.getNodeName().equalsIgnoreCase("complexContent")) {
                    return Temp.getChildNodes().item(1).getChildNodes().item(1).getChildNodes();
                }
            }
        }

        return null;
    }

    @Override
    public XSLElement GetComplexTypeContent(String TypePrefix, String Type) {
        if(TypePrefix.equalsIgnoreCase("app")) {
            return this.FindElementInNodeList(this.AppliedTypesXSD.getDocumentElement().getChildNodes(), Type);
        }

        if(TypePrefix.equalsIgnoreCase("self")) {
            return this.FindElementInNodeList(this.FormularsXSD.getDocumentElement().getChildNodes(), Type);
        }

        return null;
    }

    @Override
    public XSLElement FindElementInNodeList(NodeList ElementsInRoot, String Type) {
        XSLElement Result = null;

        for(int i = 0; i < ElementsInRoot.getLength(); i++) {
            Node Temp = ElementsInRoot.item(i);

            if(this.CheckNodeIsCorrect(Temp)) {
                if(Temp.getNodeName().equalsIgnoreCase("complexType")) {
                    if (Temp.getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase(Type)) {
                        Result = new XSLElement(null, true, true);

                        NodeList InnerTemp = Temp.getChildNodes();
                        for(int j = 0; j < InnerTemp.getLength(); j++) {
                            Node InnerElement = InnerTemp.item(j);

                            if(this.CheckNodeIsCorrect(InnerElement)) {
                                if(InnerElement.getNodeName().equalsIgnoreCase("sequence")) {
                                    NodeList Sequence = InnerElement.getChildNodes();

                                    for(int k = 0; k < Sequence.getLength(); k++) {
                                        Node SequenceTemp = Sequence.item(k);

                                        if(this.CheckNodeIsCorrect(SequenceTemp)) {
                                            Pair<String, String> TypeAndPrefix = this.GetPrefixAndType(SequenceTemp.getAttributes().getNamedItem("type").getNodeValue());
                                            XSLElement SequenceElement = new XSLElement(SequenceTemp.getAttributes().getNamedItem("name").getNodeValue(), true, false);
                                            SequenceElement.TypePrefix = TypeAndPrefix.getKey();
                                            SequenceElement.Type = TypeAndPrefix.getValue();

                                            SequenceElement.IsImportant = SequenceTemp.getAttributes().getNamedItem("minOccurs") != null ? !SequenceTemp.getAttributes().getNamedItem("minOccurs").getNodeValue().equalsIgnoreCase("0") : true;
                                            XSLElement FindSequenceElement = this.GetComplexTypeContent(SequenceElement.TypePrefix, SequenceElement.Type);

                                            if(FindSequenceElement != null) {
                                                SequenceElement.IsComplexType = FindSequenceElement.IsComplexType;
                                                SequenceElement.SetComplex(FindSequenceElement.GetElementsFromComplex());
                                            }

                                            Result.AddElementToComplex(SequenceElement);
                                        }
                                    }
                                }

                                if(InnerElement.getNodeName().equalsIgnoreCase("attribute")) {
                                    Result.AddElementAttribute(InnerElement.getAttributes().getNamedItem("name").getNodeValue(), true);
                                }
                            }
                        }
                    }
                }
            }
        }

        return Result;
    }

    @Override
    public ArrayList<XSLElement> CreateAllRootElements() {
        ArrayList<XSLElement> Result = new ArrayList<>();
        NodeList ElementsInRoot = this.GetRootContent();

        for(int i = 0; i < ElementsInRoot.getLength(); i++) {
            Node Temp = ElementsInRoot.item(i);

            if(this.CheckNodeIsCorrect(Temp)) {
                XSLElement NewElement = new XSLElement(Temp.getAttributes().getNamedItem("name").getNodeValue(), true, false);
                String RawType = Temp.getAttributes().getNamedItem("type").getNodeValue();
                NewElement.TypePrefix = RawType.substring(0, RawType.indexOf(':'));
                NewElement.Type = RawType.substring(RawType.indexOf(':') + 1, RawType.length());

                Result.add(NewElement);
            }
        }

        return Result;
    }
}
