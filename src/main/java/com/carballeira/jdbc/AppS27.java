package com.carballeira.jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppS27 {
	public static void main(String [] Args) {
		File fichero = new File("datos.txt");
		System.out.println("Nombre: " + fichero.getName());
		System.out.println("¿Existe? " + fichero.exists());
		try {
			 boolean creado = fichero.createNewFile();
			 System.out.println("¿Se ha creado? " + creado);
			} catch (Exception e) {
			 System.out.println("Error al crear el archivo");
			}
		System.out.println("Ruta absoluta: " + fichero.getAbsolutePath());
		System.out.println("¿Es un archivo? " + fichero.isFile());
		System.out.println("¿Es una carpeta? " + fichero.isDirectory());
		System.out.println("Tamaño: " + fichero.length() + " bytes");
		
		try {
			FileWriter escritor = new FileWriter(fichero);
			escritor.write("Hola, estamos trabajando con ficheros en Java.");
			escritor.close();
		}catch(IOException e) {
			System.out.println("error!: " + e.getMessage());
		}
		
		try {
			FileReader lector = new FileReader(fichero);
			int caracter;
			int numero = 1;
			System.out.print(numero + ": ");
			while ((caracter = lector.read()) != -1) {
			 System.out.print((char) caracter);
			 if (caracter == '\n') {
			 numero++;
			 System.out.print(numero + ": ");
			 }
			}
			lector.close();
		}catch(IOException e) {
			System.out.println("error!: " + e.getMessage());
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichero));
			String linea;
			int numero = 1;
			while ((linea = br.readLine()) != null) {
			 System.out.println(numero + ": " + linea);
			 numero++;
			}
		}catch(IOException e) {
			System.out.println("error!: " + e.getMessage());
		}
	}
}
