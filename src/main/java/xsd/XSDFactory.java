package xsd;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xsd.types.IXSDType;
import xsd.types.InputXSD;
import xsd.types.OutputXSD;
import xsl.XSLElement;

import java.util.ArrayList;

public class XSDFactory {
    private IXSDType XSDFiles;
    public boolean IsInputXSD;

    public XSDFactory(String Directory, boolean IsInputXSD) {
        this(Directory, new String[] {"\\appliedTypes.xsd", "\\baseTypes.xsd", "\\common.xsd", "\\entities.xsd", "\\formulars.xsd"}, IsInputXSD);
    }

    public XSDFactory(String Directory, String[] FilesNames, boolean IsInputXSD) {
        if(IsInputXSD) {
            this.XSDFiles = new InputXSD(Directory, FilesNames);
        } else {
            this.XSDFiles = new OutputXSD(Directory, FilesNames);
        }
    }

    public String GetRootName() { return this.XSDFiles.GetRootName(); }

    public String GetRootType() { return this.XSDFiles.GetRootType(); }

    public Node GetRootFromFormular() {
        return this.XSDFiles.GetRootFromFormular();
    }

    public XSLElement GetComplexTypeContent(String TypePrefix, String Type) { return this.XSDFiles.GetComplexTypeContent(TypePrefix, Type); }

    public ArrayList<XSLElement> CreateAllRootElements() { return this.XSDFiles.CreateAllRootElements(); }
}
