package cesur.zakaria.pokemonprojectzakariafarih.pokedex.view;

import cesur.zakaria.pokemonprojectzakariafarih.pokedex.controller.ControladorDashboard;

/**
 * The Dashboard class represents the main dashboard frame of the application.
 */
public class Dashboard extends javax.swing.JFrame {

    /**
     * Creates new form Dashboard.
     */
    public Dashboard() {
        initComponents();
        ControladorDashboard controladorDashboard = new ControladorDashboard(this);
        controladorDashboard.iniciar();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the components of the dashboard frame.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Componente.paneRound.JpanelRound jpanelRound1 = new Componente.paneRound.JpanelRound();
        body = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1201, 601));
        setMinimumSize(new java.awt.Dimension(1201, 601));

        body.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jpanelRound1Layout = new javax.swing.GroupLayout(jpanelRound1);
        jpanelRound1.setLayout(jpanelRound1Layout);
        jpanelRound1Layout.setHorizontalGroup(
                jpanelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpanelRound1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 1189, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jpanelRound1Layout.setVerticalGroup(
                jpanelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelRound1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jpanelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jpanelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * The Body.
     */
// Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel body;
    // End of variables declaration//GEN-END:variables
}
