package tiy.lectureorganizer;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Godfather on 5/22/2016.
 */
public interface UserRepo extends CrudRepository<User, Integer> {

    User findUserByName(String name);
}
