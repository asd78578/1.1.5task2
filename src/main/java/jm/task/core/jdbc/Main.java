package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    private static final User user = new User("Ivan", "Ivanov", (byte) 30);

    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        userService.createUsersTable();

        userService.saveUser(user.getName(), user.getLastName(), user.getAge());

        List<User> strings = userService.getAllUsers();
        strings.forEach(s -> System.out.println(s.getName()));

        userService.removeUserById(1);

        userService.cleanUsersTable();

        userService.dropUsersTable();


        Util.closeConnection();
    }
}
