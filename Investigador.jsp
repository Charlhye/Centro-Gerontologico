<!DOCTYPE jsp>
<html>
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>    
    <link href="css/foundation.css" rel="stylesheet"/>
    <link href="css/Investigador.css" rel="stylesheet"/>
    <title>Maria Jimenez</title>
  </head>
  <script>
    function getSelected(){
      alert(document.querySelector('input[name="encuestado"]:checked').value);
    }
  </script>
  <header>
    <h1 align="center">Bienvenido Maria Jimenez</h1>
  </header>
  <body>
    <form action="" method="post" target="_blank">
      <div style="margin: 20px;">
        <table style="width:100% ">
          <tr>
            <th></th>
            <th>Nombre</th>
            <th>Fecha</th>
          </tr>
          <tr>
            <td><input type="radio" name="encuestado" value="1"></td>
            <td><a>Enrique Martinez Martinez</a></td>
            <td>08/oct/2017</td>
          </tr>
          <tr>
            <td><input type="radio" name="encuestado" value="2"></td>
            <td><a>Jorge Hernandez Ochoa</a></td>
            <td>05/sep/2017</td>
          </tr>
        </table>
      </div>
      <div class="wrapper">
          <a type="submit" onclick="getSelected()" class="success button">Generar Reporte</a>
      </div>
    </form>
  </body>
</html>
