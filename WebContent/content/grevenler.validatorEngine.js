

function validarRut(field, rules, i, options){
	var rut = field.val();
	var sRut = rut.toUpperCase();


	/*
	 * Verificacion
	 */
	var error = '* Ingrese un rut correcto, sin puntos y con guion.';
	var aux = sRut.search('-');
	if (aux == -1){
		return error;
	}

	if (aux != sRut.lastIndexOf('-')){
		return error;
	}

	if (aux != sRut.length-2){
		return error;
	}

	sRut = sRut.split('-');

	if( sRut[0].length < 1){
		return error;
	}

	if ( sRut[1] != '0' && sRut[1] != '1' && sRut[1] != '2' && sRut[1] != '3' && sRut[1] != '4' && sRut[1] != '5' 
		&& sRut[1] != '6' && sRut[1] != '7' && sRut[1] != '8' && sRut[1] != '9' && sRut[1] != 'K')
	{   
		return error; 
	}

	aux = Number(sRut[0]);
	if( isNaN(aux) ){
		return error;
	}

	/*
	 * Verificacion de Rut
	 */
	
	error = '* Rut no existente, Ingrese nuevamente Rut.';
	
	var suma = 0;
	var digito = 0;
	for (mul=2; aux > 0;mul++){
		if(mul > 7){
			mul = 2;
		}
		digito = aux%10;
		aux = (aux - digito)/10;
		suma = suma + digito * mul;
	}

	aux = suma%11;
	var dv = '';
	if (aux == 1){
		dv = 'K';
	}else if(aux == 0){
		dv = '0';
	}else{
		dv = (11-aux).toString();
	}
	
	if (sRut[1] != dv){
		return error;
	}
	
}