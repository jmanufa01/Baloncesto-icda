<!DOCTYPE html>
<html lang="es">
  <head>
      <title>Votaci&oacute;n mejor jugador liga ACB</title>
      <link href="estilos.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
      Votaci&oacute;n al mejor jugador de la liga ACB
      <hr />
      <% String nombreP = (String) session.getAttribute("nombreCliente"); %>
      <br />Muchas gracias <%=nombreP%> por su voto
      <br />
      <br />
      <a href="index.html"> Ir al comienzo</a>
  </body>
</html>

