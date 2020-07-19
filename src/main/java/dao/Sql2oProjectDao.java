package dao;

import exception.DaoException;
import model.Project;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

public class Sql2oProjectDao implements ProjectDao {

    private final Sql2o sql2o;

    public Sql2oProjectDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Project project) throws DaoException {
        try (Connection conn = sql2o.open()) {
            String sql = "INSERT INTO Projects(name, description) VALUES (:name, :description);";
            int id = (int) conn.createQuery(sql)
                    .bind(project)
                    .executeUpdate()
                    .getKey();
            project.setId(id);
        } catch (Sql2oException ex) {
            throw new DaoException("Unable to add a course.", ex);
        }
    }

    @Override
    public List<Project> findAll() {
        try (Connection conn = sql2o.open()) {
            String sql = "SELECT * FROM Projects;";
            return conn.createQuery(sql).executeAndFetch(Project.class);
        }
    }
}
