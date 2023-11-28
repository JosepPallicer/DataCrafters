package Vista;

import Controlador.Controlador;

/**
 * Clase principal que inicia la aplicación de la tienda en línea.
 */
public class OnlineStore {
    /**
     * Método principal que crea y utiliza un objeto Controlador y GestionOS para iniciar la aplicación.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        try (Controlador controlador = new Controlador()) {
            GestionOS gestion = new GestionOS(controlador);
            gestion.inicio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
