<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
  <head>
    <title>Votaci&oacute;n mejor jugador liga ACB</title>
    <link href="estilos.css" rel="stylesheet" type="text/css" />
  </head>
  <body class="resultado">
    <h1>Votos</h1>

    <ul>
        <ul>
            <c:forEach var="item" items="${jugadores}">
                <li>
                    <span class="voto">${item.nombre} - ${item.votos}</span>
                </li>
            </c:forEach>
        </ul>
    </ul>

    <a href="index.html"> Ir al comienzo</a>
  </body>
</html>
