package xsd;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import xsl.XSLElement;

public interface IXSDType {
    /* ==== VARIABLES ==== */

    public Document AppliedTypesXSD = null;
    public Document BaseTypesXSD = null;
    public Document CommonXSD = null;
    public Document EntitiesXSD = null;
    public Document FormularsXSD = null;

    public NodeList FormularsRootNodeList = null;
    public String FormularsXSDName = null;
    public String FormularsXSDType = null;

    /* ====         ==== */
    /* ==== METHODS ==== */

    //
    public String GetFullName(String TypePrefix, String PartOfName);

    public NodeList ElementsInXSDType(String TypePrefix);

    public Element GetRootFromDocumentByPrefix(String Prefix);
    public XSLElement GetXSLElementIfHasType(NodeList ElementsInRoot, String TypePrefix, String Name);
    public XSLElement FindTypeByPrefix(String Prefix, String TypePrefix, String Name);
}
