package co.edu.escuelaing.dosw.skycampus.core.models;

public record Sede(
        String id,
        String nombre,
        double tasaExito,
        long misionesCompletadasHoy
) {
    public Sede {
        if (tasaExito < 0.0 || tasaExito > 100.0) {
            throw new IllegalArgumentException("La tasa de éxito debe estar entre 0 y 100");
        }
    }
}