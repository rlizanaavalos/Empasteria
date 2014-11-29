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

<script type="text/javascript">
    	$(document).ready(function () {
        $('#PersonTableContainer').jtable({
            title: 'Pedido',
            toolbarsearch: true,
            paging: true, //Enable paging
            pageSize: 5, //Set page size (default: 10)
            sorting: true, //Enable sorting
            defaultSorting: 'id_pedido ASC',
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
                listAction: 'PEDIDOController?action=list',
                createAction:'PEDIDOController?action=create',
                updateAction: 'PEDIDOController?action=update',
                //deleteAction: 'PEDIDOController?action=delete'
            },
            fields: {
                id_pedido: {
                    title: 'ID',
                    key: true,
                    list: true,
                    create:false
                },
                nombre_cliente: {
                    title: 'Cliente',
                    edit:true,
                    options: function (data) {
                              if (data.source == 'list') {
                                  return 'PEDIDOController?action=getClientePedido';
                              }
                            return 'PEDIDOController?action=getClientePedido';
                          },
                
                },
               
                nombre_empleado: {
                    title: 'Empleado',
                    edit: true,         
                    options: function (data) {
                        if (data.source == 'list') {
                            return 'PEDIDOController?action=getEmpleadoPedido';
                        }
                        return 'PEDIDOController?action=getEmpleadoPedido';
                    },
                },
                tipo_impresion: {
                    title: 'Impresion',
                    //width: '20%	',
                    edit: true,     
                    options: function (data) {
                        if (data.source == 'list') {
                            return 'PEDIDOController?action=getImpresion';
                        }
                        return 'PEDIDOController?action=getImpresion';
                    },
                   // list: false
                    
                },
                color: {
                    title: 'Color',
                   // width: '20%',
                    edit: true,
                    options: {
                    	0: 'Todas',
                    	1: 'negro',
                    	2: 'verde',
                    	3: 'azul',                	
                    	},
                    inputClass: 'validate[required,custom[color]]'	
                },
                tipo_material: {
                    title: 'Material',
                 //   width: '20%',
                    edit: true,
                    options: function (data) {
                        if (data.source == 'list') {
                            return 'PEDIDOController?action=getMaterial';
                        }
                        return 'PEDIDOController?action=getMaterial';
                    },
                },
                tamano_tapa: {
                    title: 'Tapa',
                //    width: '20%',
                    edit: true,
                    inputClass: 'validate[required,maxSize[40]]'
                },
                cantidad_pagina: {
                    title: 'Paginas',
                 //   width: '20%',
                    edit: true,
                    inputClass: 'validate[required,custom[integer],min[0],maxSize[10]]'
                },
                precio: {
                    title: 'Precio',
               	    //create: true,
               	    create: false,
                    edit: false,
                   // inputClass: 'validate[required,custom[integer],min[0],maxSize[10]]'
                },
                pago: {
                    title: 'Pago',
                //    width: '20%',
                    create: false,
                    edit: true,
                   // inputClass: 'validate[custom[phone]]'
                },
                nombre_empastador: {
                    title: 'Empastador',
                    edit: false,
                    create: false,
                   // inputClass: 'validate[required,custom[onlyLetterSp],maxSize[50]]'
                },
                estado: {
                    title: 'Estado',
                    edit: true,
                    create: true,
                    options: {
                    	0: 'Todos',
                    	1: 'Pendiente', 
                    	2: 'En proceso',
                    	3: 'Terminado',
                    	4: 'Cotización',
                    	},
                	inputClass: 'validate[required,custom[estado]]'	   	
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

         $('#PersonTableContainer').keyup(function(e){
       	 $('#PersonTableContainer').jtable('load', {
       		searchId: $('#jtable-toolbarsearch-id_pedido').val(),
       		searchCliente: $('#jtable-toolbarsearch-nombre_cliente').val(),
       		searchEmpleado: $('#jtable-toolbarsearch-nombre_empleado').val(),
       		searchImpresion: $('#jtable-toolbarsearch-tipo_impresion').val(),
       		searchColor: $('#jtable-toolbarsearch-color').val(),
       		searchMaterial: $('#jtable-toolbarsearch-tipo_material').val(),
       		searchTapa: $('#jtable-toolbarsearch-tamano_tapa').val(),
       		searchPagina: $('#jtable-toolbarsearch-cantidad_pagina').val(),  
       		searchPrecio: $('#jtable-toolbarsearch-precio').val(),
       		searchPago: $('#jtable-toolbarsearch-pago').val(),
       		searchEmpastador: $('#jtable-toolbarsearch-nombre_empastador').val(),
       		searchEstado: $('#jtable-toolbarsearch-estado').val(),
            }); 
       	}); 
         
         $('#PersonTableContainer').change(function(e){
           	 $('#PersonTableContainer').jtable('load', {
           		searchId: $('#jtable-toolbarsearch-id_pedido').val(),
           		searchCliente: $('#jtable-toolbarsearch-nombre_cliente').val(),
           		searchEmpleado: $('#jtable-toolbarsearch-nombre_empleado').val(),
           		searchImpresion: $('#jtable-toolbarsearch-tipo_impresion').val(),
           		searchColor: $('#jtable-toolbarsearch-color').val(),
           		searchMaterial: $('#jtable-toolbarsearch-tipo_material').val(),
           		searchTapa: $('#jtable-toolbarsearch-tamano_tapa').val(),
           		searchPagina: $('#jtable-toolbarsearch-cantidad_pagina').val(),  
           		searchPrecio: $('#jtable-toolbarsearch-precio').val(),
           		searchPago: $('#jtable-toolbarsearch-pago').val(),  
           		searchEmpastador: $('#jtable-toolbarsearch-nombre_empastador').val(),
           		searchEstado: $('#jtable-toolbarsearch-estado').val(),
                }); 
           	}); 
         $resetbutton.click(function(){
           	 $('#PersonTableContainer').jtable('load', {
           		searchId: "",
           		searchCliente: "",
           		searchEmpleado: "",
           		searchImpresion:"",
           		searchColor: "",
           		searchMaterial:"",
           		searchTapa: "",
           		searchPagina: "",  
           		searchPrecio: "",
           		searchPago: "",
           		searchEmpastador: "",
           		searchEstado: "",
                }); 
           	$('#jtable-toolbarsearch-color').val("0");
         	$('#jtable-toolbarsearch-estado').val("0");
				 });
         $('#tableToExcelJQuery').click(function (e) {

        	var ua = window.navigator.userAgent;
        	var msie = ua.indexOf("MSIE ");
        	var htmltable = $('#PersonTableContainer'); 
        	var html = htmltable.jtable.tbody;

        	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) 
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
<script src="content/jquery.jtable.toolbarsearch.js" type="text/javascript"></script>
<title>Resultado Busqueda</title>
</head>
<body>
<h3>Bienvenido <%=session.getAttribute("name")%></h3>
<h6><a href="LogoutServlet">Salir </a></h6> 
<div id='menucontainer'>		
<ul class="glossymenu">
   <li><a href='clienteO.jsp'><b>Clientes</b></a></li>
   <li class='current'><a href='pedidoO.jsp'><b>Pedido</b></a></li>
         <li><a href='compraproveedorO.jsp'><b>Compra</b></a></li>
   
     <li><a href='tomarpedidoO.jsp'><b>Tomar Pedido</b></a></li>
   <li><a href='inventarioO.jsp'><b>Inventario</b></a></li>
   <li><a href='proveedorO.jsp'><b>Proveedores</b></a></li>
   <li><a href='menureportesO.jsp'><b>Reportes</b></a></li>
   
</ul>
</div>
<br>
<div id="body">
<div style="width:60%;margin-right:20%;margin-left:20%;text-align:center;">
<div id="PersonTableContainer"></div>
</div> 
</div>
</body>
</html>