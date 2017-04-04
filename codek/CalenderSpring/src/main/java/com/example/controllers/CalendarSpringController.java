package com.example.controllers;

import com.example.EventRepository;
import com.example.User;
import com.example.UserRepository;
import com.example.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by kevinallen on 3/22/17.
 */
@Controller
public class CalendarSpringController {
    @Autowired
    EventRepository events;

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        List<Event> eventEntities = events.findAllByOrderByDateTimeDesc();
        if (userName != null) {
            User user = users.findFirstByName(userName);
            model.addAttribute("user", user);
            model.addAttribute("now", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        }
        model.addAttribute("events", eventEntities);//add list of events into model,to rendered on clients side.
        return "home";
    }

    @RequestMapping(path = "/create-event", method = RequestMethod.POST)//  a function to add events.
    public String createEvent(HttpSession session, String description, String dateTime) {
        String userName = (String) session.getAttribute("userName");
        if (userName != null) {
            Event event = new Event(description, LocalDateTime.parse(dateTime), users.findFirstByName(userName));
            events.save(event);// create a new event out what user submitted.
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String name) {
        User user = users.findFirstByName(name);
        if (user == null) {
            user = new User(name);
            users.save(user);
        }
        session.setAttribute("userName", name);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}


