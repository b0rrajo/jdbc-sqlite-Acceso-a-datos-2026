package com.carballeira.jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;

public class AppS45_matriculasAlumno {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Nombre: ");
		String nombre = sc.nextLine();
		System.out.print("Apellidos: ");
		String apellidos = sc.nextLine();
		System.out.print("Nota: ");
		double nota = Double.parseDouble(sc.nextLine().replace(',', '.'));
		System.out.print("¿En cuántas asignaturas quieres matricular al alumno? ");
		int cantidad = Integer.parseInt(sc.nextLine());
		String url = "jdbc:mariadb://192.168.10.95:3306/academia";
		String user = "root", pass = "abc123.";
		String sql = "INSERT INTO alumnos (nombre, apellidos, nota) VALUES (?, ?, ?)";
		String sqlMatricula = "INSERT INTO matriculas (id_alumno, asignatura) VALUES (?, ?)";
		try (Connection con = (Connection) DriverManager.getConnection(url, user, pass);
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, nombre);
			ps.setString(2, apellidos);
			ps.setDouble(3, nota);
			int filas = ps.executeUpdate();
			if (filas > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						int idAlumno = rs.getInt(1);
						System.out.println("Alumno insertado. ID: " + idAlumno);
						try (PreparedStatement psMatricula = con.prepareStatement(sqlMatricula)) {
							int totalMatriculas = 0;
							for (int i = 1; i <= cantidad; i++) {
								System.out.print("Asignatura " + i + ": ");
								String asignatura = sc.nextLine();
								psMatricula.setInt(1, idAlumno);
								psMatricula.setString(2, asignatura);
								int filasMatricula = psMatricula.executeUpdate();
								totalMatriculas += filasMatricula;
							}
							System.out.println("Matrículas insertadas: " + totalMatriculas);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("error!: " + e.getMessage());
		}
		sc.close();
	}
}