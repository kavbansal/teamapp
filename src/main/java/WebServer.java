import static spark.Spark.*;

import dao.*;
import model.Person;
import model.Project;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebServer {
    public static void main(String[] args) {
        staticFiles.location("/public");
        ProjectDao projectDao = new UnirestProjectDao();
        PersonDao personDao = new UnirestPersonDao();
        get("/", (req, res) -> {
            res.removeCookie("email");
            Map<String, String> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/", (req, res) -> {
            // Capture the provided username
            String email = req.queryParams("email");
            String password = req.queryParams("password");
            List<Person> myPerson = personDao.findPersonByEmail(email);
            if (myPerson.isEmpty() || (!myPerson.get(0).getPassword().equals(password))) {
                res.redirect("/");
            } else {
                res.cookie("email", email);
                // Redirect to main page
                res.redirect("/projects");
            }
            return null;
        }, new HandlebarsTemplateEngine());

        get("/projects", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("projectList", projectDao.findAll());
            model.put("email", req.cookie("email"));
            List<Person> myPerson = personDao.findPersonByEmail(req.cookie("email"));
            model.put("personName", myPerson);
            return new ModelAndView(model, "projects.hbs");
        }, new HandlebarsTemplateEngine());

        get("/create", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("email", req.cookie("email"));
            return new ModelAndView(model, "create.hbs");
        }, new HandlebarsTemplateEngine());

        post("/create", (req, res) -> {
            String name = req.queryParams("projectname");
            String desc = req.queryParams("projectdesc");
            int num_needed = Integer.parseInt(req.queryParams("num_needed"));
            int time_commit = Integer.parseInt(req.queryParams("time_commit"));
            List<Person> myPerson = personDao.findPersonByEmail(req.cookie("email"));
            int pId = myPerson.get(0).getId();
            projectDao.add(new Project(name, desc, time_commit, num_needed, pId));
            res.redirect("/projects");
            return null;
        }, new HandlebarsTemplateEngine());

    }
}