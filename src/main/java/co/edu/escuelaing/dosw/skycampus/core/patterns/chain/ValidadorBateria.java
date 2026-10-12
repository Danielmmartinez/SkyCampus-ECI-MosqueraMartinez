package co.edu.escuelaing.dosw.skycampus.core.patterns.chain;

import co.edu.escuelaing.dosw.skycampus.core.models.Mision;

public class ValidadorBateria extends ValidadorLanzamiento {
    @Override
    public void validar(Mision mision) {
        if (mision.drone() != null && mision.drone().bateria() < 30) {
            throw new IllegalStateException("Batería inferior al 30%: " + mision.drone().bateria() + "%");
        }
        pasarAlSiguiente(mision);
    }
}