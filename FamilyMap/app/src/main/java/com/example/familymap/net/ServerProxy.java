package com.example.familymap.net;

import android.os.AsyncTask;

import com.example.familymap.shared.model.Event;
import com.example.familymap.shared.model.Person;
import com.example.familymap.shared.requests.LoginRequest;
import com.example.familymap.shared.requests.PersonRequest;
import com.example.familymap.shared.requests.RegisterRequest;
import com.example.familymap.shared.results.EventsResult;
import com.example.familymap.shared.results.LoginResult;
import com.example.familymap.shared.results.PersonsResult;
import com.example.familymap.shared.results.RegisterResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerProxy {

    //The IP address or domain name of the machine running the server
    public static String serverHostName = "10.0.2.2"; //"DESKTOP-AMUPG0N";

    //The port number on which the server is accepting client connection
    public static int serverPortNumber = 8080;


    public static LoginResult login(LoginRequest r) {
        LoginResult result = new LoginResult();
        //Serialize request as JSON string
        //Make HTTP request to server
        //Deserialze response body to LoginResult object

        try {
            Gson gson = new Gson();

            URL url = new URL("http://" + serverHostName + ":" + serverPortNumber + "/user/login");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");

            http.setDoOutput(true);

            http.addRequestProperty("Accept", "application/json");

            //http.addRequestProperty("Authorization", "afj232hj2332");

            http.connect();

            String reqData =
                    "{" +
                            "\"userName\": " + r.userName + ", " +
                            "\"password\": " + r.password +
                            "}";

            OutputStream reqBody = http.getOutputStream();

            writeString(reqData, reqBody);

            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                System.out.println(respData);

                result = gson.fromJson(respData, LoginResult.class);
                //TODO: convert json to result object

                System.out.print('\n' + "converted obj is  :  " + result.authToken + result.personID + result.userName + '\n');
                respBody.close();
            } else {
                System.out.println("Error:" + http.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Person getPersonById(String personID, String AuthToken) {
        Person person = null;
        Gson gson = new Gson();
        try {
            URL url = new URL("http://" + serverHostName + ":" + serverPortNumber + "/person/" + personID);

            HttpURLConnection httpp = (HttpURLConnection) url.openConnection();

            httpp.setRequestMethod("GET");

            httpp.setDoOutput(false);

            httpp.addRequestProperty("Accept", "application/json");

            httpp.addRequestProperty("Authorization", AuthToken);

            httpp.connect();

            if (httpp.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = httpp.getInputStream();
                String respData = readString(respBody);
                System.out.println(respData);
                person = gson.fromJson(respData, Person.class);
                //TODO: convert json to person object
            } else {
                System.out.println("Error:" + httpp.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return person;
    }

    public RegisterResult register(RegisterRequest r) {
        RegisterResult result = new RegisterResult();
        try {
            Gson gson = new Gson();

            URL url = new URL("http://" + serverHostName + ":" + serverPortNumber + "/user/register");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");

            http.setDoOutput(true);

            http.addRequestProperty("Accept", "application/json");

            //http.addRequestProperty("Authorization", "afj232hj2332");

            http.connect();

            String reqData =
                    "{" +
                            "\"userName\": " + r.userName + ", " +
                            "\"password\": " + r.password + ", " +
                            "\"email\": " + r.email + ", " +
                            "\"firstName\": " + r.firstName + ", " +
                            "\"lastName\": " + r.lastName + ", " +
                            "\"gender\": " + r.gender +
                            "}";

            OutputStream reqBody = http.getOutputStream();

            writeString(reqData, reqBody);

            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                System.out.println(respData);

                result = gson.fromJson(respData, RegisterResult.class);
                //TODO: convert json to result object

                System.out.print('\n' + "converted Register obj is  :  " + result.authToken + result.personID + result.userName + '\n');
                respBody.close();
            } else {
                System.out.println("Error:" + http.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Person[] getFamily(String authToken) throws IOException {
        Gson gson = new Gson();
        PersonsResult persons = new PersonsResult();

        try {
            URL url = new URL("http://" + serverHostName + ":" + serverPortNumber + "/person");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");

            http.setDoOutput(false);

            http.addRequestProperty("Accept", "application/json");

            http.addRequestProperty("Authorization", authToken);

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                System.out.println(respData);
                persons = gson.fromJson(respData, PersonsResult.class);
                return persons.data;
            } else {
                System.out.println("Error:" + http.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return persons.data;
    }

    public Event[] getEvents(String authToken) throws IOException {
        Gson gson = new Gson();
        EventsResult events = new EventsResult();

        try {
            URL url = new URL("http://" + serverHostName + ":" + serverPortNumber + "/event");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");

            http.setDoOutput(false);

            http.addRequestProperty("Accept", "application/json");

            http.addRequestProperty("Authorization", authToken);

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                System.out.println(respData);
                events = gson.fromJson(respData, EventsResult.class);
                return events.data;
            } else {
                System.out.println("Error:" + http.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events.data;
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    //get all people

    //get all events
}
