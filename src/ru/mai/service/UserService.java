package ru.mai.service;

import ru.mai.data.User;
import ru.mai.error.UserNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserService {

    private static Map<Long, User> users;

    public UserService() {
        users = new HashMap<>();
    }

    public User getUserByIdWithCache(long id) {
        if(!users.containsKey(id)) {
            try {
                User user = getUserFromFile(id);
                users.put(id, user);
            }
            catch(UserNotFoundException e) {
                System.out.println("User with id = " + id + " non found.");
                return null;
            }
        }
        return users.get(id);
    }

    public User getUserByIdWithoutCache(long id){
        try {
            return getUserFromFile(id);
        }
        catch(UserNotFoundException e) {
            System.out.println("User with id = " + id + " non found.");
            return null;
        }
    }

    private User getUserFromFile(long id) {
        System.out.println("Get form file user with id = " + id);
        try (Stream<String> stream = Files.lines(Paths.get("data\\" + String.valueOf(id) + ".txt"))) {
            Map<String, String>  userData = new HashMap<>();
            for (String line : stream.collect(Collectors.toList())) {
                String[] data = line.split(":");
                userData.put(data[0].trim(), data[1].trim());
            }
            return createUser(userData);

        } catch (IOException e) {
            throw new UserNotFoundException();
        }
    }

    private User createUser(Map<String, String> userData) {
        User user = new User();
        user.setId(Long.parseLong(userData.get("id")));
        user.setFirstName(userData.get("firstName"));
        user.setLastName(userData.get("lastName"));
        user.setEmail(userData.get("email"));
        return user;
    }

}
