package cesur.zakaria.pokemonprojectzakariafarih.pokedex.controller;

import cesur.zakaria.pokemonprojectzakariafarih.pokedex.view.Dashboard;
import cesur.zakaria.pokemonprojectzakariafarih.pokedex.view.PaneInicio;

import java.awt.*;

/**
 * The ControladorDashboard class controls the Dashboard view.
 */
public class ControladorDashboard {

    private static Dashboard instancia;
    private final Dashboard dashboard;

    /**
     * Constructs a ControladorDashboard object with the specified Dashboard view.
     *
     * @param dashboard The Dashboard view to control.
     */
    public ControladorDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
        ControladorDashboard.instancia = dashboard;
    }

    /**
     * Initializes the Dashboard view.
     */
    public void iniciar() {
        mostrarForm(new PaneInicio());
    }

    /**
     * Displays the specified component in the Dashboard view.
     *
     * @param com The component to display.
     */
    private void mostrarForm(Component com) {
        dashboard.body.removeAll();
        dashboard.body.add(com);
        dashboard.body.repaint();
        dashboard.body.revalidate();
    }

    /**
     * Retrieves the instance of the Dashboard view.
     *
     * @return The instance of the Dashboard view.
     */
    public static Dashboard getInstance() {
        if (instancia == null) {
            instancia = new Dashboard();
        }
        return instancia;
    }

}
