package tiy.lectureorganizer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Godfather on 5/22/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LectureOrganizerSpringApp.class)
@WebAppConfiguration
public class NoteTrackerControllerTest {

    @Autowired
    NoteRepo notes;

    @Autowired
    UserRepo users;

    @Autowired
    LectureFormatRepo lectureFormatRepo;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testHome() throws Exception {

    }

    @Test
    public void testAddNote() throws Exception {
        String lectureCat = "primitivetype";
        String lectureNote = "double";
        boolean lectureBoolean = false;
        Note note = new Note(lectureCat, lectureNote, lectureBoolean);

        notes.save(note);

        Note noteFound = notes.findOne(note.id);
        System.out.println("==============" + note.id + " " + noteFound.id);
        assertEquals(note.id, noteFound.id);

        notes.delete(note.id);
    }

    @Test
    public void testQueryNotesByLectureNote() throws Exception {
        String lectureCat = "pizza";
        String lectureNote = "papajohns";
        boolean lectureBoolean = false;
        Note note = new Note(lectureCat, lectureNote, lectureBoolean);

        notes.save(note);

        List<Note> noteFound = notes.findByLectureNoteStartsWith(lectureNote);

        assertNotNull(noteFound);
        assertEquals(1, noteFound.size());

        Note checkNote = noteFound.get(0);
        assertEquals(note.id, checkNote.id);

        notes.delete(note);

    }

    @Test
    public void testQueryNotesByLectureTag() throws Exception {

        LectureFormat weekOneFormat = new LectureFormat("WEEK_ONE");

        lectureFormatRepo.save(weekOneFormat);

        User testUser = new User("formatTester", "password");
        users.save(testUser);

        Note testNote = new Note("primitivetype", "byte", false, testUser);

        ArrayList<LectureFormat> lectureFormats = new ArrayList<LectureFormat>();
        lectureFormats.add(weekOneFormat);

        testNote.setLectureFormats(lectureFormats);

        notes.save(testNote);

//        List<Note> retrievedNote = notes.findByTag(testNote.getLectureFormats());

//        assertEquals(testNote.getLectureFormats(), retrievedNote);

        notes.delete(testNote);
        users.delete(testUser);
        lectureFormatRepo.delete(weekOneFormat);
    }

    @Test
    public void testCreateLectureFormat() throws Exception {

        LectureFormat weekOne = new LectureFormat("WEEK_ONE");

        lectureFormatRepo.save(weekOne);

        int weekOneId = weekOne.getId();
        assertNotEquals(0, weekOneId);

        LectureFormat retrievedFormat = lectureFormatRepo.findOne(weekOneId);
        assertNotNull(retrievedFormat);

        assertEquals(weekOne.getName(), retrievedFormat.getName());

        lectureFormatRepo.delete(weekOne);

        retrievedFormat = lectureFormatRepo.findOne(weekOneId);
        assertNull(retrievedFormat);
    }

    @Test
    public void testCreateLectureWithFormat() throws Exception {

        LectureFormat weekOneFormat = new LectureFormat("WEEK_ONE");

        lectureFormatRepo.save(weekOneFormat);

        User testUser = new User("formatTester", "password");
        users.save(testUser);

        Note testNote = new Note("primitivetype", "byte", false, testUser);

        ArrayList<LectureFormat> lectureFormats = new ArrayList<LectureFormat>();
        lectureFormats.add(weekOneFormat);

        testNote.setLectureFormats(lectureFormats);

        notes.save(testNote);

        int testNoteId = testNote.getId();
        assertNotEquals(0, testNoteId);

        Note retrievedNote = notes.findOne(testNoteId);
        assertNotNull(retrievedNote);
        assertNotNull(retrievedNote.getLectureFormats());

        notes.delete(testNote);
        users.delete(testUser);
        lectureFormatRepo.delete(weekOneFormat);
    }

}