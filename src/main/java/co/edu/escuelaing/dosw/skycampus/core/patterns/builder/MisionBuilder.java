package co.edu.escuelaing.dosw.skycampus.core.patterns.builder;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.Mision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.Prioridad;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoCarga;

import java.time.LocalDateTime;

public class MisionBuilder {
    private String id;
    private Drone drone;
    private String origen;
    private String destino;
    private int pesoPaqueteGramos = 250;
    private TipoCarga tipoCarga = TipoCarga.SOBRE;
    private Prioridad prioridad = Prioridad.NORMAL;
    private EstadoMision estado = EstadoMision.PENDIENTE;
    private String notas = "";

    public MisionBuilder id(String id) { this.id = id; return this; }
    public MisionBuilder drone(Drone drone) { this.drone = drone; return this; }
    public MisionBuilder origen(String origen) { this.origen = origen; return this; }
    public MisionBuilder destino(String destino) { this.destino = destino; return this; }
    public MisionBuilder pesoPaqueteGramos(int peso) { this.pesoPaqueteGramos = peso; return this; }
    public MisionBuilder tipoCarga(TipoCarga carga) { this.tipoCarga = carga; return this; }
    public MisionBuilder prioridad(Prioridad prioridad) { this.prioridad = prioridad; return this; }

    public MisionBuilder prioridad(int p) {
        this.prioridad = switch (p) {
            case 1 -> Prioridad.URGENTE;
            case 2 -> Prioridad.NORMAL;
            default -> Prioridad.BAJO;
        };
        return this;
    }

    public MisionBuilder estado(EstadoMision estado) { this.estado = estado; return this; }
    public MisionBuilder notas(String notas) { this.notas = notas; return this; }

    public Mision build() {
        if (id == null || drone == null || origen == null || destino == null) {
            throw new IllegalStateException("id, drone, origen y destino son campos obligatorios");
        }
        return new Mision(id, drone, origen, destino, pesoPaqueteGramos, tipoCarga, prioridad, estado, LocalDateTime.now(), notas);
    }
}