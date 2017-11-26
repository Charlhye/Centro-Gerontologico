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
    function getSelected(){
      alert(document.querySelector('input[name="encuestado"]:checked').value);
    }
  </script>
  <header>
    <h1 align="center">Bienvenido ${requestScope.user}</h1>
  </header>
  <body>
    <form action="" method="post" target="_blank">
      <div style="margin: 20px;">
        <table style="width:100% ">
          <tr>
            <th></th>
            <th>Id del Cuestionario</th>
            <th>Id del Trabajador</th>
          </tr>
          <c:forEach items="${requestScope.cuestionarios}" var="it">
            <tr>
              <td></td>
              <td>${it.idCuestionarioResuelto}</td>
              <td>${it.nombreUsuario}</td>
            </tr>
          </c:forEach>
        </table>
      </div>
      <div class="wrapper">
          <a type="submit" onclick="getSelected()" class="success button">Generar Reporte</a>
      </div>
    </form>
  </body>
</html>
