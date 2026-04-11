package com.rumahsakit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class DataPasienFrame extends JPanel {

    private static final Color BG = new Color(0xF0F4F8);
    private static final Color CARD = Color.WHITE;
    private static final Color PRIMARY = new Color(0x2563EB);
    private static final Color DANGER = new Color(0xDC2626);
    private static final Color TEXT = new Color(0x1E293B);
    private static final Color BORDER = new Color(0xE2E8F0);

    JTable table;
    DefaultTableModel model;
    JTextField txtSearch = new JTextField(18);

    public DataPasienFrame() {
        setLayout(new BorderLayout(0, 12));
        setBackground(BG);
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Data Pasien");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(TEXT);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        controls.setOpaque(false);
        LoginFrame.styleField(txtSearch);
        txtSearch.putClientProperty("JTextField.placeholderText", "Cari nama...");
        JButton btnRefresh = makeBtn("Refresh", PRIMARY);
        JButton btnKeluar  = makeBtn("Pasien Keluar", DANGER);
        controls.add(txtSearch); controls.add(btnRefresh); controls.add(btnKeluar);

        header.add(title, BorderLayout.WEST);
        header.add(controls, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Table
        String[] cols = {"ID", "Nama Pasien", "Kronis", "BPJS", "Status", "Tanggal Masuk", "Estimasi Keluar", "Sisa Hari"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        //Lebar kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(60);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(200);  // Nama Pasien
        table.getColumnModel().getColumn(2).setPreferredWidth(100);  // Kronis
        table.getColumnModel().getColumn(3).setPreferredWidth(80);   // BPJS
        table.getColumnModel().getColumn(4).setPreferredWidth(100);  // Status
        table.getColumnModel().getColumn(5).setPreferredWidth(130);  // Tgl Masuk
        table.getColumnModel().getColumn(6).setPreferredWidth(130);  // Tgl Keluar
        table.getColumnModel().getColumn(7).setPreferredWidth(100);  // Sisa Hari
        
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
    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        sp.setBorder(BorderFactory.createLineBorder(BORDER));
        sp.getViewport().setBackground(CARD);
        add(sp, BorderLayout.CENTER);

        loadData("");
        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { loadData(txtSearch.getText()); }
        });
        btnRefresh.addActionListener(e -> loadData(txtSearch.getText()));
        btnKeluar.addActionListener(e -> pasienKeluar());
    }

    void loadData(String keyword) {
        try {
            model.setRowCount(0);
            Connection conn = Koneksi.getConnection();
            PreparedStatement pst = conn.prepareStatement(
                "SELECT p.id_pendaftaran, p.nama_pasien, p.level_kronis, p.bpjs, " +
                "ri.status, ri.tanggal_keluar, ri.tanggal_masuk FROM pendaftaran p " +
                "LEFT JOIN rawat_inap ri ON ri.id_pendaftaran=p.id_pendaftaran AND ri.status='aktif' " +
                "WHERE p.nama_pasien LIKE ? ORDER BY p.id_pendaftaran DESC");
            pst.setString(1, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String status = rs.getString("status");
                String tglMasuk = "-", tglKeluar = "-", sisa = "-";
                if ("aktif".equals(status)) {
    java.sql.Date masuk = rs.getDate("tanggal_masuk");
    java.sql.Date keluar = rs.getDate("tanggal_keluar");

    if (masuk != null) tglMasuk = masuk.toString();
    if (keluar != null) {
        tglKeluar = keluar.toString();
        long s = ChronoUnit.DAYS.between(LocalDate.now(), keluar.toLocalDate());
        sisa = s <= 0 ? "Habis" : s + " hari";
    }
} else {
    status = "-";
}
                model.addRow(new Object[]{
    rs.getInt("id_pendaftaran"),
    rs.getString("nama_pasien"),
    rs.getString("level_kronis"),
    rs.getBoolean("bpjs") ? "Ya" : "Tidak",
    status,
    tglMasuk,
    tglKeluar,
    sisa
});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void pasienKeluar() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih baris pasien dulu!", "Info", JOptionPane.INFORMATION_MESSAGE); return; }
        int modelRow = table.convertRowIndexToModel(row);
        int id = (int) model.getValueAt(modelRow, 0);
        String nama = (String) model.getValueAt(modelRow, 1);
        if (JOptionPane.showConfirmDialog(this, "Keluarkan pasien: " + nama + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM rawat_inap WHERE id_pendaftaran=? AND status='aktif'");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                PreparedStatement p2 = conn.prepareStatement("UPDATE rawat_inap " +
                        "SET status='selesai', tanggal_dikeluarkan=NOW() " +
                        "WHERE id_rawat=?");
                p2.setInt(1, rs.getInt("id_rawat")); p2.executeUpdate();
                PreparedStatement p3 = conn.prepareStatement("UPDATE kamar SET status_kamar='kosong'" +" WHERE id_kamar=?");
                p3.setInt(1, rs.getInt("id_kamar")); p3.executeUpdate();
                JOptionPane.showMessageDialog(this, "Pasien " + nama + " dikeluarkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadData("");
                // Refresh dashboard
                for (Window w : Window.getWindows()) {
                    if (w instanceof DashboardFrame) {
                        ((DashboardFrame) w).showHome();
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pasien tidak sedang rawat inap.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static JButton makeBtn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}