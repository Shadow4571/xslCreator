package xsl;

import java.util.ArrayList;

public class XSLElement {
    public String Name;
    public String ParseName;
    public String Type;

    public boolean IsImportant;
    public boolean IsComplexType;
    public boolean IsRootElement;

    private ArrayList<XSLElement> ComplexElements;
    private ArrayList<XSLAttribute> ElementAttributes;

    public XSLElement(String Name) {
        this(Name, false, false);
    }

    public XSLElement(String Name, boolean IsImportant) {
        this(Name, IsImportant, false);
    }

    public XSLElement(String Name, boolean IsImportant, boolean IsComplexType) {
        this(Name, IsImportant, IsComplexType, new ArrayList<XSLAttribute>());
    }

    public XSLElement(String Name, boolean IsImportant, boolean IsComplexType, ArrayList<XSLAttribute> ElementAttributes) {
        this(Name, IsImportant, IsComplexType, new ArrayList<XSLElement>(), ElementAttributes);
    }

    public XSLElement(String Name, boolean IsImportant, boolean IsComplexType, ArrayList<XSLElement> ComplexElements, ArrayList<XSLAttribute> ElementAttributes) {
        this.Name = Name;
        this.IsImportant = IsImportant;
        this.IsComplexType = IsComplexType;
        this.IsRootElement = false;
        this.ComplexElements = ComplexElements;
        this.ElementAttributes = ElementAttributes;
    }

    public void AddElementToComplex(XSLElement Insert) { this.ComplexElements.add(Insert); }

    public void AddElementToComplex(String Name) {
        this.AddElementToComplex(Name, false);
    }

    public void AddElementToComplex(String Name, boolean IsImportant) {
        this.AddElementToComplex(Name, IsImportant, false);
    }

    public void AddElementToComplex(String Name, boolean IsImportant, boolean IsComplex) {
        this.ComplexElements.add(new XSLElement(Name, IsImportant, IsComplex));
    }

    public void SetComplex(ArrayList<XSLElement> Complex) {
        this.ComplexElements = Complex;
    }

    public void AddElementAttribute(String Name) {
        this.AddElementAttribute(Name, false);
    }

    public void AddElementAttribute(String Name, boolean IsImportant) {
        this.ElementAttributes.add(new XSLAttribute(Name, IsImportant));
    }

    public ArrayList<XSLElement> GetElementsFromComplex() {
        return this.ComplexElements;
    }

    public ArrayList<XSLAttribute> GetAttributes() { return this.ElementAttributes; }
    public void SetAttributes(ArrayList<XSLAttribute> ElementAttributes) { this.ElementAttributes = ElementAttributes; }

    @Override
    public String toString() {
        StringBuilder Message = new StringBuilder();
        Message.append("Element: " + this.Name + " | Parse In: " + this.ParseName + " | Type: " + this.Type + "\n" +
                "Important: " + this.IsImportant + " Complex: " + this.IsComplexType + " Root: " + this.IsRootElement + "\n");

        if(!this.ElementAttributes.isEmpty()) {
            Message.append("    ATTRIBUTES:\n");
            for(int i = 0; i < this.ElementAttributes.size(); i++) {
                Message.append("    " + this.ElementAttributes.get(i).toString());
            }
        }

        if(!this.ComplexElements.isEmpty()) {
            Message.append("    ==== INNER ELEMENTS IN " + this.Name + " ====\n");
            for(int i = 0; i < this.ComplexElements.size(); i++) {
                Message.append(this.ComplexElements.get(i).toString() + "-    -    -    -\n");
            }
            Message.append("    ==== INNER ELEMENTS END " + this.Name + " ====\n\n");
        }

        return  Message.toString();
    }
}
