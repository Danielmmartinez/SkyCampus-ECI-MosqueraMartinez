package co.edu.escuelaing.dosw.skycampus.core.patterns.chain;

import co.edu.escuelaing.dosw.skycampus.core.models.Mision;

import java.util.List;

public class ValidadorDestino extends ValidadorLanzamiento {
    private static final List<String> DESTINOS_VALIDOS = List.of(
            "Bloque A", "Bloque B", "Bloque C", "Bloque D", "Biblioteca"
    );

    @Override
    public void validar(Mision mision) {
        if (mision.destino() == null || !DESTINOS_VALIDOS.contains(mision.destino())) {
            throw new IllegalArgumentException("Destino fuera del campus ECI: " + mision.destino());
        }
        pasarAlSiguiente(mision);
    }
}