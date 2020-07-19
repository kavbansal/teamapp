package dao;
import model.Project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryProjectDao implements ProjectDao {
    private List<Project> projectList;

    public InMemoryProjectDao() {
        projectList = new ArrayList<>();
    }

    @Override
    public void add(Project project) {
        projectList.add(project);
    }

    @Override
    public List<Project> findAll() {
        return Collections.unmodifiableList(projectList);
    }
}