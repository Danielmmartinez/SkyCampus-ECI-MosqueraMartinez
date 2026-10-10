package co.edu.escuelaing.dosw.skycampus.core.patterns.strategy;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.Mision;

import java.util.List;
import java.util.Optional;

public interface EstrategiaAsignacion {
    Optional<Drone> seleccionarDrone(List<Drone> flota, Mision mision);
}