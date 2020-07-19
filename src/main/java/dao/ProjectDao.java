package dao;

import exception.DaoException;
import model.Project;

import java.util.List;


public interface ProjectDao {
    void add(Project project) throws DaoException;
    List<Project> findAll();
}
