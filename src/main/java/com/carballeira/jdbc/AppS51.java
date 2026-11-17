package com.carballeira.jdbc;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppS51 {
	
	public static Connection conectar() throws SQLException {
	    return DriverManager.getConnection(
	        "jdbc:mariadb://10.10.32.14:3306/academia",
	        "root", "abc123.");
	}

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== GESTIÓN DE ALUMNOS =====");
            System.out.println("1. Mostrar alumnos");
            System.out.println("2. Buscar alumno por ID");
            System.out.println("3. Insertar alumno");
            System.out.println("4. Modificar alumno");
            System.out.println("5. Eliminar alumno");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Introduce un número válido.");
                sc.nextLine();
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> mostrarAlumnos();
                case 2 -> buscarAlumno(sc);
                case 3 -> insertarAlumno(sc);
                case 4 -> modificarAlumno(sc);
                case 5 -> eliminarAlumno(sc);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción incorrecta");
            }

        } while (opcion != 0);

        sc.close();
    }
    public static void mostrarAlumnos() {

        String sql = "SELECT * FROM alumnos";

        try (Connection conexion = conectar();
        	     
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(
                		 rs.getInt("id") + " - " +
                		 rs.getString("nombre") + " " +
                		 rs.getString("apellidos") + " - Nota: " +
                		 rs.getDouble("nota")
                  );
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar alumnos: "
                    + e.getMessage());
        }
    }
  

  public static void buscarAlumno(Scanner sc) {

	  System.out.print("Introduce el ID del alumno: ");
	  int id = sc.nextInt();
	  sc.nextLine();

	  String sql = "SELECT * FROM alumnos WHERE id = ?";

	  try (Connection conexion = DriverManager.getConnection(
            "jdbc:mariadb://10.10.20.19:3306/academia",
            "root", "abc123.");
         PreparedStatement ps = conexion.prepareStatement(sql)) {

		  ps.setInt(1, id);

		  try (ResultSet rs = ps.executeQuery()) {

			  if (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " - " +
                    rs.getString("nombre") + " " +
                    rs.getString("apellidos") +
                    " - Nota: " + rs.getDouble("nota")
                );
			  } else {
                System.out.println("Alumno no encontrado.");
			  }
		  }

	  } catch (SQLException e) {
		  System.out.println("Error al buscar alumno: "
                + e.getMessage());
	  }
  }

  	public static void insertarAlumno(Scanner sc) {

  		System.out.print("Introduce el nombre: ");
  		String nombre = sc.nextLine();

  		System.out.print("Introduce los apellidos: ");
  		String apellidos = sc.nextLine();

  		System.out.print("Introduce la nota: ");
  		double nota = sc.nextDouble();
  		sc.nextLine();

  		String sql = "INSERT INTO alumnos (nombre, apellidos, nota) VALUES (?, ?, ?)";
    
  		try (Connection conexion = DriverManager.getConnection(
            "jdbc:mariadb://10.10.20.19:3306/academia",
            "root", "abc123.");
  				PreparedStatement ps = conexion.prepareStatement(sql)) {

  			ps.setString(1, nombre);
  			ps.setString(2, apellidos);
  			ps.setDouble(3, nota);

  			int filas = ps.executeUpdate();

  			System.out.println("Alumnos insertados: " + filas);

  		} catch (SQLException e) {
        System.out.println("Error al insertar alumno: "
                + e.getMessage());
  		}
  	}

  	public static void modificarAlumno(Scanner sc) {

  		System.out.print("Introduce el ID del alumno a modificar: ");
  		int id = sc.nextInt();
  		sc.nextLine();

  		System.out.print("Introduce el nuevo nombre: ");
    	String nombre = sc.nextLine();
	
	    System.out.print("Introduce los nuevos apellidos: ");
	    String apellidos = sc.nextLine();

	    System.out.print("Introduce la nueva nota: ");
	    double nota = sc.nextDouble();
	    sc.nextLine();

	    String sql = "UPDATE alumnos SET nombre = ?, apellidos = ?, nota = ? WHERE id = ?";

	    try (Connection conexion = DriverManager.getConnection(
            "jdbc:mariadb://10.10.20.19:3306/academia",
            "root", "abc123.");
	    		PreparedStatement ps = conexion.prepareStatement(sql)) {

	    	ps.setString(1, nombre);
	    	ps.setString(2, apellidos);
	    	ps.setDouble(3, nota);
	    	ps.setInt(4, id);

	    	int filas = ps.executeUpdate();

	    	if (filas > 0) {
	    		System.out.println("Alumno modificado correctamente.");
	    	} else {
	    		System.out.println("No existe ningún alumno con ese ID.");
	    	}

	    } catch (SQLException e) {
	    	System.out.println("Error al modificar alumno: "
                + e.getMessage());
	    }
   }

  	public static void eliminarAlumno(Scanner sc) {

  		System.out.print("Introduce el ID del alumno a eliminar: ");
  		int id = sc.nextInt();
  		sc.nextLine();
	
	    System.out.print("¿Seguro que quieres eliminarlo? (s/n): ");
	    String respuesta = sc.nextLine();

	    if (!respuesta.equalsIgnoreCase("s")) {
	    	System.out.println("Eliminación cancelada.");
	    	return;
	    }

	    String sql = "DELETE FROM alumnos WHERE id = ?";

	    try (Connection conexion = DriverManager.getConnection(
            "jdbc:mariadb://10.10.20.19:3306/academia",
            "root", "abc123.");
	    		PreparedStatement ps = conexion.prepareStatement(sql)) {

	    	ps.setInt(1, id);

	    	int filas = ps.executeUpdate();

	    	if (filas > 0) {
            System.out.println("Alumno eliminado correctamente.");
	    	} else {
            System.out.println("No existe ningún alumno con ese ID.");
	    	}

	    } catch (SQLException e) {
	    	System.out.println("Error al eliminar alumno: "
                + e.getMessage());
	    }
  	
  	}
  			
 
}
