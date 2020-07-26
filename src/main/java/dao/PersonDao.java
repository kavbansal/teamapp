package dao;

import exception.DaoException;
import model.Person;

import java.util.List;

public interface PersonDao {
    void add(Person person) throws DaoException;
    List<Person> findAll();
    List<Person> findPersonByEmail(String email);
}
