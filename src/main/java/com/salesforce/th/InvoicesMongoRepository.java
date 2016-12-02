package com.salesforce.th;


import com.salesforce.th.invoice_web_service.Invoice;
import com.salesforce.th.invoice_web_service.Project;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import com.mongodb.client.result.DeleteResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

@Component
public class InvoicesMongoRepository {
	/*
	username, string, 25
	password, string, 25
	projectId, string, 18
	billAmount, double
	 */
	public static Logger logger = Logger.getLogger(InvoicesMongoRepository.class.getName());
	static MongoDatabase db;
	public static String COLLECTION_NAME = "invoices";
	boolean initialized = false;
	public  MongoCollection<Document> collection = null;

	public  void init() {
		String textUri = System.getenv("MONGODB_URI");
		if(textUri == null){
			textUri = "mongodb://localhost:27017/test";
		}
		//String textUri = "mongodb://heroku_kp12tz7t:j6p7ome6plqo91bigdedh0ok7j@ds119618.mlab.com:19618/heroku_kp12tz7t";
		//String textUri = "mongodb://localhost:27017/test";
		MongoClientURI uri = new MongoClientURI(textUri);
		String dbName = uri.getDatabase();
		MongoClient mongoClient = new MongoClient(uri);

		db = mongoClient.getDatabase(dbName);
		collection = db.getCollection(COLLECTION_NAME);
		if(collection.count() == 0) {
			db.createCollection(COLLECTION_NAME);
		}
	}

	private static final List<Invoice> invoices = new ArrayList<Invoice>();

	@PostConstruct
	public void initData() {

	}

	public String addInvoice(Invoice invoice){
		if(!initialized) {
			init();
		}
		String invoiceId = invoice.invoiceId;
		String projectId = invoice.projectId;
		double billAmount = invoice.billAmount;
		String billDate = invoice.billDate;

		Document myDocOne = collection.find(Filters.eq("projectId", projectId)).first();
		if(myDocOne == null) {
			//invoices.add(invoice);
			Document invoiceLocal = new Document("projectId", projectId).
					append("billAmount", billAmount).
					append("invoiceId", invoiceId).
					append("billDate", billDate);
			collection.insertOne(invoiceLocal);
		}else {
			updateInvoice(invoice);
		}

		return invoiceId;
	}

	public void updateInvoice(Invoice invoiceParam) {
		if(!initialized) {
			init();
		}
		String projectId = invoiceParam.projectId;
		String invoiceId = invoiceParam.invoiceId;
		double billAmount = invoiceParam.billAmount;
		String billDate = invoiceParam.billDate;
		Document invoiceLocal = new Document("projectId", projectId).append("billAmount", billAmount).
				append("invoiceId", invoiceId).
				append("billDate", billDate);

		collection.updateOne(Filters.eq("projectId", projectId),
						new Document("$set", invoiceLocal));

	}


	public Invoice findInvoice(String projectId) {
		if(!initialized) {
			init();
		}
		Document myDocOne = collection.find(Filters.eq("projectId", projectId)).first();
		if(myDocOne == null) {
			return null;
		}else {
			String invoiceId = myDocOne.getString("invoiceId");
			String billDate = myDocOne.getString("billDate");
			Double billAmount = myDocOne.getDouble("billAmount");
			Invoice inv = new Invoice();
			inv.projectId = projectId;
			inv.invoiceId = invoiceId;
			inv.billAmount = billAmount;
			inv.billDate = billDate;
			return inv;
		}
	}

	public long deleteInvoice(String projectId) {
		DeleteResult result = collection.deleteOne(Filters.eq("projectId", projectId));
		return result.getDeletedCount();
	}
}
