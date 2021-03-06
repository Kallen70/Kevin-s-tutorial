package com.example.controllers;
import com.example.entities.User;
import com.example.entities.Photo;
import com.example.services.PhotoRepository;
import com.example.services.UserRepository;


import com.example.utilities.PasswordStorage;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by kevinallen on 3/22/17.
 */
@RestController
public class IronGramController {
    @Autowired
    UserRepository users;

    @Autowired
    PhotoRepository photos;

Server dbui = null;

    @PostConstruct
    public void init() throws SQLException {
        dbui = Server.createWebServer().start();
    }

    @PreDestroy
    public void destroy() {
        dbui.stop();
    }

        @RequestMapping(path = "/login", method = RequestMethod.POST)
        public User login(String username, String password, HttpSession session, HttpServletResponse response) throws Exception {
            User user = users.findFirstByName(username);
            if (user == null) {
                user = new User(username, PasswordStorage.createHash(password));
                users.save(user);
            } else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
                throw new Exception("Wrong password");
            }
            session.setAttribute("username", username);
            response.sendRedirect("/");
            return user;
        }

        @RequestMapping("/logout")
        public void logout(HttpSession session, HttpServletResponse response) throws IOException {
            session.invalidate();
            response.sendRedirect("/");
        }
    }




