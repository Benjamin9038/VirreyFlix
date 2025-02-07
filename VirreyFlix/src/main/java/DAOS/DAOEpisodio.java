package DAOS;

import org.example.HibernateUtil;
import org.example.model.Episodio;
import org.example.model.Serie;
import org.example.model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Scanner;

public class DAOEpisodio {
    Scanner sc=new Scanner(System.in);

    public void crearEpisodio() {
        Scanner sc = new Scanner(System.in);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();


            System.out.print("Introduce el ID de la serie a la que quieres asociar el episodio: ");
            int serieId = sc.nextInt();
            sc.nextLine(); // Limpiar buffer


            Serie serie = session.get(Serie.class, serieId);

            if (serie == null) {
                System.out.println("No se encontró la serie con ID: " + serieId);
                return;
            }


            String titulo;
            do {
                System.out.print("Introduce el título del episodio que quieres insertar: ");
                titulo = sc.nextLine().trim();
                if (titulo.isEmpty()) {
                    System.out.println("El título no puede estar vacío.");
                }
            } while (titulo.isEmpty());


            Integer duracion = null;
            do {
                System.out.print("Introduce la duración en minutos: ");
                if (duracion>0) {
                    duracion = sc.nextInt();
                    sc.nextLine(); // Limpiar buffer
                } else {
                    System.out.println("Debes introducir un número válido.");
                    sc.nextLine(); // Limpiar buffer
                }
            } while (duracion == null);


            Episodio episodio = new Episodio(titulo, duracion);
            episodio.setSerie(serie);


            session.persist(episodio);

            tx.commit();
            System.out.println("Episodio creado correctamente y asociado a la serie '" + serie.getTitulo() + "': " + episodio);
        } catch (Exception e) {
            System.out.println("Error al crear episodio: " + e.getMessage());
        }
    }


    public void listarEpisodios(Integer id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            if (id==0) {
                Query<Episodio> query = session.createQuery("FROM Episodio", Episodio.class);
                List<Episodio> episodios = query.list();

                if (episodios.isEmpty()) {
                    System.out.println(" No hay episodios registrados.");
                } else {
                    System.out.println("\n Lista de Episodios:");
                    for (Episodio ep : episodios) {
                        System.out.println(ep);
                    }
                }
            } else {
                // Si se proporciona un ID, buscamos el episodio por ese ID usando WHERE
                Query<Episodio> query = session.createQuery("FROM Episodio WHERE id = :id", Episodio.class);
                query.setParameter("id", id);

                Episodio episodio = query.uniqueResult();

                if (episodio == null) {
                    System.out.println(" No se encontró un episodio con el ID: " + id);
                } else {
                    System.out.println("\nEpisodio encontrado:");
                    System.out.println(episodio);
                }
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println(" Error al listar episodios: " + e.getMessage());
        }
    }

    public void actualizarEpisodio(Integer id) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           tx = session.beginTransaction();

            // Buscar el episodio por ID
            Episodio episodio = session.get(Episodio.class, id);

            if (episodio == null) {
                System.out.println(" No se encontró un episodio con el ID: " + id);
            } else {

                System.out.println("Introduce el nuevo título del episodio:");
                String nuevoTitulo = sc.nextLine();

                System.out.println("Introduce la nueva duración en minutos:");
                int nuevaDuracion = sc.nextInt();


                episodio.setTitulo(nuevoTitulo);
                episodio.setDuracion(nuevaDuracion);


                session.update(episodio);

                tx.commit();
                System.out.println(" Episodio actualizado correctamente: " + episodio);
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println(" Error al actualizar episodio: " + e.getMessage());
        }
    }

    public void eliminarEpisodio(Integer id) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();


            Episodio episodio = session.get(Episodio.class, id);

            if (episodio == null) {
                System.out.println(" No se encontró un episodio con el ID: " + id);
            } else {

                session.delete(episodio);

                tx.commit();
                System.out.println(" Episodio eliminado correctamente: " + episodio);
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println(" Error al eliminar episodio: " + e.getMessage());
        }
    }

}
