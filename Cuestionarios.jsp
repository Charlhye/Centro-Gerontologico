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
<form action="AgregarCuestionario" method="post">
    <table>
        <tr>
            <th>Pregunta</th>
            <th>Respuesta</th>
            <th></th>
        </tr>

        <input name="user" type="hidden" value="${requestScope.user}"/>
        <input name="password" type="hidden" value="${requestScope.password}">
        <c:forEach items="${requestScope.preguntas}" var="preg">
            <tr>
                <td>${preg.titulo}</td>
            </tr>
            <c:forEach items="${preg.getRespuestas()}" var="resp">
                <tr>
                    <td>
                    </td>
                    <td>${resp.descripcion}</td>
                    <td><input type="radio" name="${preg.titulo}" value="${resp.descripcion}"></td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
    <div align="center"><input type="submit" class="success button"></input></div>
</form>
</body>
</html>
