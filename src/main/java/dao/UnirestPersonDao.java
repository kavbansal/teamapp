package dao;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnirestPersonDao implements PersonDao {

    private static Gson gson = new Gson();;
    public final String BASE_URL = "http://127.0.0.1:7000/";

    @Override
    public void add(Person person) {
        try {
            Unirest.post(BASE_URL + "/person").body(gson.toJson(person)).asJson();
        } catch (UnirestException e) {
            // TODO deal with errors
            e.printStackTrace();
        }
    }

    @Override
    public List<Person> findAll() {
        try {
            HttpResponse<JsonNode> jsonResponse =
                    Unirest.get(BASE_URL + "/person").asJson();
            Person[] people = gson.fromJson(jsonResponse.getBody().toString(), Person[].class);
            return new ArrayList<>(Arrays.asList(people));
        } catch (UnirestException e) {
            // TODO deal with errors
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Person> findPersonByEmail(String email) {
        final String URL = BASE_URL + "Person" + "/" + email;
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(URL).asJson();
            Person[] people = gson.fromJson(jsonResponse.getBody().toString(),Person[].class);
            return new ArrayList<>(Arrays.asList(people));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }
}