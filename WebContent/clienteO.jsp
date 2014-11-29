<%@ page import="java.util.*" %>
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" type="text/css" href="content/menuStyle.css" />
<link href="content/metro/blue/jtable.css" rel="stylesheet" type="text/css" />
<link href="content/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<link href="content/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script src="content/jquery-2.1.1.js" type="text/javascript"></script>
<script src="content/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
<script src="content/jquery.jtable.js" type="text/javascript"></script>
<script src="content/jquery.jtable.es.js" type="text/javascript"></script>
<script type="text/javascript" src="content/grevenler.validatorEngine.js"></script>
<script type="text/javascript" src="content/jquery.validationEngine.js"></script>
<script type="text/javascript" src="content/jquery.validationEngine-es.js"></script>
<script src="content/jquery.jtable.toolbarsearch.js" type="text/javascript"></script>



<script type="text/javascript">
    	$(document).ready(function () {
        $('#PersonTableContainer').jtable({
            title: 'Cliente',
            toolbarsearch: true,
            paging: true, //Enable paging
            pageSize: 10, //Set page size (default: 10)
            sorting: true, //Enable sorting
            defaultSorting: 'nombre_cliente ASC',
            toolbar: {
                items: [{
                    Tooltip: 'Click aquí para exportar a excel',
                    //icon: '/images/paginate.gif',
                    text: 'Exportar a Excel',
                    click: function () {
                    	var ua = window.navigator.userAgent;
                    	var msie = ua.indexOf("MSIE ");

                    	// Se crea Temporalmente una tabla basada en la Tabla Creada en JQUERY (la Jtable)
                    	var htmltable = document.getElementById("PersonTableContainer"); // here is the Name of your JTable Name ID
                    	var html = htmltable.outerHTML;

                    	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) // If Internet Explorer, return versión number
                    	{
                    	// Se crea el proceso de Exportación a Excel para los Navegadores de Internet Explorer
                    	var csvContent = html;
                    	var blob = new Blob([csvContent], {
                    	type: "application/vnd.ms-excel;",
                    	});
                    	navigator.msSaveBlob(blob, "Clientes.xls");
                    	$("#ExportDialog").dialog("close");
                    	}
                    	else // If another browser, return 0
                    	{
                    	// Si es otro Navegador (Chrome, FireFox, Safari) ejecuta el proceso de Exportación Siguiente
                    	window.open('data:application/vnd.ms-excel,' + encodeURIComponent(html));
                    	e.preventDefault();
                    	$("#ExportDialog").dialog("close");
                    	}
                   
                    }}]
            },
            actions: {
                listAction: 'CRUDController?action=list',
                createAction:'CRUDController?action=create',
                updateAction: 'CRUDController?action=update',
                //deleteAction: 'CRUDController?action=delete'
            },
            fields: {
                nombre_cliente: {
                    title: 'Nombre',
                    width: '25%',
                    key: true,
                    list: true,
                    edit: false,
                    columnResizable: false,
                    create:true
                },
                rut: {
                    title: 'R.U.T',
                    width: '5%',
                    edit:true,
                    columnResizable: false,
                    inputClass: 'validate[required,funcCall[validarRut]]'
                },
               
                direccion: {
                    title: 'Direccion',
                    width: '15%',
                    edit: true,
                    columnResizable: false,
                    inputClass: 'validate[required,maxSize[40]]'
                },
                region: {
                    title: 'Region',
                    width: '15%	',
                    edit: true,     
                    columnResizable: false,
                    options: {
                    	0: 'Todas',
                    	1: 'XV de Arica y Parinacota',
                    	2: 'I de Tarapaca',
                    	3: 'II de Antofagasta',
                    	4: 'III de Atacama',
                    	5: 'IV de Coquimbo',
                    	6: 'V de Valparaiso',
                    	7: 'VI del Libertador General Bernardo O`Higgins',
                    	8: 'VII del Maule',
                    	9:  'VIII del Biobio',
                    	10: 'IX de la Araucania',
                    	11: 'XIV de los Rios',             
                    	12: 'X de los Lagos',
                    	13: 'XI Aysen del General Carlos Ibañez del Campo',
                    	14: 'XII de Magallanes y Antartica Chilena',
                    	15: 'RM Metropolitana de Santiago',                   	
                    	},
                    defaultValue: 15,
                   // list: false
                    
                },
                comuna: {
                    title: 'Comuna',
                    dependsOn: 'region',
                    edit: true,
                    columnResizable: false,
                    options: function (data) {
                        if (data.source == 'list') {
                        	 return 'CRUDController?action=' + data.dependedValues.region;
                        }
                        return 'CRUDController?action=' + data.dependedValues.region;
                    },
                	list: false
                },
                ciudad: {
                    title: 'Ciudad',
                    width: '10%',
                    edit: true,
                    columnResizable: false,
                    inputClass: 'validate[required,custom[onlyLetterSp],maxSize[40]]'
                },
               
                correo: {
                    title: 'Email',
                    width: '5%',
                    edit: true,
                    columnResizable: false,
                    inputClass: 'validate[required,custom[email]]'
                },
                telefono: {
                    title: 'Telefono',
                    width: '5%',
                    edit: true,
                    columnResizable: false,
                    inputClass: 'validate[custom[phone]]'
                }
            },
            //validacion
            
            //Initialize validation logic when a form is created
         formCreated: function (event, data) {
             data.form.validationEngine();
         },
         //Validate form when it is being submitted
         formSubmitting: function (event, data) {
             return data.form.validationEngine('validate');
         },
         //Dispose validation logic when form is closed
         formClosed: function (event, data) {
             data.form.validationEngine('hide');
             data.form.validationEngine('detach');
         }
        });
      
         $('#PersonTableContainer').keyup(function(e){
       	 $('#PersonTableContainer').jtable('load', {
       		 searchNombre: $('#jtable-toolbarsearch-nombre_cliente').val(),
           	 searchRut: $('#jtable-toolbarsearch-rut').val(),
           	 searchDireccion: $('#jtable-toolbarsearch-direccion').val(),
           	 searchComuna: $('#jtable-toolbarsearch-comuna').val(),
           	 searchCiudad: $('#jtable-toolbarsearch-ciudad').val(),
           	 searchRegion: $('#jtable-toolbarsearch-region').val(),
           	 searchCorreo: $('#jtable-toolbarsearch-correo').val(),
           	 searchTelefono: $('#jtable-toolbarsearch-telefono').val(),          	 
            }); 
       	});
         $('#PersonTableContainer').change(function(e){
           	 $('#PersonTableContainer').jtable('load', {
           		 searchNombre: $('#jtable-toolbarsearch-nombre_cliente').val(),
               	 searchRut: $('#jtable-toolbarsearch-rut').val(),
               	 searchDireccion: $('#jtable-toolbarsearch-direccion').val(),
               	 searchComuna: $('#jtable-toolbarsearch-comuna').val(),
               	 searchCiudad: $('#jtable-toolbarsearch-ciudad').val(),
                 searchRegion: $('#jtable-toolbarsearch-region').val(),
               	 searchCorreo: $('#jtable-toolbarsearch-correo').val(),
               	 searchTelefono: $('#jtable-toolbarsearch-telefono').val(),          	 
                }); 
           	});
         $resetbutton.click(function(){
           	 $('#PersonTableContainer').jtable('load', {
           		 searchNombre: "",
               	 searchRut: "",
               	 searchDireccion: "",
               	 searchComuna: "",
               	 searchCiudad: "",
               	 searchRegion: "0",
               	 searchCorreo: "",
               	 searchTelefono: "", 
                }); 
           	$('#jtable-toolbarsearch-region').val("0");
				 });
         $('#tableToExcelJQuery').click(function (e) {

        	// Se crea un agente para validar si el Navegador que esa usando es Internet Explorer u algún otro 
        	var ua = window.navigator.userAgent;
        	var msie = ua.indexOf("MSIE ");

        	// Se crea Temporalmente una tabla basada en la Tabla Creada en JQUERY (la Jtable)
        	var htmltable = $('#PersonTableContainer'); // here is the Name of your JTable Name ID
        	var html = htmltable.jtable.tbody;

        	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) // If Internet Explorer, return versión number
        	{
        	// Se crea el proceso de Exportación a Excel para los Navegadores de Internet Explorer
        	var csvContent = html;
        	var blob = new Blob([csvContent], {
        	type: "application/vnd.ms-excel;",
        	});
        	navigator.msSaveBlob(blob, "Descarga.xls");
        	$("#ExportDialog").dialog("close");
        	}
        	else // If another browser, return 0
        	{
        	// Si es otro Navegador (Chrome, FireFox, Safari) ejecuta el proceso de Exportación Siguiente
        	window.open('data:application/vnd.ms-excel,' + encodeURIComponent(html));
        	e.preventDefault();
        	$("#ExportDialog").dialog("close");
        	}
        	});
        $('#PersonTableContainer').keyup();
    });
</script>

<title>Resultado Busqueda</title>
</head>
<body>

<h3>Bienvenido <%=session.getAttribute("name")%></h3>
<h6><a href="LogoutServlet">Salir </a></h6> 
<div id='menucontainer'>		
<ul class="glossymenu">
   <li class='current'><a href='clienteO.jsp'><b>Clientes</b></a></li>
      <li><a href='compraproveedorO.jsp'><b>Compra</b></a></li>
   
   <li><a href='pedidoO.jsp'><b>Pedido</b></a></li>
     <li><a href='tomarpedidoO.jsp'><b>Tomar Pedido</b></a></li>
   <li><a href='inventarioO.jsp'><b>Inventario</b></a></li>
   <li><a href='proveedorO.jsp'><b>Proveedores</b></a></li>
      <li><a href='menureportesO.jsp'><b>Reportes</b></a></li>
   
</ul>
</div>
<div id="body">
	<div style="width:80%; margin-right:10% ;margin-left:10%;margin-top:1%;text-align:center;">
		<div id="PersonTableContainer"></div>
	</div> 
</div>
</body>
</html>	