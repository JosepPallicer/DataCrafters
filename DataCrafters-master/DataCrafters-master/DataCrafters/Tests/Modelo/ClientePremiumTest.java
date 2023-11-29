package Modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class ClientePremiumTest {
    @Test
    public void testCalcularDtoGastosEnvio() {
        ClientePremium cliente = new ClientePremium("Nombre", "Domicilio", "NIF", "Email");
        cliente.setDtoGastosEnvio(0.2);

        double gastosEnvioOriginales = 100.0;
        double expected = 80.0;

        double result = cliente.calcularDtoGastosEnvio(gastosEnvioOriginales);

        assertEquals(expected, result);
    }
}