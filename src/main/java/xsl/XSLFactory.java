package xsl;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xsd.XSDFactory;

import java.util.ArrayList;


public class XSLFactory {
    private XSDFactory OutputSchema;
    private XSDFactory InputSchema;

    private XSLElement RootXSL;

    public XSLFactory(XSDFactory OutputSchema, XSDFactory InputSchema) {
        this.OutputSchema = OutputSchema;
        this.InputSchema = InputSchema;

        this.RootXSL = new XSLElement(OutputSchema.GetRootName(), true, true);
        this.RootXSL.Type = OutputSchema.GetRootType();
        this.RootXSL.IsRootElement = true;
    }

    public void BuildXSLDocument() {

        ArrayList<XSLElement> AllElements = this.OutputSchema.CreateAllRootElements();
        for(int i = 0; i < AllElements.size(); i++) {
            XSLElement Temp = AllElements.get(i);
            XSLElement Find = this.OutputSchema.GetComplexTypeContent(Temp.TypePrefix, Temp.Type);

            if(Find != null) {
                Temp.IsImportant = Find.IsImportant;
                Temp.IsComplexType = Find.IsComplexType;
                Temp.SetComplex(Find.GetElementsFromComplex());
                Temp.SetAttributes(Find.GetAttributes());
            }

            System.out.println(Temp.toString());
        }

        //System.out.println(RootXSL);

        try {
            //BufferedWriter WriteFile = new BufferedWriter(new FileWriter("C:\\Users\\nmyznikov\\Documents\\ideaprojects\\xslCreator\\src\\main\\resources\\output.txt", true));
            //WriteFile.append(RootXSL.toString());
            //WriteFile.close();
        } catch (Exception Exp) {
            Exp.printStackTrace();
        }
    }
}
