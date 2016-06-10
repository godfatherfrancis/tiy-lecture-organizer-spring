package tiy.lectureorganizer;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Godfather on 5/22/2016.
 */
@Entity
@Table (name = "notes")
public class Note {

    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    User user;

    @ManyToMany
    private Collection<LectureFormat> lectureFormats;

    @Column(nullable = false)
    String category;

    @Column(nullable = false)
    String lectureNote;

    @Column(nullable = false)
    boolean priority;

    public Note() {
    }

    public Note(String category, String lectureNote, boolean priority) {
        this.category = category;
        this.lectureNote = lectureNote;
        this.priority = priority;
    }

    public Note(String category, String lectureNote, boolean priority, User user) {
        this.category = category;
        this.lectureNote = lectureNote;
        this.priority = priority;
        this.user = user;
    }

    public Note(String tag, String category, String lectureNote, boolean priority, User user) {

        this.category = category;
        this.lectureNote = lectureNote;
        this.priority = priority;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        String returnString = "lectureNote = " + lectureNote + "\n";
        returnString += "category = " + category + "\n";
//        returnString += "releaseYear = " + releaseYear + "\n";
        if (user != null) {
            returnString += "user = " + user.getName() + "\n";
        }

        return returnString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Collection<LectureFormat> getLectureFormats() {
        return lectureFormats;
    }

    public void setLectureFormats(Collection<LectureFormat> lectureFormats) {
        this.lectureFormats = lectureFormats;
    }
}
