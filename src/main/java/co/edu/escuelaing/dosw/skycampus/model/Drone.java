package co.edu.escuelaing.dosw.skycampus.model;

public record Drone(
        String id,
        String modelo,
        int bateria,       // 0 a 100%
        boolean disponible,
        String ubicacion   // Ej: "Bloque A", "Bloque B", "Biblioteca"
) {}