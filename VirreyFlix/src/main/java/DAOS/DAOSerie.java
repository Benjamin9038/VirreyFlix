package DAOS;

import org.example.HibernateUtil;
import org.example.model.Serie;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class DAOSerie {
    private final Scanner scanner = new Scanner(System.in);

    public void listarSeries(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (id == 0) {
                Query<Serie> query = session.createQuery("FROM Serie", Serie.class);
                List<Serie> series = query.list();
                series.forEach(System.out::println);
            } else {
                Serie serie = session.get(Serie.class, id);
                System.out.println(serie != null ? serie : "Serie no encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Error al listar series: " + e.getMessage());
        }
    }

    public void actualizarSerie(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Serie serie = session.get(Serie.class, id);
            if (serie == null) {
                System.out.println("Serie no encontrada.");
                return;
            }

            System.out.print("Introduce el nuevo título: ");
            serie.setTitulo(scanner.nextLine());

            System.out.print("Introduce el nuevo género: ");
            serie.setGenero(scanner.nextLine());

            System.out.print("Introduce la nueva calificación de edad: ");
            serie.setCalificacion_edad(scanner.nextInt());
            scanner.nextLine();

            session.merge(serie);
            tx.commit();
            System.out.println("Serie actualizada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar serie: " + e.getMessage());
        }
    }

    public void crearSerie() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            System.out.print("Introduce el título de la serie: ");
            String titulo = scanner.nextLine();

            System.out.print("Introduce el género: ");
            String genero = scanner.nextLine();

            System.out.print("Introduce la calificación de edad: ");
            int calificacionEdad = scanner.nextInt();
            scanner.nextLine();

            Serie serie = new Serie(titulo, genero, calificacionEdad);
            session.persist(serie);
            tx.commit();
            System.out.println("Serie creada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al crear serie: " + e.getMessage());
        }
    }

    public void eliminarSerie(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Serie serie = session.get(Serie.class, id);
            if (serie == null) {
                System.out.println("Serie no encontrada.");
                return;
            }
            session.remove(serie);
            tx.commit();
            System.out.println("Serie eliminada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar serie: " + e.getMessage());
        }
    }
}
