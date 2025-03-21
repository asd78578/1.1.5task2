package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

// UserHibernateDaoImpl должен реализовывать интерефейс UserDao
//Методы создания и удаления таблицы пользователей в классе UserHibernateDaoImpl должны быть реализованы с помощью SQL

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getConnection();
    public UserDaoHibernateImpl() {

    }

//createUsersTable: неверно подобраны числовые типы данных для age и id, посмотри соответствие для byte и long в языке SQL

    @Override
    public void createUsersTable() {
        // Session обеспечивает методы для сохранения, обновления, удаления и извлечения объектов из бд
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS mydbtest" +
                    " (id tinyint not null auto_increment, name VARCHAR(30), " +
                    "lastname VARCHAR(30), " +
                    "age tinyint, " +
                    "PRIMARY KEY (id))").executeUpdate();
            transaction.commit();
            System.out.println("The table has been created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("Drop table if exists mydbtest").executeUpdate();
            transaction.commit();
            System.out.println("Table deleted");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User with the name – " + name + " added to the database");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
    }

//removeUserById: переделай удаление, которое у тебя происходит в 2 запроса (session.delete(session.get(User.class, id)))
// так, чтобы удалялось в 1 запрос. Нужен HQL запрос

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            // Нужен HQL запрос для удаления пользователя по id
            String hql = "DELETE FROM User u WHERE u.id = :userId";
            int deletedUser = session.createQuery(hql)
                    .setParameter("userId", id)
                    .executeUpdate();

            transaction.commit();
            // Проверка
            if (deletedUser > 0) {
                System.out.println("User with id " + id + " removed from the database");
            }else {
                System.out.println("User with id " + id + " not removed from the database");
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            list = session.createCriteria(User.class).list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
        return list;
    }

// cleanUsersTable: есть специальный sql оператор для очистки таблицы, поищи. Итеративно удалять, как у тебя - можно,
// но не нужно

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE mydbtest").executeUpdate();
            transaction.commit();

            System.out.println("The table is cleared");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
    }
}
