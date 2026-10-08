package co.edu.escuelaing.dosw.skycampus.pattern.builder;

import co.edu.escuelaing.dosw.skycampus.model.Drone;
import co.edu.escuelaing.dosw.skycampus.model.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.model.Mision;
import co.edu.escuelaing.dosw.skycampus.model.TipoCarga;

public class MisionBuilder {
    private String id;
    private Drone drone;
    private String origen;
    private String destino;
    private TipoCarga tipoCarga = TipoCarga.SOBRE;
    private EstadoMision estado = EstadoMision.PENDIENTE;
    private String notas = "";
    private int prioridad = 3;

    public MisionBuilder id(String id) { this.id = id; return this; }
    public MisionBuilder drone(Drone drone) { this.drone = drone; return this; }
    public MisionBuilder origen(String origen) { this.origen = origen; return this; }
    public MisionBuilder destino(String destino) { this.destino = destino; return this; }
    public MisionBuilder tipoCarga(TipoCarga carga) { this.tipoCarga = carga; return this; }
    public MisionBuilder notas(String notas) { this.notas = notas; return this; }
    public MisionBuilder prioridad(int prioridad) { this.prioridad = prioridad; return this; }

    public Mision build() {
        if (id == null || drone == null || origen == null || destino == null) {
            throw new IllegalStateException("id, drone, origen y destino son obligatorios");
        }
        return new Mision(id, drone, origen, destino, tipoCarga, estado);
    }
}