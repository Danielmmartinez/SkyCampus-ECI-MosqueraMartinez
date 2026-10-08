package co.edu.escuelaing.dosw.skycampus.pattern.chain;

import co.edu.escuelaing.dosw.skycampus.model.Drone;
import co.edu.escuelaing.dosw.skycampus.model.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.model.Mision;
import co.edu.escuelaing.dosw.skycampus.model.TipoCarga;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChainValidadorTest {

    private ValidadorLanzamiento cadena;

    @BeforeEach
    void setUp() {
        ValidadorBateria valBateria = new ValidadorBateria();
        ValidadorDestino valDestino = new ValidadorDestino();
        ValidadorCarga valCarga = new ValidadorCarga();

        // Enlazar la cadena: Batería -> Destino -> Carga
        valBateria.enlazarCon(valDestino).enlazarCon(valCarga);
        cadena = valBateria;
    }

    @Test
    @DisplayName("Cadena completa: Misión válida pasa todas las validaciones sin lanzar excepción")
    void cadena_misionValida_noLanzaExcepcion() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 80, true, "Bloque A");
        Mision mision = new Mision("M-01", drone, "Bloque A", "Biblioteca", TipoCarga.SOBRE, EstadoMision.PENDIENTE);

        assertDoesNotThrow(() -> cadena.validar(mision));
    }

    @Test
    @DisplayName("ValidadorBateria: Batería menor a 30% lanza IllegalStateException")
    void validadorBateria_bateriaBaja_lanzaExcepcion() {
        Drone drone = new Drone("D-02", "DJI Mini 3", 20, true, "Bloque A");
        Mision mision = new Mision("M-02", drone, "Bloque A", "Bloque B", TipoCarga.SOBRE, EstadoMision.PENDIENTE);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> cadena.validar(mision));
        assertTrue(ex.getMessage().contains("Batería inferior al 30%"));
    }

    @Test
    @DisplayName("ValidadorDestino: Destino fuera del campus lanza IllegalArgumentException")
    void validadorDestino_destinoInvalido_lanzaExcepcion() {
        Drone drone = new Drone("D-03", "DJI Mini 3", 75, true, "Bloque A");
        Mision mision = new Mision("M-03", drone, "Bloque A", "Plaza Central", TipoCarga.SOBRE, EstadoMision.PENDIENTE);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> cadena.validar(mision));
        assertTrue(ex.getMessage().contains("Destino fuera del campus ECI"));
    }

    @Test
    @DisplayName("ValidadorCarga: Carga nula lanza IllegalStateException")
    void validadorCarga_cargaNula_lanzaExcepcion() {
        Drone drone = new Drone("D-04", "DJI Mini 3", 75, true, "Bloque A");
        Mision mision = new Mision("M-04", drone, "Bloque A", "Bloque C", null, EstadoMision.PENDIENTE);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> cadena.validar(mision));
        assertTrue(ex.getMessage().contains("Tipo de carga no asignado"));
    }

    @Test
    @DisplayName("Validador sin siguiente: Valida correctamente cuando siguiente es null")
    void validador_sinSiguiente_completaValidacion() {
        ValidadorCarga valCarga = new ValidadorCarga();
        Drone drone = new Drone("D-05", "DJI Mini 3", 90, true, "Bloque A");
        Mision mision = new Mision("M-05", drone, "Bloque A", "Bloque D", TipoCarga.LIBRO, EstadoMision.PENDIENTE);

        assertDoesNotThrow(() -> valCarga.validar(mision));
    }
}