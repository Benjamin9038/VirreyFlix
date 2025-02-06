import org.example.HibernateUtil;
import org.example.model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UsuarioPerfilTest {
    @Test
    public void prueba() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();

        Usuario usuarioPrueba = new Usuario("Benjamin", "ben@gmail.com");
        Perfil perfilPrueba = new Perfil("Benjamin",21,usuarioPrueba);

        session.persist(usuarioPrueba);
        session.persist(perfilPrueba);

        tx.commit();
        session.close();
    }

    @Test
    public void pruebaSerieEpisodio(){

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();

        Serie serie = new Serie("Los simpsons",12);

        Episodio ep1 = new Episodio("Navidad",20);
        Episodio ep2 = new Episodio("Verano",10);
        Episodio ep3 = new Episodio("Halloween",70);

        ep1.setSerie(serie);
        ep2.setSerie(serie);
        ep3.setSerie(serie);

        session.persist(serie);
        session.persist(ep1);
        session.persist(ep2);
        session.persist(ep3);

        tx.commit();
        session.close();

    }

    @Test
    public void consultarSerie(){
        Session session2 = HibernateUtil.getSessionFactory().openSession();
        Serie consultada=session2.find(Serie.class, 1);

        System.out.println("Serie "+consultada);
        System.out.println("Episodios "+consultada.getEpisodios());
        session2.close();
    }

    @Test
    public void generarHistoriales(){
        Session s3 = HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s3.beginTransaction();

        Historial h1=new Historial(LocalDateTime.now().minusDays(4));
        Historial h2=new Historial(LocalDateTime.now().minusDays(5));

        h1.setEpisodio(s3.find(Episodio.class,1));
        h1.setPerfil(s3.find(Perfil.class,1));

        h2.setEpisodio(s3.find(Episodio.class,1));
        h2.setPerfil(s3.find(Perfil.class,1));

        s3.persist(h1);
        s3.persist(h2);

        tx.commit();
        s3.close();
    }

    @Test
    public void consultarPerfilHistorial(){
        Session session3 = HibernateUtil.getSessionFactory().openSession();
        Perfil consultada=session3.find(Perfil.class,1);

        System.out.println("Perfil "+consultada);
        System.out.println("Historial "+consultada);
        session3.close();
    }


}
