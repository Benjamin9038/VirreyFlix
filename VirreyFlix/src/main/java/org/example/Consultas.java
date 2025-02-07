package org.example;

import DAOS.DAOEpisodio;
import DAOS.DAOSerie;
import org.example.model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Consultas {
    public void consulta() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Scanner sc=new Scanner(System.in);
        DAOSerie daoSerie = new DAOSerie();
        DAOEpisodio daoEpisodio = new DAOEpisodio();

        int opcion=-1;
        do {
            System.out.println("Dime la consulta que quieres realiar");
            System.out.println("1.Mostrar todos los usuarios junto con la informacion de sus perfiles");
            System.out.println("2.Mostrar todas las series.");
            System.out.println("3.Mostrar las series que existen en funcion de una edad introducida por usuario");
            System.out.println("4.Mostrar todos los capítulos de una serie a partir de un ID de serie");
            System.out.println("5.Mostrar los capítulos que ha visto un usuario a partir de un ID");
            System.out.println("6.Anadir al historial de un usuario a traves de su ID, todos los de una serie a traves de su ID de golpe ");
            System.out.println("7.Mostrar series por genero introducido por el usuario, con la duracion media de sus capítulos");
            System.out.println("8.Mostrar las 5 series mas vistas del catalogo");
            opcion=sc.nextInt();

            switch (opcion){
                case 1->{
                    List<Usuario> lista_Usuario=session.createQuery("FROM Usuario usuario", Usuario.class).list();
                    for (Usuario usuario : lista_Usuario) {
                        System.out.println(usuario.toString());
                        List<Perfil>lista_perfiles=session.createQuery("FROM Perfil perfil WHERE perfil.usuario=:usuario", Perfil.class).setParameter("usuario", usuario).list();
                        for (Perfil perfil : lista_perfiles) {
                            System.out.println(perfil.toString());
                        }
                    }
                }
                case 2->{
                    opcion=0;
                    daoSerie.listarSeries(opcion);
                }
                case 3->{
                    System.out.println("Introduce una edad para ver las series que puedes ver con ese limite de edad");
                    int edad=sc.nextInt();
                    List<Serie> series = session.createQuery("FROM Serie serie WHERE serie.calificacion_edad <= :edad",Serie.class).setParameter("edad",edad).list();
                    System.out.println("Series: ");
                    if (!series.isEmpty()) {
                        for (Serie serie : series) {
                            System.out.println(serie);
                        }
                    }else System.out.println("No hay series de esa edad");
                }
                case 4->{

                        System.out.println("Introduce el ID de la serie para ver sus capítulos:");
                        int idSerie = sc.nextInt();

                        List<Episodio> episodios = session.createQuery(
                                        "FROM Episodio e WHERE e.serie.id = :idSerie", Episodio.class)
                                .setParameter("idSerie", idSerie)
                                .list();

                        if (episodios.isEmpty()) {
                            System.out.println("No hay episodios registrados para esta serie o el ID es incorrecto.");
                        } else {
                            System.out.println("Capítulos de la serie con ID " + idSerie + ":");
                            episodios.forEach(System.out::println);

                    }
                }
                    case 5 -> {
                        System.out.println("Introduce el ID del usuario para ver los capítulos que ha visto:");
                        long idUsuario = sc.nextLong();


                        List<Perfil> perfiles = session.createQuery(
                                        "FROM Perfil p WHERE p.usuario.id = :idUsuario", Perfil.class)
                                .setParameter("idUsuario", idUsuario)
                                .list();

                        if (perfiles.isEmpty()) {
                            System.out.println("Este usuario no tiene perfiles.");
                        } else {
                            System.out.println("Capítulos vistos por el usuario con ID " + idUsuario + ":");

                            for (Perfil perfil : perfiles) {

                                List<Historial> historiales = session.createQuery(
                                                "SELECT h FROM Historial h " +
                                                        "JOIN FETCH h.episodio e " +
                                                        "JOIN FETCH e.serie s " +
                                                        "WHERE h.perfil.id = :idPerfil", Historial.class)
                                        .setParameter("idPerfil", perfil.getId())
                                        .list();

                                if (historiales.isEmpty()) {
                                    System.out.println("No hay capítulos vistos por el perfil con ID " + perfil.getId());
                                } else {
                                    for (Historial h : historiales) {
                                        Episodio episodio = h.getEpisodio();
                                        Serie serie = episodio.getSerie();
                                        System.out.println("Serie: " + serie.getTitulo() +
                                                ", Título del capítulo: " + episodio.getTitulo() +
                                                ", Duración: " + episodio.getDuracion() + " minutos" +
                                                ", Fecha de reproducción: " + h.getFecha_reproduccion());
                                    }
                                }
                            }
                        }
                    }
                    case 6 -> {
                            System.out.print("Ingrese el ID del perfil: ");
                            long perfilId = sc.nextInt();
                            System.out.print("Ingrese el ID de la serie: ");
                            Long serieId = sc.nextLong();
                            LocalDateTime fechaHoy = LocalDateTime.now();


                                List<Episodio> episodios = session.createQuery(
                                                "SELECT e FROM Episodio e WHERE e.serie.id = :serieId", Episodio.class)
                                        .setParameter("serieId", serieId)
                                        .getResultList();


                                Perfil perfil = session.get(Perfil.class, perfilId);
                                if (perfil == null) {
                                    System.out.println("Perfil no encontrado");
                                    tx.rollback();//Te hecha haci atras si hay algun error
                                    session.close();
                                    break;
                                }

                                for (Episodio episodio : episodios) {

                                    Historial historial = session.createQuery(
                                                    "SELECT h FROM Historial h WHERE h.perfil.id = :perfilId AND h.episodio.id = :episodioId",
                                                    Historial.class)
                                            .setParameter("perfilId", perfilId)
                                            .setParameter("episodioId", episodio.getId())
                                            .uniqueResult();

                                    if (historial != null) {
                                        // Si ya existe, actualizar la fecha
                                        historial.setFecha_reproduccion(fechaHoy);
                                        session.update(historial);
                                    } else {
                                        // Si no existe, insertar uno nuevo
                                        Historial nuevoHistorial = new Historial();
                                        nuevoHistorial.setPerfil(perfil);
                                        nuevoHistorial.setEpisodio(episodio);
                                        nuevoHistorial.setFecha_reproduccion(fechaHoy);
                                        session.persist(nuevoHistorial);
                                    }
                                }
                                tx = session.beginTransaction();
                                tx.commit();
                                System.out.println("Historial actualizado con la serie.");
                    }
                case 7 -> {
                    sc.nextLine();
                    System.out.println("Introduce un género para ver las series y la duración media de sus capítulos:");
                    String generoUsuario = sc.nextLine().trim(); // Eliminar espacios en blanco extras

                    List<Object[]> seriesPorGenero = session.createQuery(
                                    "SELECT s.titulo, AVG(e.duracion) " +
                                            "FROM Serie s " +
                                            "JOIN s.generos g " +
                                            "JOIN s.episodios e " +
                                            "WHERE LOWER(TRIM(g.nombre)) = LOWER(:genero) " + // Ignorar mayúsculas y espacios
                                            "GROUP BY s.id",
                                    Object[].class)
                            .setParameter("genero", generoUsuario) // Pasar el nombre del género en minúsculas
                            .list();

                    if (seriesPorGenero.isEmpty()) {
                        System.out.println("No hay series registradas para el género: " + generoUsuario);
                    } else {
                        System.out.println("Series del género '" + generoUsuario + "' con duración media de capítulos:");
                        for (Object[] result : seriesPorGenero) {
                            String tituloSerie = (String) result[0];
                            Double duracionMedia = (Double) result[1];
                            System.out.println("Serie: " + tituloSerie + " - Duración media: " + duracionMedia + " min");
                        }
                    }

                }
                case 8->{
                        List<Object[]> seriesmasVistas = session.createQuery(
                                        "SELECT s.titulo, COUNT(h.id) " +
                                                "FROM Historial h " +
                                                "JOIN h.episodio e " +
                                                "JOIN e.serie s " +
                                                "GROUP BY s.id " +
                                                "ORDER BY COUNT(h.id) DESC",
                                        Object[].class)
                                .setMaxResults(5) // Limitar a las 5 más vistas
                                .list();

                        System.out.println("Mostrando las 5 series más vistas:");
                        for (Object[] result : seriesmasVistas) {
                            String tituloSerie = (String) result[0];
                            Long reproducciones = (Long) result[1];
                            System.out.println("Serie: " + tituloSerie + " - Reproducciones: " + reproducciones);
                        }


                    }
            }

        }while(opcion!=0);
    }
}
