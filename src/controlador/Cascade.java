package controlador;

public class Cascade {
    private String DisplayText;
    private String Value;
 
    public Cascade(String DisplayText, String Value) {
        this.DisplayText = DisplayText;
        this.Value = Value;
    }
 
    public String getDisplayText() {
        return DisplayText;
    }
 
    public void setDisplayText(String DisplayText) {
        this.DisplayText = DisplayText;
    }
 
   
    public String getValue() {
        return Value;
    }
 
    public void setValue(String Value) {
        this.Value = Value;
    }
    @Override
  	public String toString() {
  		return "region [id=" + Value + ", nombre=" + DisplayText + "]";
  	}
    
}