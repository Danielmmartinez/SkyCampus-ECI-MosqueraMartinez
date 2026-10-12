package co.edu.escuelaing.dosw.skycampus.core.models;

import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.Prioridad;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoCarga;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoDrone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelosEnterpriseTest {

    @Test
    @DisplayName("Paquete: Creación válida y validación de peso en límites")
    void paquete_validaciones() {
        Paquete p = new Paquete("P-01", 500, TipoCarga.LIBRO, Prioridad.NORMAL);
        assertEquals("P-01", p.id());
        assertEquals(500, p.pesoGramos());

        assertThrows(IllegalArgumentException.class, () -> new Paquete("P-02", 0, TipoCarga.SOBRE, Prioridad.NORMAL));
        assertThrows(IllegalArgumentException.class, () -> new Paquete("P-03", 2500, TipoCarga.SOBRE, Prioridad.NORMAL));
    }

    @Test
    @DisplayName("Sede: Validación de tasa de éxito entre 0 y 100")
    void sede_validaciones() {
        Sede s = new Sede("S-1", "ECI", 85.0, 10);
        assertEquals("ECI", s.nombre());

        assertThrows(IllegalArgumentException.class, () -> new Sede("S-2", "ECI", -5.0, 10));
        assertThrows(IllegalArgumentException.class, () -> new Sede("S-3", "ECI", 105.0, 10));
    }

    @Test
    @DisplayName("Drone: Métodos helper conEstado, tieneBateriaSuficiente y soportaPeso")
    void drone_helpers() {
        Drone d = new Drone("D-01", "DJI", 80, true, "Bloque A");
        assertTrue(d.tieneBateriaSuficiente(50));
        assertFalse(d.tieneBateriaSuficiente(90));
        assertTrue(d.soportaPeso(300));
        assertFalse(d.soportaPeso(1000));

        Drone actualizado = d.conEstado(EstadoDrone.EN_VUELO, false);
        assertEquals(EstadoDrone.EN_VUELO, actualizado.estado());
        assertFalse(actualizado.disponible());
    }

    @Test
    @DisplayName("Mision: Helper conDrone genera nueva misión con drone y estado actualizado")
    void mision_helpers() {
        Mision m = new Mision("M-01", null, "A", "B", TipoCarga.SOBRE, EstadoMision.PENDIENTE);
        Drone d = new Drone("D-01", "DJI", 80, true, "A");

        Mision enVuelo = m.conDrone(d, EstadoMision.EN_VUELO);
        assertEquals(EstadoMision.EN_VUELO, enVuelo.estado());
        assertEquals("D-01", enVuelo.drone().id());
    }
}