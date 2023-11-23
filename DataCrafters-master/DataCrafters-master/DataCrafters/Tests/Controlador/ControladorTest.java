package Controlador;

import static org.junit.jupiter.api.Assertions.*;
import Modelo.Articulo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ControladorTest {

    Controlador controlador;

    // Test para crear un artículo y buscarlo por su código
    @BeforeEach
    public void setUp() {
        controlador = new Controlador();
    }

    @Test
    public void testCrearArticulo() {
        controlador.crearArticulo("codigo1", "desc", 100.0, 10, 5.0);
        Articulo result = controlador.buscarPorCodigo("codigo1");
        assertNotNull(result);
        assertEquals("codigo1", ((Articulo) result).getCodigo());
    }


}