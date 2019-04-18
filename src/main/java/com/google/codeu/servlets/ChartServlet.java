package com.google.codeu.servlets;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.codeu.data.Datastore;
import java.util.List;
import com.google.codeu.data.Message;
import com.google.codeu.data.Migrant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * Handles fetching site statistics.
 */
@WebServlet("/allChart")
public class ChartServlet extends HttpServlet{
	
	JsonArray migrantArray;
	private Datastore datastore;

	@Override
	public void init() {
		datastore = new Datastore();
		migrantArray = new JsonArray();
		Gson gson = new Gson();
		List<Migrant> migList = datastore.getAllMigrants(); 
		
		for(Migrant temp: migList) {
			migrantArray.add(gson.toJsonTree(temp));
		}
	}

	/**
	 * Responds with site statistics in JSON.
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.getOutputStream().println(migrantArray.toString());
		/*response.setContentType("application/json");
		List<Migrant> migList = datastore.getAllMigrants(); 
		Gson gson = new Gson();
		String json = gson.toJson(migList);
		response.getWriter().println(json); */
	}
}