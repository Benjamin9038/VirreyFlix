package DAOS;

import org.example.HibernateUtil;
import org.example.model.Perfil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class DAOPerfil {
    private final Scanner scanner = new Scanner(System.in);

    public void listarPerfiles(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (id == 0) {
                Query<Perfil> query = session.createQuery("FROM Perfil", Perfil.class);
                List<Perfil> perfiles = query.list();
                perfiles.forEach(System.out::println);
            } else {
                Perfil perfil = session.get(Perfil.class, id);
                System.out.println(perfil != null ? perfil : "Perfil no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error al listar perfiles: " + e.getMessage());
        }
    }

    public void actualizarPerfil(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Perfil perfil = session.get(Perfil.class, id);
            if (perfil == null) {
                System.out.println("Perfil no encontrado.");
                return;
            }

            System.out.print("Introduce el nuevo nombre: ");
            perfil.setNombre(scanner.nextLine());

            System.out.print("Introduce la nueva edad: ");
            perfil.setEdad(scanner.nextInt());
            scanner.nextLine();

            session.merge(perfil);
            tx.commit();
            System.out.println("Perfil actualizado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar perfil: " + e.getMessage());
        }
    }

    public void crearPerfil() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            System.out.print("Introduce el nombre del perfil: ");
            String nombre = scanner.nextLine();

            System.out.print("Introduce la edad: ");
            int edad = scanner.nextInt();
            scanner.nextLine();

            Perfil perfil = new Perfil(nombre, edad, null);
            session.persist(perfil);
            tx.commit();
            System.out.println("Perfil creado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al crear perfil: " + e.getMessage());
        }
    }

    public void eliminarPerfil(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Perfil perfil = session.get(Perfil.class, id);
            if (perfil == null) {
                System.out.println("Perfil no encontrado.");
                return;
            }
            session.remove(perfil);
            tx.commit();
            System.out.println("Perfil eliminado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar perfil: " + e.getMessage());
        }
    }
}
