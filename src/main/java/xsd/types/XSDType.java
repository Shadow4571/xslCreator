package xsd.types;

import javafx.util.Pair;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xsl.XSLElement;

import java.util.ArrayList;

/**
 * TODO: comment
 *
 * @author nmyznikov 28.09.2020   17:14
 */
public class XSDType implements IXSDType {
	public Pair<String, String> GetPrefixAndType(String Type) {
		if(Type.indexOf(':') == -1)
			return null;

		Pair<String, String> Result = new Pair<>(Type.substring(0, Type.indexOf(':')), Type.substring(Type.indexOf(':') + 1, Type.length()));
		return Result;
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
