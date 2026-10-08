package co.edu.escuelaing.dosw.skycampus.pattern.builder;

import co.edu.escuelaing.dosw.skycampus.model.Drone;
import co.edu.escuelaing.dosw.skycampus.model.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.model.Mision;
import co.edu.escuelaing.dosw.skycampus.model.TipoCarga;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MisionBuilderTest {

    @Test
    @DisplayName("Builder: Construcción completa con valores personalizados y por defecto")
    void build_construccionExitosa() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 80, true, "Bloque A");
        Mision mision = new MisionBuilder()
                .id("M-101")
                .drone(drone)
                .origen("Bloque A")
                .destino("Biblioteca")
                .tipoCarga(TipoCarga.CARPETA)
                .notas("Urgente decanatura")
                .prioridad(1)
                .build();

        assertNotNull(mision);
        assertEquals("M-101", mision.id());
        assertEquals("Bloque A", mision.origen());
        assertEquals("Biblioteca", mision.destino());
        assertEquals(TipoCarga.CARPETA, mision.tipoCarga());
        assertEquals(EstadoMision.PENDIENTE, mision.estado());
    }

    @Test
    @DisplayName("Builder rama: Falla si id es nulo")
    void build_idNulo_lanzaExcepcion() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 80, true, "Bloque A");
        MisionBuilder builder = new MisionBuilder()
                .drone(drone)
                .origen("Bloque A")
                .destino("Biblioteca");

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    @DisplayName("Builder rama: Falla si drone es nulo")
    void build_droneNulo_lanzaExcepcion() {
        MisionBuilder builder = new MisionBuilder()
                .id("M-101")
                .origen("Bloque A")
                .destino("Biblioteca");

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    @DisplayName("Builder rama: Falla si origen es nulo")
    void build_origenNulo_lanzaExcepcion() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 80, true, "Bloque A");
        MisionBuilder builder = new MisionBuilder()
                .id("M-101")
                .drone(drone)
                .destino("Biblioteca");

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    @DisplayName("Builder rama: Falla si destino es nulo")
    void build_destinoNulo_lanzaExcepcion() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 80, true, "Bloque A");
        MisionBuilder builder = new MisionBuilder()
                .id("M-101")
                .drone(drone)
                .origen("Bloque A");

        assertThrows(IllegalStateException.class, builder::build);
    }
}