package co.edu.escuelaing.dosw.skycampus.core.patterns.chain;

import co.edu.escuelaing.dosw.skycampus.core.models.Mision;

public class ValidadorCarga extends ValidadorLanzamiento {
    @Override
    public void validar(Mision mision) {
        if (mision.tipoCarga() == null) {
            throw new IllegalStateException("Tipo de carga no asignado");
        }
        if (mision.drone() != null && !mision.drone().soportaPeso(mision.pesoPaqueteGramos())) {
            throw new IllegalStateException("La carga supera la capacidad del drone: " + mision.drone().tipo().getCapacidadGramos() + "g");
        }
        pasarAlSiguiente(mision);
    }
}