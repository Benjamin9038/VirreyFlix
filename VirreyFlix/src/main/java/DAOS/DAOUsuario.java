package DAOS;

import org.example.HibernateUtil;
import org.example.model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class DAOUsuario {
    Scanner sc = new Scanner(System.in);

    public void crearUsuario() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            String nombre;
            do {
                System.out.print("Introduce el nombre del usuario: ");
                nombre = sc.nextLine().trim();
                if (nombre.isEmpty()) {
                    System.out.println("El nombre no puede estar vacío.");
                }
            } while (nombre.isEmpty());

            String email;
            do {
                System.out.print("Introduce el email del usuario: ");
                email = sc.nextLine().trim();
                if (email.isEmpty()) {
                    System.out.println("El email no puede estar vacío.");
                }
            } while (email.isEmpty());

            Usuario usuario = new Usuario(nombre, email);
            session.persist(usuario);
            tx.commit();

            System.out.println("Usuario creado correctamente: " + usuario);
        } catch (Exception e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
        }
    }

    public void listarUsuarios(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            if (id == 0) {
                Query<Usuario> query = session.createQuery("FROM Usuario", Usuario.class);
                List<Usuario> usuarios = query.list();

                if (usuarios.isEmpty()) {
                    System.out.println("No hay usuarios registrados.");
                } else {
                    System.out.println("\nLista de Usuarios:");
                    for (Usuario usuario : usuarios) {
                        System.out.println(usuario);
                    }
                }
            } else {
                Usuario usuario = session.get(Usuario.class, id);
                if (usuario == null) {
                    System.out.println("No se encontró un usuario con el ID: " + id);
                } else {
                    System.out.println("\nUsuario encontrado:");
                    System.out.println(usuario);
                }
            }

            tx.commit();
        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
    }

    public void actualizarUsuario(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Usuario usuario = session.get(Usuario.class, id);
            if (usuario == null) {
                System.out.println("No se encontró un usuario con el ID: " + id);
            } else {
                System.out.println("Introduce el nuevo nombre del usuario:");
                String nuevoNombre = sc.nextLine().trim();

                System.out.println("Introduce el nuevo email del usuario:");
                String nuevoEmail = sc.nextLine().trim();

                usuario.setNombre(nuevoNombre);
                usuario.setEmail(nuevoEmail);

                session.update(usuario);
                tx.commit();
                System.out.println("Usuario actualizado correctamente: " + usuario);
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    public void eliminarUsuario(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Usuario usuario = session.get(Usuario.class, id);
            if (usuario == null) {
                System.out.println("No se encontró un usuario con el ID: " + id);
            } else {
                session.delete(usuario);
                tx.commit();
                System.out.println("Usuario eliminado correctamente: " + usuario);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }
}
