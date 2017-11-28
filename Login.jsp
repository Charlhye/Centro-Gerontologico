<!DOCTYPE jsp>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
        <link href="css/foundation.css" rel="stylesheet"/>
        <link href="css/Login.css" rel="stylesheet"/>
        <title>Log in</title>
    </head>
    <body>
      <fieldset>
        <div>
            <form action="Login" class="form-signin" method="post">
                <div align="center"><h2 class="form-signin-heading">Iniciar sesi&oacute;n</h2></div>
                <input name="user" type="text" id="inputUser" class="form-control" placeholder="Usuario"  required autofocus/>
                <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Contrase&ntilde;a" required/>
                <div align="center"><input name="submit" type="submit" class="button"/><br/></div>
            </form>
            <div class="row">
                <div align="center" class="large-4 medium-4 columns alert">
                    <label style="color:red">${requestScope.error}</label>
                </div>
            </div>
        </div> <!-- /container -->
      </fieldset>
    </body>
</html>
