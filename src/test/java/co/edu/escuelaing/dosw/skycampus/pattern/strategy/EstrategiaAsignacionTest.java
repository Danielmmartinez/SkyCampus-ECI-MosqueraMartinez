package co.edu.escuelaing.dosw.skycampus.pattern.strategy;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.Mision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.Prioridad;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoCarga;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoDrone;
import co.edu.escuelaing.dosw.skycampus.core.patterns.strategy.AsignacionMayorBateria;
import co.edu.escuelaing.dosw.skycampus.core.patterns.strategy.AsignacionPorCapacidadCarga;
import co.edu.escuelaing.dosw.skycampus.core.patterns.strategy.AsignacionUrgenteExpress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EstrategiaAsignacionTest {

    private final AsignacionMayorBateria estrategiaMayorBateria = new AsignacionMayorBateria();
    private final AsignacionUrgenteExpress estrategiaExpress = new AsignacionUrgenteExpress();
    private final AsignacionPorCapacidadCarga estrategiaCarga = new AsignacionPorCapacidadCarga();

    @Test
    @DisplayName("AsignacionMayorBateria: Selecciona el drone disponible con mayor batería")
    void mayorBateria_seleccionaCorrecto() {
        List<Drone> flota = List.of(
                new Drone("D-01", "DJI Mini 3", 60, true, "Bloque A"),
                new Drone("D-02", "DJI Mini 3", 95, false, "Bloque B"),
                new Drone("D-03", "DJI Mini 3", 85, true, "Bloque C")
        );
        Mision mision = new Mision("M-01", null, "Bloque A", "Biblioteca", TipoCarga.SOBRE, EstadoMision.PENDIENTE);

        Optional<Drone> seleccionado = estrategiaMayorBateria.seleccionarDrone(flota, mision);

        assertTrue(seleccionado.isPresent());
        assertEquals("D-03", seleccionado.get().id());
    }

    @Test
    @DisplayName("AsignacionUrgenteExpress: Prefiere drone EXPRESS disponible")
    void urgenteExpress_prefiereExpress() {
        Drone express = new Drone("D-EXP", "DJI", TipoDrone.EXPRESS, 70, true, EstadoDrone.DISPONIBLE, "Bloque A");
        Drone standard = new Drone("D-STD", "DJI", TipoDrone.MINI, 90, true, EstadoDrone.DISPONIBLE, "Bloque A");

        Mision mision = new Mision("M-02", null, "Bloque A", "Biblioteca", TipoCarga.SOBRE, EstadoMision.PENDIENTE);

        Optional<Drone> seleccionado = estrategiaExpress.seleccionarDrone(List.of(standard, express), mision);

        assertTrue(seleccionado.isPresent());
        assertEquals("D-EXP", seleccionado.get().id());
    }

    @Test
    @DisplayName("AsignacionPorCapacidadCarga: Filtra drones que no soportan el peso")
    void capacidadCarga_filtraPorPeso() {
        Drone mini = new Drone("D-MINI", "DJI", TipoDrone.MINI, 90, true, EstadoDrone.DISPONIBLE, "Bloque A"); // 500g max
        Drone cargo = new Drone("D-CARGO", "DJI", TipoDrone.CARGO, 75, true, EstadoDrone.DISPONIBLE, "Bloque A"); // 2000g max

        Mision misionPesada = new Mision("M-03", null, "Bloque A", "Biblioteca", 700, TipoCarga.EQUIPO, Prioridad.NORMAL, EstadoMision.PENDIENTE, LocalDateTime.now(), "");

        Optional<Drone> seleccionado = estrategiaCarga.seleccionarDrone(List.of(mini, cargo), misionPesada);

        assertTrue(seleccionado.isPresent());
        assertEquals("D-CARGO", seleccionado.get().id());
    }
}