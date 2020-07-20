package model;

public class Person {
    private String name;
    private String email;
    private int id;
    private String password;
    private int uniId;
    private int year;

    public Person(String name, String email, String password, int uniId, int year) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.uniId = uniId;
        this.year = year;
    }

    public int getUniId() {
        return uniId;
    }

    public void setUniId(int uniId) {
        this.uniId = uniId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
