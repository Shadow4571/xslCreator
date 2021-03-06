package xsl;

public class XSLAttribute {
    public String Name;
    public String ParseName;
    public String Value;

    public boolean IsImportant;

    public XSLAttribute(String Name, boolean IsImportant) {
        this.Name = Name;
        this.IsImportant = IsImportant;
    }

    @Override
    public String toString() {
        return "Name: " + this.Name + " Parse Name: " + this.ParseName  +" | Value: " + this.Value + " ( Important: " + this.IsImportant + " )\n";
    }
}
