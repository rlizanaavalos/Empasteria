package controlador;

public class BeanImpresion {


    private String DisplayText;
    private String Value;
 
    public BeanImpresion(String DisplayText, String Value) {
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
  		return "tipo_impresion[id=" + Value + ", nombre=" + DisplayText + "]";
  	}
    
}