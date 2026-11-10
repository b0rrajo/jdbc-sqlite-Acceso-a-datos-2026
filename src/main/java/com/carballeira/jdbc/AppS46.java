package com.carballeira.jdbc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;

public class AppS46 {
	static String archivo = "alumnos.csv";
	public static void main(String[] args) {
		String user="root", pass="abc123.",url="jdbc:mariadb://10.10.32.14:3306/academia";
		try(BufferedReader br = new BufferedReader(new FileReader(archivo))){
			String linea;
			br.readLine();
			while((linea=br.readLine())!=null) {
				String[] datos = linea.split(";");
				String nombre= datos[0];
				String apellidos= datos[1];
				double nota = Double.parseDouble(datos[2]);
				
				try(Connection con = (Connection) DriverManager.getConnection(url, user, pass)){
					String sql ="INSERT INTO alumnos (nombre, apellidos, nota) VALUES (?, ?, ?)";
					PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, nombre);
					ps.setString(2, apellidos);
					ps.setDouble(3, nota);
					ps.executeUpdate();
					System.out.println("Alumno Registrado");
				}catch(Exception e) {
					System.out.println("error!: " + e.getMessage());
				}
				System.out.println("Nombre: " + nombre+"\n\r"
						+"Apellidos: " + apellidos+"\n\r"
						+"Nota: " + nota +"\n\r"
						+"------------------------");
				
			}
		}catch(Exception e) {
			System.out.println("error!: " + e.getMessage());
		}

	}

}
