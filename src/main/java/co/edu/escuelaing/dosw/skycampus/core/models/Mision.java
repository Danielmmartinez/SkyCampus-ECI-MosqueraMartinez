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
        String notas,
        String sede,
        double tiempoEntregaMinutos
) {
    // Constructor de 10 parámetros de Monferno
    public Mision(String id, Drone drone, String origen, String destino, int pesoPaqueteGramos,
                  TipoCarga tipoCarga, Prioridad prioridad, EstadoMision estado,
                  LocalDateTime fechaCreacion, String notas) {
        this(id, drone, origen, destino, pesoPaqueteGramos, tipoCarga, prioridad, estado, fechaCreacion, notas, "ECI", 15.0);
    }

    // Constructor simplificado de Chimchar
    public Mision(String id, Drone drone, String origen, String destino, TipoCarga tipoCarga, EstadoMision estado) {
        this(id, drone, origen, destino, 200, tipoCarga, Prioridad.NORMAL, estado, LocalDateTime.now(), "", "ECI", 15.0);
    }

    public Mision conDrone(Drone nuevoDrone, EstadoMision nuevoEstado) {
        return new Mision(this.id, nuevoDrone, this.origen, this.destino, this.pesoPaqueteGramos, this.tipoCarga,
                this.prioridad, nuevoEstado, this.fechaCreacion, this.notas, this.sede, this.tiempoEntregaMinutos);
    }
}