package org.example;

import DAOS.DAOSerie;
import org.example.model.Perfil;
import org.example.model.Serie;
import org.example.model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class Consultas {
    public void consulta() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Scanner sc=new Scanner(System.in);
        DAOSerie daoSerie = new DAOSerie();

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
                    List<Usuario> lista_Usuario=session.createQuery("SELECT usuario FROM Usuario usuario", Usuario.class).list();
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
            }

        }while(opcion!=0);
    }
}
