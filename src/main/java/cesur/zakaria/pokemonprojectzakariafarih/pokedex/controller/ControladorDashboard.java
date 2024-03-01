package cesur.zakaria.pokemonprojectzakariafarih.pokedex.controller;

import cesur.zakaria.pokemonprojectzakariafarih.pokedex.view.PaneInicio;
import cesur.zakaria.pokemonprojectzakariafarih.pokedex.view.Dashboard;
import java.awt.Component;

public class ControladorDashboard {

    private static Dashboard instancia;
    private final Dashboard dashboard;

    public ControladorDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
        ControladorDashboard.instancia = dashboard;
    }

    public void iniciar() {
        mostrarForm(new PaneInicio());
    }

    private void mostrarForm(Component com) {
        dashboard.body.removeAll();
        dashboard.body.add(com);
        dashboard.body.repaint();
        dashboard.body.revalidate();
    }

    public static Dashboard getInstance() {
        if (instancia == null) {
            instancia = new Dashboard();
        }
        return instancia;
    }

}
