package co.edu.escuelaing.dosw.skycampus.pattern.observer;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;
import co.edu.escuelaing.dosw.skycampus.core.patterns.observer.AlertaTecnico;
import co.edu.escuelaing.dosw.skycampus.core.patterns.observer.GestorFlotaObservable;
import co.edu.escuelaing.dosw.skycampus.core.patterns.observer.PanelOperador;
import co.edu.escuelaing.dosw.skycampus.core.patterns.observer.SistemaLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObserverTest {

    @Test
    @DisplayName("Observer: Notifica a todos los suscriptores y activa alerta solo en FALLO")
    void observer_notificaSuscriptores() {
        GestorFlotaObservable gestor = new GestorFlotaObservable();
        PanelOperador panel = new PanelOperador();
        AlertaTecnico alerta = new AlertaTecnico();
        SistemaLog log = new SistemaLog();

        gestor.suscribir(panel);
        gestor.suscribir(alerta);
        gestor.suscribir(log);

        Drone drone = new Drone("D-01", "DJI Mini 3", 80, true, "Bloque A");

        // Evento 1: Despegue normal
        gestor.notificarCambio(drone, EstadoDrone.EN_VUELO);
        assertEquals(1, panel.getNotificaciones().size());
        assertEquals(1, log.getLogs().size());
        assertFalse(alerta.isAlertaDisparada());

        // Evento 2: Fallo técnico
        gestor.notificarCambio(drone, EstadoDrone.FALLO);
        assertEquals(2, panel.getNotificaciones().size());
        assertTrue(alerta.isAlertaDisparada());
    }
}