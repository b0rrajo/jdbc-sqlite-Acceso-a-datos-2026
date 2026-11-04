package com.carballeira.jdbc;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class AppS35 {

	public static void main(String[] args) {

		try (FileInputStream fis = new FileInputStream("alumnos.dat");
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			
			alumno2 alumno = (alumno2) ois.readObject();
			System.out.println("Alumno recuperado correctamente");
			System.out.println("Nombre: " + alumno.getNombre());
			
			System.out.println("Fichero abierto para leer objetos");
			
		}
		catch (Exception e) {
			System.out.println("error!: " + e.getMessage());
		}

	}

}
