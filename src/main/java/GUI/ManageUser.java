/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import com.pemkom.objects.services.I18nService;

/**
 *
 * @author LENOVO
 */
public class ManageUser extends javax.swing.JPanel implements I18nService.I18nChangeListener {

    /**
     * Creates new form ManageSiswa
     */
    public ManageUser() {
        initComponents();
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    searchUser();
                }
            }
        });
        javax.swing.SwingUtilities.invokeLater(() -> loadUser());
        I18nService.registerListener(this);
        onLanguageChanged();
    }

    @Override
    public void onLanguageChanged() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            jLabel1.setText(I18nService.get("ui.manage.user.title"));
            roundedButtonAdd2.setText(I18nService.get("ui.btn.add"));
            this.revalidate();
            this.repaint();
        });
    }

private void loadUser() {
        panelKonten.removeAll();

        javax.swing.JPanel gridPanel = new javax.swing.JPanel(new java.awt.GridLayout(0, 3, 15, 15));
        gridPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        gridPanel.setBackground(new java.awt.Color(220, 220, 220));

        com

.pemkom.objects.GenericDAO<com.pemkom.objects.User> userDAO
                = new com.pemkom.objects.GenericDAO<>("User", com.pemkom.objects.User.class  

);

        for (com.pemkom.objects.User u : userDAO.findAll()) {
            gridPanel.add(buatCard(u.getIdUser(), u.getUsername(), u.getRole()));
        }

        panelKonten.setLayout(new java.awt.BorderLayout());
        panelKonten.add(gridPanel, java.awt.BorderLayout.NORTH);
        panelKonten.revalidate();
        panelKonten.repaint();
    }

    private void searchUser() {
        panelKonten.removeAll();

        javax.swing.JPanel gridPanel = new javax.swing.JPanel(new java.awt.GridLayout(0, 3, 15, 15));
        gridPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        gridPanel.setBackground(new java.awt.Color(220, 220, 220));

        com

.pemkom.objects.GenericDAO<com.pemkom.objects.User> userDAO
                = new com.pemkom.objects.GenericDAO<>("User", com.pemkom.objects.User.class  

);

        String keyword = search.getText().toLowerCase();

        for (com.pemkom.objects.User u : userDAO.findAll()) {
            if (keyword.isEmpty()
                    || u.getUsername().toLowerCase().contains(keyword)
                    || u.getRole().toLowerCase().contains(keyword)) {
                gridPanel.add(buatCard(u.getIdUser(), u.getUsername(), u.getRole()));
            }
        }

        panelKonten.setLayout(new java.awt.BorderLayout());
        panelKonten.add(gridPanel, java.awt.BorderLayout.NORTH);
        panelKonten.revalidate();
        panelKonten.repaint();
    }

    private javax.swing.JPanel buatCard(String idUser, String username, String role) {
        javax.swing.JPanel card = new javax.swing.JPanel(new java.awt.BorderLayout(5, 5));
        card.setBackground(new java.awt.Color(50, 65, 80));
        card.setPreferredSize(new java.awt.Dimension(0, 150));
        card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(91, 164, 207), 2),
                javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        javax.swing.JPanel infoPanel = new javax.swing.JPanel(new java.awt.GridLayout(3, 1, 2, 2));
        infoPanel.setBackground(new java.awt.Color(50, 65, 80));

        javax.swing.JLabel lblId = new javax.swing.JLabel("ID: " + idUser);
        lblId.setForeground(java.awt.Color.WHITE);

        javax.swing.JLabel lblUsername = new javax.swing.JLabel("Username: " + username);
        lblUsername.setForeground(java.awt.Color.WHITE);

        javax.swing.JLabel lblRole = new javax.swing.JLabel("Role: " + role);
        lblRole.setForeground(java.awt.Color.WHITE);

        infoPanel.add(lblId);
        infoPanel.add(lblUsername);
        infoPanel.add(lblRole);

        javax.swing.JPanel btnPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));
        btnPanel.setBackground(new java.awt.Color(50, 65, 80));

        javax.swing.JButton btnEdit = new javax.swing.JButton("Edit");
        btnEdit.setBackground(new java.awt.Color(0, 180, 0));
        btnEdit.setForeground(java.awt.Color.WHITE);
        btnEdit.setFocusPainted(false);
        btnEdit.setBorderPainted(false);
        btnEdit.addActionListener(e -> {
            UpdateUser dialog = new UpdateUser(
                    (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this),
                    true, idUser, username, role
            );
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            loadUser();
        });

        javax.swing.JButton btnDelete = new javax.swing.JButton("Delete");
        btnDelete.setBackground(new java.awt.Color(200, 0, 0));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(false);
        btnDelete.addActionListener(e -> {
            int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(
                    null, "Hapus " + username + "?", "Konfirmasi", javax.swing.JOptionPane.YES_NO_OPTION
            );
            if (konfirmasi == javax.swing.JOptionPane.YES_OPTION) {
                com.pemkom.objects.GenericDAO<com.pemkom.objects.User> userDAO
                        = new com.pemkom.objects.GenericDAO<>("User", com.pemkom.objects.User.class);
                userDAO.delete(
                        com.mongodb.client.model.Filters.eq("idUser", idUser)
                );
                loadUser();
            }
        });

        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);

        card.add(infoPanel, java.awt.BorderLayout.CENTER);
        card.add(btnPanel, java.awt.BorderLayout.SOUTH);

        return card;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        panelKonten = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        roundedButtonRefresh = new GUI.RoundedButton();
        roundedButtonAdd2 = new GUI.RoundedButton();
        search = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(null);

        jScrollPane1.setViewportView(panelKonten);

        add(jScrollPane1);
        jScrollPane1.setBounds(0, 130, 1380, 670);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Manage User");
        add(jLabel1);
        jLabel1.setBounds(470, 40, 430, 54);

        roundedButtonRefresh.setBackground(new java.awt.Color(34, 208, 104));
        roundedButtonRefresh.setForeground(new java.awt.Color(255, 255, 255));
        roundedButtonRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-refresh-24.png"))); // NOI18N
        roundedButtonRefresh.setRadius(10);
        roundedButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedButtonRefreshActionPerformed(evt);
            }
        });
        add(roundedButtonRefresh);
        roundedButtonRefresh.setBounds(290, 50, 100, 40);

        roundedButtonAdd2.setBackground(new java.awt.Color(34, 208, 104));
        roundedButtonAdd2.setForeground(new java.awt.Color(255, 255, 255));
        roundedButtonAdd2.setText("Add");
        roundedButtonAdd2.setRadius(10);
        roundedButtonAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedButtonAdd2ActionPerformed(evt);
            }
        });
        add(roundedButtonAdd2);
        roundedButtonAdd2.setBounds(180, 50, 100, 40);
        add(search);
        search.setBounds(920, 50, 220, 40);
    }// </editor-fold>//GEN-END:initComponents

    private void roundedButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedButtonRefreshActionPerformed
        loadUser();
    }//GEN-LAST:event_roundedButtonRefreshActionPerformed

    private void roundedButtonAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedButtonAdd2ActionPerformed
        AddUser dialog = new AddUser(
                (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this),
                true
        );
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadUser();
    }//GEN-LAST:event_roundedButtonAdd2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelKonten;
    private GUI.RoundedButton roundedButtonAdd2;
    private GUI.RoundedButton roundedButtonRefresh;
    private javax.swing.JTextField search;
    // End of variables declaration//GEN-END:variables
}
