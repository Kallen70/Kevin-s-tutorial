package com.example;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by kevinallen on 3/22/17.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findFirstByName(String name);
}


