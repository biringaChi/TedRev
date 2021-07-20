package main.db;

import main.utils.Utils;

import java.io.IOException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class StudentDB extends Utils {

	public StudentDB(String DATA_DIR) {
		super(DATA_DIR);
	}
	
	public static MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
	public static MongoDatabase studentDB = client.getDatabase("studentDB");;
	public static MongoCollection<Document> studentCollection = studentDB.getCollection("studentCollection");; 

	public void createMongoDB() throws IOException {
		Document document = new Document("name", getStudentDetails().get("name"));
		document.append("title", getStudentDetails().get("title"));
		document.append("thesis", getPDF2());
		document.append("major", getStudentDetails().get("major"));
		document.append("degree", getStudentDetails().get("degree"));
		document.append("date", getStudentDetails().get("date"));
		studentCollection.insertOne(document);
	}	
}