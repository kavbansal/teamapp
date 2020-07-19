package model;

import java.util.Objects;

public class Project {
    private String name;
    private String description;
    private int time_commit;
    private int num_needed;
    private int id;

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        time_commit = 0;
        num_needed = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTime_commit() {
        return time_commit;
    }

    public void setTime_commit(int time_commit) {
        this.time_commit = time_commit;
    }

    public int getNum_needed() {
        return num_needed;
    }

    public void setNum_needed(int num_needed) {
        this.num_needed = num_needed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return getTime_commit() == project.getTime_commit() &&
                getNum_needed() == project.getNum_needed() &&
                getName().equals(project.getName()) &&
                Objects.equals(getDescription(), project.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getTime_commit(), getNum_needed());
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
