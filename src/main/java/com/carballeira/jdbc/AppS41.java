package com.carballeira.jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.mariadb.jdbc.Connection;

public class AppS41 {

	public static void main(String[] args) {
		String url = "jdbc:mariadb://10.10.32.14:3306/academia";
		String usuario = "root";
		String password = "abc123.";
		try (Connection conexion = (Connection) DriverManager.getConnection(url, usuario, password)) {
		    System.out.println("Conexión correcta con MariaDB");
		    
		    String sql = "SELECT * FROM alumnos";
			try (PreparedStatement ps = conexion.prepareStatement(sql);
			     ResultSet rs = ps.executeQuery()) {
			    while (rs.next()) {
			        int id = rs.getInt("id");
			        String nombre = rs.getString("nombre");
			        String apellidos = rs.getString("apellidos");
			        double nota = rs.getDouble("nota");
			        System.out.println(id + " - " + nombre + " " + apellidos + " - Nota: " + nota);
			    }
			}
			
			try (Scanner sc = new Scanner(System.in)) {
			    System.out.print("Introduce la nota mínima: ");
			    double notaMinima = sc.nextDouble();
			    sql = "SELECT * FROM alumnos WHERE nota >= ?";
			    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
			        ps.setDouble(1, notaMinima); // 1 = primer parámetro ?
			        try (ResultSet rs = ps.executeQuery()) {
			            while (rs.next()) {
			                String nombre = rs.getString("nombre");
			                String apellidos = rs.getString("apellidos");
			                double nota = rs.getDouble("nota");
			                System.out.println(nombre + " " + apellidos + " - Nota: " + nota);
			            }
			        }
			    }
			}
		    
		} catch (SQLException e) {
		    System.out.println("Error: " + e.getMessage());
		}

	}

}
