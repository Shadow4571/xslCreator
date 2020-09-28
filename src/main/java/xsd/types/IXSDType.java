package xsd.types;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xsl.XSLElement;

import java.util.ArrayList;

public interface IXSDType {
    /* ==== VARIABLES ==== */



    /* ====         ==== */
    /* ==== METHODS ==== */

    //

    public void FindXSDRootNameAndType();

    public String GetRootName();
    public String GetRootType();

    public boolean CheckNodeIsCorrect(Node Check);

    public Node GetRootFromFormular();
    public NodeList GetRootContent();

    public XSLElement GetComplexTypeContent(String TypePrefix, String Type);

    public XSLElement FindElementInNodeList(NodeList ElementsInRoot, String Type);

    public ArrayList<XSLElement> CreateAllRootElements();
}
