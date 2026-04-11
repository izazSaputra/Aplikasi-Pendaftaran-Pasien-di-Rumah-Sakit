package com.rumahsakit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class HistoryPasienFrame extends JPanel {

    private static final Color BG = new Color(0xF0F4F8);
    private static final Color CARD = Color.WHITE;
    private static final Color TEXT = new Color(0x1E293B);
    private static final Color BORDER = new Color(0xE2E8F0);

    JTable table;
    DefaultTableModel model;
    JTextField txtSearch = new JTextField(18);

    public HistoryPasienFrame() {
        setLayout(new BorderLayout(0, 12));
        setBackground(BG);
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("History Keluar Masuk Pasien");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(TEXT);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setOpaque(false);

        txtSearch.putClientProperty("JTextField.placeholderText", "Cari nama pasien...");
        right.add(txtSearch);

        header.add(title, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Table
        String[] cols = {"ID", "Nama Pasien", "Tanggal Masuk", "Tanggal Estimasi Keluar", "Tanggal Dikeluarkan"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.setGridColor(BORDER);
        table.setShowVerticalLines(false);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(0xF8FAFC));
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane sp = new JScrollPane(
                table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        sp.setBorder(BorderFactory.createLineBorder(BORDER));
        sp.getViewport().setBackground(CARD);

        add(sp, BorderLayout.CENTER);

        loadData("");
        
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                loadData(txtSearch.getText());
    }
});
    }

void loadData(String keyword) {
    model.setRowCount(0);

    try (Connection conn = Koneksi.getConnection();
         PreparedStatement pst = conn.prepareStatement(
            "SELECT p.id_pendaftaran, p.nama_pasien, ri.tanggal_masuk, " +
            "ri.tanggal_keluar, ri.tanggal_dikeluarkan " +
            "FROM rawat_inap ri " +
            "JOIN pendaftaran p ON ri.id_pendaftaran = p.id_pendaftaran " +
            " WHERE ri.status='selesai'AND p.nama_pasien LIKE ? " +
            "ORDER BY ri.tanggal_masuk DESC"
         )) {

        pst.setString(1, "%" + keyword + "%");

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {

                String tglMasuk = rs.getDate("tanggal_masuk") != null
                        ? rs.getDate("tanggal_masuk").toString() : "-";

                String tglEstimasi = rs.getDate("tanggal_keluar") != null
                        ? rs.getDate("tanggal_keluar").toString() : "-";

                String tglKeluar = rs.getDate("tanggal_dikeluarkan") != null
                        ? rs.getDate("tanggal_dikeluarkan").toString() : "-";

                model.addRow(new Object[]{
                        rs.getInt("id_pendaftaran"),
                        rs.getString("nama_pasien"),
                        tglMasuk,
                        tglEstimasi,
                        tglKeluar
                });
            }
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}