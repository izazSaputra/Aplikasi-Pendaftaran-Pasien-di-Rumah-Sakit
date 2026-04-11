/*package com.rumahsakit;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame {

    static final Color BG = new Color(0xF0F4F8);
    static final Color CARD = Color.WHITE;
    static final Color PRIMARY = new Color(0x2563EB);
    static final Color TEXT = new Color(0x1E293B);
    static final Color SUBTEXT = new Color(0x64748B);
    static final Color BORDER = new Color(0xE2E8F0);

    JTextField txtUsername = new JTextField();
    JPasswordField txtPassword = new JPasswordField();

    public LoginFrame() {
        setTitle("RS Admin - Login");
        setSize(380, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(BG);
        setContentPane(root);

        JPanel card = new JPanel(new GridLayout(0, 1, 0, 10));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(28, 30, 28, 30)
        ));

        JLabel title = new JLabel("RS Admin", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(PRIMARY);

        JLabel sub = new JLabel("Panel Administrasi", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(SUBTEXT);

        styleField(txtUsername);
        styleField(txtPassword);
        txtUsername.putClientProperty("JTextField.placeholderText", "Username");
        txtPassword.putClientProperty("JTextField.placeholderText", "Password");

        JButton btnLogin = new JButton("Masuk");
        btnLogin.setBackground(PRIMARY);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        card.add(title);
        card.add(sub);
        card.add(txtUsername);
        card.add(txtPassword);
        card.add(btnLogin);

        root.add(card);

        btnLogin.addActionListener(e -> login());
        txtPassword.addActionListener(e -> login());
    }

    static void styleField(JComponent f) {
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
    }

    void login() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM admin WHERE username=? AND password=?");
            pst.setString(1, user);
            pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) { new DashboardFrame().setVisible(true); dispose(); }
            else JOptionPane.showMessageDialog(this, "Username atau password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Koneksi database gagal: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Reusable border class used by other frames
    static class RoundedBorder extends AbstractBorder {
        private final int r; private final Color c;
        RoundedBorder(int r, Color c) { this.r = r; this.c = c; }
        public void paintBorder(Component comp, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c); g2.drawRoundRect(x, y, w-1, h-1, r*2, r*2); g2.dispose();
        }
        public Insets getBorderInsets(Component c) { return new Insets(r, r, r, r); }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}*/
package com.rumahsakit;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginFrame() {
        setTitle("Login Admin Rumah Sakit");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        initUI();
    }

    public static void styleField(JComponent f) {
    f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    f.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(0xE2E8F0)),
        BorderFactory.createEmptyBorder(6, 10, 6, 10)
    ));
}
    
    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("LOGIN ADMIN", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        // Username
        gbc.gridy++;
        panel.add(new JLabel("Username"), gbc);

        txtUsername = new JTextField();
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Password"), gbc);

        txtPassword = new JPasswordField();
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        // Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(37, 99, 235));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        add(panel);

        // Event
        btnLogin.addActionListener(e -> login());
        txtPassword.addActionListener(e -> login());
    }

    private void login() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi!");
            return;
        }

        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement pst = conn.prepareStatement(
                "SELECT * FROM admin WHERE username=? AND password=?"
            );
            pst.setString(1, user);
            pst.setString(2, pass);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login berhasil!");
                new DashboardFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login gagal!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}