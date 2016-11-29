package com.salesforce.th;

import com.salesforce.th.invoice_web_service.BillProjectResponse;
import com.salesforce.th.invoice_web_service.BillProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.salesforce.th.invoice_web_service.Invoice;
import com.salesforce.th.invoice_web_service.Project;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

@Endpoint
public class InvoiceEndpoint {
	private static final String NAMESPACE_URI = "http://salesforce.com/th/invoice-web-service";

	private InvoicesRepository invoicesRepository;

	@Autowired
	public InvoiceEndpoint(InvoicesRepository invoicesRepository) {
		this.invoicesRepository = invoicesRepository;
	}

	//If the username is not "bsUser1" and the password is not "bsPass1"
	// it should return the string "unauthorized". If any of the 4 parameters are null,
	// it should return the string "bad request".


	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "billProjectRequest")
	@ResponsePayload
	public BillProjectResponse billProject(@RequestPayload BillProjectRequest request) {
		BillProjectResponse response = new BillProjectResponse();
		Project project = request.getProject();
		if(project.getUsername() == null ||
				project.getPassword() == null ||
				project.getProjectid() == null || project.getBillAmount() == 0.0){
			response.setStatus("unauthorized");
		}else {
			if(project.getUsername().equals("bsUser1") && project.getPassword().equals("bsPass1")){
				String projectId = project.getProjectid();
				Invoice inv = invoicesRepository.findInvoice(projectId);
				if(inv == null){
					response.setStatus("created");
					Invoice invoiceNew = new Invoice();
					invoiceNew.invoiceId = UUID.randomUUID().toString();
					invoiceNew.billAmount = project.getBillAmount();
					invoiceNew.billDate = convertMilliSecondsToFormattedDate(System.currentTimeMillis());
					invoiceNew.projectId = projectId;
					invoicesRepository.addInvoice(invoiceNew);
				}else {
					Invoice invoiceOld = invoicesRepository.findInvoice(projectId);
					Invoice invoiceNew = new Invoice();
					invoiceNew.invoiceId = invoiceOld.invoiceId;
					invoiceNew.billAmount = project.getBillAmount();
					invoiceNew.billDate = invoiceOld.billDate;
					invoiceNew.projectId = projectId;
					invoicesRepository.updateInvoice(invoiceNew);
					response.setStatus("updated");
				}

			}else {
				response.setStatus("unauthorized");
			}
		}
		return response;

	}
	public static String dateFormat = "yyyy-MM-dd-hh:mm:ss-z";
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

	public static String convertMilliSecondsToFormattedDate(long milliSeconds){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return simpleDateFormat.format(calendar.getTime());
	}
}
