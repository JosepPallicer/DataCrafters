
package Controlador;

import static org.junit.jupiter.api.Assertions.*;

import Modelo.Articulo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ControladorTest {
    private Controlador controlador;


    public void setUp() {
        controlador = new Controlador();
    }

    @Test
    public void testAgregarArticulo() {
        // Prueba la lógica de negocio al agregar un artículo
        controlador.agregarArticulo("Sacapuntas", "Sacapuntas Road", 3.5, 1, 1.5);

        // Verifica que el artículo se haya agregado correctamente
        Articulo result = controlador.buscarArticulo(String.valueOf(Integer.parseInt("Sacapuntas")));
        assertNotNull(result);
        assertEquals("Sacapuntas", result.getNombre());
        assertEquals("Sacapuntas Road", result.getDescripcion());
        assertEquals(3.5, result.getPrecio(), 0.01); // se usa delta para comparar los valores double
        assertEquals(1, result.getTiempoPreparacion());
        assertEquals(1.5, result.getGastosEnvio(), 0.01);
    }
    @Test
    public void testBuscarArticulo() {
        // Le voy a crear un artículo para poder buscarlo más adelante
        controlador.agregarArticulo("Rotuladores", "Rotuladores punta fina Sman", 5.25, 1, 1);

        // Buscar el artículo por nombre
        Articulo result = controlador.buscarArticulo("Rotuladores");

        // Mirar si el artículo se ha encontrado bien
        assertNotNull(result);
        assertEquals("Rotuladores", result.getNombre());
        assertEquals("Rotuladores punta fina Sman", result.getDescripcion());
        assertEquals(5.25, result.getPrecio(), 0.01); // Utilizando delta para la comparación de valores double
        assertEquals(1, result.getTiempoPreparacion());
        assertEquals(1, result.getGastosEnvio(), 0.01);
    }

}

