package com.carballeira.jdbc;

import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mariadb.jdbc.Connection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class AppS50 {
	static String url="jdbc:mariadb://10.10.32.14:3306/academia", user="root", pass="abc123.";
	public static void main(String[] args){
		try(Connection conexion = (Connection) DriverManager.getConnection(url,user,pass)){
			Gson gson = new Gson();
			try(FileReader lector = new FileReader("alumnos.json")){
				ArrayList<alumno> alumnos = gson.fromJson(lector, new TypeToken<ArrayList<alumno>>() {}.getType());
				System.out.println("Alumnos recuperados de Json: " + alumnos.size());
				
				String sqlBuscar = """
						SELECT COUNT(*) FROM alumnos WHERE nombre = ? AND apellidos = ?
						""";
				String sqlInsertar = """
						INSERT INTO alumnos (nombre, apellidos, nota) VALUES (?, ?, ?)
						""";
				
				for(alumno a : alumnos) {
					System.out.println(a.getNombre() + " - Nota: " + a.getNotaFinal());
				}
				
				try(PreparedStatement psBuscar = conexion.prepareStatement(sqlBuscar); PreparedStatement psInsertar = conexion.prepareStatement(sqlInsertar)){
					for (alumno a : alumnos) {

			            // Unir los dos apellidos
			            String apellidos = a.getApellido1() + " " + a.getApellido2();

			            // Asignar los parámetros de búsqueda
			            psBuscar.setString(1, a.getNombre());
			            psBuscar.setString(2, apellidos);

			            // Ejecutar la consulta
			            try (ResultSet rs = psBuscar.executeQuery()) {

			                if (rs.next()) {

			                    int cantidad = rs.getInt(1);

			                    if (cantidad > 0) {
			                        System.out.println(
			                            a.getNombre() + " ya existe en MariaDB."
			                        );
			                    } else {
			                 
			                        // Asignar los datos del alumno
			                        psInsertar.setString(1, a.getNombre());
			                        psInsertar.setString(2, apellidos);
			                        psInsertar.setInt(3, a.getNotaFinal());

			                        // Ejecutar la inserción
			                        int filas = psInsertar.executeUpdate();

			                        System.out.println(
			                            a.getNombre() + " insertado. Filas añadidas: " + filas
			                        );
			                    }
			                }
			            }
			        }
					conexion.commit();
			        System.out.println("Importación confirmada.");
				}
				catch(SQLException e) {
					System.out.println("error SQL TYPE!: " + e.getMessage());
				}
			}
		}
		catch(Exception e) {
			System.out.println("error!: " + e.getMessage());
		}
	}
}
