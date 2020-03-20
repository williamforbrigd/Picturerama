package Database;

import Database.HibernateClasses.*;
import javax.persistence.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Class that is used to connect to the database
 */
public class Hibernate {
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("Database", getProperties());
    private static EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

    /**
     * Sets up the password and username to the database
     * @return a map with the password and username
     */
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

    /**
     * Register a new user in the database
     *
     * @param username the username
     * @param email    the email
     * @param hash     the hash
     * @param salt     the salt
     * @return if the registration was successful
     */
    public static boolean registerUser(String username, String email, String hash, String salt) {
        if (username == null || email == null || hash == null || salt == null) {
            throw new IllegalArgumentException();
        }
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

    /**
     * Update user.
     *
     * @param user the user
     */
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

    /**
     * Gets salt of user.
     *
     * @param username username of user
     * @return the salt
     * @throws NoResultException if the user was not found
     */
    public static String getSalt(String username) throws NoResultException {
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
        } catch (NoResultException e) {
            if (et != null) {
                et.rollback();
            }
            throw e;
        } catch (Error | Exception e) {
            throw e;
        }
    }

    /**
     * Gets user.
     *
     * @param username username of user
     * @return the user
     */
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

    /**
     * Login user
     *
     * @param username the username
     * @param hash     the hash
     * @return if login was successful
     */
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
            return false;
        }
    }

    /**
     * Get user id
     *
     * @param username the username
     * @return the user id
     */
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

    /**
     * Delete user.
     *
     * @param username the username
     */
    public static void deleteUser(String username) {
        EntityTransaction et = null;
        try{
            User user = getUser(username);
            et=em.getTransaction();
            et.begin();
            em.remove(user);
            et.commit();
        }catch (Exception e){
            if(et !=null){
                et.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Gets entity manager
     *
     * @return the entity manager
     */
    public static EntityManager getEm() {
        return em;
    }

    /**
     * Gets entity manager factory.
     *
     * @return the entity manager factory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }

}
