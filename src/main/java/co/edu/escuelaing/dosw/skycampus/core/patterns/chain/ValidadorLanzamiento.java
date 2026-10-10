package co.edu.escuelaing.dosw.skycampus.core.patterns.chain;

import co.edu.escuelaing.dosw.skycampus.core.models.Mision;

public abstract class ValidadorLanzamiento {
    protected ValidadorLanzamiento siguiente;

    public ValidadorLanzamiento enlazarCon(ValidadorLanzamiento siguiente) {
        this.siguiente = siguiente;
        return siguiente;
    }

    public abstract void validar(Mision mision);

    protected void pasarAlSiguiente(Mision mision) {
        if (siguiente != null) {
            siguiente.validar(mision);
        }
    }
}