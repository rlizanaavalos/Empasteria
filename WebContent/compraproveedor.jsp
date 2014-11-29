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
            title: 'Compra',
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
                    	navigator.msSaveBlob(blob, "Compra.xls");
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
                listAction: 'CompraController?action=list',
                createAction:'CompraController?action=create',
                updateAction: 'CompraController?action=update',
                deleteAction: 'CompraController?action=delete'
            },
            fields: {
                codigo_compra: {
                    title: 'ID',
                    key: true,
                    list: true,
                    create:false
                },
                              
                nombre_empleado: {
                    title: 'Empleado',
                    edit: true,         
                    options: function (data) {
                        if (data.source == 'list') {
                            return 'CompraController?action=getEmpleadoCompra';
                        }
                        return 'CompraController?action=getEmpleadoCompra';
                    },
                },
                nombre_proveedor: {
                    title: 'Proveedor',
                    //width: '20%	',
                    edit: true,     
                    options: function (data) {
                        if (data.source == 'list') {
                            return 'CompraController?action=getProveedorCompra';
                        }
                        return 'CompraController?action=getProveedorCompra';
                    },
                   // list: false
                    
                },
                
                fecha:{
                	title: 'Fecha',
                	edit:true,
                	inputClass: 'validate[required,custom[date]]'
                },
                
                medio_pago: {
                    title: 'Medio de pago',
                   // width: '20%',
                    edit: true,
                    inputClass: 'validate[required,maxSize[40]]'	
                },
                
                total: {
                    title: 'Total',
                 //   width: '20%',
                    edit: true,
                    inputClass: 'validate[required,custom[integer],maxSize[20]]'
                    
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
       		searchId: $('#jtable-toolbarsearch-codigo_compra').val(),
       		searchEmpleado: $('#jtable-toolbarsearch-nombre_empleado').val(),
       		searchProveedor: $('#jtable-toolbarsearch-nombre_proveedor').val(),
       		searchFecha: $('#jtable-toolbarsearch-fecha').val(),
       		searchMedio_pago: $('#jtable-toolbarsearch-medio_pago').val(),
       		searchTotal: $('#jtable-toolbarsearch-total').val(),
       		}); 
       	}); 
         
         $('#PersonTableContainer').change(function(e){
           	 $('#PersonTableContainer').jtable('load', {
           		searchId: $('#jtable-toolbarsearch-codigo_compra').val(),
           		searchEmpleado: $('#jtable-toolbarsearch-nombre_empleado').val(),
           		searchProveedor: $('#jtable-toolbarsearch-nombre_proveedor').val(),
           		searchFecha: $('#jtable-toolbarsearch-fecha').val(),
           		searchMedio_pago: $('#jtable-toolbarsearch-medio_pago').val(),
           		searchTotal: $('#jtable-toolbarsearch-total').val(),
                }); 
           	}); 
         $resetbutton.click(function(){
           	 $('#PersonTableContainer').jtable('load', {
           		searchId: "",
           		searchEmpleado: "",
           		searchProveedor:"",
           		searchFecha: "",
           		searchMedio_pago:"",
           		searchTotal: "",
                });
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
   <li><a href='cliente.jsp'><b>Clientes</b></a></li>
   <li class='current'><a href='compraproveedor.jsp'><b>Compra</b></a></li>
   <li><a href='pedido.jsp'><b>Pedido</b></a></li>
   <li><a href='tomarpedido.jsp'><b>Tomar Pedido</b></a></li>
   <li><a href='inventario.jsp'><b>Inventario</b></a></li>
   <li><a href='proveedor.jsp'><b>Proveedores</b></a></li>
   <li><a href='administracion.jsp'><b>Usuarios</b></a></li>
   <li><a href='menureportes.jsp'><b>Reportes</b></a></li>
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