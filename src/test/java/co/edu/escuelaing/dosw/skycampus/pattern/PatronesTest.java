package co.edu.escuelaing.dosw.skycampus.pattern;

import co.edu.escuelaing.dosw.skycampus.model.Drone;
import co.edu.escuelaing.dosw.skycampus.model.Mision;
import co.edu.escuelaing.dosw.skycampus.model.TipoCarga;
import co.edu.escuelaing.dosw.skycampus.pattern.builder.MisionBuilder;
import co.edu.escuelaing.dosw.skycampus.pattern.chain.*;
import co.edu.escuelaing.dosw.skycampus.pattern.strategy.EstrategiaAsignacion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PatronesTest {

    @Test
    @DisplayName("Builder: Construcción exitosa de misión con campos obligatorios y opcionales")
    void builder_construyeMisionCorrectamente() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 90, true, "Bloque A");
        Mision mision = new MisionBuilder()
                .id("M-101")
                .drone(drone)
                .origen("Bloque A")
                .destino("Biblioteca")
                .tipoCarga(TipoCarga.SOBRE)
                .notas("Documentos decanatura")
                .prioridad(1)
                .build();

        assertNotNull(mision);
        assertEquals("M-101", mision.id());
        assertEquals("Biblioteca", mision.destino());
    }

    @Test
    @DisplayName("Builder: Falla si falta algún campo obligatorio")
    void builder_camposIncompletos_lanzaExcepcion() {
        MisionBuilder builder = new MisionBuilder().id("M-102");
        assertThrows(IllegalStateException.class, builder::build);
    }
}