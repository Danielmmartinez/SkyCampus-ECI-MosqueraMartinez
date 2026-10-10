package co.edu.escuelaing.dosw.skycampus.core.patterns.observer;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;

public class AlertaTecnico implements ObservadorDrone {
    private boolean alertaDisparada = false;

    @Override
    public void onEstadoCambiado(Drone drone, EstadoDrone nuevoEstado) {
        if (nuevoEstado == EstadoDrone.FALLO) {
            this.alertaDisparada = true;
        }
    }

    public boolean isAlertaDisparada() {
        return alertaDisparada;
    }
}