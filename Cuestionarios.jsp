<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link href="css/foundation.css" rel="stylesheet"/>
    <link href="css/Investigador.css" rel="stylesheet"/>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>Agregar Cuestionario</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script>
        function OtroEnab(pregu) {
            $("#"+pregu).prop("disabled", false);
        }
        function OtroDis(pregu) {
            $("#"+pregu).prop("disabled", true);
            $("#"+pregu).val("");
        }
    </script>
</head>

<body>
<form action="AgregarCuestionario" method="post">
    <table>
        <tr>
            <th>Pregunta</th>
            <th colspan="2">Respuesta</th>
        </tr>

        <input name="user" type="hidden" value="${requestScope.user}"/>
        <input name="password" type="hidden" value="${requestScope.password}">
        <<input type="hidden" name="Cuestionario" value="${requestScope.cuestionario}">
        <c:forEach items="${requestScope.preguntas}" var="preg">
            <tr>
                <td rowspan="${preg.getRespuestas().size()+2}">${preg.titulo}</td>
            </tr>
            <c:forEach items="${preg.getRespuestas()}" var="resp">
                <tr>
                    <td><input onclick="OtroDis('${preg.idPregunta}-other')" type="radio" name="${preg.idPregunta}" value="${resp.descripcion}"> ${resp.descripcion}</td>
                </tr>
            </c:forEach>
            <tr>
                <td><input onclick="OtroEnab('${preg.idPregunta}-other')" type="radio" name="${preg.idPregunta}" value="Otro"> Otro<input disabled type="text" name="${preg.idPregunta}-other" id="${preg.idPregunta}-other"></td>
            </tr>
        </c:forEach>
    </table>
    <div align="center"><input type="submit" class="success button"></input></div>
</form>
</body>



</html>
