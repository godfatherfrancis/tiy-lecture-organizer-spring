package tiy.lectureorganizer;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Godfather on 5/22/2016.
 */
public interface LectureFormatRepo extends CrudRepository<LectureFormat, Integer> {

    LectureFormat findByName(String name);
}
