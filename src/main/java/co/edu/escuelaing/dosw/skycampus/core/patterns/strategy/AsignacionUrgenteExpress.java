package co.edu.escuelaing.dosw.skycampus.core.patterns.strategy;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.Mision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoDrone;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AsignacionUrgenteExpress implements EstrategiaAsignacion {
    @Override
    public Optional<Drone> seleccionarDrone(List<Drone> flota, Mision mision) {
        return flota.stream()
                .filter(Drone::disponible)
                .filter(d -> d.tipo() == TipoDrone.EXPRESS)
                .max(Comparator.comparingInt(Drone::bateria))
                .or(() -> flota.stream()
                        .filter(Drone::disponible)
                        .max(Comparator.comparingInt(Drone::bateria)));
    }
}