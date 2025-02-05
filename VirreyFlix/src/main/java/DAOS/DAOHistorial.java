package DAOS;

import org.example.HibernateUtil;
import org.example.model.Historial;
import org.example.model.Perfil;
import org.example.model.Episodio;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class DAOHistorial {
    Scanner sc = new Scanner(System.in);

    public void crearHistorial() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            System.out.print("Introduce el ID del perfil: ");
            long perfilId = sc.nextLong();
            sc.nextLine();
            Perfil perfil = session.get(Perfil.class, perfilId);

            System.out.print("Introduce el ID del episodio: ");
            long episodioId = sc.nextLong();
            sc.nextLine();
            Episodio episodio = session.get(Episodio.class, episodioId);

            if (perfil == null || episodio == null) {
                System.out.println("Perfil o episodio no encontrado.");
                return;
            }

            Historial historial = new Historial(LocalDateTime.now());
            historial.setPerfil(perfil);
            historial.setEpisodio(episodio);

            session.persist(historial);
            tx.commit();

            System.out.println("Historial creado correctamente: " + historial);
        } catch (Exception e) {
            System.out.println("Error al crear historial: " + e.getMessage());
        }
    }

    public void listarHistorial(long perfilId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Historial> query;
            if (perfilId == 0) {
                query = session.createQuery("FROM Historial", Historial.class);
            } else {
                query = session.createQuery("FROM Historial WHERE perfil.id = :perfilId", Historial.class);
                query.setParameter("perfilId", perfilId);
            }
            List<Historial> historiales = query.list();

            if (historiales.isEmpty()) {
                System.out.println("No hay registros de historial.");
            } else {
                System.out.println("\nLista de Historial:");
                for (Historial h : historiales) {
                    System.out.println(h);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar historial: " + e.getMessage());
        }
    }

    public void actualizarHistorial(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Historial historial = session.get(Historial.class, id);
            if (historial == null) {
                System.out.println("No se encontró un historial con el ID: " + id);
                return;
            }

            System.out.print("Introduce el nuevo ID del perfil: ");
            long perfilId = sc.nextLong();
            sc.nextLine();
            Perfil perfil = session.get(Perfil.class, perfilId);

            System.out.print("Introduce el nuevo ID del episodio: ");
            long episodioId = sc.nextLong();
            sc.nextLine();
            Episodio episodio = session.get(Episodio.class, episodioId);

            if (perfil == null || episodio == null) {
                System.out.println("Perfil o episodio no encontrado.");
                return;
            }

            historial.setPerfil(perfil);
            historial.setEpisodio(episodio);
            historial.setFecha_reproduccion(LocalDateTime.now());

            session.update(historial);
            tx.commit();
            System.out.println("Historial actualizado correctamente: " + historial);
        } catch (Exception e) {
            System.out.println("Error al actualizar historial: " + e.getMessage());
        }
    }

    public void eliminarHistorial(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Historial historial = session.get(Historial.class, id);
            if (historial == null) {
                System.out.println("No se encontró un historial con el ID: " + id);
            } else {
                session.delete(historial);
                tx.commit();
                System.out.println("Historial eliminado correctamente: " + historial);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar historial: " + e.getMessage());
        }
    }
}
