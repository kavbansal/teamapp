package dao;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnirestProjectDao implements ProjectDao {

    private static Gson gson = new Gson();;
    public final String BASE_URL = "http://127.0.0.1:7000/";

    @Override
    public void add(Project project) {
        try {
            Unirest.post(BASE_URL + "/projects").body(gson.toJson(project)).asJson();
        } catch (UnirestException e) {
            // TODO deal with errors
            e.printStackTrace();
        }
    }

    @Override
    public List<Project> findAll() {
        try {
            HttpResponse<JsonNode> jsonResponse =
                    Unirest.get(BASE_URL + "/projects").asJson();
            Project[] projects = gson.fromJson(jsonResponse.getBody().toString(), Project[].class);
            return new ArrayList<>(Arrays.asList(projects));
        } catch (UnirestException e) {
            // TODO deal with errors
            e.printStackTrace();
        }
        return null;
    }
}
