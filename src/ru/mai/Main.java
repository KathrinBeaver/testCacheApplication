package ru.mai;

import ru.mai.data.User;
import ru.mai.service.UserService;

import java.time.LocalDate;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        UserService userService = new UserService();

        // test without cache
        long startTime1 = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            User user = userService.getUserByIdWithoutCache(1L);
//            System.out.println(user.toString());
        }
        long stopTime1 = System.currentTimeMillis();
        long elapsedTime1 = stopTime1 - startTime1;
        System.out.println("Without cache time: " + elapsedTime1);

        // test with cache
        long startTime2 = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            User user = userService.getUserByIdWithCache(2L);
//            System.out.println(user.toString());
        }
        long stopTime2 = System.currentTimeMillis();
        long elapsedTime2 = stopTime2 - startTime2;
        System.out.println("With cache time: " + elapsedTime2);
    }
}
