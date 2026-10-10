package co.edu.escuelaing.dosw.skycampus.core.patterns.observer;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;

import java.util.ArrayList;
import java.util.List;

public class GestorFlotaObservable {
    private final List<ObservadorDrone> observadores = new ArrayList<>();

    public void suscribir(ObservadorDrone observador) {
        observadores.add(observador);
    }

    public void notificarCambio(Drone drone, EstadoDrone nuevoEstado) {
        observadores.forEach(obs -> obs.onEstadoCambiado(drone, nuevoEstado));
    }

    public int totalObservadores() {
        return observadores.size();
    }
}