package com.example.utilities;

import com.example.EventRepository;
import com.example.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;

/**
 * Created by kevinallen on 3/23/17.
 */
public interface EventPagingandSortingRepository extends PagingAndSortingRepository<Event, Integer> {
    Page<Event> findByCategory(Pageable pageable, String category);

    Page<Event> findByDescription(Pageable pagable, String description);
}


