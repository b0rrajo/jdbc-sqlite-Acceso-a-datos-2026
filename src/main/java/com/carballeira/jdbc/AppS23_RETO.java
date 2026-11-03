package com.carballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppS23_RETO {
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
			
			System.out.println("\nBÚSQUEDA COMBINADA");
			System.out.print("Introduzca datos: ");
			String texto = sc.nextLine();
			String sqlBusqueda =
			 "SELECT * FROM alumno " +
			 "WHERE apellido1 LIKE ? OR nombre LIKE ? " +
			 "ORDER BY apellido1, nombre";

			PreparedStatement psBusqueda =
					 conexion.prepareStatement(sqlBusqueda);
					psBusqueda.setString(1, "%" + texto + "%");
					
			ResultSet rsBusqueda = psBusqueda.executeQuery();
			if (rsBusqueda.next()) {
				System.out.println(
				rsBusqueda.getString("dni") + " - " +
				rsBusqueda.getString("nombre") + " " +
				rsBusqueda.getString("apellido1") + " " +
				rsBusqueda.getString("apellido2"));
			}else {
				System.out.println("error!: No existen registros");
			}
			
			
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
