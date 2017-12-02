<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link href="css/foundation.css" rel="stylesheet"/>
    <link href="css/Investigador.css" rel="stylesheet"/>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>Agregar Pregunta</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.6/angular.min.js"></script>
    <script>
        var cont=0;
        function create(htmlStr) {
            var frag = document.createDocumentFragment(),
                temp = document.createElement('tbody');
            temp.innerHTML = htmlStr;
            while (temp.firstChild) {
                frag.appendChild(temp.firstChild);
            }
            return frag;
        }

        function anadir() {
            cont++;
            var fragment = create('<tbody><tr><td><input type="text" placeholder="Respuesta '+cont+'" name="'+cont+'"/></td></tr></tbody>');

            document.getElementById("generate-here").appendChild(fragment);
        }
    </script>
</head>
<body>
<h5>A&ntilde;adir pregunta a cuestionario ${requestScope.idCuestionario}</h5>
<form action="AgregarRespuesta" method="post">
    <input name="pregunta" type="hidden" value="${requestScope.pregunta}"/>
    <input name="Cuestionario" type="hidden" value="${requestScope.idCuestionario}"/>
    <input name="user" type="hidden" value="${requestScope.user}"/>
    <input name="password" type="hidden" value="${requestScope.password}">
    <table>
        <tbody id="generate-here">
        <tr>
            <th rowspan="100">${requestScope.pregunta}</th>
            <th colspan="2">Respuestas</th>
        </tr>
        </tbody>
    </table>
    <div align="center">
        <a onclick="anadir()" type="button" class="button">A&ntilde;adir respuesta</a>
        <input type="submit" class="success button" value="Terminar pregunta">
    </div>

</form>

</body>
</html>
