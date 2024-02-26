# Práctica Final

## Descripción
En este archivo tipo README.md se incluye la documentación de la práctica final
de Integración Contínua en el Desarrollo Ágil, con la realización de los cambios pedidos
sobre el proyecto de la práctica 5.

## Índice
- [Práctica Final](#práctica-final)
    - [Trabajo "stage"](#trabajo-stage)


## Trabajo "stage"
En primer lugar, se añade al workflow un trabajo "stage" para desplegar la aplicación en un entorno de pre-producción.
En este caso llamado "baloncesto-pre-jmfando".
    
```yaml
stage:
needs: qa
runs-on: ubuntu-latest
if: github.ref == 'refs/heads/main'
steps:
- name: Descargar repositorio
  uses: actions/checkout@v3
- name: Crear el archivo .war
  run: |
    mvn package -DskipTests=true
- name: Desplegar en Azure
  uses: Azure/webapps-deploy@v2
  with:
    app-name: baloncesto-pre-jmfando
    publish-profile: ${{ secrets.AZURE_WEBAPP_PUBLISH_PROFILE_PRE }}
    package: target/*.war
```

Este trabajo será prácticamente igual que el de deploy, pero no será necesaria la 
aprovación manual para desplegar en pre-producción. 

Ahora le añadimos al job "deploy" que
espere a que el job "stage" termine para poder ejecutarse. Esto tiene sentido ya que si hay algún 
error en pre-producción, no tendría sentido desplegar en producción.

```yaml
  deploy:
    needs: stage
    runs-on: ubuntu-latest
```

## Creación Milestone

Se ha creado un Milestone llamado "Práctica Final" para poder asignar las issues requeridas
en la práctica.

![img_1.png](img_1.png)

También se añaden las issues al proyecto "Baloncesto" para llevar el control de las tareas.

![img_2.png](img_2.png)

## REQ-1: Votos a 0

Para realizar esta tarea, junto con la mejora de la calidad del código (QA) y la PU, se ha 
creado una nueva rama llamada "feat/REQ-1-Votos-a-0" para realizar los cambios.

![img_3.png](img_3.png)

Para realizar el reseteo de votos, actualizamos la clase ModeloDatos.java añadiendo una sentencia
SQL que actualice los votos a 0.

```java
    public void resetVotos(){
        try {
            set = con.createStatement();
            set.executeUpdate("UPDATE Jugadores SET votos = 0");
            rs.close();
            set.close();
        } catch (Exception e) {
            // No modifica la tabla
            logger.info("No modifica la tabla");
            logger.info("El error es: " + e.getMessage());
        }
    }
```

En el archivo index.html, añadimos un botón para realizar el reseteo de votos.

```html
    <form action="resetVotos" method="post">
        <input type="submit" value="Resetear Votos">
    </form>
```

Y conectamos el servlet en el archivo web.xml.

```xml
    <servlet>
        <servlet-name>ResetVotos</servlet-name>
        <servlet-class>es.iespuertodelacruz.jmfranqueado.baloncesto.controlador.ResetVotos</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>ResetVotos</servlet-name>
        <url-pattern>/resetVotos</url-pattern>
    </servlet-mapping>
```
Vemos que el botón aparece en la página principal de la aplicación.

![img_7.png](img_7.png)

Tras pulsar el botón comprobamos que se han reseteado los votos correctamente.

![img_5.png](img_5.png)

Con respecto a la prueba unitaria, he modificado el archivo ModeloDatosTest.java añadiendo
una nueva consulta a la hora de actualizar el voto, obteniendo el voto actual y devolviéndolo, y hacemos un Mock de 
esa clase en el test, para que simular la respuesta retornando el valor siguiente.

```java
public int actualizarJugador(String nombre) {
    try {
        set = con.createStatement();
        set.executeUpdate("UPDATE Jugadores SET votos = votos + 1 WHERE nombre " + " LIKE '%" + nombre + "%'");
        rs = set.executeQuery("SELECT * FROM Jugadores WHERE nombre LIKE '%" + nombre + "%'");
        int votos = rs.getInt("votos");
        rs.close();
        set.close();
        return votos;
    } catch (Exception e) {
        // No modifica la tabla
        logger.info("No modifica la tabla");
        logger.info(ERROR + e.getMessage());
    }
    return 0;
}

@Test
public void testActualizarJugador() {
        logger.info("Prueba de actualizarJugador");
        ModeloDatos modeloDatosMock = Mockito.mock(ModeloDatos.class);
        String jugador = "Rudy";
        int votosAntes = 5;
        Mockito.when(modeloDatosMock.actualizarJugador(jugador)).thenReturn(votosAntes + 1);
        assertEquals(votosAntes + 1, modeloDatosMock.actualizarJugador(jugador));
}
```

Tras realizar el commit, en el job "build" comprobamos que los 2 tests pasan correctamente.

![img_6.png](img_6.png)

Ahora realizamos el merge de la rama "feat/REQ-1-Votos-a-0" a "main" y comprobamos que el despliegue funciona correctamente.

![img_8.png](img_8.png)

Vemos que se ha desplegado correctamente en pre-producción. Revisamos que esté todo bien y aprobamos el despliegue en producción.

![img_9.png](img_9.png)

![img_10.png](img_10.png)

Una vez finalizado el primer requerimiento, actualizamos las tareas en el tablero, cerrando las tareas finalizadas. Quedando así el
milestone:

![img_11.png](img_11.png)

## REQ-2: Ver Votos

El resto de requerimientos lo haremos en otra rama llamada "feat/REQ-2-Ver-Votos".



