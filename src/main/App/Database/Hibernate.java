package Database;

import Database.HibernateClasses.*;
import javax.persistence.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Hibernate {
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("Database", getProperties());
    private static EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    private static Map getProperties() {
        Map result = new HashMap();
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            result.put( "hibernate.connection.username", prop.getProperty("username"));
            result.put( "hibernate.connection.password", prop.getProperty("password"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static boolean registerUser(String username, String email, String hash, String salt) {
        EntityTransaction et = null;
        boolean isSuccess = true;
        try {
            et = em.getTransaction();
            et.begin();
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setHash(hash);
            user.setSalt(salt);
            em.persist(user);
            et.commit();
            isSuccess = true;

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
            isSuccess = false;
        } finally {
            return isSuccess;
        }
    }

    public static void updateUser(User user) {
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            em.merge(user);
            em.flush();
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        }
    }

    public static String getSalt(String username) {
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            User user = em.createQuery(
                    "select e from User e where e.username =:username",
                    User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            et.commit();
            return user.getSalt();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    public static User getUser(String username) {
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            User user = em.createQuery(
                    "select e from User e where e.username =:username",
                    User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            et.commit();
            return user;
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    public static boolean login(String username, String hash) {
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            User user = em.createQuery(
                    "select e from User e where e.username =:username and e.hash =:hash",
                    User.class)
                    .setParameter("username", username)
                    .setParameter("hash", hash)
                    .getSingleResult();
            et.commit();
            return (user != null);
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    public static int getUserID(String username){
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            User user = em.createQuery(
                    "select e from User e where e.username =:username",
                    User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            et.commit();
            return user.getId();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        }
        return -1;
    }

    public static void deleteUser(String username) {
        EntityTransaction et = null;
        try{
            et=em.getTransaction();
            et.begin();
            User user = em.createQuery(
                    "select e from User e where e.username =:username",User.class)
                    .setParameter("username",username)
                    .getSingleResult();
            em.detach(user);
        }catch (Exception e){
            if(et !=null){
                et.rollback();
            }
            e.printStackTrace();
        }
    }

    public static EntityManager getEm() {
        return em;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }

}
