package co.edu.escuelaing.dosw.skycampus.service;

import co.edu.escuelaing.dosw.skycampus.exception.DestinoInvalidoException;
import co.edu.escuelaing.dosw.skycampus.model.Drone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorMisionTest {

    private ValidadorMision validador;

    @BeforeEach
    void setUp() {
        validador = new ValidadorMision();
    }

    // ==========================================
    // PRUEBAS: tieneBateriaSuficiente (Límite: 30%)
    // ==========================================

    @Test
    @DisplayName("Debe retornar true si el drone tiene más de 30% de batería")
    void tieneBateriaSuficiente_bateriaMayorA30_retornaTrue() {
        // Arrange
        Drone drone = new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A");
        // Act
        boolean resultado = validador.tieneBateriaSuficiente(drone);
        // Assert
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debe retornar true en el caso borde exacto de 30% de batería")
    void tieneBateriaSuficiente_bateriaExacta30_retornaTrue() {
        Drone drone = new Drone("D-02", "DJI Mini 3", 30, true, "Bloque B");
        boolean resultado = validador.tieneBateriaSuficiente(drone);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debe retornar false si el drone tiene batería inferior a 30%")
    void tieneBateriaSuficiente_bateriaMenorA30_retornaFalse() {
        Drone drone = new Drone("D-03", "DJI Mini 3", 29, true, "Bloque C");
        boolean resultado = validador.tieneBateriaSuficiente(drone);
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Debe retornar false si el drone proporcionado es nulo")
    void tieneBateriaSuficiente_droneNull_retornaFalse() {
        assertFalse(validador.tieneBateriaSuficiente(null));
    }

    // ==========================================
    // PRUEBAS: validarDestino
    // ==========================================

    @Test
    @DisplayName("No debe lanzar excepción para todos los destinos permitidos del campus ECI")
    void validarDestino_destinosPermitidos_noLanzaExcepcion() {
        List<String> destinosValidos = List.of("Bloque A", "Bloque B", "Bloque C", "Bloque D", "Biblioteca");
        for (String destino : destinosValidos) {
            assertDoesNotThrow(() -> validador.validarDestino(destino), "Falló validando el destino: " + destino);
        }
    }

    @Test
    @DisplayName("Debe lanzar DestinoInvalidoException si el destino no pertenece al campus")
    void validarDestino_destinoFueraDelCampus_lanzaExcepcion() {
        DestinoInvalidoException ex = assertThrows(DestinoInvalidoException.class,
                () -> validador.validarDestino("Plaza Mayor"));
        assertTrue(ex.getMessage().contains("Destino no permitido en el campus ECI"));
    }

    @Test
    @DisplayName("Debe lanzar DestinoInvalidoException si el destino es nulo o vacío")
    void validarDestino_destinoNuloOVacio_lanzaExcepcion() {
        assertThrows(DestinoInvalidoException.class, () -> validador.validarDestino(null));
        assertThrows(DestinoInvalidoException.class, () -> validador.validarDestino("   "));
    }

    // ==========================================
    // PRUEBAS: droneEstaDisponible
    // ==========================================

    @Test
    @DisplayName("Debe retornar true si el drone está marcado como disponible")
    void droneEstaDisponible_disponibleTrue_retornaTrue() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 90, true, "Bloque A");
        assertTrue(validador.droneEstaDisponible(drone));
    }

    @Test
    @DisplayName("Debe retornar false si el drone está ocupado")
    void droneEstaDisponible_disponibleFalse_retornaFalse() {
        Drone drone = new Drone("D-02", "DJI Mini 3", 90, false, "Bloque A");
        assertFalse(validador.droneEstaDisponible(drone));
    }

    @Test
    @DisplayName("Debe retornar false si el drone evaluado es nulo")
    void droneEstaDisponible_droneNull_retornaFalse() {
        assertFalse(validador.droneEstaDisponible(null));
    }
}