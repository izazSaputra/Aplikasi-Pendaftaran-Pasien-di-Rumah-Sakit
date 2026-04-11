package com.rumahsakit;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/rumah_sakit";
            String user = "root";
            String pass = "";

            Connection conn = DriverManager.getConnection(url, user, pass);
            return conn;
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}