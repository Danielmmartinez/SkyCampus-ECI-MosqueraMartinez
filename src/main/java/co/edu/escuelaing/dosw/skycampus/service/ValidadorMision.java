package co.edu.escuelaing.dosw.skycampus.service;

import co.edu.escuelaing.dosw.skycampus.exception.DestinoInvalidoException;
import co.edu.escuelaing.dosw.skycampus.model.Drone;
import java.util.List;

public class ValidadorMision {

    private static final List<String> DESTINOS_VALIDOS = List.of(
            "Bloque A", "Bloque B", "Bloque C", "Bloque D", "Biblioteca"
    );

    public boolean tieneBateriaSuficiente(Drone drone) {
        if (drone == null) return false;
        return drone.bateria() >= 30;
    }

    public void validarDestino(String destino) {
        if (destino == null || destino.trim().isEmpty() || !DESTINOS_VALIDOS.contains(destino.trim())) {
            throw new DestinoInvalidoException("Destino no permitido en el campus ECI: " + destino);
        }
    }

    public boolean droneEstaDisponible(Drone drone) {
        return drone != null && drone.disponible();
    }
}