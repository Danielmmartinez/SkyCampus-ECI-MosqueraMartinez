package co.edu.escuelaing.dosw.skycampus.model;

public record Drone(
        String id,
        String modelo,
        TipoDrone tipo,
        int bateria,
        boolean disponible,
        EstadoDrone estado,
        String ubicacion
) {
    // Constructor de conveniencia retrocompatible con Chimchar
    public Drone(String id, String modelo, int bateria, boolean disponible, String ubicacion) {
        this(id, modelo, TipoDrone.MINI, bateria, disponible, disponible ? EstadoDrone.DISPONIBLE : EstadoDrone.EN_VUELO, ubicacion);
    }

    public int capacidadGramos() {
        return tipo.getCapacidadGramos();
    }
}