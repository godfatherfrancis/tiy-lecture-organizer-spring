package tiy.lectureorganizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Godfather on 5/22/2016.
 */
@Controller
public class NoteTrackerController {

    @Autowired
    NoteRepo notes;

    @Autowired
    UserRepo users;

    @Autowired
    LectureFormatRepo lectureFormatRepo;

    @PostConstruct
    public void init() {
        if (users.count() == 0) {
            User user = new User();
            user.name = "Lord Talent";
            user.password = "test123";
            users.save(user);
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model, String lectureCategory) {
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        List<Note> noteList = new ArrayList<Note>();
        if (lectureCategory != null) {
            noteList = notes.findByCategory(lectureCategory);
        }
//        else if (lectureTag != null) {
//            noteList = notes.findByTag(lectureTag);
//        }
        else {
            User savedUser = (User)session.getAttribute("user");
            if (savedUser != null) {
                noteList = notes.findByUser(savedUser);
            } else {
                Iterable<Note> allNotes = notes.findAll();
                for (Note note : allNotes) {
                    noteList.add(note);
                }
            }
        }
        model.addAttribute("notes", noteList);
        return "home";
    }

    @RequestMapping(path = "/add-note", method = RequestMethod.POST)
    public String addNote(HttpSession session,String lectureTag, String lectureCategory, String lectureNote, boolean priority) {
        System.out.println("Adding Lecture Note... ");

        LectureFormat lectureFormat = new LectureFormat(lectureTag);
        lectureFormatRepo.save(lectureFormat);

        User user = (User) session.getAttribute("user");
        Note note = new Note(lectureCategory, lectureNote, priority, user);

        ArrayList<LectureFormat> lectureFormats = new ArrayList<LectureFormat>();
        lectureFormats.add(lectureFormat);

        note.setLectureFormats(lectureFormats);

        notes.save(note);
        return "redirect:/";
    }

    @RequestMapping(path = "/searchByLecture", method = RequestMethod.GET)
    public String queryNotesByLectureNote(HttpSession session, Model model, String search) {
        System.out.println("Searching Lecture Notes... ");
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }
        List<Note> noteList = notes.findByLectureNoteStartsWith(search);
        model.addAttribute("notes", noteList);
        return "home";
    }

    @RequestMapping(path = "/toggle", method = RequestMethod.GET)
    public String toggleNote(Model model, Integer noteID) {
        if (noteID != null) {
            Note note = notes.findOne(noteID);
            note.priority = !note.priority;
            notes.save(note);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteNote(Model model, Integer noteID) {
        if (noteID != null) {
            notes.delete(noteID);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {

        User user = users.findUserByName(userName);
        if (user == null) {
            user = new User(userName, password);
            users.save(user);
        } else if (!password.equals(user.getPassword())) {
            throw new Exception("Incorrect password");
        }
        session.setAttribute("user", user);

        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/notes", method = RequestMethod.GET)
    public String notes(Model model, HttpSession session) {
        return "hometwo";
    }
}
