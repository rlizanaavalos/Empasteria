<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  
    pageEncoding="ISO-8859-1"%>  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">  
<link href="content/cloud-director-51-login-template.css" rel="stylesheet" type="text/css" />
<link href="content/cloud-director-51-template.css" rel="stylesheet" type="text/css" />
<link href="content/login2.css" rel="stylesheet" type="text/css" />
<title>Login Application</title>  
</head>  
<body>  
    <section class="login">
	<div class="titulo">Iniciar Sesión</div>
	<form action="LoginServlet?action=login" method="post">
    	<input type="text" name="username" required title="Usuario requerido" placeholder="Usuario" data-icon="U">
        <input type="password" name="userpass"required title="Contraseña requerida" placeholder="Contraseña" data-icon="x">       	
        <br>
		<br>
		<br>
      <input type="submit" class="enviar" value="Login" />
      
     <div id="error" class="olvido">
     <%
    if(null!=request.getAttribute("errorMessage"))
    {
        out.println(request.getAttribute("errorMessage"));
    }
	%>
     
     </div>
     
    </form>
	</section>
</body>  
</html>  