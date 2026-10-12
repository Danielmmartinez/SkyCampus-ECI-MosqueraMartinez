package co.edu.escuelaing.dosw.skycampus.service;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.services.ConsultaFlotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaFlotaServiceTest {

    private ConsultaFlotaService service;
    private List<Drone> flota;

    @BeforeEach
    void setUp() {
        service = new ConsultaFlotaService();
        flota = List.of(
                new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A"),
                new Drone("D-02", "DJI Mini 3", 42, false, "Biblioteca"),
                new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C"),
                new Drone("D-04", "DJI Mini 3", 18, true, "Bloque B"),
                new Drone("D-05", "DJI Mini 3", 67, true, "Bloque D")
        );
    }

    @Test
    @DisplayName("Consulta 1: Drones disponibles con batería >= 50% ordenados descendentemente")
    void obtenerDisponiblesBateriaAlta_retornaOrdenadosCorrectamente() {
        List<String> ids = service.obtenerDisponiblesBateriaAlta(flota);
        assertEquals(List.of("D-03", "D-01", "D-05"), ids);
    }

    @Test
    @DisplayName("Consulta 2: Verificar si hay al menos un drone disponible en Bloque C")
    void hayDroneDisponibleEnBloqueC_retornaTrue() {
        assertTrue(service.hayDroneDisponibleEnBloqueC(flota));
    }

    @Test
    @DisplayName("Consulta 3: Contar drones con batería crítica (< 20%)")
    void contarDronesBateriaCritica_retornaCantidadExacta() {
        long criticos = service.contarDronesBateriaCritica(flota);
        assertEquals(1, criticos);
    }

    @Test
    @DisplayName("Consulta 4: Formatear ID y batería en cadena de texto")
    void formatearIdYBateria_formateaTodosLosElementos() {
        List<String> formateados = service.formatearIdYBateria(flota);
        assertEquals(5, formateados.size());
        assertEquals("D-01: 85%", formateados.get(0));
        assertEquals("D-04: 18%", formateados.get(3));
    }
}