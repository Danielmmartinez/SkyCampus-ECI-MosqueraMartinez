package co.edu.escuelaing.dosw.skycampus.core.patterns.factory;

public class ReporteECIFactory implements ReporteSedeFactory {
    @Override
    public FormateadorHeader crearHeader() {
        return (sede, fecha) -> "<header><h1>SkyCampus ECI - " + fecha + "</h1></header>";
    }

    @Override
    public FormateadorCuerpo crearCuerpo() {
        return metricas -> "<main><div class='kpi'>" + metricas + "</div></main>";
    }

    @Override
    public String getExtension() {
        return ".html";
    }
}