package co.edu.escuelaing.dosw.skycampus.core.patterns.composite;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RutaCompositeTest {

    @Test
    @DisplayName("Composite: Tramo simple calcula sus propios atributos")
    void tramoSimple_calculaValoresCorrectos() {
        TramoSimple tramo = new TramoSimple("ECI", "Estación 116", 8.5, 12, 25);

        assertEquals("ECI -> Estación 116", tramo.getNombre());
        assertEquals(8.5, tramo.getDistanciaKm());
        assertEquals(12, tramo.getTiempoEstimadoMinutos());
        assertEquals(25, tramo.getConsumoBateriaEstimado());
        assertEquals(List.of("ECI", "Estación 116"), tramo.getParadas());
    }

    @Test
    @DisplayName("Composite: Ruta compuesta agrega distancias, consumos y tiempos con recarga")
    void rutaCompuesta_agregaTramosConParadasTecnicas() {
        TramoSimple t1 = new TramoSimple("ECI", "Estación 116", 8.0, 10, 20);
        TramoSimple t2 = new TramoSimple("Estación 116", "Uniandes", 12.0, 15, 30);

        RutaCompuesta rutaInterSede = new RutaCompuesta("Ruta Norte-Centro", 20);
        rutaInterSede.agregarTramo(t1);
        rutaInterSede.agregarTramo(t2);

        assertEquals("Ruta Norte-Centro", rutaInterSede.getNombre());
        assertEquals(2, rutaInterSede.totalEtapas());
        assertEquals(20.0, rutaInterSede.getDistanciaKm());
        assertEquals(50, rutaInterSede.getConsumoBateriaEstimado());
        assertEquals(45, rutaInterSede.getTiempoEstimadoMinutos());
        assertEquals(List.of("ECI", "Estación 116", "Uniandes"), rutaInterSede.getParadas());
    }
}