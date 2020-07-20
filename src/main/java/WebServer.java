import static spark.Spark.*;

import dao.InMemoryProjectDao;
import dao.ProjectDao;
import dao.UnirestProjectDao;
import model.Project;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class WebServer {
    public static void main(String[] args) {
        staticFiles.location("/public");
        get("/", (req, res) -> {
            return new ModelAndView(null, "index.hbs");
        }, new HandlebarsTemplateEngine());


        ProjectDao projectDao = new UnirestProjectDao();

        get("/projects", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("projectList", projectDao.findAll());
            return new ModelAndView(model, "projects.hbs");
        }, new HandlebarsTemplateEngine());

        post("/projects", (req, res) -> {
            String name = req.queryParams("projectname");
            String desc = req.queryParams("projectdesc");
            //TODO: Fix this
            int pId = 0;
            projectDao.add(new Project(name, desc, pId));
            res.redirect("/projects");
            return null;
        }, new HandlebarsTemplateEngine());

    }
}