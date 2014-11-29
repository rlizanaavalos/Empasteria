package controlador;

public class BeanInventarioPedido {


    private String DisplayText;
    private String Value;
 
    public BeanInventarioPedido(String DisplayText, String Value) {
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
  		return "tipo_material[id=" + Value + ", nombre=" + DisplayText + "]";
  	}
    
}