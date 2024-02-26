import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.ModeloDatos;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ModeloDatosTest {

    private final Logger logger = Logger.getLogger(ModeloDatos.class.getName());

    @Test
    void testExisteJugador() {
        logger.info("Prueba de existeJugador");
        String nombre = "";
        ModeloDatos instance = new ModeloDatos();
        boolean expResult = false;
        boolean result = instance.existeJugador(nombre);
        assertEquals(expResult, result);
        //fail("Fallo forzado.");
    }

    @Test
    void testActualizarJugador() {
        logger.info("Prueba de actualizarJugador");
        ModeloDatos modeloDatosMock = Mockito.mock(ModeloDatos.class);
        String jugador = "Rudy";
        int votosAntes = 5;
        Mockito.when(modeloDatosMock.actualizarJugador(jugador)).thenReturn(votosAntes + 1);
        assertEquals(votosAntes + 1, modeloDatosMock.actualizarJugador(jugador));
    }
}
