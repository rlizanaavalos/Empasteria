package controlador;

public class Validador {
	public boolean validacion(String $cadena) {
        char[] cadena = $cadena.toLowerCase().toCharArray();
         
        for (int i = 0; i < cadena.length; i++) {
            //Compruebo que no existan caracteres especiales (solamento los que podrian ser usados para una inyeccion SQL o perj	udicar en la consulta);
            if (cadena[i] == '='
                    || cadena[i] == '?'
                    || cadena[i] == '+'
                    || cadena[i] == '*'
                    //|| cadena[i] == '-'
                    || cadena[i] == '%'
                    || cadena[i] == '/'
                    //|| cadena[i] == '.'
                    || cadena[i] == ','
                    || cadena[i] == ';'
                    || cadena[i] == '!'
                    || cadena[i] == '<'
                    || cadena[i] == '>'
                    || cadena[i] == ':') {
                return false;
            }
 
        }
        return true;
    }
	
	public boolean validarut(String rut) {
		boolean validacion = false;
		try {
			rut =  rut.toUpperCase();
			rut = rut.replace(".", "");
			rut = rut.replace("-", "");
			int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));
		 
			char dv = rut.charAt(rut.length() - 1);
		 
			int m = 0, s = 1;
			for (; rutAux != 0; rutAux /= 10) {
					s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
			}
		if (dv == (char) (s != 0 ? s + 47 : 75)) {
			validacion = true;
		}
		 
		} catch (java.lang.NumberFormatException e) {
		} catch (Exception e) {
		}
		return validacion;
	} 
	
	public boolean esNumero(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
}
