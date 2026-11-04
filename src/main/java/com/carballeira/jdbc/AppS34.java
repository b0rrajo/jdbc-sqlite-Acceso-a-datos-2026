package com.carballeira.jdbc;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class AppS34 {
	public static void main(String[] Args) {
		
		alumno2 alumno = new alumno2("12345678A", "Ana", "García", "López", "DAM", 1, 7, 8, 9, 8);
		
		try (FileOutputStream fos = new FileOutputStream("alumnos.dat");
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			
			System.out.println("Fichero preparado para guardar objetos");
			
			oos.writeObject(alumno);
			System.out.println("Alumno guardado correctamente");
			
		}
		catch (Exception e) {
			System.out.println("error!: " + e.getMessage());
		}
	}
}
