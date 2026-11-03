package com.carballeira.jdbc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AppS28 {
	 public static void main(String[]Args) {
		 try {
			 FileInputStream entrada = new FileInputStream("datos.txt");
			 int dato = entrada.read();
			 while ((dato = entrada.read()) != -1) {
				 System.out.print((char) dato);
			 }
			 entrada.close();
		 }catch(IOException e) {
			 System.out.println("error!: " +e.getMessage());
		 }
		 
		 try {
			 FileOutputStream salida = new FileOutputStream("bytes.txt");
			 salida.write(65);
			 salida.write(66);
			 salida.write(67);

		 }catch(IOException e) {
			 System.out.println("error!: " +e.getMessage());
		 }
		 
		 try {
			 FileInputStream entrada = new FileInputStream("datos.txt");
			 FileOutputStream salida = new FileOutputStream("copia_datos.txt");
			 int dato;
			 while ((dato = entrada.read()) != -1) {
				 salida.write(dato);
				}
			 entrada.close();
			 salida.close();
			 System.out.println("Archivo copiado con éxito!");
		 }catch(IOException e) {
			 System.out.println("error!: " + e);

		 }
		 
	 }
}
