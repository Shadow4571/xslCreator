package xsl;

import main.FileLoader;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class XSLFactory {
    private Document XSDSchema;
    private Document XSLTransform;

    public XSLFactory(Document XSDSchema) {
        this.XSDSchema = XSDSchema;

        try {
            this.XSLTransform = FileLoader.CreateXSLDocument();
        } catch (Exception Exp) {
            this.XSLTransform = null;
            Exp.printStackTrace();
        }
    }

    private XSLElement FindRootElementForXSL(NodeList ElementsInRoot) {
        XSLElement Result = null;

        for(int i = 0; i < ElementsInRoot.getLength(); i++) {
            Node Temp = ElementsInRoot.item(i);

            if(Temp.getNodeName().equalsIgnoreCase("element")) {
                NamedNodeMap Attributes = Temp.getAttributes();

                Result = new XSLElement(Attributes.getNamedItem("name").getNodeValue(), true, true);
                Result.IsRootElement = true;
                Result.Type = Attributes.getNamedItem("type").getNodeValue().replace("self:", "").trim();
                break;
            }
        }

        return Result;
    }

    private ArrayList<XSLElement> FindComplexElementsForXSL(NodeList ElementsInRoot, String RootType) {
        ArrayList<XSLElement> Result = new ArrayList<>();

        for(int i = 0; i < ElementsInRoot.getLength(); i++) {
            Node Temp = ElementsInRoot.item(i);
            if(Temp.getNodeName().equalsIgnoreCase("complexType")) {
                if(!Temp.getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase(RootType)) {
                    XSLElement InsertElement = new XSLElement("NULL", false, true);
                    InsertElement.Type = Temp.getAttributes().getNamedItem("name").getNodeValue();

                    NodeList InnerSequence = Temp.getChildNodes().item(1).getChildNodes();
                    for(int j = 0; j < InnerSequence.getLength(); j++) {
                        if(InnerSequence.item(j).getNodeType() != Node.TEXT_NODE) {
                            NamedNodeMap InnerAttributes = InnerSequence.item(j).getAttributes();
                            boolean IsImportant = true;

                            int Position = this.FindTypeInComplex(Result, InnerAttributes.getNamedItem("type").getNodeValue().replace("self:", ""));

                            if(Position == -1) {
                                if (InnerAttributes.getNamedItem("minOccurs") != null)
                                    IsImportant = !InnerAttributes.getNamedItem("minOccurs").getNodeValue().equalsIgnoreCase("0");

                                InsertElement.AddElementToComplex(InnerAttributes.getNamedItem("name").getNodeValue(), IsImportant, false);
                            } else {
                                XSLElement Insert = Result.get(Position);
                                Insert.Name = InnerAttributes.getNamedItem("name").getNodeValue();
                                InsertElement.AddElementToComplex(Insert);
                            }
                        }
                    }

                    Result.add(InsertElement);
                }
            }
        }

        return Result;
    }

    private void FinalizeElementsForXSL(NodeList ElementsInRoot, XSLElement RootXSL, ArrayList<XSLElement> ComplexXSL) {
        ArrayList<XSLElement> InnerRoot = new ArrayList<>();
        XSLElement FinalRootXSL = null;
        NodeList XSLSequence = null;
        Node FindSequence = null;

        for(int i = 0; i < ElementsInRoot.getLength(); i++) {
            if(ElementsInRoot.item(i).getNodeType() != Node.TEXT_NODE && ElementsInRoot.item(i).getNodeName().equalsIgnoreCase("complexType")) {
                if (ElementsInRoot.item(i).getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase(RootXSL.Type)) {
                    FindSequence = ElementsInRoot.item(i);
                    break;
                }
            }
        }

        XSLSequence = FindSequence.getChildNodes().item(3).getChildNodes().item(1).getChildNodes().item(1).getChildNodes();

        for(int i = 0; i < XSLSequence.getLength(); i++) {
            if(XSLSequence.item(i).getNodeType() != Node.TEXT_NODE) {
                String NodeName = XSLSequence.item(i).getAttributes().getNamedItem("name").getNodeValue();
                String NodeType = XSLSequence.item(i).getAttributes().getNamedItem("type").getNodeValue().replace("app:", "").replace("en:", "").replace("self:", "").trim();
                boolean IsImportant = true;

                if(XSLSequence.item(i).getAttributes().getNamedItem("minOccurs") != null) {
                    IsImportant = !XSLSequence.item(i).getAttributes().getNamedItem("minOccurs").getNodeValue().equalsIgnoreCase("0");
                }

                int Position = this.FindTypeInComplex(ComplexXSL, NodeType);

                if(Position == -1) {
                    InnerRoot.add(new XSLElement(NodeName, IsImportant, false));
                    InnerRoot.get(InnerRoot.size() - 1).Type = NodeType;
                } else {
                    XSLElement Temp = ComplexXSL.get(Position);
                    Temp.Name = NodeName;
                    InnerRoot.add(Temp);
                }
            }
        }

        RootXSL.SetComplex(InnerRoot);
    }

    private int FindTypeInComplex(ArrayList<XSLElement> ComplexXSL, String Type) {
        for(int i = 0; i < ComplexXSL.size(); i++)
            if(ComplexXSL.get(i).Type.equalsIgnoreCase(Type) && ComplexXSL.get(i).Name.equalsIgnoreCase("NULL"))
                return i;

        return -1;
    }

    public void BuildXSLDocument() {
        Node Root = this.XSDSchema.getDocumentElement();

        NodeList AllElements = Root.getChildNodes();

        XSLElement RootXSL = this.FindRootElementForXSL(AllElements);
        ArrayList<XSLElement> ComplexXSL = this.FindComplexElementsForXSL(AllElements, RootXSL.Type);

        this.FinalizeElementsForXSL(AllElements, RootXSL, ComplexXSL);

        //System.out.println(RootXSL);

        try {
            BufferedWriter WriteFile = new BufferedWriter(new FileWriter("C:\\Users\\nmyznikov\\Documents\\ideaprojects\\xslCreator\\src\\main\\resources\\output.txt", true));
            WriteFile.append(RootXSL.toString());
            WriteFile.close();
        } catch (Exception Exp) {
            Exp.printStackTrace();
        }
    }
}
