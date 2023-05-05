package com.baloot.service;

import com.baloot.exception.InValidInputException;
import com.baloot.exception.ProviderNotFoundException;
import com.baloot.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

public class BalootSystem {
    private DataBase db = new DataBase();
    private static BalootSystem instance;
    private BalootSystem() {}
    public static BalootSystem getInstance() {
        if(instance == null) {
            instance = new BalootSystem();
            instance.readDataFromServer();
        }
        return instance;
    }
    public DataBase getDataBase(){
        return db;
    }
    public User getLoggedInUser() throws InValidInputException {
        return db.getLoggedInUser();
    }
    public void readDataFromServer() {
        try {
            System.out.println("Importing data ...");
            importUsers();
            importProviders();
            importCommodities();
            importComments();
            importDiscounts();
            System.out.println("Done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void importDiscounts() throws Exception{
        String strJsonData = HTTPRequestHandler("http://5.253.25.110:5000/api/discount");
        ObjectMapper mapper = new ObjectMapper();
        List<Discount> discounts = mapper.readValue(strJsonData, new TypeReference<List<Discount>>(){});
        db.addDiscount(discounts);
    }

    private void importComments() throws Exception{
        String strJsonData = HTTPRequestHandler("http://5.253.25.110:5000/api/comments");
        ObjectMapper mapper = new ObjectMapper();
        List<Comment> comments = mapper.readValue(strJsonData, new TypeReference<List<Comment>>(){});
        db.addComment(comments);
    }

    private void importCommodities() throws Exception{
        String strJsonData = HTTPRequestHandler("http://5.253.25.110:5000/api/v2/commodities");
        ObjectMapper mapper = new ObjectMapper();
        List<Commodity> commodities = mapper.readValue(strJsonData, new TypeReference<List<Commodity>>(){});
        db.addCommodity(commodities);
    }

    private void importProviders() throws Exception{
        String strJsonData = HTTPRequestHandler("http://5.253.25.110:5000/api/v2/providers");
        ObjectMapper mapper = new ObjectMapper();
        List<Provider> providers = mapper.readValue(strJsonData, new TypeReference<List<Provider>>(){});
        db.addProvider(providers);
    }

    private void importUsers() throws Exception {
        String strJsonData = HTTPRequestHandler("http://5.253.25.110:5000/api/users");
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(strJsonData, new TypeReference<List<User>>(){});
        db.addUser(users);
    }

    private String HTTPRequestHandler(String users_url) throws URISyntaxException, IOException {
        URL url =  new URI(users_url).toURL();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        int status = urlConnection.getResponseCode();
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder lines = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            lines.append(line);
        }
        String strJsonData = lines.toString();
        bufferedReader.close();
        return strJsonData;
    }
    public Boolean hasAnyUserLoggedIn(){
        return db.hasAnyUserLoggedIn();
    }

    public Boolean isUserValid(String username) throws Exception{
        return db.getUserById(username) != null;
    }
    public void loginInUser(String username, String password) throws Exception{
        db.setLoggedInUser(username, password);
    }

    public void logOutUser() throws Exception {db.logOutUser();}

    public void increaseCredit(int credit){db.increaseCredit(credit);}
    public Provider getProvider(int id) throws ProviderNotFoundException { return db.getProviderById(id); }
}
