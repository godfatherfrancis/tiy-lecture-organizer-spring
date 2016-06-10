package tiy.lectureorganizer;

import javax.persistence.*;

/**
 * Created by Godfather on 5/22/2016.
 */
@Entity
@Table(name = "lecture_formats")
public class LectureFormat {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name;

    public LectureFormat() {
    }

    public LectureFormat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
