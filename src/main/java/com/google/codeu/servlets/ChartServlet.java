package com.google.codeu.servlets;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.codeu.data.Datastore;
import java.util.List;
import java.util.Scanner;

import com.google.codeu.data.Message;
import com.google.codeu.data.Migrant;
import com.google.codeu.data.TypeDeaths;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * Handles fetching site statistics.
 */
@WebServlet("/pieChart")
public class ChartServlet extends HttpServlet{
	
	JsonArray migrantArray;
	private Datastore datastore;

	@Override
	public void init() {
		datastore = new Datastore();
		migrantArray = new JsonArray();
		Gson gson = new Gson();
		Scanner scanner = new Scanner(getServletContext().getResourceAsStream("/WEB-INF/dataset.csv"));
		int [] waysToDie = new int[5];
		TypeDeaths deathObject;
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] cells = line.split(",");

			// for (String cell : cells) {
			//  System.out.println(cell);
			// }
			if(cells[1].equals("Presumed drowning") || cells[1].equals("Drowning") || cells[1].equals("drowning") || cells[1].equals("Drowning, Boat collided with ferry")) {
				waysToDie[0]++;
			}
			else if(cells[1].equals("Fell from train") || cells[1].equals("Vehicle Accident") || cells[1].equals("Hit by Train") || cells[1].equals("Hit by Vehicle") || cells[1].equals("Train accident") || cells[1].equals("Burned to death hiding in truck")) {
				waysToDie[1]++;
			}
			else if(cells[1].equals("Exposure") || cells[1].equals("Died upon entry to refugee camp") || cells[1].equals("Hypothermia") || cells[1].equals("Severe exhaustion and dehydration") || cells[1].equals("Starvation") || cells[1].equals("Hypothermia starvation")) {
				waysToDie[2]++;
			}
			else if(cells[1].equals("Suffocation") || cells[1].equals("Shot") || cells[1].equals("Electrocution") || cells[1].equals("Unknown torture involved") || cells[1].equals("Beating/shot by traffickers") || cells[1].equals("Killed") || cells[1].equals("Homicide likely by asphixiation") || cells[1].equals("On board violence") || cells[1].equals("Clubbed/beaten to death") || cells[1].equals("Asphixiation")) {
				waysToDie[3]++;
			}
			else {
				waysToDie[4]++;
			}
		}
		deathObject = new TypeDeaths(waysToDie[0], waysToDie[1], waysToDie[2], waysToDie[3], waysToDie[4]);
		migrantArray.add(gson.toJsonTree(deathObject));

	}

	/**
	 * Responds with site statistics in JSON.
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.getOutputStream().println(migrantArray.toString());
	}
}