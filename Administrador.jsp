<!DOCTYPE jsp>
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
        <form action="" method="post">
          <input type="text" placeholder="Nombre del usuario"/>
          <input type="password" placeholder="Nuevo password"/>
          <select>
            <option value="Investigador">Investigador</option>
            <option value="Trabajador">Trabajador</option>
          </select>
          <div align="center"><a type="submit" class="success button">Dar de alta</a></div>
        </form>
      </div>
      <div class="large-6 medium-6 cell">
        <h5 align="center">Dar de baja</h5>
        <form action="" method="post">
          <select>

          </select>
          <div align="center"><a type="submit" class="alert button">Dar de baja</a></div>
        </form>
      </div>
    </div>
    <div class="grid-x grid-padding-x">
      <div class="large-6 medium-6 cell">
        <h5 align="center">Cambiar nombre de usuario</h5>
          <form action="" method="post">
            <input type="text" placeholder="Nombre del usuario anterior"/>
            <input type="text" placeholder="Nuevo nombre de usuario"/>
            <input type="password" placeholder="Password"/>
            <div align="center"><a type="submit" class="button">Cambiar</a></div>
          </form>
      </div>
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
