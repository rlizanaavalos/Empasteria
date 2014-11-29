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
        $('#PedidoTableContainer').jtable({
            title: 'Tomar Pedidos',
            toolbarsearch: false,
            paging: true, //Enable paging
            pageSize: 10, //Set page size (default: 10)
            sorting: true, //Enable sorting
            defaultSorting: 'estado ASC',            
            actions: {
                listAction: 'CRUDTomarPedido?action=lista2',
                //createAction:'CRUDController?action=create',
                updateAction: 'CRUDTomarPedido?action=update',
                //deleteAction: 'CRUDController?action=delete'
            },
            fields: {
            	
            	id_pedido: {
                    title: 'ID',
                    key: true,
                    list: true,
                    create:false,
            
                },
                
            	nombre_cliente: {
                    title: 'Cliente',
                    width: '20%',
                    edit: false,
                    list: false,
                },
                
                nombre_empleado: {
                    title: 'Nombre empleado',
                    width: '20%',
                    edit: false,
                    list: false,
                },
            	
                tipo_material: {
                    title: 'Material',
                    width: '50%',
                    edit: true,
                    input: function (data){
                    		return '<input type="text" readonly name="tipo_material" style="width:200px; background-color:#c0c0c0; border: none" id="tipo_material" value="'+data.record.tipo_material+'"/>';
                     	
                    }
                },
                tipo_impresion: {
                    title: 'Impresion',
                    width: '50%',
                    edit: true,
                    input: function (data){
                		return '<input type="text" readonly name="tipo_impresion" style="width:200px; background-color:#c0c0c0; border: none" id="tipo_impresiono" value="'+data.record.tipo_impresion+'" />';
                }
                },
                
                color: {
                    title: 'Color',
                    width: '30%',
                    edit: true,
                    list: true,
                    input: function (data){
                		return '<input type="text" readonly name="color" style="width:200px; background-color:#c0c0c0; border: none" id="color" value="'+data.record.color+'" />';
                }
                },
               
                tamano_tapa: {
                    title: 'Tapa',
                    width: '40%',
                    edit: true,
                    list: true,
                    input: function (data){
                		return '<input type="text" readonly name="tamano_tapa" style="width:200px; background-color:#c0c0c0; border: none" id="tamano_tapa" value="'+data.record.tamano_tapa+'" />';
	                }
                },
                
                cantidad_pagina: {
                    title: 'Páginas',
                    width: '50%',
                    edit: true,
                    input: function (data){
                		return '<input type="text" readonly name="cantidad_pagina" style="width:200px; background-color:#c0c0c0; border: none" id="cantidad_pagina" value="'+data.record.cantidad_pagina+'" />';
                }
                },
               
                precio: {
                    title: 'Precio',
                    width: '50%',
                    edit: false,
                    list: false,
                },
                
                nombre_empastador: {
                    title: 'Empastador',
                    width: '40%',
                    edit: false,
                },
                
                estado: {
                    title: 'Estado',
                    width: '20%	',
                    edit: true,
                    options: {
                    	0: 'Todos',
                    	1: 'No asignado',
                    	2: 'En proceso',
                    	},
                    inputClass: 'validate[required,custom[estado]]'
                },
                
            },
        
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
      
         $('#tableToExcelJQuery').click(function (e) {

        	// Se crea un agente para validar si el Navegador que esa usando es Internet Explorer u algún otro 
        	var ua = window.navigator.userAgent;
        	var msie = ua.indexOf("MSIE ");

        	// Se crea Temporalmente una tabla basada en la Tabla Creada en JQUERY (la Jtable)
        	var htmltable = $('#PedidoTableContainer'); // here is the Name of your JTable Name ID
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
         $('#PedidoTableContainer').jtable('load');
    });
    	
</script>

<title>Resultado Busqueda</title>
</head>
<body>

<h3>Bienvenido <%=session.getAttribute("name")%></h3>
<h6><a href="LogoutServlet">Salir </a></h6> 
<div id='menucontainer'>		
<ul class="glossymenu">
   <li><a href='cliente.jsp'><b>Clientes</b></a></li>
      <li><a href='compraproveedor.jsp'><b>Compra</b></a></li>
   
   <li><a href='pedido.jsp'><b>Pedido</b></a></li>
   <li  class='current'><a href='tomarpedido.jsp'><b>Tomar Pedido</b></a></li>
   <li><a href='inventario.jsp'><b>Inventario</b></a></li>
   <li><a href='proveedor.jsp'><b>Proveedores</b></a></li>
   <li><a href='administracion.jsp'><b>Usuarios</b></a></li>
   <li><a href='menureportes.jsp'><b>Reportes</b></a></li>
</ul>
</div>
<div id="body">
	<div style="width:60%; margin-right:20% ;margin-left:20%;margin-top:1%;text-align:center;">
		<div id="PedidoTableContainer"></div>
	</div> 
</div>
</body>
</html>	