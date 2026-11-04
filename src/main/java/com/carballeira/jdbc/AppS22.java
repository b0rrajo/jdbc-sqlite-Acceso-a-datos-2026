package com.carballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppS22 {
	public static void main(String[] Args) {
		ArrayList<alumno> ListaAlumnos = new ArrayList<alumno>();
		System.out.println("Número de alumnos: " + ListaAlumnos.size());
		String url = "jdbc:sqlite:java26.db";

		try (Connection conexion = DriverManager.getConnection(url)) {
			String sql = "SELECT * FROM alumno";
			PreparedStatement ps =
			conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				alumno alumnoBD = new alumno(
						rs.getString("dni"),
						rs.getString("nombre"),
						rs.getString("apellido1"),
						rs.getString("apellido2"),
						rs.getString("ciclo"),
						rs.getInt("curso"),
						rs.getInt("eva1"),
						rs.getInt("eva2"),
						rs.getInt("eva3"),
						rs.getInt("notaFinal")
						);
				ListaAlumnos.add(alumnoBD);
			}
			
			System.out.println("Número de alumnos guardados: " + ListaAlumnos.size());
			
			for (alumno alumno : ListaAlumnos) {
				System.out.println(
				alumno.getDni() + " - " +
				alumno.getNombre() + " " +
				alumno.getApellido1() +
				" - Nota final: " +
				alumno.getNotaFinal()
				);
			}
			for (alumno alumno : ListaAlumnos) {
				if (alumno.getNotaFinal() < 5) {
				System.out.println(
				alumno.getNombre() + " " +
				alumno.getApellido1() +
				" - Nota final: " +
				alumno.getNotaFinal()
				);
				}
				}


		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}
}
