package com.carballeira.jdbc;

import java.sql.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class AppS52 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== GESTIÓN DE FICHEROS =====");
            System.out.println("1. Exportar alumnos a CSV");
            System.out.println("2. Exportar alumnos a JSON");
            System.out.println("3. Importar alumnos desde JSON");
            System.out.println("4. Crear copia de seguridad binaria");
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
                case 1 -> exportarCSV();
                case 2 -> exportarJSON();
                case 3 -> importarJSON();
                case 4 -> copiaBinaria();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción incorrecta");
            }

        } while (opcion != 0);

        sc.close();
    }

    public static void exportarCSV() {

    String sql = "SELECT id, nombre, apellidos, nota FROM alumnos";

    try (Connection conexion = DriverManager.getConnection(
            "jdbc:mariadb://10.10.20.19:3306/academia",
            "root", "abc123.");
         PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery();
         BufferedWriter bw = new BufferedWriter(
                 new FileWriter("alumnos.csv", true))) {

        bw.write("id;nombre;apellidos;nota");
        bw.newLine();

        while (rs.next()) {
            bw.write(
                rs.getInt("id") + ";" +
                rs.getString("nombre") + ";" +
                rs.getString("apellidos") + ";" +
                rs.getDouble("nota")
            );
            bw.newLine();
        }

        System.out.println("Alumnos exportados correctamente a CSV.");

    } catch (SQLException | IOException e) {
        System.out.println("Error al exportar: " + e.getMessage());
    }
  }

    public static void exportarJSON() {

    String sql = "SELECT id, nombre, apellidos, nota FROM alumnos";

    ArrayList<Map<String, Object>> alumnos = new ArrayList<>();

    	try (Connection conexion = DriverManager.getConnection(
            "jdbc:mariadb://10.10.20.19:3306/academia",
            "root", "abc123.");
         PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

    		while (rs.next()) {

            Map<String, Object> alumno = new LinkedHashMap<>();

            alumno.put("id", rs.getInt("id"));
            alumno.put("nombre", rs.getString("nombre"));
            alumno.put("apellidos", rs.getString("apellidos"));
            alumno.put("nota", rs.getDouble("nota"));

            alumnos.add(alumno);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        	try (FileWriter fw = new FileWriter("alumnos.json")) {
            gson.toJson(alumnos, fw);
        }

        System.out.println("Alumnos exportados correctamente a JSON.");

    	} catch (SQLException | IOException e) {
        System.out.println("Error al exportar JSON: "
                + e.getMessage());
    	}
    }

    public static void importarJSON() {

    Gson gson = new Gson();

    Type tipoLista = new TypeToken<
            ArrayList<Map<String, Object>>>() {}.getType();

            try (FileReader fr = new FileReader("alumnos.json")) {

        ArrayList<Map<String, Object>> alumnos =
                gson.fromJson(fr, tipoLista);

        	if (alumnos == null) {
            System.out.println("El archivo JSON está vacío.");
            return;
        	}

        	for (Map<String, Object> alumno : alumnos) {

            System.out.println(
                alumno.get("id") + " - " +
                alumno.get("nombre") + " " +
                alumno.get("apellidos") +
                " - Nota: " + alumno.get("nota")
            		);
        	}

        	System.out.println("Total de alumnos: " + alumnos.size());

            } catch (IOException e) {
        System.out.println("Error al leer JSON: "
                + e.getMessage());
            }
    }

    public static void copiaBinaria() {

    String sql = "SELECT id, nombre, apellidos, nota FROM alumnos";

    ArrayList<LinkedHashMap<String, Object>> alumnos =
            new ArrayList<>();

    	try (Connection conexion = DriverManager.getConnection(
            "jdbc:mariadb://10.10.20.19:3306/academia",
            "root", "abc123.");
         PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

    		while (rs.next()) {

            LinkedHashMap<String, Object> alumno =
                    new LinkedHashMap<>();

            alumno.put("id", rs.getInt("id"));
            alumno.put("nombre", rs.getString("nombre"));
            alumno.put("apellidos", rs.getString("apellidos"));
            alumno.put("nota", rs.getDouble("nota"));

            alumnos.add(alumno);
    		}

    		try (ObjectOutputStream salida =
                new ObjectOutputStream(
                        new FileOutputStream("alumnos.dat"))) {

            salida.writeObject(alumnos);
	        }

    		System.out.println( "Copia binaria creada correctamente.");
    		System.out.println( "Alumnos guardados: " + alumnos.size());

    	} catch (SQLException | IOException e) {
        System.out.println(
            "Error al crear la copia: " + e.getMessage());
    	}

    try (ObjectInputStream entrada =
        new ObjectInputStream(
                new FileInputStream("alumnos.dat"))) {

    ArrayList<?> recuperados =
            (ArrayList<?>) entrada.readObject();

    System.out.println(
        "Alumnos recuperados: " + recuperados.size());

    } catch (IOException | ClassNotFoundException e) {
    System.out.println(
        "Error al recuperar los objetos: " + e.getMessage());
  }
    }
}