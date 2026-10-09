package co.edu.escuelaing.dosw.skycampus.pattern.observer;

import co.edu.escuelaing.dosw.skycampus.model.Drone;
import co.edu.escuelaing.dosw.skycampus.model.EstadoDrone;
import java.util.ArrayList;
import java.util.List;

public class PanelOperador implements ObservadorDrone {
    private final List<String> notificaciones = new ArrayList<>();

    @Override
    public void onEstadoCambiado(Drone drone, EstadoDrone nuevoEstado) {
        notificaciones.add("Panel: Drone " + drone.id() + " cambió a " + nuevoEstado);
    }

    public List<String> getNotificaciones() { return notificaciones; }
}

class SistemaLog implements ObservadorDrone {
    @Override
    public void onEstadoCambiado(Drone drone, EstadoDrone nuevoEstado) {
        // En producción registraría a log
    }
}

class AlertaTecnico implements ObservadorDrone {
    private boolean alertaDisparada = false;

    @Override
    public void onEstadoCambiado(Drone drone, EstadoDrone nuevoEstado) {
        if (nuevoEstado == EstadoDrone.FALLO) {
            this.alertaDisparada = true;
        }
    }

    public boolean isAlertaDisparada() { return alertaDisparada; }
}