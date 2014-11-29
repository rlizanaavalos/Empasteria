<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="content/menuStyle.css" />
<link href="content/metro/purple/jtable.css" rel="stylesheet" type="text/css" />
<link href="content/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css"/>
<link href="content/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script src="content/jquery-2.1.1.js" type="text/javascript"></script>
<script src="content/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
<script src="content/jquery.jtable.js" type="text/javascript"></script>
<script src="content/jquery.jtable.es.js" type="text/javascript"></script>
<script type="text/javascript" src="content/grevenler.validatorEngine.js"></script>
<script type="text/javascript" src="content/jquery.validationEngine.js"></script>
<script type="text/javascript" src="content/jquery.validationEngine-es.js"></script>
<script type="text/javascript" src="content/jspdf.js"></script>
        <title>Pedidos por temporada</title>
        <!--En este ejercicio repasaremos por todas las funciones vistas anteriormente -->
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
   <li><a href='administracion.jsp'><b>Usuarios</b></a></li>
   <li class='current'><a href='menureportes.jsp'><b>Reportes</b></a></li>
</ul>
<div align=center>
    		<img name='miimagen' src='PedidosporTemporad?condicionreporte=<%=request.getParameter("condicionreporte")%>' heigth='80%'>
    	</div>

</div>
    	
    </body>
</html>