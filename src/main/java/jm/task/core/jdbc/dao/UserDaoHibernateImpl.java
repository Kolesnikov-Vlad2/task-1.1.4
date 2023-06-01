package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS dbuser (
                id SERIAL PRIMARY KEY,
                name VARCHAR(20) NOT NULL,
                lastName VARCHAR(20) NOT NULL,
                age SMALLINT NOT NULL)""";

        try (Session session = Util.connection()) {

            transaction = session.beginTransaction();
            session.createNativeQuery(createTableSQL).executeUpdate();

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.connection()) {

            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS dbuser").executeUpdate();

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.connection()) {

            User user = new User(name, lastName, age);

            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.connection()) {

            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try (Session session = Util.connection()) {

            transaction = session.beginTransaction();
            Query<User> query =  session.createQuery("FROM User", User.class);
            return query.list();

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.connection()) {

            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE dbuser").executeUpdate();

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
