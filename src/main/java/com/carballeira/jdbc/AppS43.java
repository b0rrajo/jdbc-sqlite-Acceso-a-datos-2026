package com.carballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AppS43 {
	public static void main(String[] args) throws SQLException {
		String url = "jdbc:mariadb://192.168.10.95:3306/academia";
		String usuario = "root";
		String password = "abc123.";
		String sql = "SELECT alumnos.nombre, alumnos.apellidos, " + "matriculas.asignatura FROM alumnos "
				+ "INNER JOIN matriculas " + "ON alumnos.id = matriculas.id_alumno "
				+ "ORDER BY alumnos.id, matriculas.id";
		try (Connection con = DriverManager.getConnection(url, usuario, password);
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			System.out.println("MATRÍCULAS REGISTRADAS");
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				String asignatura = rs.getString("asignatura");
				System.out.println(nombre + " " + apellidos + " - " + asignatura);
			}
		}

		sql = "SELECT alumnos.nombre, alumnos.apellidos, " + "matriculas.asignatura FROM alumnos "
				+ "INNER JOIN matriculas ON alumnos.id = matriculas.id_alumno "
				+ "WHERE alumnos.id = ? ORDER BY matriculas.id";
		try (Scanner sc = new Scanner(System.in);
				Connection con = DriverManager.getConnection(url, usuario, password);
				PreparedStatement ps = con.prepareStatement(sql)) {
			System.out.print("Introduce el ID del alumno: ");
			int idAlumno = sc.nextInt();
			ps.setInt(1, idAlumno);
			try (ResultSet rs = ps.executeQuery()) {
				boolean encontrado = false;
				int contador =0;
				while (rs.next()) {
					 if (!encontrado) {
					 System.out.println("Asignaturas de " +
					 rs.getString("nombre") + " " +
					 rs.getString("apellidos") + ":");
					 }
					 System.out.println("- " + rs.getString("asignatura"));
					 contador++;
					 encontrado = true;
					}
					if (!encontrado) {
					 System.out.println("No hay matrículas para ese alumno.");
					} else {
					 System.out.println("Total de asignaturas: " + contador);
					}

			}
			sql = "SELECT alumnos.nombre, alumnos.apellidos FROM alumnos "
					+ "INNER JOIN matriculas ON alumnos.id = matriculas.id_alumno " + "WHERE matriculas.asignatura = ? "
					+ "ORDER BY alumnos.apellidos, alumnos.nombre";
			try (Scanner sc1 = new Scanner(System.in);
					Connection con1 = DriverManager.getConnection(url, usuario, password);
					PreparedStatement ps1 = con1.prepareStatement(sql)) {
				System.out.print("Introduce una asignatura: ");
				String asignatura = sc1.nextLine();
				ps1.setString(1, asignatura);
				try (ResultSet rs = ps1.executeQuery()) {
					int contador = 0;
					System.out.println("Alumnos matriculados:");
					while (rs.next()) {
						String nombre = rs.getString("nombre");
						String apellidos = rs.getString("apellidos");
						System.out.println("- " + nombre + " " + apellidos);
						contador++;
					}
					if (contador == 0) {
						System.out.println("No hay alumnos en esa asignatura.");
					}
					System.out.println("Total de alumnos: " + contador);
				}
			} catch (SQLException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}