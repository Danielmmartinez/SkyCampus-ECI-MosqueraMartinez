package co.edu.escuelaing.dosw.skycampus.service;

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
import co.edu.escuelaing.dosw.skycampus.core.patterns.observer.GestorFlotaObservable;
import co.edu.escuelaing.dosw.skycampus.core.patterns.observer.ObservadorDrone;
import co.edu.escuelaing.dosw.skycampus.core.patterns.strategy.AsignacionMayorBateria;
import co.edu.escuelaing.dosw.skycampus.core.patterns.strategy.AsignacionUrgenteExpress;
import co.edu.escuelaing.dosw.skycampus.core.services.ApiMeteorologica;
import co.edu.escuelaing.dosw.skycampus.core.services.AsignadorMision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class AsignadorMisionTest {

    @Mock
    private ApiMeteorologica apiClima;

    @Mock
    private ObservadorDrone notificador;

    private GestorFlotaObservable gestorFlota;
    private ValidadorDestino cadenaValidadora;

    @BeforeEach
    void setUp() {
        gestorFlota = new GestorFlotaObservable();
        gestorFlota.suscribir(notificador);

        cadenaValidadora = new ValidadorDestino();
        ValidadorBateria valBateria = new ValidadorBateria();
        ValidadorCarga valCarga = new ValidadorCarga();
        cadenaValidadora.enlazarCon(valBateria).enlazarCon(valCarga);
    }

    @Test
    @DisplayName("TDD 1: Asignación exitosa con mayor batería y clima apto")
    void asignar_misionNormal_asignaDroneMayorBateria() {
        Mockito.when(apiClima.esAptoParaVuelo("Bloque A", "Biblioteca")).thenReturn(true);

        List<Drone> flota = List.of(
                new Drone("D-01", "DJI", TipoDrone.MINI, 60, true, EstadoDrone.DISPONIBLE, "Bloque A"),
                new Drone("D-02", "DJI", TipoDrone.MINI, 91, true, EstadoDrone.DISPONIBLE, "Bloque A")
        );

        Mision mision = new Mision("M-01", null, "Bloque A", "Biblioteca", 200, TipoCarga.SOBRE, Prioridad.NORMAL, EstadoMision.PENDIENTE, LocalDateTime.now(), "");

        AsignadorMision asignador = new AsignadorMision(apiClima, new AsignacionMayorBateria(), gestorFlota, cadenaValidadora);
        Optional<Drone> asignado = asignador.asignar(flota, mision);

        assertTrue(asignado.isPresent());
        assertEquals("D-02", asignado.get().id());
        Mockito.verify(notificador).onEstadoCambiado(any(), eq(EstadoDrone.EN_VUELO));
    }

    @Test
    @DisplayName("TDD 2: Clima adverso bloquea la misión")
    void asignar_climaAdverso_retornaVacio() {
        Mockito.when(apiClima.esAptoParaVuelo("Bloque A", "Biblioteca")).thenReturn(false);

        Mision mision = new Mision("M-02", null, "Bloque A", "Biblioteca", 200, TipoCarga.SOBRE, Prioridad.NORMAL, EstadoMision.PENDIENTE, LocalDateTime.now(), "");

        AsignadorMision asignador = new AsignadorMision(apiClima, new AsignacionMayorBateria(), gestorFlota, cadenaValidadora);
        Optional<Drone> asignado = asignador.asignar(List.of(), mision);

        assertTrue(asignado.isEmpty());
        Mockito.verifyNoInteractions(notificador);
    }

    @Test
    @DisplayName("TDD 3: Retorna vacío si no hay drones disponibles")
    void asignar_sinDronesDisponibles_retornaVacio() {
        Mockito.when(apiClima.esAptoParaVuelo("Bloque A", "Biblioteca")).thenReturn(true);

        List<Drone> flota = List.of(
                new Drone("D-01", "DJI", TipoDrone.MINI, 90, false, EstadoDrone.EN_VUELO, "Bloque A")
        );

        Mision mision = new Mision("M-03", null, "Bloque A", "Biblioteca", 200, TipoCarga.SOBRE, Prioridad.NORMAL, EstadoMision.PENDIENTE, LocalDateTime.now(), "");

        AsignadorMision asignador = new AsignadorMision(apiClima, new AsignacionMayorBateria(), gestorFlota, cadenaValidadora);
        Optional<Drone> asignado = asignador.asignar(flota, mision);

        assertTrue(asignado.isEmpty());
    }

    @Test
    @DisplayName("TDD 4: Carga muy pesada es rechazada por la cadena")
    void asignar_paquetePesado_esRechazadoPorCadena() {
        Mockito.when(apiClima.esAptoParaVuelo("Bloque A", "Biblioteca")).thenReturn(true);

        List<Drone> flota = List.of(
                new Drone("D-01", "DJI", TipoDrone.MINI, 90, true, EstadoDrone.DISPONIBLE, "Bloque A") // capacidad 500g
        );

        Mision misionPesada = new Mision("M-04", null, "Bloque A", "Biblioteca", 900, TipoCarga.EQUIPO, Prioridad.NORMAL, EstadoMision.PENDIENTE, LocalDateTime.now(), "");

        AsignadorMision asignador = new AsignadorMision(apiClima, new AsignacionMayorBateria(), gestorFlota, cadenaValidadora);
        Optional<Drone> asignado = asignador.asignar(flota, misionPesada);

        assertTrue(asignado.isEmpty());
    }

    @Test
    @DisplayName("TDD 5: Misión urgente selecciona drone EXPRESS preferentemente")
    void asignar_misionUrgente_seleccionaDroneExpress() {
        Mockito.when(apiClima.esAptoParaVuelo("Bloque A", "Biblioteca")).thenReturn(true);

        Drone standard = new Drone("D-STD", "DJI", TipoDrone.MINI, 95, true, EstadoDrone.DISPONIBLE, "Bloque A");
        Drone express = new Drone("D-EXP", "DJI", TipoDrone.EXPRESS, 75, true, EstadoDrone.DISPONIBLE, "Bloque A");

        Mision misionUrgente = new Mision("M-05", null, "Bloque A", "Biblioteca", 300, TipoCarga.SOBRE, Prioridad.URGENTE, EstadoMision.PENDIENTE, LocalDateTime.now(), "");

        AsignadorMision asignador = new AsignadorMision(apiClima, new AsignacionUrgenteExpress(), gestorFlota, cadenaValidadora);
        Optional<Drone> asignado = asignador.asignar(List.of(standard, express), misionUrgente);

        assertTrue(asignado.isPresent());
        assertEquals("D-EXP", asignado.get().id());
    }
}