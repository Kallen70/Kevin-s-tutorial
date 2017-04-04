package com.example;

import com.example.entities.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by kevinallen on 3/22/17.
 */
public interface EventRepository extends CrudRepository<Event, Integer> {
    List<Event> findAllByOrderByDateTimeDesc();
}


