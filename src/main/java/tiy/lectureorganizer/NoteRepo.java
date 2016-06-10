package tiy.lectureorganizer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Godfather on 5/22/2016.
 */
public interface NoteRepo extends CrudRepository<Note, Integer> {

    List<Note> findByCategory(String lectureCategory);

    List<Note> findByUser(User user);

//    @Query("SELECT n FROM Note n JOIN LectureFormat ON  LIKE ?1%") // (= == LIKE)
//    List<Note> findByTag(Collection lectureTag);

    @Query("SELECT g FROM Note g WHERE g.lectureNote LIKE ?1%")
    List<Note> findByLectureNoteStartsWith(String lectureNote);
}
