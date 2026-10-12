package co.edu.escuelaing.dosw.skycampus.core.services;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.Mision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;
import co.edu.escuelaing.dosw.skycampus.core.patterns.chain.ValidadorLanzamiento;
import co.edu.escuelaing.dosw.skycampus.core.patterns.observer.GestorFlotaObservable;
import co.edu.escuelaing.dosw.skycampus.core.patterns.strategy.EstrategiaAsignacion;

import java.util.List;
import java.util.Optional;

public class AsignadorMision {
    private final ApiMeteorologica apiClima;
    private final EstrategiaAsignacion estrategia;
    private final GestorFlotaObservable gestorFlota;
    private final ValidadorLanzamiento validadorCadena;

    public AsignadorMision(ApiMeteorologica apiClima,
                           EstrategiaAsignacion estrategia,
                           GestorFlotaObservable gestorFlota,
                           ValidadorLanzamiento validadorCadena) {
        this.apiClima = apiClima;
        this.estrategia = estrategia;
        this.gestorFlota = gestorFlota;
        this.validadorCadena = validadorCadena;
    }

    public Optional<Drone> asignar(List<Drone> flota, Mision mision) {
        // 1. Verificación del clima externo
        if (!apiClima.esAptoParaVuelo(mision.origen(), mision.destino())) {
            return Optional.empty();
        }

        // 2. Selección según Strategy
        Optional<Drone> seleccionado = estrategia.seleccionarDrone(flota, mision);
        if (seleccionado.isEmpty()) {
            return Optional.empty();
        }

        Drone drone = seleccionado.get();

        // 3. Validación por Cadena de Responsabilidad
        Mision misionConDrone = new Mision(
                mision.id(), drone, mision.origen(), mision.destino(),
                mision.pesoPaqueteGramos(), mision.tipoCarga(), mision.prioridad(),
                mision.estado(), mision.fechaCreacion(), mision.notas()
        );

        try {
            if (validadorCadena != null) {
                validadorCadena.validar(misionConDrone);
            }
        } catch (RuntimeException ex) {
            return Optional.empty();
        }

        // 4. Notificación reactiva al Observer
        if (gestorFlota != null) {
            gestorFlota.notificarCambio(drone, EstadoDrone.EN_VUELO);
        }

        return Optional.of(drone);
    }
}