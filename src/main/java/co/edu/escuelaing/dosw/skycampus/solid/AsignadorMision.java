package co.edu.escuelaing.dosw.skycampus.solid;

import co.edu.escuelaing.dosw.skycampus.model.Drone;
import co.edu.escuelaing.dosw.skycampus.model.Mision;

public class AsignadorMision {
    private final RepositorioMision repositorio;
    private final AlertaOperador alerta;

    public AsignadorMision(RepositorioMision repositorio, AlertaOperador alerta) {
        this.repositorio = repositorio;
        this.alerta = alerta;
    }

    public void asignar(Drone drone, Mision mision) {
        repositorio.guardar(mision);
        alerta.enviarAlerta("Operador-01", "Misión asignada al drone: " + drone.id());
    }
}