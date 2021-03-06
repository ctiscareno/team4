/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.data;

import com.google.appengine.api.datastore.DatastoreService;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.google.appengine.api.datastore.FetchOptions;

/*import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets; 
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.nio.file.Paths; */


/** Provides access to the data stored in Datastore. */
public class Datastore {
	private DatastoreService datastore;

	public Datastore() {
		datastore = DatastoreServiceFactory.getDatastoreService();
	}


	/** Stores the Message in Datastore. */
	public void storeMessage(Message message) {
		Entity messageEntity = new Entity("Message", message.getId().toString());
		messageEntity.setProperty("user", message.getUser());
		messageEntity.setProperty("text", message.getText());
		messageEntity.setProperty("timestamp", message.getTimestamp());
		messageEntity.setProperty("recipient", message.getRecipient());
		if(message.getImageUrl() != null) {
			messageEntity.setProperty("imageUrl", message.getImageUrl());
		}

		datastore.put(messageEntity);
	}

	/**
	 * Gets messages posted by a specific user.
	 *
	 * @return a list of messages posted by the user, or empty list if user has never posted a
	 *     message. List is sorted by time descending.
	 */
	public List<Message> getMessages(String user) {
		if(user == null){
			return getAllMessages();
		}
		List<Message> messages = new ArrayList<>();

		Query query =
				new Query("Message")
				.setFilter(new Query.FilterPredicate("user", FilterOperator.EQUAL, user))
				.addSort("timestamp", SortDirection.DESCENDING);
		PreparedQuery results = datastore.prepare(query);


		for (Entity entity : results.asIterable()) {
			try {
				String idString = entity.getKey().getName();
				UUID id = UUID.fromString(idString);
				//String user = (String) entity.getProperty("user");
				String text = (String) entity.getProperty("text");
				long timestamp = (long) entity.getProperty("timestamp");
				String imageUrl = (String) entity.getProperty("imageUrl");
				Message message = new Message(id, user, text, timestamp, imageUrl);
				messages.add(message);
			} catch (Exception e) {
				System.err.println("Error reading message.");
				System.err.println(entity.toString());
				e.printStackTrace();
			}
		}

		return messages;
	}
	public List<Message> getAllMessages(){
		List<Message> messages = new ArrayList<>();

		Query query = new Query("Message")
				.addSort("timestamp", SortDirection.DESCENDING);
		PreparedQuery results = datastore.prepare(query);

		for (Entity entity : results.asIterable()) {
			try {
				String idString = entity.getKey().getName();
				UUID id = UUID.fromString(idString);
				String user = (String) entity.getProperty("user");
				String text = (String) entity.getProperty("text");
				long timestamp = (long) entity.getProperty("timestamp");
				String imageUrl = (String) entity.getProperty("imageUrl");
				Message message = new Message(id, user, text, timestamp, imageUrl);
				messages.add(message);
			} catch (Exception e) {
				System.err.println("Error reading message.");
				System.err.println(entity.toString());
				e.printStackTrace();
			}
		}
		return messages;
	}

	/** Stores the User in Datastore. */
	public void storeUser(User user) {
		Entity userEntity = new Entity("User", user.getEmail());
		userEntity.setProperty("email", user.getEmail());
		userEntity.setProperty("aboutMe", user.getAboutMe());
		datastore.put(userEntity);
	}

	/**
	 * Returns the User owned by the email address, or null if no matching User was found.
	 */
	public User getUser(String email) {

		Query query = new Query("User").setFilter(new Query.FilterPredicate("email", FilterOperator.EQUAL, email));
		PreparedQuery results = datastore.prepare(query);
		Entity userEntity = results.asSingleEntity();
		if(userEntity == null) {
			return null;
		}

		String aboutMe = (String) userEntity.getProperty("aboutMe");
		User user = new User(email, aboutMe);

		return user;
	}

	/** Returns the total number of messages for all users. */
	public int getTotalMessageCount(){
		Query query = new Query("Message");
		PreparedQuery results = datastore.prepare(query);
		System.out.print(results.countEntities(FetchOptions.Builder.withLimit(1000)));
		return results.countEntities(FetchOptions.Builder.withLimit(1000));
	}

	/** Stores the Migrant in Datastore. */
	public void storeMigrant(Migrant mig) {
		Entity migEntity = new Entity("Migrant", mig.getId());
		migEntity.setProperty("id", mig.getId());
		migEntity.setProperty("cause_of_death", mig.getCause_of_death());
		migEntity.setProperty("region_origin", mig.getRegion_origin());
		migEntity.setProperty("numDead", mig.getDead());
		migEntity.setProperty("incident_region", mig.getIncident_region());
		migEntity.setProperty("date", mig.getDate());
		migEntity.setProperty("latitude", mig.getLatitude());
		migEntity.setProperty("longitude", mig.getLongitude());
		datastore.put(migEntity);
	}

	/**
	 * Returns the Migrant owned by the email id, or null if no matching Migrant was found.
	 */
	public Migrant getMigrant(int id) {

		Query query = new Query("Migrant").setFilter(new Query.FilterPredicate("id", FilterOperator.EQUAL, id));
		PreparedQuery results = datastore.prepare(query);
		Entity migEntity = results.asSingleEntity();
		if(migEntity == null) {
			return null;
		}

		String cause_of_death = (String) migEntity.getProperty("cause_of_death");
		String region_origin = (String) migEntity.getProperty("region_origin");;
		int numDead = (Integer) migEntity.getProperty("numDead");
		String incident_region = (String) migEntity.getProperty("incident_region");;
		String date = (String) migEntity.getProperty("date");;
		double latitude = (Double) migEntity.getProperty("latitude");
		double longitude = (Double) migEntity.getProperty("longitude");

		Migrant migrant = new Migrant(id, cause_of_death, region_origin, numDead,
				incident_region, date, latitude, longitude);

		return migrant;
	}

	public List<Migrant> getAllMigrants(){
		List<Migrant> listMig = new ArrayList<>();

		Query query = new Query("Migrant")
				.addSort("id", SortDirection.ASCENDING);
		PreparedQuery results = datastore.prepare(query);

		for (Entity migEntity : results.asIterable()) {
			try {
				int id = (Integer) migEntity.getProperty("id");
				String cause_of_death = (String) migEntity.getProperty("cause_of_death");
				String region_origin = (String) migEntity.getProperty("region_origin");;
				int numDead = (Integer) migEntity.getProperty("numDead");
				String incident_region = (String) migEntity.getProperty("incident_region");;
				String date = (String) migEntity.getProperty("date");;
				double latitude = (Double) migEntity.getProperty("latitude");
				double longitude = (Double) migEntity.getProperty("longitude");

				Migrant migrant = new Migrant(id, cause_of_death, region_origin, numDead,
						incident_region, date, latitude, longitude);
				listMig.add(migrant);
			} catch (Exception e) {
				System.err.println("Error reading message.");
				System.err.println(migEntity.toString());
				e.printStackTrace();
			}
		}
		return listMig;
	}

	/* Used to store the map markers information the user adds */
	public List<UserMarker> getMarkers() {
		List<UserMarker> markers = new ArrayList<>();

		Query query = new Query("UserMarker");
		PreparedQuery results = datastore.prepare(query);

		for (Entity entity : results.asIterable()) {
			try {
				double lat = (double) entity.getProperty("lat");
				double lng = (double) entity.getProperty("lng");    
				String content = (String) entity.getProperty("content");
				String cause_of_death = (String) entity.getProperty("cause_of_death");
				String origin = (String) entity.getProperty("origin");;
				int numDead = (Integer) entity.getProperty("numDead");
				String incident_region = (String) entity.getProperty("incident_region");;
				String date = (String) entity.getProperty("date");

				UserMarker marker = new UserMarker(lat, lng, content, cause_of_death, origin, numDead, incident_region, date);
				markers.add(marker);
			} catch (Exception e) {
				System.err.println("Error reading marker.");
				System.err.println(entity.toString());
				e.printStackTrace();
			}
		}
		return markers;
	}

	public void storeMarker(UserMarker marker) {
		Entity markerEntity = new Entity("UserMarker");
		markerEntity.setProperty("lat", marker.getLat());
		markerEntity.setProperty("lng", marker.getLng());
		markerEntity.setProperty("content", marker.getContent());
		datastore.put(markerEntity);
	}
}
