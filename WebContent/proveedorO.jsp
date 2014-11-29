<%@ page import="java.util.*" %>
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="content/menuStyle.css" />
<link href="content/metro/blue/jtable.css" rel="stylesheet" type="text/css" />
<link href="content/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css"/>
<link href="content/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script src="content/jquery-2.1.1.js" type="text/javascript"></script>
<script src="content/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
<script src="content/jquery.jtable.js" type="text/javascript"></script>
<script src="content/jquery.jtable.es.js" type="text/javascript"></script>
<script type="text/javascript" src="content/grevenler.validatorEngine.js"></script>
<script type="text/javascript" src="content/jquery.validationEngine.js"></script>
<script type="text/javascript" src="content/jquery.validationEngine-es.js"></script>
<script type="text/javascript" src="content/jquery.Rut.js"></script>
<script type="text/javascript" src="content/jquery.Rut.min.js"></script>
<script src="content/jquery.jtable.toolbarsearch.js" type="text/javascript"></script>

<script type="text/javascript">
    	$(document).ready(function () {
        $('#ProveedorTableContainer').jtable({
            title: 'Proveedor',
            toolbarsearch: true,
            paging: true, //Enable paging
            pageSize: 5, //Set page size (default: 10)
            sorting: true, //Enable sorting
            defaultSorting: 'rut ASC',
            toolbar: {
                items: [{
                    Tooltip: 'Click aquí para exportar a excel',
                    //icon: '/images/paginate.gif',
                    text: 'Exportar a Excel',
                    click: function () {
                    	var ua = window.navigator.userAgent;
                    	var msie = ua.indexOf("MSIE ");

                    	// Se crea Temporalmente una tabla basada en la Tabla Creada en JQUERY (la Jtable)
                    	var htmltable = document.getElementById("ProveedorTableContainer"); // here is the Name of your JTable Name ID
                    	var html = htmltable.outerHTML;

                    	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) // If Internet Explorer, return versión number
                    	{
                    	// Se crea el proceso de Exportación a Excel para los Navegadores de Internet Explorer
                    	var csvContent = html;
                    	var blob = new Blob([csvContent], {
                    	type: "application/vnd.ms-excel;",
                    	});
                    	navigator.msSaveBlob(blob, "Proveedores.xls");
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
                listAction: 'CRUDProveedor?action=list',
                createAction:'CRUDProveedor?action=create',
                updateAction: 'CRUDProveedor?action=update',
                //deleteAction: 'CRUDProveedor?action=delete'
            },
            fields: {
                rut: {
                    title: 'R.U.T',
                    key: true,
                    list: true,
                    create:true,
                    inputClass: 'validate[required,funcCall[validarRut]]'
                },
                nombre: {
                    title: 'Nombre',
                    width: '30%',
                    edit:true,
                    inputClass: 'validate[required,maxSize[50]]'
                },
               
                tipo_insumos: {
                    title: 'Tipo Insumos',
                    width: '20%',
                    edit: true,
                    inputClass: 'validate[required,maxSize[50]]'
                },
                telefono: {
                    title: 'Teléfono',
                    width: '20%',
                    edit: true,
                    inputClass: 'validate[required,custom[phone],maxSize[20]]'
                },
                direccion: {
                    title: 'Dirección',
                    width: '20%',
                    edit: true,
                    inputClass: 'validate[required,maxSize[50]]'
                },
                correo: {
                    title: 'Correo',
                    width: '20%',
                    edit: true,
                    inputClass: 'validate[required,custom[email],maxSize[50]]'
                },
               
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
        
   
         $('#ProveedorTableContainer').keyup(function(e){
       	 $('#ProveedorTableContainer').jtable('load', {
       		 searchRut: $('#jtable-toolbarsearch-rut').val(),
           	 searchNombre: $('#jtable-toolbarsearch-nombre').val(),
           	 searchTipo_insumos: $('#jtable-toolbarsearch-tipo_insumos').val(),
           	 searchTelefono: $('#jtable-toolbarsearch-telefono').val(), 
           	 searchDireccion: $('#jtable-toolbarsearch-direccion').val(),
           	 searchCorreo: $('#jtable-toolbarsearch-correo').val(), 
            }); 
       	});
         $resetbutton.click(function(){
           	 $('#ProveedorTableContainer').jtable('load', {
           		searchRut: "",
               	searchNombre: "",
               	searchTipo_insumos: "",
               	searchTelefono: "",  
               	searchDireccion: "",
               	searchCorreo: "", 
                }); 
				 });
         $('#tableToExcelJQuery').click(function (e) {

        	// Se crea un agente para validar si el Navegador que esa usando es Internet Explorer u algún otro 
        	var ua = window.navigator.userAgent;
        	var msie = ua.indexOf("MSIE ");

        	// Se crea Temporalmente una tabla basada en la Tabla Creada en JQUERY (la Jtable)
        	var htmltable = $('#ProveedorTableContainer'); // here is the Name of your JTable Name ID
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
        $('#ProveedorTableContainer').keyup();
    });
</script>

<title>Proveedor</title>
</head>
<body>
<h3>Bienvenido <%=session.getAttribute("name")%></h3>
<h6><a href="LogoutServlet">Salir </a></h6> 
<div id='menucontainer'>		
<ul class="glossymenu">
   <li><a href='clienteO.jsp'><b>Clientes</b></a></li>
         <li><a href='compraproveedorO.jsp'><b>Compra</b></a></li>
   
   <li><a href='pedidoO.jsp'><b>Pedido</b></a></li>
     <li><a href='tomarpedidoO.jsp'><b>Tomar Pedido</b></a></li>
   <li><a href='inventarioO.jsp'><b>Inventario</b></a></li>
   <li class='current'><a href='proveedorO.jsp'><b>Proveedores</b></a></li>
   <li><a href='menureportesO.jsp'><b>Reportes</b></a></li>
</ul>
</div>
<div id="body">
	<div style="width:60%; margin-right:20% ;margin-left:20%;margin-top:1%;text-align:center;">
		<div id="ProveedorTableContainer"></div>
	</div> 
</div>
</body>
</html>