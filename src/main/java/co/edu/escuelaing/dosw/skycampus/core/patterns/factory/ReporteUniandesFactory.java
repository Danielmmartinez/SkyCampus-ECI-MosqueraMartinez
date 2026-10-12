package co.edu.escuelaing.dosw.skycampus.core.patterns.factory;

public class ReporteUniandesFactory implements ReporteSedeFactory {

    @Override
    public FormateadorHeader crearHeader() {
        return (sede, fecha) -> "{\"universidad\": \"" + sede + "\", \"timestamp\": \"" + fecha + "\"}";
    }

    @Override
    public FormateadorCuerpo crearCuerpo() {
        return metricas -> "{\"payload\": \"" + metricas + "\"}";
    }

    @Override
    public String getExtension() {
        return ".json";
    }
}