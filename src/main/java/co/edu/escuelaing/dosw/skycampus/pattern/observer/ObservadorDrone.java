package co.edu.escuelaing.dosw.skycampus.pattern.observer;

import co.edu.escuelaing.dosw.skycampus.model.Drone;
import co.edu.escuelaing.dosw.skycampus.model.EstadoDrone;

public interface ObservadorDrone {
    void onEstadoCambiado(Drone drone, EstadoDrone nuevoEstado);
}