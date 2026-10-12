package co.edu.escuelaing.dosw.skycampus.pattern.chain;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.Mision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.Prioridad;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoCarga;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoDrone;
import co.edu.escuelaing.dosw.skycampus.core.patterns.chain.ValidadorBateria;
import co.edu.escuelaing.dosw.skycampus.core.patterns.chain.ValidadorCarga;
import co.edu.escuelaing.dosw.skycampus.core.patterns.chain.ValidadorDestino;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ChainValidadorTest {

    private ValidadorDestino cadena;

    @BeforeEach
    void setUp() {
        cadena = new ValidadorDestino();
        ValidadorBateria valBateria = new ValidadorBateria();
        ValidadorCarga valCarga = new ValidadorCarga();

        cadena.enlazarCon(valBateria).enlazarCon(valCarga);
    }

    @Test
    @DisplayName("Cadena: Misión válida pasa sin lanzar excepciones")
    void cadena_misionValida_noLanzaExcepcion() {
        Drone drone = new Drone("D-01", "DJI", TipoDrone.CARGO, 80, true, EstadoDrone.DISPONIBLE, "Bloque A");
        Mision mision = new Mision("M-01", drone, "Bloque A", "Biblioteca", 200, TipoCarga.SOBRE, Prioridad.NORMAL, EstadoMision.PENDIENTE, LocalDateTime.now(), "");

        assertDoesNotThrow(() -> cadena.validar(mision));
    }

    @Test
    @DisplayName("ValidadorDestino: Destino fuera del campus lanza IllegalArgumentException")
    void cadena_destinoInvalido_lanzaExcepcion() {
        Drone drone = new Drone("D-01", "DJI", TipoDrone.MINI, 80, true, EstadoDrone.DISPONIBLE, "Bloque A");
        Mision mision = new Mision("M-02", drone, "Bloque A", "Plaza Central", TipoCarga.SOBRE, EstadoMision.PENDIENTE);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> cadena.validar(mision));
        assertTrue(ex.getMessage().contains("Destino fuera del campus ECI"));
    }

    @Test
    @DisplayName("ValidadorBateria: Batería menor a 30% lanza IllegalStateException")
    void cadena_bateriaBaja_lanzaExcepcion() {
        Drone drone = new Drone("D-02", "DJI", TipoDrone.MINI, 20, true, EstadoDrone.DISPONIBLE, "Bloque A");
        Mision mision = new Mision("M-03", drone, "Bloque A", "Bloque B", TipoCarga.SOBRE, EstadoMision.PENDIENTE);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> cadena.validar(mision));
        assertTrue(ex.getMessage().contains("Batería inferior al 30%"));
    }

    @Test
    @DisplayName("ValidadorCarga: Carga que supera capacidad máxima lanza IllegalStateException")
    void cadena_sobrepeso_lanzaExcepcion() {
        Drone mini = new Drone("D-03", "DJI", TipoDrone.MINI, 90, true, EstadoDrone.DISPONIBLE, "Bloque A"); // 500g max
        Mision mision = new Mision("M-04", mini, "Bloque A", "Bloque B", 700, TipoCarga.EQUIPO, Prioridad.NORMAL, EstadoMision.PENDIENTE, LocalDateTime.now(), "");

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> cadena.validar(mision));
        assertTrue(ex.getMessage().contains("La carga supera la capacidad del drone"));
    }
}