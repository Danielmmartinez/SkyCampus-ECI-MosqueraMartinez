package co.edu.escuelaing.dosw.skycampus.pattern.strategy;

import co.edu.escuelaing.dosw.skycampus.model.Drone;
import co.edu.escuelaing.dosw.skycampus.model.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.model.Mision;
import co.edu.escuelaing.dosw.skycampus.model.TipoCarga;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EstrategiaAsignacionTest {

    private final EstrategiaAsignacion estrategia = new AsignacionMayorBateria();

    @Test
    @DisplayName("AsignacionMayorBateria: Selecciona el drone disponible con la mayor batería")
    void seleccionarDrone_seleccionaMayorBateriaDisponible() {
        List<Drone> flota = List.of(
                new Drone("D-01", "DJI Mini 3", 60, true, "Bloque A"),
                new Drone("D-02", "DJI Mini 3", 95, false, "Bloque B"), // Mayor batería pero NO disponible
                new Drone("D-03", "DJI Mini 3", 85, true, "Bloque C"),  // Ganador
                new Drone("D-04", "DJI Mini 3", 40, true, "Bloque D")
        );

        Mision mision = new Mision("M-01", null, "Bloque A", "Biblioteca", TipoCarga.SOBRE, EstadoMision.PENDIENTE);

        Optional<Drone> seleccionado = estrategia.seleccionarDrone(flota, mision);

        assertTrue(seleccionado.isPresent());
        assertEquals("D-03", seleccionado.get().id());
        assertEquals(85, seleccionado.get().bateria());
    }

    @Test
    @DisplayName("AsignacionMayorBateria: Retorna Optional.empty si no hay drones disponibles")
    void seleccionarDrone_sinDisponibles_retornaVacio() {
        List<Drone> flota = List.of(
                new Drone("D-01", "DJI Mini 3", 90, false, "Bloque A"),
                new Drone("D-02", "DJI Mini 3", 80, false, "Bloque B")
        );

        Optional<Drone> seleccionado = estrategia.seleccionarDrone(flota, null);

        assertTrue(seleccionado.isEmpty());
    }
}