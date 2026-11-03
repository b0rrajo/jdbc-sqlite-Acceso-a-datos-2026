package com.carballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppS21 {

	public static void main(String[] args) throws SQLException {
//		alumno alumno = new alumno(
//				"11111111A", "Ana", "García", "López", "DAM",
//				2, 7, 8, 9, 8
//				);
//		System.out.println(alumno.getNombre());
//		System.out.println(alumno.getNotaFinal());
		String url = "jdbc:sqlite:java26.db";
		try (Connection conexion = DriverManager.getConnection(url)) {
			String sql = "SELECT * FROM alumno";
			PreparedStatement ps =
			conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			System.out.println("ALUMNOS CREADOS DESDE LA BASE DE DATOS");
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
				
				System.out.println(
						alumnoBD.getDni() + 
						alumnoBD.getNombre() + 
						alumnoBD.getNotaFinal());
			}
			
		}catch (SQLException e) {
			System.out.println("error:!" + e.getMessage());
		}

	}

}
