package com.carballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppS23 {
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
			
			System.out.println("\nBÚSQUEDA POR APELLIDO");
			System.out.print("Introduce parte del primer apellido: ");
			String texto = sc.nextLine();
			String sqlBusqueda =
			 "SELECT * FROM alumno " +
			 "WHERE apellido1 LIKE ? " +
			 "ORDER BY apellido1, nombre";
			
			PreparedStatement psBusqueda =
					 conexion.prepareStatement(sqlBusqueda);
					psBusqueda.setString(1, "%" + texto + "%");
					
			ResultSet rsBusqueda = psBusqueda.executeQuery();
			while (rsBusqueda.next()) {
				System.out.println(
				rsBusqueda.getString("dni") + " - " +
				rsBusqueda.getString("nombre") + " " +
				rsBusqueda.getString("apellido1") + " " +
				rsBusqueda.getString("apellido2"));
			}
			
			
			
			System.out.println("\nBÚSQUEDA POR CICLO");
			System.out.print("Introduce el ciclo: ");
			String ciclo = sc.nextLine();
			String sqlCiclo =
			 "SELECT * FROM alumno " +
			 "WHERE ciclo = ? " +
			 "ORDER BY apellido1, nombre";
			PreparedStatement psCiclo =
			 conexion.prepareStatement(sqlCiclo);
			psCiclo.setString(1, ciclo);
			ResultSet rsCiclo = psCiclo.executeQuery();
			
			
			System.out.println("\nBÚSQUEDA POR CURSO");
			System.out.print("Introduce el curso: ");
			int curso = sc.nextInt();
			String sqlCurso =
			 "SELECT * FROM alumno " +
			 "WHERE curso = ? " +
			 "ORDER BY apellido1, nombre";
			PreparedStatement psCurso =
			 conexion.prepareStatement(sqlCurso);
			psCurso.setInt(1, curso);
			
			
			String sqlOrdenados =
					 "SELECT * FROM alumno " +
					 "ORDER BY apellido1, apellido2, nombre";
					PreparedStatement psOrdenados =
					 conexion.prepareStatement(sqlOrdenados);
					ResultSet rsOrdenados = psOrdenados.executeQuery();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}
}
