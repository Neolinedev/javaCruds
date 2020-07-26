/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacruds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user999
 */
public class JavaCruds {
    // Melakukan koneksi ke mysql
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/db_python3?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "";
    
    // Menyiapkan objek untuk mengelola Database
    static Connection db;
    static Statement stmt;
    static ResultSet rs;
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Melakukan koneksi kedalam Database
        try {
            // register driver
            Class.forName(JDBC_DRIVER);

            db = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = db.createStatement();

            while (!db.isClosed()) {
                showMenu();
            }

            stmt.close();
            db.close();

        } catch (ClassNotFoundException | SQLException e) {
        
        }   
    }
    
    static void showMenu() {
        System.out.println("\n========= Daftar Perintah =========");
        System.out.println("1. Insert Data");
        System.out.println("2. Show Data");
        System.out.println("3. Edit Data");
        System.out.println("4. Delete Data");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN : ");
        
        try {
            int pilihan = Integer.parseInt(input.readLine());
            
            switch(pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertData();
                    break;
                case 2:
                    showData();
                    break;
                case 3:
                    updateData();
                    break;
                case 4:
                    deleteData();
                    break;
                default:
                    System.out.println("Pilihan Salah !");
            }
        } catch (IOException | NumberFormatException e) {
        
        }
    }
    
    static void showData() {
        String sql = "SELECT * FROM students";

        try {
            rs = stmt.executeQuery(sql);
            
            System.out.println("+----------------------+");
            System.out.println("|    DATA MAHASISWA    |");
            System.out.println("+----------------------+");

            while (rs.next()) {
                int id = rs.getInt("students_id");
                String nama = rs.getString("name");
                String nim = rs.getString("nim");
                String jurusan = rs.getString("jurusan");

                
                System.out.println(String.format("%d. %s -- %s (%s)", id, nama, nim, jurusan));
            }

        } catch (SQLException e) {
        }

    }
    
    static void insertData() throws IOException {
        try {
            // Mengambil input dari user
            System.out.print("Nama : ");
            String nama = input.readLine().trim();
            System.out.print("Nim : ");
            String nim = input.readLine().trim();
            System.out.print("Jurusan : ");
            String jurusan = input.readLine().trim();
            
            // Masukkan data kedalam Database
            String sql = "INSERT INTO students (name,nim,jurusan) VALUES ('%s','%s','%s')";
            sql = String.format(sql,nama,nim,jurusan);
            
            // Simpan Data
            stmt.execute(sql);
            
            System.out.println("Data berhasil disimpan...");
        } catch (SQLException ex) {
            Logger.getLogger(JavaCruds.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static void updateData() {
        try {
            
            // ambil input dari user
            System.out.print("ID yang mau diedit: ");
            int id = Integer.parseInt(input.readLine());
            System.out.print("Nama: ");
            String nama = input.readLine().trim();
            System.out.print("Nim: ");
            String nim = input.readLine().trim();
            System.out.print("Jurusan: ");
            String jurusan = input.readLine().trim();

            // query update
            String sql = "UPDATE students SET nama='%s', nim='%s', jurusan='%s' WHERE students_id=%d";
            sql = String.format(sql, nama, nim, jurusan, id);

            // update data buku
            stmt.execute(sql);
            
            System.out.println("Data berhasil diubah...");
            
        } catch (IOException | NumberFormatException | SQLException e) {
        }
    }

    static void deleteData() {
        try {
            
            // ambil input dari user
            System.out.print("ID yang mau dihapus: ");
            int id = Integer.parseInt(input.readLine());
            
            // buat query hapus
            String sql = String.format("DELETE FROM students WHERE students_id=%d", id);

            // hapus data
            stmt.execute(sql);
            
            System.out.println("Data berhasil dihapus...");
        } catch (IOException | NumberFormatException | SQLException e) {
        }
    }
    
}
