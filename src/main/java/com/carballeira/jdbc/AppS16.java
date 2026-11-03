package com.carballeira.jdbc;

import java.awt.Menu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AppS16 {

	public static void main(String[] args) throws SQLException {
		String url = "jdbc:sqlite:java26.db";
		Connection conexion = DriverManager.getConnection(url);
		database_check(conexion);
		Scanner sc = new Scanner(System.in);
		boolean exit = false;
		System.out.println("Conexión realizada correctamente");

		exit = menu(sc, url, exit, conexion);
		while (exit == false) {
			exit = menu(sc, url, exit, conexion);
			System.out.println(exit);
		}
	}

	private static void database_check(Connection conexion) {
		try {
			String sqlTabla = """
					CREATE TABLE IF NOT EXISTS alumno (
					dni TEXT PRIMARY KEY,
					nombre TEXT NOT NULL,
					apellido1 TEXT NOT NULL,
					apellido2 TEXT NOT NULL,
					ciclo TEXT NOT NULL,
					curso INTEGER NOT NULL,
					eva1 INTEGER, eva2 INTEGER, eva3 INTEGER,
					notaFinal INTEGER
					)
					""";
			Statement sentencia = conexion.createStatement();
			sentencia.execute(sqlTabla);
		} catch (SQLException e) {
			System.out.println("error!: " + e);
		}
	}

	private static boolean menu(Scanner sc, String url, boolean exit, Connection conexion) {
		try {
			System.out.println("GESTIÓN DE ALUMNOS\r\n" + "1. Insertar alumno\r\n" + "2. Listar alumnos\r\n"
					+ "3. Modificar alumno\r\n" + "4. Eliminar alumno\r\n" + "5. Buscar alumno\r\n" + "0. Salir");
			System.out.print("Elige una opcion: ");
			int opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				insert(sc, url, conexion);
				break;

			case 2:
				list(sc,url,conexion);
				break;

			case 3:
				modify(sc, url, conexion);
				break;

			case 4:
				delete(sc, url, conexion);
				break;
				
			case 5:
				search(sc, url, conexion);
				break;

			case 0:
				System.out.println("Saliendo...");
				exit = true;
				
				break;
			}

		} catch (SQLException e) {
			System.out.println("error!: " + e);
		}
		return exit;
	}

	private static void search(Scanner sc, String url, Connection conexion) throws SQLException {
		System.out.print("Introduce el dni del alumno: ");
		String dniBuscar = sc.nextLine();
		String buscar="""
				SELECT * FROM alumno WHERE dni = ?
				""";
		PreparedStatement psBuscar = conexion.prepareStatement(buscar);
		psBuscar.setString(1, dniBuscar);
		ResultSet resultadoBuscar = psBuscar.executeQuery();
		if (resultadoBuscar.next()) {
			System.out.println("\n Se encontró el siguiente resultado para " + resultadoBuscar.getString("dni"));
			
			System.out.println(
					"Nombre: " + resultadoBuscar.getString("nombre") +
					"\nPrimer apellido: " + resultadoBuscar.getString("apellido1") +
					"\nSegundo apellido: " + resultadoBuscar.getString("apellido2") +
					"\nCiclo: " + resultadoBuscar.getString("ciclo") +
					"\nCurso: " + resultadoBuscar.getInt("curso")
			);
			
		}
		else {
			System.out.println("error!: DNI no existe");
		}
	}

	private static void modify(Scanner sc, String url, Connection conexion) throws SQLException {
		System.out.print("\nIntroduce el DNI del alumno que quieres modificar: ");
		String dniModificar = sc.nextLine();
		System.out.println("""
				¿Qué campo quieres modificar?
				1. Nombre
				2. Primer apellido
				3. Segundo apellido
				4. Ciclo
				5. Curso
				6. DNI
				7. Notas
				""");
		int opcion = Integer.parseInt(sc.nextLine());
		switch (opcion) {
		case 1 -> {
			System.out.print("Introduce el nuevo nombre: ");
			String nuevoNombre = sc.nextLine();
			String sql = "UPDATE alumno SET nombre = ? WHERE dni = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, nuevoNombre);
			ps.setString(2, dniModificar);
			int filas = ps.executeUpdate();
			System.out.println(
					filas > 0 ? "Nombre actualizado correctamente" : "No existe ningún alumno con ese DNI");
		}
		case 2 -> {
			System.out.print("Introduce el nuevo primer apellido: ");
			String valor = sc.nextLine();
			String sql = "UPDATE alumno SET apellido1 = ? WHERE dni = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, valor);
			ps.setString(2, dniModificar);
			int filas = ps.executeUpdate();
			System.out.println(filas > 0 ? "Primer apellido actualizado correctamente"
					: "No existe ningún alumno con ese DNI");
		}
		case 3 -> {
			System.out.print("Introduce el nuevo segundo apellido: ");
			String valor = sc.nextLine();
			String sql = "UPDATE alumno SET apellido2 = ? WHERE dni = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, valor);
			ps.setString(2, dniModificar);
			int filas = ps.executeUpdate();
			System.out.println(filas > 0 ? "Segundo apellido actualizado correctamente"
					: "No existe ningún alumno con ese DNI");
		}
		case 4 -> {
			System.out.print("Introduce el nuevo ciclo: ");
			String valor = sc.nextLine();
			String sql = "UPDATE alumno SET ciclo = ? WHERE dni = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, valor);
			ps.setString(2, dniModificar);
			int filas = ps.executeUpdate();
			System.out
					.println(filas > 0 ? "Ciclo actualizado correctamente" : "No existe ningún alumno con ese DNI");
		}
		case 5 -> {
			System.out.print("Introduce el nuevo curso: ");
			int valor = Integer.parseInt(sc.nextLine());
			String sql = "UPDATE alumno SET curso = ? WHERE dni = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, valor);
			ps.setString(2, dniModificar);
			int filas = ps.executeUpdate();
			System.out
					.println(filas > 0 ? "Curso actualizado correctamente" : "No existe ningún alumno con ese DNI");
		}
		case 6 -> {
			System.out.print("Introduce el nuevo DNI: ");
			String nuevoDni = sc.nextLine();
			String sql = "UPDATE alumno SET dni = ? WHERE dni = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, nuevoDni);
			ps.setString(2, dniModificar);
			int filas = ps.executeUpdate();
			System.out.println(filas > 0 ? "DNI actualizado correctamente" : "No existe ningún alumno con ese DNI");
		}
		case 7 -> {
			System.out.print("Nota de la 1ª evaluación: ");
			int n1 = Integer.parseInt(sc.nextLine());
			System.out.print("Nota de la 2ª evaluación: ");
			int n2 = Integer.parseInt(sc.nextLine());
			System.out.print("Nota de la 3ª evaluación: ");
			int n3 = Integer.parseInt(sc.nextLine());
			int nf = (n1 + n2 + n3) / 3;
			String sql = "UPDATE alumno SET eva1=?, eva2=?, eva3=?, notaFinal=? WHERE dni=?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, n1);
			ps.setInt(2, n2);
			ps.setInt(3, n3);
			ps.setInt(4, nf);
			ps.setString(5, dniModificar);
			int filas = ps.executeUpdate();
			System.out.println(
					filas > 0 ? "Notas actualizadas correctamente" : "No existe ningún alumno con ese DNI");
		}
		default -> System.out.println("Opción no válida");
		}
		
	}

	private static void delete(Scanner sc, String url, Connection conexion) throws SQLException {
		System.out.print("\nIntroduce el dni del alumno que quieres eliminar: ");
		sc.nextLine();
		String dniEliminar = sc.nextLine();
		
		String eliminar = """
				DELETE FROM alumno WHERE dni = ?
				""";
		
		PreparedStatement psDelete = conexion.prepareStatement(eliminar);
		psDelete.setString(1, dniEliminar);
		int filasEliminadas = psDelete.executeUpdate();
		
		if (filasEliminadas>0) {
			System.out.println("Alumno eliminado con éxito!");
		}
		else {
			System.out.println("error!: DNI no existe.");
		}
		
	}

	private static void list(Scanner sc, String url, Connection conexion) throws SQLException {
		System.out.println("\nLISTA DE ALUMNOS");
		Statement sentencia = conexion.createStatement();
		ResultSet resultado = sentencia.executeQuery("SELECT * FROM alumno ORDER BY apellido1, apellido2, nombre");
		
		while(resultado.next()) {
			String eva1 = resultado.getString("eva1");
			String eva2 = resultado.getString("eva2");
			String eva3 = resultado.getString("eva3");
			String notaFinalBD = resultado.getString("notaFinal");
			
			System.out.println(
					resultado.getString("dni") + " - " +
					resultado.getString("apellido1") + " - " +
					resultado.getString("apellido2") + " - " +
					resultado.getString("nombre") + " - " +
					resultado.getString("ciclo") + " - Curso " +
					resultado.getInt("curso") + " - Notas: " +
					(eva1 == null ? "Sin nota" : eva1) + ", " +
					(eva2 == null ? "Sin nota" : eva2) + ", " +
					(eva3 == null ? "Sin nota" : eva3) +
					" - Final: " + 
					(notaFinalBD == null ? "Sin calcular" : notaFinalBD)
			);
			
			System.out.println();
			
		}
	}

	private static void insert(Scanner sc, String url, Connection conexion) throws SQLException {
		String insertar = """
				INSERT INTO alumno
				(dni, nombre, apellido1, apellido2, ciclo, curso)
				VALUES (?, ?, ?, ?, ?, ?)
				""";
		PreparedStatement ps = conexion.prepareStatement(insertar);
		System.out.print("¿Cuántos alumnos quieres introducir? ");
		int numAlumnos = Integer.parseInt(sc.nextLine());
		for (int i = 1; i <= numAlumnos; i++) {
			System.out.println("\nALUMNO " + i);
			System.out.print("Introduce el DNI: ");
			String dni = sc.nextLine();
			System.out.print("Introduce el nombre: ");
			String nombre = sc.nextLine();
			System.out.print("Introduce el primer apellido: ");
			String apellido1 = sc.nextLine();
			System.out.print("Introduce el segundo apellido: ");
			String apellido2 = sc.nextLine();
			System.out.print("Introduce el ciclo: ");
			String ciclo = sc.nextLine();
			System.out.print("Introduce el curso: ");
			int curso = Integer.parseInt(sc.nextLine());
			ps.setString(1, dni);
			ps.setString(2, nombre);
			ps.setString(3, apellido1);
			ps.setString(4, apellido2);
			ps.setString(5, ciclo);
			ps.setInt(6, curso);
			ps.executeUpdate();
			System.out.println("Alumno insertado correctamente");
		}

	}

}
