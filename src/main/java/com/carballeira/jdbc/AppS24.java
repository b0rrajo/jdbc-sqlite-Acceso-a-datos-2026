package com.carballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppS24 {
	public static void main(String[] Args) {
		ArrayList<alumno> ListaAlumnos = new ArrayList<alumno>();
		System.out.println("Número de alumnos: " + ListaAlumnos.size());
		String url = "jdbc:sqlite:java26.db";

		try (Connection conexion = DriverManager.getConnection(url)) {
			String sql = "SELECT * FROM alumno";
			PreparedStatement ps = conexion.prepareStatement(sql);
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
			
			Scanner sc = new Scanner (System.in);
			
			System.out.println("\nNÚMERO TOTAL DE ALUMNOS");
			String sqlTotal =
			 "SELECT COUNT(*) AS total FROM alumno";
			PreparedStatement psTotal =
			 conexion.prepareStatement(sqlTotal);
			ResultSet rsTotal = psTotal.executeQuery();
			if (rsTotal.next()) {
			 int total = rsTotal.getInt("total");
			 System.out.println("Total de alumnos: " + total);
			}
			
			System.out.println("\nNOTA MEDIA DE LOS ALUMNOS");
			String sqlMedia =
			 "SELECT AVG(notaFinal) AS media FROM alumno";
			PreparedStatement psMedia =
			 conexion.prepareStatement(sqlMedia);
			ResultSet rsMedia = psMedia.executeQuery();
			if (rsMedia.next()) {
			 double media = rsMedia.getDouble("media");
			 System.out.println("Nota media: " + media);
			}
			
			System.out.println("\nNOTA MÁXIMA");
			String sqlMax =
			 "SELECT MAX(notaFinal) AS maxima FROM alumno";
			PreparedStatement psMax =
			 conexion.prepareStatement(sqlMax);
			ResultSet rsMax = psMax.executeQuery();
			if (rsMax.next()) {
			 int maxima = rsMax.getInt("maxima");
			 System.out.println("Nota máxima: " + maxima);
			}
			
			System.out.println("\nNOTA MÍNIMA");
			String sqlMin =
			 "SELECT MIN(notaFinal) AS minima FROM alumno";
			PreparedStatement psMin =
			 conexion.prepareStatement(sqlMin);
			ResultSet rsMin = psMin.executeQuery();
			if (rsMin.next()) {
			 int minima = rsMin.getInt("minima");
			 System.out.println("Nota mínima: " + minima);
			}

			String sqlAprobados =
					 "SELECT COUNT(*) AS aprobados FROM alumno " +
					 "WHERE notaFinal >= 5";
					PreparedStatement psAprobados =
					 conexion.prepareStatement(sqlAprobados);
					ResultSet rsAprobados = psAprobados.executeQuery();
					if (rsAprobados.next()) {
					 int aprobados = rsAprobados.getInt("aprobados");
					 System.out.println("Aprobados: " + aprobados);
					}
			
					String sqlSuspensos =
							 "SELECT COUNT(*) AS suspensos FROM alumno " +
							 "WHERE notaFinal < 5";
							PreparedStatement psSuspensos =
							 conexion.prepareStatement(sqlSuspensos);
							ResultSet rsSuspensos = psSuspensos.executeQuery();
							if (rsSuspensos.next()) {
							 int suspensos = rsSuspensos.getInt("suspensos");
							 System.out.println("Suspensos: " + suspensos);
							}


		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}
}
