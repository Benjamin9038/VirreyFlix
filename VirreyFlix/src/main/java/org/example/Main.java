package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();

        int opcion = 0;
        System.out.println("\n--- Menú Principal ---");
        do{
            System.out.println("1. Mostrar libros");
            System.out.println("2. Actualizar libro");
            System.out.println("3. Insertar libro");
            System.out.println("4. Actualizar libro");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = introduceEntero();
        }while (opcion != 0);
    }
    private static int introduceEntero() {
        int numero;
        Scanner scanner = new Scanner(System.in);
        try {
            numero = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Debes introducir un número natural válido.");
            return -1;
        }
        return numero;
    }
}