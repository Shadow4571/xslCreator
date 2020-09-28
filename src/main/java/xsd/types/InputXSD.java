package xsd.types;

import main.FileLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xsl.XSLElement;

import java.util.ArrayList;

public class InputXSD extends XSDType {
    private Document AppliedTypesXSD = null;
    private Document BaseTypesXSD = null;
    private Document CommonXSD = null;
    private Document EntitiesXSD = null;
    private Document FormularsXSD = null;

    public NodeList FormularsRootNodeList = null;
    public String FormularsXSDName = null;
    public String FormularsXSDType = null;

    public InputXSD(String Directory) {
        this(Directory, new String[] {"\\appliedTypes.xsd", "\\baseTypes.xsd", "\\common.xsd", "\\entities.xsd", "\\formulars.xsd"});
    }

    public InputXSD(String Directory, String[] FilesNames) {
        try {
            this.AppliedTypesXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[0]);
            this.BaseTypesXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[1]);
            this.CommonXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[2]);
            this.EntitiesXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[3]);
            this.FormularsXSD = FileLoader.LoadXSDorXMLDocument(Directory + FilesNames[4]);
        } catch (Exception Exp) {
            Exp.printStackTrace();
        }

        this.FormularsRootNodeList = FormularsXSD.getDocumentElement().getChildNodes();
        this.FindXSDRootNameAndType();
    }

    @Override
    public void FindXSDRootNameAndType() {

    }

    @Override
    public String GetRootName() {
        return null;
    }

    @Override
    public String GetRootType() {
        return null;
    }

    @Override
    public boolean CheckNodeIsCorrect(Node Check) {
        return false;
    }

    @Override
    public Node GetRootFromFormular() {
        return null;
    }

    @Override
    public NodeList GetRootContent() {
        return null;
    }

    @Override
    public XSLElement GetComplexTypeContent(String TypePrefix, String Type) {
        return null;
    }

    @Override
    public XSLElement FindElementInNodeList(NodeList ElementsInRoot, String Type) {
        return null;
    }

    @Override
    public ArrayList<XSLElement> CreateAllRootElements() {
        return null;
    }
}
