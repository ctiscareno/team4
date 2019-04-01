package com.google.codeu.servlets;

import java.io.IOException;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * Returns Migration data as a JSON array, e.g. [{"lat": 38.4404675, "lng": -122.7144313}]
 */
@WebServlet("/maps-part2")
public class MapsServlet extends HttpServlet {

  JsonArray migrantArray;

 @Override
 public void init() throws NullPointerException {
  migrantArray = new JsonArray();
  Gson gson = new Gson();
  Scanner scanner = new Scanner(getServletContext().getResourceAsStream("/WEB-INF/dataset.csv"));
  while(scanner.hasNextLine()) {
   String line = scanner.nextLine();
   String[] cells = line.split(",");

   // for (String cell : cells) {
   //  System.out.println(cell);
   // }

    int id = Integer.parseInt(cells[0]);
    String causeOfDeath = cells[1].equals("") ? " " : cells[1];
    String origin = cells[2].equals("") ? " " : cells[2];
    int numDead = cells[3].equals("") ? 0 : Integer.parseInt(cells[3]);
    String incidentRegion = cells[4].equals("") ? " " : cells[4];
    String date = cells[5].equals("") ? " " : cells[5];
    double lat = cells[6].equals("") ? 0 : Double.parseDouble(cells[6]);
    double lng = cells[7].equals("") ? 0 : Double.parseDouble(cells[7]);

   migrantArray.add(gson.toJsonTree(new MissingMigrant(id, causeOfDeath, origin, numDead, incidentRegion, date, lat, lng)));
  }
  scanner.close();
 }

 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  response.setContentType("application/json");
  response.getOutputStream().println(migrantArray.toString());
 }

 private static class MissingMigrant{
  int id;
  String causeOfDeath;
  String origin;
  int numDead;
  String incidentRegion;
  String date;
  double lat;
  double lng;

  private MissingMigrant(){
    id = 0;
    causeOfDeath = "";
    origin = "";
    numDead = 0;
    incidentRegion = "";
    date = "";
    lat = 0;
    lng = 0;

  }


  private MissingMigrant(int id, String causeOfDeath, String origin, int numDead,String incidentRegion,String date, double lat, double lng)throws NullPointerException {
  //add Java Error handling)
   this.id = id;
   this.causeOfDeath = causeOfDeath;
   this.origin = origin;
   this.numDead = numDead;
   this.incidentRegion = incidentRegion;
   this.date = date;
   this.lat = lat;
   this.lng = lng;
  }
 }
}