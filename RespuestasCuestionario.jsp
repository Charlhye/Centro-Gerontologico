<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link href="css/foundation.css" rel="stylesheet"/>
    <link href="css/Investigador.css" rel="stylesheet"/>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>Cuestionario</title>
</head>
<body>
    <table>
        <tr>
            <th>Pregunta</th>
            <th>Respuesta</th>
        </tr>
        <c:forEach items="${requestScope.preguntas}" var="it">
            <tr>
                <td>${it.titulo}</td>
                <c:forEach items="${it.respuestas}" var="resp">
                    <td>${resp.descripcion}</td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
