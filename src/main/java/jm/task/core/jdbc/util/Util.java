package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

//В класс Util должна быть добавлена конфигурация для Hibernate (рядом с JDBC), без использования xml.
public class Util {
    // реализуйте настройку соеденения с БД

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String HOST = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "1593575Alav+";

    private static SessionFactory sessionFactory = null;      // создание и управление сессиями работы бд

    //static {
    public static SessionFactory getConnection() {
        try {
            // Создаём SessionFactory без использования xml
            Configuration configuration = new Configuration()   // объединения свойств конфигурации
                    // Установливаем значение свойства
                    .setProperty("hibernate.connection.driver_class", DRIVER)
                    .setProperty("hibernate.connection.url", HOST)
                    .setProperty("hibernate.connection.username", LOGIN)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .addAnnotatedClass(User.class);

            // Управления настройками и контроля сервисов
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);


            //sessionFactory = configuration.configure().buildSessionFactory();

        } catch (Throwable ex) {
            // Обработка исключений (например, лог ошибки)
            ex.printStackTrace();
        }
        return sessionFactory;
    }


    public static void closeConnection() {
        if (sessionFactory != null)
            sessionFactory.close();
    }


//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }


}
