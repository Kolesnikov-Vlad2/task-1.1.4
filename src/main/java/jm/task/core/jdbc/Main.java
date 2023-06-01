package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Petr", "Petrov", (byte) 13);
        userService.saveUser("Ivan", "Zaharov", (byte) 15);
        userService.saveUser("Sveta", "Petrova", (byte) 19);
        userService.saveUser("Olya", "Sidorova", (byte) 23);

        userService.removeUserById(1);

        for (User users : userService.getAllUsers()) {
            System.out.println(users);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
