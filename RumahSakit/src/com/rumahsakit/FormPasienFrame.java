package com.rumahsakit;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class FormPasienFrame extends JFrame {

    private static final Color BG = new Color(0xF0F4F8);
    private static final Color CARD = Color.WHITE;
    private static final Color PRIMARY = new Color(0x2563EB);
    private static final Color TEXT = new Color(0x1E293B);
    private static final Color BORDER = new Color(0xE2E8F0);
    private static final Map<String, Integer> DURASI = new HashMap<>();
        static {
            DURASI.put("REGULER", 3);
            DURASI.put("BPJS", 2);
            DURASI.put("VIP", 5);
        }

    JTextField txtNama = new JTextField(), txtHP = new JTextField();
    JTextArea txtAlamat = new JTextArea(3, 20);
    JComboBox<String> comboKronis = new JComboBox<>(new String[]{"Ringan", "Sedang", "Berat"});
    JCheckBox chkBPJS = new JCheckBox("BPJS");
    JLabel lblInfo = new JLabel();

    public FormPasienFrame() {
        setTitle("Input Pasien Baru");
        setSize(420, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        setContentPane(root);

        JLabel title = new JLabel("Form Pendaftaran Pasien");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(TEXT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
        root.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(CARD);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        styleField(txtNama); styleField(txtHP);
        txtNama.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtHP.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtAlamat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtAlamat.setBorder(BorderFactory.createLineBorder(BORDER));
        txtAlamat.setLineWrap(true);
        comboKronis.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboKronis.setMaximumSize(comboKronis.getPreferredSize());
        chkBPJS.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkBPJS.setBackground(CARD);
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(0x16A34A));
        lblInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane scrollAlamat = new JScrollPane(txtAlamat);
        scrollAlamat.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        scrollAlamat.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel bpjsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bpjsRow.setBackground(CARD);
        bpjsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        bpjsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        bpjsRow.add(new JLabel("Kronis: "));
        bpjsRow.add(comboKronis);
        bpjsRow.add(Box.createHorizontalStrut(12));
        bpjsRow.add(chkBPJS);

        JLabel lblNama = lbl("Nama Lengkap"); lblNama.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblAlamat = lbl("Alamat");      lblAlamat.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblHP = lbl("No. HP");          lblHP.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtNama.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtHP.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(lblNama);
        form.add(Box.createVerticalStrut(4));
        form.add(txtNama);
        form.add(Box.createVerticalStrut(8));
        form.add(lblAlamat);
        form.add(Box.createVerticalStrut(4));
        form.add(scrollAlamat);
        form.add(Box.createVerticalStrut(8));
        form.add(lblHP);
        form.add(Box.createVerticalStrut(4));
        form.add(txtHP);
        form.add(Box.createVerticalStrut(8));
        form.add(bpjsRow);
        form.add(Box.createVerticalStrut(6));
        form.add(lblInfo);

        root.add(form, BorderLayout.CENTER);

        // PERBAIKAN: Mengganti DataPasienPanel.makeBtn menjadi standar JButton agar tidak error
        JButton btnSimpan = new JButton("Simpan & Daftarkan");
        btnSimpan.setBackground(PRIMARY);
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFocusPainted(false);
        btnSimpan.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSimpan.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(BG);
        bottom.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        bottom.add(btnSimpan, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        updateInfo();
        comboKronis.addActionListener(e -> updateInfo());
        chkBPJS.addActionListener(e -> updateInfo());
        btnSimpan.addActionListener(e -> simpan());
    }

    void updateInfo() {
        String tipe = getTipe();
        lblInfo.setText("Kamar: " + tipe + "  |  Durasi: " + DURASI.getOrDefault(tipe, 3) + " hari");
    }

    String getTipe() {
        if (chkBPJS.isSelected()) return "BPJS";
        return comboKronis.getSelectedItem().toString().equalsIgnoreCase("Berat") ? "VIP" : "REGULER";
    }

    void simpan() {
        String nama = txtNama.getText().trim(), alamat = txtAlamat.getText().trim(), hp = txtHP.getText().trim();
        if (nama.isEmpty() || alamat.isEmpty() || hp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipeKamar = getTipe();

        // === LOADING DIALOG ===
        JDialog loadingDialog = new JDialog(this, "Memproses...", false);
        loadingDialog.setSize(340, 150);
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setUndecorated(true);
        loadingDialog.setResizable(false);

        JPanel loadingPanel = new JPanel(new BorderLayout(0, 10));
        loadingPanel.setBackground(Color.WHITE);
        loadingPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(22, 28, 20, 28)
        ));

        JLabel lblStatus = new JLabel("Mendaftarkan pasien baru...", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblStatus.setForeground(new Color(0x1E293B));

        JProgressBar progress = new JProgressBar(0, 100);
        progress.setValue(0);
        progress.setStringPainted(false);
        progress.setPreferredSize(new Dimension(280, 10));
        progress.setBorderPainted(false);
        progress.setForeground(PRIMARY);
        progress.setBackground(new Color(0xE2E8F0));

        JLabel lblSub = new JLabel("Menginisialisasi sistem...", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblSub.setForeground(new Color(0x64748B));

        loadingPanel.add(lblStatus, BorderLayout.NORTH);
        loadingPanel.add(progress, BorderLayout.CENTER);
        loadingPanel.add(lblSub, BorderLayout.SOUTH);
        loadingDialog.setContentPane(loadingPanel);
        loadingDialog.setVisible(true);

        // Tahap-tahap animasi pencarian kamar
        String[] stepLabels = {
            "Memvalidasi data pasien...",
            "Memeriksa ketersediaan kamar " + tipeKamar + "...",
            "Menelusuri " + (tipeKamar.equals("VIP") ? "lantai khusus VIP" : "seluruh lantai") + "...",
            "Kamar ditemukan, memverifikasi kondisi...",
            "Menyimpan data ke sistem..."
        };
        int[] stepProgress = {15, 35, 60, 80, 95};

        final int[] stepIdx = {0};
        javax.swing.Timer animTimer = new javax.swing.Timer(500, null);
        animTimer.addActionListener(e -> {
            if (stepIdx[0] < stepLabels.length) {
                lblSub.setText(stepLabels[stepIdx[0]]);
                progress.setValue(stepProgress[stepIdx[0]]);
                stepIdx[0]++;
            }
        });
        animTimer.start();

        new Thread(() -> {
            try { Thread.sleep(2500); } catch (InterruptedException ignored) {}

            try (Connection conn = Koneksi.getConnection()) {
                conn.setAutoCommit(false);
                try {
                    int durasi = DURASI.getOrDefault(tipeKamar, 3);

                    // 1. CEK KAMAR
                    PreparedStatement pk = conn.prepareStatement(
                        "SELECT id_kamar, nama_kamar FROM kamar WHERE tipe_kamar=? AND status_kamar='kosong' LIMIT 1");
                    pk.setString(1, tipeKamar);
                    ResultSet rk = pk.executeQuery();

                    if (!rk.next()) {
                        animTimer.stop();
                        SwingUtilities.invokeLater(() -> {
                            loadingDialog.dispose();
                            JOptionPane.showMessageDialog(this, "Kamar " + tipeKamar + " penuh!", "Kamar Penuh", JOptionPane.WARNING_MESSAGE);
                        });
                        return;
                    }

                    int idKamar = rk.getInt("id_kamar");
                    String namaKamar = rk.getString("nama_kamar");

                    // 2. INSERT PENDAFTARAN
                    PreparedStatement pst = conn.prepareStatement(
                        "INSERT INTO pendaftaran (nama_pasien, alamat, no_hp, level_kronis, bpjs, tanggal_daftar, id_admin) VALUES (?,?,?,?,?,NOW(),1)",
                        Statement.RETURN_GENERATED_KEYS);
                    pst.setString(1, nama); pst.setString(2, alamat); pst.setString(3, hp);
                    pst.setString(4, comboKronis.getSelectedItem().toString()); pst.setBoolean(5, chkBPJS.isSelected());
                    pst.executeUpdate();
                    ResultSet keys = pst.getGeneratedKeys();
                    int idPendaftaran = keys.next() ? keys.getInt(1) : -1;

                    // 3. INSERT RAWAT INAP
                    PreparedStatement pr = conn.prepareStatement(
                        "INSERT INTO rawat_inap (id_pendaftaran, id_kamar, tanggal_masuk, tanggal_keluar, status) VALUES (?,?,NOW(),DATE_ADD(NOW(),INTERVAL ? DAY),'aktif')");
                    pr.setInt(1, idPendaftaran); pr.setInt(2, idKamar); pr.setInt(3, durasi); pr.executeUpdate();

                    // 4. UPDATE STATUS KAMAR
                    PreparedStatement pu = conn.prepareStatement("UPDATE kamar SET status_kamar='terisi' WHERE id_kamar=?");
                    pu.setInt(1, idKamar); pu.executeUpdate();

                    conn.commit();

                    SwingUtilities.invokeLater(() -> {
                        animTimer.stop();
                        progress.setValue(100);
                        lblSub.setText("Selesai!");
                    });

                    Thread.sleep(400);

                    String finalNamaKamar = namaKamar;
                    SwingUtilities.invokeLater(() -> {
                        loadingDialog.dispose();
                        JOptionPane.showMessageDialog(this,
                            "Pasien berhasil didaftarkan!\n\nKamar: " + finalNamaKamar + " (" + tipeKamar + ")\nDurasi Rawat: " + durasi + " hari",
                            "Pendaftaran Berhasil", JOptionPane.INFORMATION_MESSAGE);
                        txtNama.setText(""); txtAlamat.setText(""); txtHP.setText("");
                        chkBPJS.setSelected(false); comboKronis.setSelectedIndex(0);
                    });

                } catch (Exception ex) {
                    conn.rollback();
                    throw ex;
                } finally {
                    conn.setAutoCommit(true);
                }
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    animTimer.stop();
                    loadingDialog.dispose();
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    static void styleField(JTextField f) {
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER), BorderFactory.createEmptyBorder(6, 8, 6, 8)));
    }

    static JLabel lbl(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(new Color(0x1E293B));
        return l;
    }
}