package Vista;

import Controlador.Controlador;

public class OnlineStore {
    public static void main(String[] args) {
        try (Controlador controlador = new Controlador()) {
            GestionOS gestion = new GestionOS(controlador);
            gestion.inicio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
