package api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.*;
import exception.ApiError;
import exception.DaoException;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import model.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ApiServer {

    private static Javalin app;

    public static void main(String[] args) {
        // Preparation!
        Sql2o sql2o = getSql2o();
        createProjectsTable(sql2o);
        createPeopleTable(sql2o);
        ProjectDao projectDao = getProjectDao(sql2o);
        PersonDao personDao = getPersonDao(sql2o);
        initPeople(personDao);
        initData(projectDao);

        app = startServer();
        app.get("/", ctx -> ctx.result("Welcome to CourseReVU App"));
        // TODO: update the code below to actually show a list of courses!

        app.post("/projects", ctx -> {
            // Typically, the user provides the course as a JSON object.
            // Maps a JSON body to a Java class using JavalinJson:
            Project project = ctx.bodyAsClass(Project.class);
            // Next, add the course to our databse using our DAO:
            projectDao.add(project);
            // Conventionally, return the added course to client:
            ctx.json(project);
            ctx.contentType("application/json");
            // Also, return 201 status code to say operation succeeded.
            ctx.status(201);
        });

        app.get("/projects", ctx -> {
            List<Project> courseList = projectDao.findAll();
            ctx.json(courseList);
            ctx.contentType("application/json");
            ctx.status(200);
        });

        getPersonByEmail(personDao);
    }

    private static Javalin startServer() {
        Gson gson = new GsonBuilder().create();
        JavalinJson.setFromJsonMapper(gson::fromJson);
        JavalinJson.setToJsonMapper(gson::toJson);
        final int PORT = 7000;
        return Javalin.create().start(PORT);
    }


    private static Sql2o getSql2o() {
        final String URI = "jdbc:sqlite:./Store.db";
        final String USERNAME = "";
        final String PASSWORD = "";
        return new Sql2o(URI, USERNAME, PASSWORD);
    }


    private static void createProjectsTable(Sql2o sql2o) {
        dropProjectsTableIfExists(sql2o);
        String sql = "CREATE TABLE IF NOT EXISTS Projects(" +
                "id INTEGER PRIMARY KEY," +
                "name VARCHAR(30) NOT NULL," +
                "description VARCHAR(100)," +
                "ownerId INTEGER," +
                "num_needed INTEGER," +
                "time_commit INTEGER" +
                ");";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        }
    }

    private static void dropProjectsTableIfExists(Sql2o sql2o) {
        String sql = "DROP TABLE IF EXISTS Projects;";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        }
    }

    private static void createPeopleTable(Sql2o sql2o) {
        dropPeopleTableIfExists(sql2o);
        String sql = "CREATE TABLE IF NOT EXISTS People(" +
                "id INTEGER PRIMARY KEY," +
                "name VARCHAR(30) NOT NULL," +
                "email VARCHAR(30) NOT NULL," +
                "password VARCHAR(30) NOT NULL" +
                ");";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        }
    }

    private static void dropPeopleTableIfExists(Sql2o sql2o) {
        String sql = "DROP TABLE IF EXISTS People;";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        }
    }

    private static ProjectDao getProjectDao(Sql2o sql2o) {
        return new Sql2oProjectDao(sql2o);
    }

    private static PersonDao getPersonDao(Sql2o sql2o) {
        return new Sql2oPersonDao(sql2o);
    }

    private static void initData(ProjectDao projectDao) {
        projectDao.add(new Project("TeamApp", "App to connect software development teams", 1));
        projectDao.add(new Project("Prioriteams", "Priority-based scheduling app", 1));
    }

    private static void initPeople(PersonDao personDao) {
        personDao.add(new Person("KavanBansal", "kbansal2@jhu.edu", "KavanB123", 0, 22));
    }

    private static void getPersonByEmail(PersonDao pDao) {
        app.get("/Person/:email",ctx->{
            String email = ctx.pathParam("email");
            List<Person> person = pDao.findPersonByEmail(email);
            ctx.json(person);
            ctx.status(200);
        });
    }
}

