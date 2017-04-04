package com.example.services;

import com.example.entities.Photo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kevinallen on 3/22/17.
 */

public interface PhotoRepository extends CrudRepository<Photo, Integer> {
}

