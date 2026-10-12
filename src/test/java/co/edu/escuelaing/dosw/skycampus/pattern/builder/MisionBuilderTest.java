package co.edu.escuelaing.dosw.skycampus.pattern.builder;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.Mision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.Prioridad;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoCarga;
import co.edu.escuelaing.dosw.skycampus.core.patterns.builder.MisionBuilder;
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
                .pesoPaqueteGramos(350)
                .tipoCarga(TipoCarga.CARPETA)
                .notas("Urgente decanatura")
                .prioridad(Prioridad.URGENTE)
                .build();

        assertNotNull(mision);
        assertEquals("M-101", mision.id());
        assertEquals("Bloque A", mision.origen());
        assertEquals("Biblioteca", mision.destino());
        assertEquals(TipoCarga.CARPETA, mision.tipoCarga());
        assertEquals(EstadoMision.PENDIENTE, mision.estado());
        assertEquals(350, mision.pesoPaqueteGramos());
    }

    @Test
    @DisplayName("Builder: Falla si falta algún campo obligatorio")
    void build_camposIncompletos_lanzaExcepcion() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 80, true, "Bloque A");

        MisionBuilder b1 = new MisionBuilder().drone(drone).origen("Bloque A").destino("Biblioteca");
        assertThrows(IllegalStateException.class, b1::build);

        MisionBuilder b2 = new MisionBuilder().id("M-01").origen("Bloque A").destino("Biblioteca");
        assertThrows(IllegalStateException.class, b2::build);

        MisionBuilder b3 = new MisionBuilder().id("M-01").drone(drone).destino("Biblioteca");
        assertThrows(IllegalStateException.class, b3::build);

        MisionBuilder b4 = new MisionBuilder().id("M-01").drone(drone).origen("Bloque A");
        assertThrows(IllegalStateException.class, b4::build);
    }

    @Test
    @DisplayName("Builder: Prioridad numérica 1, 2 y 3")
    void builder_prioridadNumerica() {
        Drone d = new Drone("D-01", "DJI", 80, true, "A");
        Mision m1 = new MisionBuilder().id("1").drone(d).origen("A").destino("B").prioridad(1).build();
        Mision m2 = new MisionBuilder().id("2").drone(d).origen("A").destino("B").prioridad(2).build();
        Mision m3 = new MisionBuilder().id("3").drone(d).origen("A").destino("B").prioridad(3).build();

        assertEquals(Prioridad.URGENTE, m1.prioridad());
        assertEquals(Prioridad.NORMAL, m2.prioridad());
        assertEquals(Prioridad.BAJO, m3.prioridad());
    }
}