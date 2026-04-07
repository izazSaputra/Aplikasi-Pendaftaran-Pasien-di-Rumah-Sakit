package com.rumahsakit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.ChronoUnit;

public class DashboardFrame extends JFrame {

    static final Color BG = new Color(0xF0F4F8);
    static final Color SIDEBAR = new Color(0x1E293B);
    static final Color CARD = Color.WHITE;
    static final Color PRIMARY = new Color(0x2563EB);
    static final Color SUCCESS = new Color(0x16A34A);
    static final Color WARNING = new Color(0xD97706);
    static final Color DANGER = new Color(0xDC2626);
    static final Color TEXT = new Color(0x1E293B);
    static final Color SUBTEXT = new Color(0x64748B);
    static final Color BORDER = new Color(0xE2E8F0);

    private CardLayout cardLayout = new CardLayout();
    private JPanel contentPanel = new JPanel(cardLayout);
    private JLabel lblTime = new JLabel();

    public DashboardFrame() {
        setTitle("RS Admin - Dashboard");
        setSize(1020, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        autoDischarge();

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        setContentPane(root);
        root.add(buildSidebar(), BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);
        main.add(buildTopBar(), BorderLayout.NORTH);
        contentPanel.setBackground(BG);
        contentPanel.add(buildHome(), "home");
        main.add(contentPanel, BorderLayout.CENTER);
        root.add(main, BorderLayout.CENTER);

        new Timer(1000, e -> lblTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))).start();
    }

    JPanel buildSidebar() {
        JPanel s = new JPanel(new BorderLayout());
        s.setBackground(SIDEBAR);
        s.setPreferredSize(new Dimension(200, 0));

        JLabel logo = new JLabel("RS Admin");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        logo.setForeground(Color.WHITE);
        logo.setBorder(BorderFactory.createEmptyBorder(22, 18, 18, 0));

        JPanel nav = new JPanel(new GridLayout(0, 1));
        nav.setBackground(SIDEBAR);
        nav.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        nav.add(sideBtn("Dashboard",    e -> showHome()));
        nav.add(sideBtn("Input Pasien", e -> new FormPasienFrame().setVisible(true)));
        nav.add(sideBtn("Data Pasien",  e -> showPanel("data", new DataPasienFrame())));

        JPanel bottom = new JPanel(new GridLayout(0, 1));
        bottom.setBackground(SIDEBAR);
        JButton btnLogout = sideBtn("Logout", e -> { dispose(); new LoginFrame().setVisible(true); });
        btnLogout.setForeground(new Color(0xFCA5A5));
        bottom.add(btnLogout);

        s.add(logo, BorderLayout.NORTH);
        s.add(nav, BorderLayout.CENTER);
        s.add(bottom, BorderLayout.SOUTH);
        return s;
    }

    JButton sideBtn(String text, java.awt.event.ActionListener action) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setForeground(new Color(0xCBD5E1));
        b.setBackground(SIDEBAR);
        b.setBorderPainted(false); b.setFocusPainted(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 0));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(action);
        return b;
    }

    JPanel buildTopBar() {
        JPanel t = new JPanel(new BorderLayout());
        t.setBackground(CARD);
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        JLabel lbl = new JLabel("Selamat Datang, Admin");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(TEXT);
        lblTime.setFont(new Font("Courier New", Font.BOLD, 14));
        lblTime.setForeground(PRIMARY);
        t.add(lbl, BorderLayout.WEST);
        t.add(lblTime, BorderLayout.EAST);
        return t;
    }

    void showHome() {
        contentPanel.removeAll();
        contentPanel.add(buildHome(), "home");
        cardLayout.show(contentPanel, "home");
        contentPanel.revalidate(); contentPanel.repaint();
    }

    void showPanel(String key, JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, key);
        cardLayout.show(contentPanel, key);
        contentPanel.revalidate(); contentPanel.repaint();
    }

    JPanel buildHome() {
        JPanel p = new JPanel(new BorderLayout(0, 16));
        p.setBackground(BG);
        p.setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));

        JLabel heading = new JLabel("Overview Hari Ini");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 16));
        heading.setForeground(TEXT);
        p.add(heading, BorderLayout.NORTH);

        int[] s = getStats();
        JPanel cards = new JPanel(new GridLayout(1, 4, 14, 0));
        cards.setOpaque(false);
        cards.add(statCard("Pasien Aktif",    s[0], PRIMARY));
        cards.add(statCard("Kamar Tersedia",  s[1], SUCCESS));
        cards.add(statCard("Kamar Terisi",    s[2], WARNING));
        cards.add(statCard("Keluar Hari Ini", s[3], DANGER));

        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setOpaque(false);
        center.add(cards, BorderLayout.NORTH);
        center.add(buildTable(), BorderLayout.CENTER);
        p.add(center, BorderLayout.CENTER);
        return p;
    }

    JPanel statCard(String label, int value, Color accent) {
        JPanel c = new JPanel(new BorderLayout(0, 4));
        c.setBackground(CARD);
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, accent),
            BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        JLabel val = new JLabel(String.valueOf(value));
        val.setFont(new Font("Segoe UI", Font.BOLD, 28));
        val.setForeground(accent);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(SUBTEXT);
        c.add(val, BorderLayout.CENTER);
        c.add(lbl, BorderLayout.SOUTH);
        return c;
    }

    JScrollPane buildTable() {
        String[] cols = {"Nama Pasien", "Tipe Kamar", "Kronis", "BPJS", "Tgl Masuk", "Tgl Keluar", "Sisa Hari"};
        DefaultTableModel mdl = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        try {
            Connection conn = Koneksi.getConnection();
            ResultSet rs = conn.prepareStatement(
                "SELECT p.nama_pasien, k.tipe_kamar, p.level_kronis, p.bpjs, ri.tanggal_masuk, ri.tanggal_keluar " +
                "FROM rawat_inap ri JOIN pendaftaran p ON ri.id_pendaftaran=p.id_pendaftaran " +
                "JOIN kamar k ON ri.id_kamar=k.id_kamar WHERE ri.status='aktif' ORDER BY ri.tanggal_masuk DESC LIMIT 30"
            ).executeQuery();
            while (rs.next()) {
                java.sql.Date d = rs.getDate("tanggal_keluar");
                long sisa = ChronoUnit.DAYS.between(LocalDate.now(), d.toLocalDate());
                mdl.addRow(new Object[]{ rs.getString("nama_pasien"), rs.getString("tipe_kamar"),
                    rs.getString("level_kronis"), rs.getBoolean("bpjs") ? "Ya" : "Tidak",
                    rs.getDate("tanggal_masuk").toString(), d.toString(),
                    sisa <= 0 ? "Habis" : sisa + " hari" });
            }
        } catch (Exception e) { e.printStackTrace(); }

        JTable tbl = styledTable(mdl);
        JScrollPane sp = new JScrollPane(tbl);
        sp.setBorder(BorderFactory.createLineBorder(BORDER));
        sp.getViewport().setBackground(CARD);
        return sp;
    }

    int[] getStats() {
        int[] r = new int[4];
        try {
            Connection conn = Koneksi.getConnection();
            ResultSet rs;
            rs = conn.prepareStatement("SELECT COUNT(*) FROM rawat_inap WHERE status='aktif'").executeQuery();
            if (rs.next()) r[0] = rs.getInt(1);
            rs = conn.prepareStatement("SELECT COUNT(*) FROM kamar WHERE status_kamar='kosong'").executeQuery();
            if (rs.next()) r[1] = rs.getInt(1);
            rs = conn.prepareStatement("SELECT COUNT(*) FROM kamar WHERE status_kamar='terisi'").executeQuery();
            if (rs.next()) r[2] = rs.getInt(1);
            rs = conn.prepareStatement("SELECT COUNT(*) FROM rawat_inap WHERE status='selesai' AND DATE(tanggal_keluar)=CURDATE()").executeQuery();
            if (rs.next()) r[3] = rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return r;
    }

    void autoDischarge() {
        try {
            Connection conn = Koneksi.getConnection();
            ResultSet rs = conn.prepareStatement("SELECT id_rawat, id_kamar FROM rawat_inap WHERE tanggal_keluar<=NOW() AND status='aktif'").executeQuery();
            while (rs.next()) {
                PreparedStatement p2 = conn.prepareStatement("UPDATE rawat_inap SET status='selesai' WHERE id_rawat=?");
                p2.setInt(1, rs.getInt("id_rawat")); p2.executeUpdate();
                PreparedStatement p3 = conn.prepareStatement("UPDATE kamar SET status_kamar='kosong' WHERE id_kamar=?");
                p3.setInt(1, rs.getInt("id_kamar")); p3.executeUpdate();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    static JTable styledTable(DefaultTableModel mdl) {
        JTable tbl = new JTable(mdl);
        tbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tbl.setRowHeight(28);
        tbl.setGridColor(BORDER);
        tbl.setShowVerticalLines(false);
        tbl.setAutoCreateRowSorter(true);
        tbl.setSelectionBackground(new Color(0xEFF6FF));
        tbl.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tbl.getTableHeader().setBackground(new Color(0xF8FAFC));
        tbl.getTableHeader().setReorderingAllowed(false);
        return tbl;
    }
}