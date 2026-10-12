package co.edu.escuelaing.dosw.skycampus.core.patterns.observer;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;

import java.util.ArrayList;
import java.util.List;

public class SistemaLog implements ObservadorDrone {
    private final List<String> logs = new ArrayList<>();

    @Override
    public void onEstadoCambiado(Drone drone, EstadoDrone nuevoEstado) {
        logs.add("[LOG] Drone " + drone.id() + " registrado en estado: " + nuevoEstado);
    }

    public List<String> getLogs() {
        return logs;
    }
}