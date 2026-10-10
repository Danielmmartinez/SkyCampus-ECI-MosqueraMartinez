package co.edu.escuelaing.dosw.skycampus.core.models;

import co.edu.escuelaing.dosw.skycampus.core.models.enums.Prioridad;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoCarga;

public record Paquete(
        String id,
        int pesoGramos,
        TipoCarga tipo,
        Prioridad prioridad
) {
    public Paquete {
        if (pesoGramos <= 0 || pesoGramos > 2000) {
            throw new IllegalArgumentException("El peso del paquete debe estar entre 1g y 2000g");
        }
    }
}