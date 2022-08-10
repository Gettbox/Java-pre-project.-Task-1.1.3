package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        User firstUser = new User("Viktor", "Pelevin", (byte) 59);
        User secondUser = new User("Boris", "Akunin", (byte) 66);
        User thirdUser = new User("Evgeniy", "Vodolazkin", (byte) 58);
        User fourthUser = new User("Mikhail", "Shishkin", (byte) 61);

        userService.createUsersTable();

        userService.saveUser(firstUser.getName(), firstUser.getLastName(), firstUser.getAge());
        userService.saveUser(secondUser.getName(), secondUser.getLastName(), secondUser.getAge());
        userService.saveUser(thirdUser.getName(), thirdUser.getLastName(), thirdUser.getAge());
        userService.saveUser(fourthUser.getName(), fourthUser.getLastName(), fourthUser.getAge());

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();

        Util.closeConnection();
    }
}
