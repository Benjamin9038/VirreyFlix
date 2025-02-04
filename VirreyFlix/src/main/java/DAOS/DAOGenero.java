package DAOS;
import org.example.HibernateUtil;
import org.example.model.Genero;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class DAOGenero {
    Scanner sc = new Scanner(System.in);

    // Crear un nuevo Genero
    public void crearGenero() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            String nombre;
            do {
                System.out.print("Introduce el nombre del género: ");
                nombre = sc.nextLine().trim();
                if (nombre.isEmpty()) {
                    System.out.println("El nombre no puede estar vacío.");
                }
            } while (nombre.isEmpty());

            // Crear y persistir el género
            Genero genero = new Genero(nombre);
            session.persist(genero);
            tx.commit();

            System.out.println("Género creado correctamente: " + genero);
        } catch (Exception e) {
            System.out.println("Error al crear género: " + e.getMessage());
        }
    }

    // Listar géneros (si se pasa ID muestra solo el que coincide, si no muestra todos)
    public void listarGeneros(Integer id) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            if (id == 0) {
                Query<Genero> query = session.createQuery("FROM Genero", Genero.class);
                List<Genero> generos = query.list();

                if (generos.isEmpty()) {
                    System.out.println("No hay géneros registrados.");
                } else {
                    System.out.println("\nLista de Géneros:");
                    for (Genero genero : generos) {
                        System.out.println(genero);
                    }
                }
            } else {
                Query<Genero> query = session.createQuery("FROM Genero WHERE id = :id", Genero.class);
                query.setParameter("id", id);

                Genero genero = query.uniqueResult();

                if (genero == null) {
                    System.out.println("No se encontró un género con el ID: " + id);
                } else {
                    System.out.println("\nGénero encontrado:");
                    System.out.println(genero);
                }
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error al listar géneros: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    // Actualizar un género
    public void actualizarGenero(Integer id) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            // Buscar el género por ID
            Genero genero = session.get(Genero.class, id);

            if (genero == null) {
                System.out.println("No se encontró un género con el ID: " + id);
            } else {
                // Solicitar nuevos valores para los campos del género
                System.out.println("Introduce el nuevo nombre del género:");
                String nuevoNombre = sc.nextLine();

                // Actualizar el nombre del género
                genero.setNombre(nuevoNombre);

                // Guardar los cambios
                session.update(genero);

                tx.commit();
                System.out.println("Género actualizado correctamente: " + genero);
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error al actualizar género: " + e.getMessage());
        }
    }

    // Eliminar un género
    public void eliminarGenero(Integer id) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            // Buscar el género por ID
            Genero genero = session.get(Genero.class, id);

            if (genero == null) {
                System.out.println("No se encontró un género con el ID: " + id);
            } else {
                // Eliminar el género
                session.delete(genero);

                tx.commit();
                System.out.println("Género eliminado correctamente: " + genero);
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error al eliminar género: " + e.getMessage());
        }
    }
}

