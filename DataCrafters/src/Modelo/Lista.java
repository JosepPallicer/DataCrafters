package Modelo;

import java.util.ArrayList;

public class Lista<T> {
    protected ArrayList<T> lista;

    public Lista() {
        lista = new ArrayList<>();
    }

    public int numElementos() {
        return lista.size();
    }

    public void agregar(T t) {
        lista.add(t);
    }

    public void eliminar(T t) {
        lista.remove(t);
    }

    public T posicion(int position) {
        if (position >= 0 && position < lista.size()) {
            return lista.get(position);
        } else {
            return null;
        }
    }

    public void limpiar() {
        lista.clear();
    }

    public boolean estaVacia() {
        return lista.isEmpty();
    }

    public void mostrar() {
        for (T t : lista) {
            System.out.println(t);
        }
    }

    public ArrayList<T> obtenerLista() {
        return lista;
    }
}


