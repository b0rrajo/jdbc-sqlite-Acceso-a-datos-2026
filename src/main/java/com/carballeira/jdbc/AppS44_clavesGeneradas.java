package com.carballeira.jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;

public class AppS44_clavesGeneradas {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws SQLException {
		String url = "jdbc:mariadb://10.10.32.14:3306/academia";
		String user = "root", pass = "abc123.";
		
		System.out.print("Indica cuantos alumnos quieres meter: ");
		
		int nuevosRegistros=sc.nextInt();
		sc.nextLine();
		
		for(int i=0; i<nuevosRegistros; i++) {
			
			System.out.println("=== NUEVO ALUMNO " + (i+1) + " ===");
			
			System.out.print("Nombre: ");
			String nombre = sc.nextLine();
			System.out.print("Apellidos: ");
			String apellidos = sc.nextLine();
			System.out.print("Nota: ");
			double nota = Double.parseDouble(sc.nextLine().replace(',', '.'));
		

			String sql = "INSERT INTO alumnos (nombre, apellidos, nota) " + "VALUES (?, ?, ?)";

			try(Connection con = (Connection) DriverManager.getConnection(url, user, pass)){
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				int filas;
				ps.setString(1, nombre);
				ps.setString(2, apellidos);
				ps.setDouble(3, nota);
				filas = ps.executeUpdate();
				System.out.println("Filas insertadas: " + filas);
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						int idGenerado = rs.getInt(1);
						System.out.println("ID generado: " + idGenerado);
					}
				}
			}
			 catch (Exception e) {
					System.out.println("error!: " + e.getMessage());
			}
		}
		
		

	}

}
