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

/**
 * Handles fetching site statistics.
 */
@WebServlet("/searchMigrant")
public class SearchMigrantServlet extends HttpServlet{

	private Datastore datastore;
	//List<Migrant> migList;

	@Override
	public void init() {
		datastore = new Datastore();
	}

	/**
	 * Responds with site statistics in JSON.
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		String idString = request.getParameter("id");
		int id = Integer.parseInt(idString);
		Migrant mig = datastore.getMigrant(id);
		//migList.add(mig);
		Gson gson = new Gson();
		String json = gson.toJson(mig);
		response.getWriter().println(json);
	}

}