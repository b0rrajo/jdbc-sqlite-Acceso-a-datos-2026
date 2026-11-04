package com.carballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AppS20 {

	public static void main(String[] args) throws SQLException {
		String url = "jdbc:sqlite:java26.db";
		Connection conexion = DriverManager.getConnection(url);
		Scanner sc = new Scanner(System.in);
		System.out.println("Conexión realizada correctamente");
		mostrarAprobados(sc, url, conexion);
	}
	
	private static void mostrarAprobados(Scanner sc, String url, Connection conexion) throws SQLException {

		Statement sentencia = conexion.createStatement();
		System.out.println("\nLISTA DE ALUMNOS APROBADOS");
		System.out.println("-------------------------------------------------------------------------------------------------------");
		System.out.printf("%-10s %-10s %-10s %-12s %-12s %-12s %-6s", "DNI","APELLIDO 1", "APELLIDO 2","NOME", "CICLO", "CURSO", "FINAL");
		System.out.println();
		System.out.println("-------------------------------------------------------------------------------------------------------");
		ResultSet resultado2 = sentencia.executeQuery("SELECT * FROM alumno WHERE notaFinal>=5 ORDER BY apellido1, apellido2, nombre");
		while(resultado2.next()) {
			String notaFinalBD = resultado2.getString("notaFinal");
			
			System.out.printf("%-10s %-10s %-10s %-12s %-12s %-12s %-6s",
					resultado2.getString("dni"),
					resultado2.getString("apellido1"),
					resultado2.getString("apellido2"),
					resultado2.getString("nombre"),
					resultado2.getString("ciclo"),
					resultado2.getInt("curso"),
					(notaFinalBD == null ? "Sin calcular" : notaFinalBD)
			);
			System.out.println();
		}
		System.out.println();
	}

}
