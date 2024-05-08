package MongoDB;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;

public class ConnectToDB {
	public static void main( String args[] ) { 
	 
	 // Creating a Mongo client 
	 MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
	 
	 // Creating Credentials 
	 MongoCredential credential; 
	 credential = MongoCredential.createCredential("sampleUser","myDb","password".toCharArray()); 
	 System.out.println("Connected to the database successfully"); 
	 
	 // Accessing the database 
	 MongoDatabase database = mongo.getDatabase("myDb"); 
	 System.out.println("Credentials ::"+ credential);
	 
	 //Creating a collection 
	 database.createCollection("sampleCollection"); 
	 System.out.println("Collection created successfully");
	 
	 // Retrieving a collection
	 MongoCollection<Document> collection = 
	database.getCollection("sampleCollection");
	 System.out.println("Collection sampleCollection selected successfully");
	 /*Document document = new Document("title", "MongoDB")
	 .append("description", "database")
	 .append("likes", 100)
	 .append("url", 
	"http://www.tutorialspoint.com/mongodb/")
	 .append("by", "tutorials point");
	 
	 //Inserting document into the collection
	 collection.insertOne(document);
	 System.out.println("Document inserted successfully");*/
	 
	 Document document1 = new Document("title", "MongoDB")
	 .append("description", "database")
	 .append("likes", 100)
	 .append("url", 
	"http://www.tutorialspoint.com/mongodb/")
	 .append("by", "tutorials point");
	 Document document2 = new Document("title", "RethinkDB")
	 .append("description", "database")
	 .append("likes", 200)
	 .append("url", 
	"http://www.tutorialspoint.com/rethinkdb/")
	 .append("by", "tutorials point");
	 List<Document> list = new ArrayList<Document>();
	 list.add(document1);
	 list.add(document2);
	 collection.insertMany(list);
	 // Getting the iterable object
	 FindIterable<Document> iterDoc = collection.find();
	 int i = 1;
	 // Getting the iterator
	 Iterator it = iterDoc.iterator();
	 while (it.hasNext()) {
	 System.out.println(it.next());
	 i++;
	 }
	 
	// Deleting the documents 
	 collection.deleteOne(Filters.eq("title", "MongoDB")); 
	 System.out.println("\nDocument deleted successfully..."); 
	 
	 // Retrieving the documents after updation
	 // Getting the iterable object 
	 FindIterable<Document> iterDoc1 = collection.find(); 
	 int j = 1; 
	 // Getting the iterator 
	 Iterator it1 = iterDoc1.iterator(); 
	 while (it1.hasNext()) { 
	 System.out.println(it1.next()); 
	 j++; 
	 }
	 //Listing All the Collections
	 System.out.println("\nCollections List:\n");
	 for (String name : database.listCollectionNames()) { 
	 System.out.println(name); 
	 } 
	// Dropping a Collection 
	collection.drop(); 
	System.out.println("Collection dropped successfully");
	 
	}  
}
