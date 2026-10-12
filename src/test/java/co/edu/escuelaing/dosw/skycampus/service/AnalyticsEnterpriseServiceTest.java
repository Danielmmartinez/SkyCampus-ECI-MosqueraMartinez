package co.edu.escuelaing.dosw.skycampus.service;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.Mision;
import co.edu.escuelaing.dosw.skycampus.core.models.Sede;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoDrone;
import co.edu.escuelaing.dosw.skycampus.core.services.AnalyticsEnterpriseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsEnterpriseServiceTest {

    private AnalyticsEnterpriseService analytics;

    @BeforeEach
    void setUp() {
        analytics = new AnalyticsEnterpriseService();
    }

    @Test
    @DisplayName("Infernape 1: Sede más activa con Optional y caso vacío")
    void obtenerSedeMasActiva_retornaCorrectoYVacio() {
        Sede eci = new Sede("1", "ECI", 95.0, 50);
        Sede unal = new Sede("2", "UNAL", 90.0, 80);

        Optional<Sede> max = analytics.obtenerSedeMasActiva(List.of(eci, unal));
        assertTrue(max.isPresent());
        assertEquals("UNAL", max.get().nombre());

        Optional<Sede> vacia = analytics.obtenerSedeMasActiva(List.of());
        assertTrue(vacia.isEmpty());
    }

    @Test
    @DisplayName("Infernape 2: Ranking multi-criterio (Tasa éxito DESC -> Misiones DESC -> Nombre ASC)")
    void clasificarRankingSedes_ordenaConTresCriterios() {
        Sede s1 = new Sede("1", "Uniandes", 90.0, 50);
        Sede s2 = new Sede("2", "ECI", 95.0, 30);
        Sede s3 = new Sede("3", "EAFIT", 90.0, 60);
        Sede s4 = new Sede("4", "UNAL", 90.0, 50);

        List<Sede> ranking = analytics.clasificarRankingSedes(List.of(s1, s2, s3, s4));

        assertEquals("ECI", ranking.get(0).nombre());      // 95% éxito
        assertEquals("EAFIT", ranking.get(1).nombre());    // 90% éxito, 60 misiones
        assertEquals("UNAL", ranking.get(2).nombre());     // 90% éxito, 50 misiones, alfabético antes que Uniandes
        assertEquals("Uniandes", ranking.get(3).nombre()); // 90% éxito, 50 misiones
    }

    @Test
    @DisplayName("Infernape 3: Collectors.teeing() en una sola pasada (entregadas y tiempo promedio)")
    void calcularMetricasSede_usaTeeingExacto() {
        List<Mision> misiones = List.of(
                new Mision("M-1", null, "A", "B", 100, null, null, EstadoMision.ENTREGADA, LocalDateTime.now(), "", "ECI", 20.0),
                new Mision("M-2", null, "A", "B", 100, null, null, EstadoMision.ENTREGADA, LocalDateTime.now(), "", "ECI", 10.0),
                new Mision("M-3", null, "A", "B", 100, null, null, EstadoMision.FALLIDA, LocalDateTime.now(), "", "ECI", 40.0),
                new Mision("M-4", null, "A", "B", 100, null, null, EstadoMision.ENTREGADA, LocalDateTime.now(), "", "UNAL", 15.0)
        );

        Map.Entry<Long, Double> metricasECI = analytics.calcularMetricasSede(misiones, "ECI");
        assertEquals(2L, metricasECI.getKey());
        assertEquals(15.0, metricasECI.getValue());
    }

    @Test
    @DisplayName("Infernape 4: groupingBy Anidado por Sede y Tipo de Drone")
    void contarDronesPorSedeYTipo_agrupaAnidado() {
        List<Drone> flota = List.of(
                new Drone("D1", "M", TipoDrone.MINI, 80, true, EstadoDrone.DISPONIBLE, "A", "ECI"),
                new Drone("D2", "M", TipoDrone.MINI, 80, true, EstadoDrone.DISPONIBLE, "A", "ECI"),
                new Drone("D3", "M", TipoDrone.HEAVY, 80, true, EstadoDrone.DISPONIBLE, "A", "ECI"),
                new Drone("D4", "M", TipoDrone.ENDURANCE, 80, true, EstadoDrone.DISPONIBLE, "A", "UNAL")
        );

        Map<String, Map<TipoDrone, Long>> resultado = analytics.contarDronesPorSedeYTipo(flota);

        assertEquals(2L, resultado.get("ECI").get(TipoDrone.MINI));
        assertEquals(1L, resultado.get("ECI").get(TipoDrone.HEAVY));
        assertEquals(1L, resultado.get("UNAL").get(TipoDrone.ENDURANCE));
    }

    @Test
    @DisplayName("Infernape 5: Tasa de éxito por sede")
    void tasaExitoPorSede_calculaPorcentaje() {
        List<Mision> misiones = List.of(
                new Mision("M-1", null, "A", "B", 100, null, null, EstadoMision.ENTREGADA, LocalDateTime.now(), "", "ECI", 10.0),
                new Mision("M-2", null, "A", "B", 100, null, null, EstadoMision.FALLIDA, LocalDateTime.now(), "", "ECI", 10.0)
        );

        Map<String, Double> tasas = analytics.tasaExitoPorSede(misiones);
        assertEquals(50.0, tasas.get("ECI"));
    }
}