package co.edu.escuelaing.dosw.skycampus.core.models;

import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.Prioridad;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoCarga;

import java.time.LocalDateTime;

public record Mision(
        String id,
        Drone drone,
        String origen,
        String destino,
        int pesoPaqueteGramos,
        TipoCarga tipoCarga,
        Prioridad prioridad,
        EstadoMision estado,
        LocalDateTime fechaCreacion,
        String notas
) {
    // Constructor simplificado para misiones estándar
    public Mision(String id, Drone drone, String origen, String destino, TipoCarga tipoCarga, EstadoMision estado) {
        this(id, drone, origen, destino, 200, tipoCarga, Prioridad.NORMAL, estado, LocalDateTime.now(), "");
    }

    public Mision conDrone(Drone nuevoDrone, EstadoMision nuevoEstado) {
        return new Mision(this.id, nuevoDrone, this.origen, this.destino, this.pesoPaqueteGramos, this.tipoCarga, this.prioridad, nuevoEstado, this.fechaCreacion, this.notas);
    }
}