<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link href="css/foundation.css" rel="stylesheet"/>
    <link href="css/Investigador.css" rel="stylesheet"/>
    <title>${requestScope.user}</title>
</head>
<script>
    function getSelected() {
        alert(document.querySelector('input[name="encuestado"]:checked').value);
    }
</script>
<header>
    <h1 align="center">Bienvenido ${requestScope.nombreCompleto}</h1>
</header>
<body>
<div class="grid-x grid-padding-x">
    <div align="center" class="large-6 medium-6 cell">
        <form action="AgregarCuestionarioVacio" method="post">
            <input name="user" type="hidden" value="${requestScope.user}"/>
            <input name="password" type="hidden" value="${requestScope.password}">
            <input name="nombreCuestionario" type="text" placeholder="Nombre del Cuestionario" required>
            <input type="submit" class="success button" value="Agregar Cuestionario">
        </form>
    </div>
    <div align="center" class="large-6 medium-6 cell">
        <form action="AgregarPregunta" method="post" target="_blank">
            <input name="user" type="hidden" value="${requestScope.user}"/>
            <input name="password" type="hidden" value="${requestScope.password}">
            <select name="Cuestionario">
                <c:forEach items="${requestScope.cuestionariosVacios}" var="it">
                    <option value="${it.idCuestionario}">${it.nombre}</option>
                </c:forEach>
            </select>
            <input name="pregunta" type="text" placeholder="Pregunta" required/>
            <input type="submit" class="success button" value="Agregar Pregunta">
        </form>
    </div>
</div>
<table style="width:100%">
    <tr>
        <th></th>
        <th>Id del Cuestionario</th>
        <th>Id del Trabajador</th>
    </tr>
    <c:forEach items="${requestScope.cuestionarios}" var="it">
        <tr>
            <form action="VerCuestionario" method="post" target="_blank">
                <input name="user" type="hidden" value="${requestScope.user}"/>
                <input name="password" type="hidden" value="${requestScope.password}">
                <input name="idCuestR" type="hidden" value="${it.getIdCuestionarioResuelto()}">
                <td>${it.getFecha()}</td>
                <td><input type="submit" value="${it.getIdCuestionarioResuelto()}"/>${it.getNombrePlantilla()}</td>
                <td>${it.getNombreUsuario()}</td>
            </form>
        </tr>
    </c:forEach>
</table>
</body>
</html>
