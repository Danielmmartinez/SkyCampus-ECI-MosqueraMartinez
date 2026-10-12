package co.edu.escuelaing.dosw.skycampus.core.patterns.factory;

public interface ReporteSedeFactory {
    FormateadorHeader crearHeader();
    FormateadorCuerpo crearCuerpo();
    String getExtension();
}