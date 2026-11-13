package com.carballeira.jdbc;

import java.io.*;
import java.sql.*;

public class AppS47 {
	public static void main(String[] args) {
		String archivo = "alumnos.csv";
		String url = "jdbc:mariadb://SERVIDOR:3306/academia";
		String usuario = "USUARIO";
		String password = "CLAVE";
		String sql = "INSERT INTO alumnos (nombre, apellidos, nota) " + "VALUES (?, ?, ?)";
		String sqlBuscar = "SELECT COUNT(*) FROM alumnos " + "WHERE nombre = ? AND apellidos = ?";
		try (BufferedReader br = new BufferedReader(new FileReader(archivo));
				Connection con = DriverManager.getConnection(url, usuario, password);
				PreparedStatement ps = con.prepareStatement(sql);
				PreparedStatement psBuscar = con.prepareStatement(sqlBuscar)) {
			con.setAutoCommit(false);
			try {
				br.readLine(); // saltar cabecera
				String linea;
				int contador = 0;
				int duplicados = 0;
				int rechazados = 0;
				while ((linea = br.readLine()) != null) {
					String[] datos = linea.split(";", -1);
					if (datos.length != 3) {
						System.out.println("Linea incorrecta: " + linea);
						rechazados++;
						continue;
					}
					if (datos[0].isBlank() || datos[1].isBlank() || datos[2].isBlank()) {
						System.out.println("Faltan datos: " + linea);
						rechazados++;
						continue;
					}
					String nombre = datos[0].trim();
					String apellidos = datos[1].trim();
					double nota;
					try {
						nota = Double.parseDouble(datos[2].trim());
					} catch (NumberFormatException e) {
						System.out.println("Nota incorrecta: " + linea);
						rechazados++;
						continue;
					}
					psBuscar.setString(1, nombre);
					psBuscar.setString(2, apellidos);
					boolean existe;
					try (ResultSet rs = psBuscar.executeQuery()) {
						rs.next();
						existe = rs.getInt(1) > 0;
					}
					if (existe) {
						System.out.println("Alumno duplicado: " + nombre + " " + apellidos);
						duplicados++;
						continue;
					}
					ps.setString(1, nombre);
					ps.setString(2, apellidos);
					ps.setDouble(3, nota);
					contador += ps.executeUpdate();
					System.out.println("Alumno insertado: " + nombre + " " + apellidos);
				}
				con.commit();
				System.out.println("Transaccion confirmada correctamente.");
				System.out.println("Alumnos importados: " + contador);
				System.out.println("Alumnos duplicados: " + duplicados);
				System.out.println("Registros rechazados: " + rechazados);
				System.out.println("Total procesados: " + (contador + duplicados + rechazados));
			} catch (SQLException | IOException e) {
				con.rollback();
				System.out.println("Error durante la importacion: " + e.getMessage());
				System.out.println("Transaccion deshecha.");
			}
		} catch (IOException | SQLException e) {
			System.out.println("Error al abrir o cerrar recursos: " + e.getMessage());
		}
	}
}