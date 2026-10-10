package co.edu.escuelaing.dosw.skycampus.core.patterns.observer;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;

import java.util.ArrayList;
import java.util.List;

public class PanelOperador implements ObservadorDrone {
    private final List<String> notificaciones = new ArrayList<>();

    @Override
    public void onEstadoCambiado(Drone drone, EstadoDrone nuevoEstado) {
        notificaciones.add("Panel: Drone " + drone.id() + " cambió a " + nuevoEstado);
    }

    public List<String> getNotificaciones() {
        return notificaciones;
    }
}