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
<script type="text/javascript" src="content/jquery.validationEngine.js"></script>
<script type="text/javascript" src="content/jquery.validationEngine-es.js"></script>
<script type="text/javascript" src="content/jquery.Rut.js"></script>
<script type="text/javascript" src="content/jquery.Rut.min.js"></script>
<script src="content/jquery.jtable.toolbarsearch.js" type="text/javascript"></script>
<script type="text/javascript" src="content/grevenler.validatorEngine.js"></script>
<script type="text/javascript">
    	$(document).ready(function () {
        $('#UserTableContainer').jtable({
            title: 'Usuarios',
            toolbarsearch: true,
            paging: true, //Enable paging
            pageSize: 5, //Set page size (default: 10)
            sorting: true, //Enable sorting
            defaultSorting: 'user ASC',
            toolbar: {
                items: [{
                    Tooltip: 'Click aqu� para exportar a excel',
                    //icon: '/images/paginate.gif',
                    text: 'Exportar a Excel',
                    click: function () {
                    	var ua = window.navigator.userAgent;
                    	var msie = ua.indexOf("MSIE ");

                    	// Se crea Temporalmente una tabla basada en la Tabla Creada en JQUERY (la Jtable)
                    	var htmltable = document.getElementById("InventarioTableContainer"); // here is the Name of your JTable Name ID
                    	var html = htmltable.outerHTML;

                    	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) // If Internet Explorer, return versi�n number
                    	{
                    	// Se crea el proceso de Exportaci�n a Excel para los Navegadores de Internet Explorer
                    	var csvContent = html;
                    	var blob = new Blob([csvContent], {
                    	type: "application/vnd.ms-excel;",
                    	});
                    	navigator.msSaveBlob(blob, "Clientes.xls");
                    	$("#ExportDialog").dialog("close");
                    	}
                    	else // If another browser, return 0
                    	{
                    	// Si es otro Navegador (Chrome, FireFox, Safari) ejecuta el proceso de Exportaci�n Siguiente
                    	window.open('data:application/vnd.ms-excel,' + encodeURIComponent(html));
                    	e.preventDefault();
                    	$("#ExportDialog").dialog("close");
                    	}
                   
                    }}]
            },
            actions: {
                listAction: 'LoginServlet?action=list',
                createAction:'LoginServlet?action=create',
                updateAction: 'LoginServlet?action=update',
                deleteAction: 'LoginServlet?action=delete'
            },
            fields: {
                id: {
                       title: 'ID',
                       key: true,
                       list: true,
                       create:false
                },
                user: {
                    title: 'Usuario',
                    edit: true,
                    inputClass: 'validate[required]'
                },
                password: {
                    title: 'Password',
                    width: '30%',
                    edit:true,
                    visibility: false,
                    type: 'password',
                    input: function (data){
                		return '<input type="password" name="password" style="width:150px" id="password"/>';
                 	
                	},
                    inputClass: 'validate[required,maxSize[40]]'
                },
               
                rol: {
                    title: 'Rol',
                    width: '20%',
                    edit: true,
                    options: {
                    	0:'Todos',
                    	Operador: 'Operador',
                    	Administrador: 'Administrador',
                    	},
                    inputClass: 'validate[required,custom[usuario],maxSize[30]]'
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
        
        $('#UserTableContainer').change(function(e){
          	 $('#UserTableContainer').jtable('load', {
          		 searchId: $('#jtable-toolbarsearch-id').val(),
          		 searchUser: $('#jtable-toolbarsearch-user').val(),
               	 searchPassword: $('#jtable-toolbarsearch-password').val(),
               	 searchRol: $('#jtable-toolbarsearch-rol').val(),	         	 
               }); 
          	});
         $('#UserTableContainer').keyup(function(e){
       	 $('#UserTableContainer').jtable('load', {
       		 searchId: $('#jtable-toolbarsearch-id').val(),
       		 searchUser: $('#jtable-toolbarsearch-user').val(),
           	 searchPassword: $('#jtable-toolbarsearch-password').val(),
           	 searchRol: $('#jtable-toolbarsearch-rol').val(),	 
            }); 
       	});
         $resetbutton.click(function(){
           	 $('#UserTableContainer').jtable('load', {
           		 searchId: "",
           		 searchUser: "",
               	 searchPassword: "",
               	searchRol: "0",    	 
                }); 
           	$('#jtable-toolbarsearch-rol').val("0");
				 });
         $('#tableToExcelJQuery').click(function (e) {

        	// Se crea un agente para validar si el Navegador que esa usando es Internet Explorer u alg�n otro 
        	var ua = window.navigator.userAgent;
        	var msie = ua.indexOf("MSIE ");

        	// Se crea Temporalmente una tabla basada en la Tabla Creada en JQUERY (la Jtable)
        	var htmltable = $('#UserTableContainer'); // here is the Name of your JTable Name ID
        	var html = htmltable.jtable.tbody;

        	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) // If Internet Explorer, return versi�n number
        	{
        	// Se crea el proceso de Exportaci�n a Excel para los Navegadores de Internet Explorer
        	var csvContent = html;
        	var blob = new Blob([csvContent], {
        	type: "application/vnd.ms-excel;",
        	});
        	navigator.msSaveBlob(blob, "Descarga.xls");
        	$("#ExportDialog").dialog("close");
        	}
        	else // If another browser, return 0
        	{
        	// Si es otro Navegador (Chrome, FireFox, Safari) ejecuta el proceso de Exportaci�n Siguiente
        	window.open('data:application/vnd.ms-excel,' + encodeURIComponent(html));
        	e.preventDefault();
        	$("#ExportDialog").dialog("close");
        	}
        	});
        $('#UserTableContainer').keyup();
    });
</script>

<title>Usuarios</title>
</head>
<body>
<h3>Bienvenido <%=session.getAttribute("name")%></h3>
<h6><a href="LogoutServlet">Salir </a></h6> 
<div id='menucontainer'>		
<ul class="glossymenu">
   <li><a href='cliente.jsp'><b>Clientes</b></a></li>
   <li><a href='compraproveedor.jsp'><b>Compra</b></a></li>
   <li><a href='pedido.jsp'><b>Pedido</b></a></li>
   <li><a href='tomarpedido.jsp'><b>Tomar Pedido</b></a></li>
   <li><a href='inventario.jsp'><b>Inventario</b></a></li>
   <li><a href='proveedor.jsp'><b>Proveedores</b></a></li>
   <li class='current'><a href='administracion.jsp'><b>Usuarios</b></a></li>
   <li><a href='menureportes.jsp'><b>Reportes</b></a></li>
</ul>
</div>
<br>
<div id="body">
<div style="width:60%;margin-right:20%;margin-left:20%;text-align:center;">
<div id="UserTableContainer"></div>
</div> 
</div>
</body>
</html>