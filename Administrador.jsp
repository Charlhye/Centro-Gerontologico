<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link href="css/foundation.css" rel="stylesheet"/>
    <link href="css/Investigador.css" rel="stylesheet"/>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>Administrador</title>
</head>
<body>
<h1 align="center">Bienvenido</h1>
<div class="grid-x grid-padding-x">
    <div class="large-6 medium-6 cell">
        <h5 align="center">Dar de alta</h5>
        <form action="AltaUsuario" method="post">
            <input name="user" type="hidden" value="${requestScope.user}"/>

            <input name="password" type="hidden" value="${requestScope.password}">
            <input name="usuarioNuevo" type="text" placeholder="Nombre del usuario" required/>
            <input name="passNuevo" type="password" placeholder="Nuevo password" required/>
            <select name="ocupacion" required>
                <option value="Investigador">Investigador</option>
                <option value="Trabajador">Trabajador</option>
            </select>
            <div align="center"><input type="submit" class="success button"></input></div>
            <label style="color:red">${requestScope.statusAlta}</label>
        </form>
    </div>
    <div class="large-6 medium-6 cell">
        <h5 align="center">Dar de baja</h5>
        <form action="BajaUsuario" method="post">
            <input name="user" type="hidden" value="${requestScope.user}"/>
            <input name="password" type="hidden" value="${requestScope.password}">
            <select name="usuarioABorrar">
                <c:forEach items="${requestScope.usuarios}" var="it">
                    <option value="${it.getNombre()}">${it.getNombre()}</option>
                </c:forEach>
            </select>
            <div align="center"><input type="submit" class="alert button"></input></div>
            <label style="color:red">${requestScope.statusBaja}</label>
        </form>
    </div>
</div>
<div class="grid-x grid-padding-x">
    <div class="large-6 medium-6 cell">
        <h5 align="center">Cambiar password</h5>
        <form action="" method="post">
            <input type="text" placeholder="Nombre del usuario"/>
            <input type="password" placeholder="Password anterior"/>
            <input type="password" placeholder="Password nuevo"/>
            <div align="center"><a type="submit" class="button">Cambiar</a></div>
        </form>
    </div>
</div>
</body>
</html>
