package co.edu.escuelaing.dosw.skycampus.core.models;

import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoDrone;

public record Drone(
        String id,
        String modelo,
        TipoDrone tipo,
        int bateria,
        boolean disponible,
        EstadoDrone estado,
        String ubicacion,
        String sede
) {
    // Constructor para compatibilidad total con Monferno
    public Drone(String id, String modelo, TipoDrone tipo, int bateria, boolean disponible, EstadoDrone estado, String ubicacion) {
        this(id, modelo, tipo, bateria, disponible, estado, ubicacion, "ECI");
    }

    // Constructor de 5 parámetros para compatibilidad total con Chimchar
    public Drone(String id, String modelo, int bateria, boolean disponible, String ubicacion) {
        this(id, modelo, TipoDrone.MINI, bateria, disponible, disponible ? EstadoDrone.DISPONIBLE : EstadoDrone.EN_MANTENIMIENTO, ubicacion, "ECI");
    }

    public boolean tieneBateriaSuficiente(int minimo) {
        return this.bateria >= minimo;
    }

    public boolean soportaPeso(int pesoGramos) {
        return this.tipo != null && this.tipo.getCapacidadGramos() >= pesoGramos;
    }

    public Drone conEstado(EstadoDrone nuevoEstado, boolean nuevaDisponibilidad) {
        return new Drone(this.id, this.modelo, this.tipo, this.bateria, nuevaDisponibilidad, nuevoEstado, this.ubicacion, this.sede);
    }
}