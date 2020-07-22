package dao;

import exception.DaoException;
import model.Person;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

public class Sql2oPersonDao implements PersonDao {

    private final Sql2o sql2o;

    public Sql2oPersonDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Person person) throws DaoException {
        try (Connection conn = sql2o.open()) {
            String sql = "INSERT INTO People(name, email, password) VALUES (:name, :email, :password);";
            int id = (int) conn.createQuery(sql)
                    .bind(person)
                    .executeUpdate()
                    .getKey();
            person.setId(id);
        } catch (Sql2oException ex) {
            throw new DaoException("Unable to add a person.", ex);
        }
    }

    @Override
    public List<Person> findAll() {
        try (Connection conn = sql2o.open()) {
            String sql = "SELECT * FROM People;";
            return conn.createQuery(sql).executeAndFetch(Person.class);
        }
    }
}
