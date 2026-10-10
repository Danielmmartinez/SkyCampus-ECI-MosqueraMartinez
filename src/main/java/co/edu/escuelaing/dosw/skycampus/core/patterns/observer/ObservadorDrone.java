package co.edu.escuelaing.dosw.skycampus.core.patterns.observer;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;

public interface ObservadorDrone {
    void onEstadoCambiado(Drone drone, EstadoDrone nuevoEstado);
}