package co.edu.escuelaing.dosw.skycampus.core.patterns.factory;

public class ReporteUNALFactory implements ReporteSedeFactory {
    @Override
    public FormateadorHeader crearHeader() {
        return (sede, fecha) -> "[PDF-HEADER: UNIVERSIDAD NACIONAL - " + fecha + "]";
    }

    @Override
    public FormateadorCuerpo crearCuerpo() {
        return metricas -> "[PDF-BODY: Misiones=" + metricas + "]";
    }

    @Override
    public String getExtension() {
        return ".pdf";
    }
}