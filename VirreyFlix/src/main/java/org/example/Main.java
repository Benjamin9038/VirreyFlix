package org.example;

import DAOS.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();
        Scanner scanner = new Scanner(System.in);
        DAOEpisodio daoEpisodio = new DAOEpisodio();
        DAOGenero daoGenero = new DAOGenero();
        DAOPerfil daoPerfil = new DAOPerfil();
        DAOSerie daoSerie = new DAOSerie();
        DAOUsuario daoUsuario = new DAOUsuario();
        DAOHistorial daoHistorial = new DAOHistorial();
        int opcion;

        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Episodio");
            System.out.println("2. Género");
            System.out.println("3. Historial");
            System.out.println("4. Perfil");
            System.out.println("5. Serie");
            System.out.println("6. Usuario");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = introduceEntero(scanner);

            switch (opcion) {
                case 1 -> manejarSubMenu(scanner, daoEpisodio, "Episodio");
                case 2 -> manejarSubMenu(scanner, daoGenero, "Género");
                case 3 -> manejarSubMenu(scanner, daoHistorial, "Historial");
                case 4 -> manejarSubMenu(scanner, daoPerfil, "Perfil");
                case 5 -> manejarSubMenu(scanner, daoSerie, "Serie");
                case 6 -> manejarSubMenu(scanner, daoUsuario, "Usuario");
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static int introduceEntero(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Debes introducir un número válido.");
            return -1;
        }
    }

    private static void manejarSubMenu(Scanner scanner, Object dao, String entidad) {
        int subOpcion;

        do {
            System.out.println("\n--- Menú " + entidad + " ---");
            System.out.println("1. Mostrar " + entidad);
            System.out.println("2. Actualizar " + entidad);
            System.out.println("3. Insertar " + entidad);
            System.out.println("4. Eliminar " + entidad);
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            subOpcion = introduceEntero(scanner);

            switch (subOpcion) {
                case 1 -> manejarMostrar(scanner, dao, entidad);
                case 2 -> manejarActualizar(scanner, dao, entidad);
                case 3 -> manejarInsertar(scanner, dao, entidad);
                case 4 -> manejarEliminar(scanner, dao, entidad);
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (subOpcion != 0);
    }

    private static void manejarMostrar(Scanner scanner, Object dao, String entidad) {
        System.out.println("En caso de que quieras mostrar todos los elementos pon un 0, sino busca por id");
        int opcionId = introduceEntero(scanner);
        System.out.println("Mostrando " + entidad + "...");
        if (dao instanceof DAOEpisodio) {
            ((DAOEpisodio) dao).listarEpisodios(opcionId);
        } else if (dao instanceof DAOPerfil) {
            ((DAOPerfil) dao).listarPerfiles(opcionId);
        } else if (dao instanceof DAOHistorial) {
            ((DAOHistorial) dao).listarHistorial(opcionId);
        }else if (dao instanceof DAOSerie) {
            ((DAOSerie) dao).listarSeries(opcionId);
        }else if (dao instanceof DAOUsuario) {
        ((DAOUsuario) dao).listarUsuarios(opcionId);
    }
        }


    private static void manejarActualizar(Scanner scanner, Object dao, String entidad) {
        System.out.println("Dime el id del " + entidad.toLowerCase() + " que quieras modificar");
        int opcionId = introduceEntero(scanner);
        System.out.println("Actualizando " + entidad + "...");
        if (dao instanceof DAOEpisodio) {
            ((DAOEpisodio) dao).actualizarEpisodio(opcionId);
        }else if (dao instanceof DAOPerfil) {
            ((DAOPerfil) dao).actualizarPerfil(opcionId);
        }else if (dao instanceof DAOHistorial) {
            ((DAOHistorial) dao).actualizarHistorial(opcionId);
        }else if (dao instanceof DAOSerie) {
            ((DAOSerie) dao).actualizarSerie(opcionId);
        }else if (dao instanceof DAOUsuario) {
            ((DAOUsuario) dao).actualizarUsuario(opcionId);
        }
//        else if (dao instanceof DAOGenero) {
//            ((DAOGenero) dao).actualizarGenero(opcionId);
//        }
    }

    private static void manejarInsertar(Scanner scanner, Object dao, String entidad) {
        System.out.println(" Insertando " + entidad + "...");
        if (dao instanceof DAOEpisodio) {
            ((DAOEpisodio) dao).crearEpisodio();
        }else if (dao instanceof DAOPerfil) {
            ((DAOPerfil) dao).crearPerfil();
        }else if (dao instanceof DAOHistorial) {
            ((DAOHistorial) dao).crearHistorial();
        }else if (dao instanceof DAOSerie) {
            ((DAOSerie) dao).crearSerie();
        }else if (dao instanceof DAOUsuario) {
            ((DAOUsuario) dao).crearUsuario();
        }
//        else if (dao instanceof DAOGenero) {
//            ((DAOGenero) dao).crearGenero();
//        }
    }

    private static void manejarEliminar(Scanner scanner, Object dao, String entidad) {
        System.out.println("Dime el id del " + entidad.toLowerCase() + " que quieres eliminar");
        int opcionId = introduceEntero(scanner);
        System.out.println("Eliminando " + entidad + "...");
        if (dao instanceof DAOEpisodio) {
            ((DAOEpisodio) dao).eliminarEpisodio(opcionId);
        }else if (dao instanceof DAOPerfil) {
            ((DAOPerfil) dao).eliminarPerfil(opcionId);
        }else if (dao instanceof DAOHistorial) {
            ((DAOHistorial) dao).eliminarHistorial(opcionId);
        }else if (dao instanceof DAOSerie) {
            ((DAOSerie) dao).eliminarSerie(opcionId);
        }else if (dao instanceof DAOUsuario) {
            ((DAOUsuario) dao).eliminarUsuario(opcionId);
        }
//        else if (dao instanceof DAOGenero) {
//            ((DAOGenero) dao).eliminarGenero(opcionId);
//        }
    }
}
