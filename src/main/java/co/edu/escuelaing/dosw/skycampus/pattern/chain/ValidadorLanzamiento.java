package co.edu.escuelaing.dosw.skycampus.pattern.chain;

import co.edu.escuelaing.dosw.skycampus.model.Mision;
import java.util.List;

public abstract class ValidadorLanzamiento {
    protected ValidadorLanzamiento siguiente;

    public ValidadorLanzamiento enlazarCon(ValidadorLanzamiento siguiente) {
        this.siguiente = siguiente;
        return siguiente;
    }

    public abstract void validar(Mision mision);

    protected void validarSiguiente(Mision mision) {
        if (siguiente != null) siguiente.validar(mision);
    }
}

class ValidadorBateria extends ValidadorLanzamiento {
    @Override
    public void validar(Mision mision) {
        if (mision.drone().bateria() < 30) {
            throw new IllegalStateException("Rechazado: Batería inferior al 30%");
        }
        validarSiguiente(mision);
    }
}

class ValidadorDestino extends ValidadorLanzamiento {
    private static final List<String> DESTINOS = List.of(
            "Bloque A", "Bloque B", "Bloque C", "Bloque D", "Biblioteca"
    );

    @Override
    public void validar(Mision mision) {
        if (!DESTINOS.contains(mision.destino())) {
            throw new IllegalArgumentException("Rechazado: Destino fuera del campus ECI: " + mision.destino());
        }
        validarSiguiente(mision);
    }
}

class ValidadorCarga extends ValidadorLanzamiento {
    @Override
    public void validar(Mision mision) {
        if (mision.tipoCarga() == null) {
            throw new IllegalStateException("Rechazado: Tipo de carga no asignado");
        }
        validarSiguiente(mision);
    }
}