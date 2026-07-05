package GUI;

import com.pemkom.objects.services.LogAbsensiService;
import com.pemkom.objects.LogAbsensiView;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.pemkom.objects.services.I18nService;

public class LogAbsensi extends javax.swing.JPanel implements I18nService.I18nChangeListener {

    private LogAbsensiService service = new LogAbsensiService();
    private DefaultTableModel tableModel;
    private boolean isInitializing = false;

    public LogAbsensi() {
        isInitializing = true;
        initComponents();
        initTabel();
        loadDropdown(); // ← HANYA dipanggil di sini
        loadData();
        isInitializing = false;
        I18nService.registerListener(this);
    }

    @Override
    public void onLanguageChanged() {
        System.out.println("onLanguageChanged dipanggil: " + I18nService.getCurrentLocale());
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Update judul
            jLabel1.setText(I18nService.get("ui.log.title"));

            // Update header kolom
            tableModel.setColumnIdentifiers(new Object[]{
                I18nService.get("ui.table.nama"),
                I18nService.get("ui.table.uid"),
                I18nService.get("ui.table.sekolah"),
                I18nService.get("ui.table.hari"),
                I18nService.get("ui.table.jam"),
                I18nService.get("ui.table.status")
            });

            // Update HANYA item pertama dropdown — JANGAN rebuild
            isInitializing = true;
            javax.swing.DefaultComboBoxModel<String> model =
                (javax.swing.DefaultComboBoxModel<String>) cbSekolah.getModel();
            model.removeElementAt(0);
            model.insertElementAt(I18nService.get("ui.dropdown.semua"), 0);
            cbSekolah.setSelectedIndex(0);
            isInitializing = false;

            loadData();
            this.revalidate();
            this.repaint();
        });
    }

    // SATU method untuk init dropdown — hanya dipanggil dari constructor
    private void loadDropdown() {
        isInitializing = true;
        javax.swing.DefaultComboBoxModel<String> model = new javax.swing.DefaultComboBoxModel<>();
        model.addElement(I18nService.get("ui.dropdown.semua"));
        for (String s : service.getAllSekolah()) {
            model.addElement(s);
        }
        cbSekolah.setModel(model);
        cbSekolah.setSelectedIndex(0);
        isInitializing = false;
    }

    private void initTabel() {
        tableModel = new DefaultTableModel(
                new Object[]{
                    I18nService.get("ui.table.nama"),
                    I18nService.get("ui.table.uid"),
                    I18nService.get("ui.table.sekolah"),
                    I18nService.get("ui.table.hari"),
                    I18nService.get("ui.table.jam"),
                    I18nService.get("ui.table.status")
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblLog.setModel(tableModel);
        tblLog.setRowHeight(28);
    }

    private void loadData() {
        String sekolahDipilih = (String) cbSekolah.getSelectedItem();
        String sekolah = null;
        String semua = I18nService.get("ui.dropdown.semua");
        if (sekolahDipilih != null
                && !sekolahDipilih.equals(semua)
                && !sekolahDipilih.equals("Semua")
                && !sekolahDipilih.equals("All")) {
            sekolah = sekolahDipilih;
        }

        String nama = txtCariNama.getText().trim();
        LocalDate tanggal = null;
        if (dateChooser.getDate() != null) {
            tanggal = dateChooser.getDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        List<LogAbsensiView> data = service.filterLog(sekolah, nama, tanggal);
        tableModel.setRowCount(0);
        for (LogAbsensiView item : data) {
            String status = item.getStatus();
            String statusTampil = "Hadir".equalsIgnoreCase(status)
                    ? I18nService.get("ui.table.hadir") : status;
            String hariTampil = mapHari(item.getHari());

            tableModel.addRow(new Object[]{
                item.getNamaLengkap(),
                item.getUidRfid(),
                item.getSekolah(),
                hariTampil,
                item.getJam(),
                statusTampil
            });
        }
    }

    private String mapHari(String hari) {
        if (hari == null) return "";
        switch (hari) {
            case "Senin":   return I18nService.get("ui.hari.senin");
            case "Selasa":  return I18nService.get("ui.hari.selasa");
            case "Rabu":    return I18nService.get("ui.hari.rabu");
            case "Kamis":   return I18nService.get("ui.hari.kamis");
            case "Jumat":   return I18nService.get("ui.hari.jumat");
            case "Sabtu":   return I18nService.get("ui.hari.sabtu");
            case "Minggu":  return I18nService.get("ui.hari.minggu");
            default:        return hari;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLog = new javax.swing.JTable();
        txtCariNama = new javax.swing.JTextField();
        cbSekolah = new javax.swing.JComboBox<>();
        btnRefresh = new javax.swing.JButton();
        dateChooser = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(null);

        jPanel1.setBackground(new java.awt.Color(119, 183, 226));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Riwayat Siswa");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addContainerGap(1185, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        add(jPanel1);
        jPanel1.setBounds(0, 0, 1380, 100);

        tblLog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblLog);

        add(jScrollPane1);
        jScrollPane1.setBounds(20, 190, 1260, 490);

        txtCariNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariNamaActionPerformed(evt);
            }
        });
        txtCariNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariNamaKeyReleased(evt);
            }
        });
        add(txtCariNama);
        txtCariNama.setBounds(850, 110, 220, 30);

        cbSekolah.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSekolah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSekolahActionPerformed(evt);
            }
        });
        add(cbSekolah);
        cbSekolah.setBounds(1080, 110, 100, 30);

        btnRefresh.setBackground(new java.awt.Color(0, 204, 51));
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-refresh-24.png"))); // NOI18N
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        add(btnRefresh);
        btnRefresh.setBounds(1250, 700, 30, 31);

        dateChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateChooserPropertyChange(evt);
            }
        });
        add(dateChooser);
        dateChooser.setBounds(1190, 110, 88, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadData();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtCariNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariNamaActionPerformed

    }//GEN-LAST:event_txtCariNamaActionPerformed

    private void txtCariNamaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariNamaKeyReleased
        loadData();
    }//GEN-LAST:event_txtCariNamaKeyReleased

    private void cbSekolahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSekolahActionPerformed
        if (!isInitializing) {
            loadData();
        }
    }//GEN-LAST:event_cbSekolahActionPerformed

    private void dateChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateChooserPropertyChange
        if ("date".equals(evt.getPropertyName())) {
            loadData();
        }
    }//GEN-LAST:event_dateChooserPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox<String> cbSekolah;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblLog;
    private javax.swing.JTextField txtCariNama;
    // End of variables declaration//GEN-END:variables
}
