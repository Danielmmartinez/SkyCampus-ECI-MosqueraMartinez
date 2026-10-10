package co.edu.escuelaing.dosw.skycampus.service;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.Mision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.Prioridad;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoCarga;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoDrone;
import co.edu.escuelaing.dosw.skycampus.core.services.AnalyticsFlotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsFlotaServiceTest {

    private AnalyticsFlotaService analytics;

    @BeforeEach
    void setUp() {
        analytics = new AnalyticsFlotaService();
    }

    @Test
    @DisplayName("Analytics 1: Misiones completadas agrupadas por tipo de drone")
    void misionesCompletadasPorTipo_agrupaCorrectamente() {
        Drone mini = new Drone("D-01", "DJI", TipoDrone.MINI, 90, true, EstadoDrone.DISPONIBLE, "A");
        Drone express = new Drone("D-02", "DJI", TipoDrone.EXPRESS, 90, true, EstadoDrone.DISPONIBLE, "A");

        List<Mision> misiones = List.of(
                new Mision("M-1", mini, "A", "B", 100, TipoCarga.SOBRE, Prioridad.NORMAL, EstadoMision.ENTREGADA, LocalDateTime.now(), ""),
                new Mision("M-2", mini, "A", "B", 100, TipoCarga.SOBRE, Prioridad.NORMAL, EstadoMision.ENTREGADA, LocalDateTime.now(), ""),
                new Mision("M-3", express, "A", "B", 100, TipoCarga.SOBRE, Prioridad.NORMAL, EstadoMision.ENTREGADA, LocalDateTime.now(), ""),
                new Mision("M-4", express, "A", "B", 100, TipoCarga.SOBRE, Prioridad.NORMAL, EstadoMision.FALLIDA, LocalDateTime.now(), "")
        );

        Map<TipoDrone, Long> res = analytics.misionesCompletadasPorTipo(misiones);
        assertEquals(2L, res.get(TipoDrone.MINI));
        assertEquals(1L, res.get(TipoDrone.EXPRESS));
    }

    @Test
    @DisplayName("Analytics 2: Drone con más misiones completadas")
    void droneConMasMisionesCompletadas_retornaMaximo() {
        Drone d1 = new Drone("D-01", "DJI", 80, true, "A");
        Drone d2 = new Drone("D-02", "DJI", 80, true, "A");

        List<Mision> misiones = List.of(
                new Mision("M-1", d1, "A", "B", TipoCarga.SOBRE, EstadoMision.ENTREGADA),
                new Mision("M-2", d2, "A", "B", TipoCarga.SOBRE, EstadoMision.ENTREGADA),
                new Mision("M-3", d2, "A", "B", TipoCarga.SOBRE, EstadoMision.ENTREGADA)
        );

        Optional<String> max = analytics.droneConMasMisionesCompletadas(misiones);
        assertTrue(max.isPresent());
        assertEquals("D-02", max.get());
    }

    @Test
    @DisplayName("Analytics 3: Porcentaje de misiones fallidas sobre el total")
    void porcentajeMisionesFallidas_calculaExacto() {
        List<Mision> misiones = List.of(
                new Mision("M-1", null, "A", "B", TipoCarga.SOBRE, EstadoMision.ENTREGADA),
                new Mision("M-2", null, "A", "B", TipoCarga.SOBRE, EstadoMision.FALLIDA)
        );

        assertEquals(50.0, analytics.porcentajeMisionesFallidas(misiones));
        assertEquals(0.0, analytics.porcentajeMisionesFallidas(List.of()));
    }

    @Test
    @DisplayName("Analytics 4: PartitioningBy drones operativos vs en fallo")
    void clasificarOperatividad_particionaCorrectamente() {
        List<Drone> flota = List.of(
                new Drone("D-01", "DJI", TipoDrone.MINI, 80, true, EstadoDrone.DISPONIBLE, "A"),
                new Drone("D-02", "DJI", TipoDrone.MINI, 80, false, EstadoDrone.FALLO, "A"),
                new Drone("D-03", "DJI", TipoDrone.MINI, 80, false, EstadoDrone.EN_MANTENIMIENTO, "A")
        );

        Map<Boolean, List<Drone>> map = analytics.clasificarOperatividad(flota);
        assertEquals(1, map.get(true).size());
        assertEquals(2, map.get(false).size());
    }

    @Test
    @DisplayName("Analytics 5: Detecta si existe misión urgente retrasada (>10 min)")
    void existeMisionUrgenteRetrasada_detectaRetraso() {
        Mision retrasada = new Mision("M-1", null, "A", "B", 100, TipoCarga.SOBRE, Prioridad.URGENTE, EstadoMision.PENDIENTE, LocalDateTime.now().minusMinutes(15), "");
        Mision aTiempo = new Mision("M-2", null, "A", "B", 100, TipoCarga.SOBRE, Prioridad.URGENTE, EstadoMision.PENDIENTE, LocalDateTime.now().minusMinutes(2), "");

        assertTrue(analytics.existeMisionUrgenteRetrasada(List.of(retrasada), 10));
        assertFalse(analytics.existeMisionUrgenteRetrasada(List.of(aTiempo), 10));
    }
}