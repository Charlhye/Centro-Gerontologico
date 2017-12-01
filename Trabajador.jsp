<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link href="css/foundation.css" rel="stylesheet"/>
    <link href="css/Trabajador.css" rel="stylesheet"/>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
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
<div class="wrapper">
    <form action="RealizarEncuesta" method="post">
        <input name="user" type="hidden" value="${requestScope.user}"/>
        <input name="password" type="hidden" value="${requestScope.password}">
        <input type="submit" class="success button" value="Realizar encuesta"></input>
    </form>
</div>
<div style="margin: 20px;">
    <table style="width:100% ">
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
                    <td><input type="submit" value="${it.getIdCuestionarioResuelto()}"/></td>
                    <td>${it.getNombreUsuario()}</td>
                </form>
            </tr>
        </c:forEach>
    </table>
</div>
</div>
</body>
</html>
