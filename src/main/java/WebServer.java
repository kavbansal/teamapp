import static spark.Spark.*;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class WebServer {
    public static void main(String[] args) {
        get("/", (req, res) -> {
            return new ModelAndView(null, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/projects", (req, res) -> {
            return new ModelAndView(null, "projects.hbs");
        }, new HandlebarsTemplateEngine());
    }
}