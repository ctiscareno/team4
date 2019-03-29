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
    //private List<Migrant> migs = readBooksFromCSV("dataset.txt");

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


        datastore.put(messageEntity);
    }

    /**
     * Gets messages posted by a specific user.
     *
     * @return a list of messages posted by the user, or empty list if user has never posted a
     *     message. List is sorted by time descending.
     */
    public List<Message> getMessages(String user) {
    	if(user == null) {
        	return getAllMessages(); //if user null then return all Messages
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
                String recipient = (String) entity.getProperty("recipient");

                Message message = new Message(id, user, text, timestamp, recipient);
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
                String recipient = (String) entity.getProperty("recipient");

                Message message = new Message(id, user, text, timestamp, recipient);
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
        migEntity.setProperty("affected_nationality", mig.getAffected_nationality());
        migEntity.setProperty("missing", mig.getMissing());
        migEntity.setProperty("dead", mig.getDead());
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
        int affected_nationality = (Integer) migEntity.getProperty("affected_nationality");
        int missing = (Integer) migEntity.getProperty("missing");
        int dead = (Integer) migEntity.getProperty("dead");
        String incident_region = (String) migEntity.getProperty("incident_region");;
        String date = (String) migEntity.getProperty("date");;
        double latitude = (Double) migEntity.getProperty("latitude");
        double longitude = (Double) migEntity.getProperty("longitude");
        
        Migrant migrant = new Migrant(id, cause_of_death, region_origin, affected_nationality, missing, dead,
    			 incident_region, date, latitude, longitude);

        return migrant;
    }
    
    /** * Simple Java program to read CSV file in Java. In this program we will read 
     * * list of books stored in CSV file as comma separated values. * 
     * * @author WINDOWS 8 * */
    /*public class CSVReaderInJava {
    	public static void main(String... args) {
    		List<Book> books = readBooksFromCSV("books.txt");
    		//let's print all the person read from CSV file 
    		for (Book b : books) {
    			System.out.println(b); }
    		} 
    	private static List<Book> readBooksFromCSV(String fileName) {
    		List<Book> books = new ArrayList<>(); Path pathToFile = Paths.get(fileName); 
    		// create an instance of BufferedReader 
    		// using try with resource, Java 7 feature to close resources 
    		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) { 
    			// read the first line from the text file String line = br.readLine(); 
    			// loop until all lines are read while (line != null) { 
    			// use string.split to load a string array with the values from 
    			// each line of 
    			// the file, using a comma as the delimiter 
    			String[] attributes = line.split(",");
    			Book book = createBook(attributes); 
    			// adding book into ArrayList books.add(book); 
    			// read next line before looping 
    			// if end of file reached, line would be null line = br.readLine(); } }
    			catch (IOException ioe) {
    				ioe.printStackTrace(); 
    				}
    			return books; 
    			} 
    		private static Book createBook(String[] metadata) {
    			String name = metadata[0]; int price = Integer.parseInt(metadata[1]);
    			String author = metadata[2]; 
    			// create and return book of this metadata 
    			return new Book(name, price, author); } 
    		}
    		}
    	}
    } */

}