package co.edu.escuelaing.dosw.skycampus.pattern.observer;

import co.edu.escuelaing.dosw.skycampus.model.Drone;
import co.edu.escuelaing.dosw.skycampus.model.EstadoDrone;
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
}