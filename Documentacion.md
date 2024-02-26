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
Tras pulsar el botón comprobamos que funciona correctamente:

![img_5.png](img_5.png)


