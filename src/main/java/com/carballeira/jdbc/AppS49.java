package com.carballeira.jdbc;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AppS49 {
	public static void main(String[] args) {
		Gson gson = new Gson();
		try (FileReader lector = new FileReader("alumnos.json")) {
			ArrayList<alumno> alumnos = gson.fromJson(lector, new TypeToken<ArrayList<alumno>>() {
			}.getType());
			System.out.println("Fichero JSON abierto correctamente.");
			System.out.println("Número de alumnos recuperados: " + alumnos.size());
			System.out.println("\nLISTADO DE ALUMNOS:");
			for (alumno a : alumnos) {
				System.out.println(
						"DNI: " + a.getDni() + " | Nombre: " + a.getNombre() + " | Nota final: " + a.getNotaFinal());
			}
			System.out.println("\nALUMNOS APROBADOS:");
			for (alumno a : alumnos) {
				if (a.getNotaFinal() >= 5) {
					System.out.println(a.getNombre() + " - Nota final: " + a.getNotaFinal());
				}
			}
		} catch (IOException e) {
			System.out.println("Error al leer el fichero: " + e.getMessage());
		}
	}
}