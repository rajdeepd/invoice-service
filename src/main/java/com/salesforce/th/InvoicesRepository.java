package com.salesforce.th;

import com.salesforce.th.invoice_web_service.Invoice;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.salesforce.th.invoice_web_service.Project;

@Component
public class InvoicesRepository {
	/*
	username, string, 25
	password, string, 25
	projectId, string, 18
	billAmount, double
	 */
	private static final List<Invoice> invoices = new ArrayList<Invoice>();

	@PostConstruct
	public void initData() {

	}


	public String addInvoice(Invoice invoice){
		String invoiceId = invoice.invoiceId;
		String projectId = invoice.projectId;
		Invoice invObj = findInvoice(invoiceId);
		if( invObj == null){
			invoices.add(invoice);
		}else {
			updateInvoice(invoice);
		}
		return invoiceId;
	}

	public void updateInvoice(Invoice invoiceParam) {
		String projectId = invoiceParam.projectId;

		Project result = null;
		int i = 0;
		for (Invoice invoice : invoices) {
			if (projectId.equals(invoice.projectId)) {
				invoices.remove(i);
				invoices.add(invoiceParam);
			}
		}
	}

	public Invoice findInvoice(String projectId) {
		Invoice result = null;

		for (Invoice inv : invoices) {
			if (projectId.equals(inv.projectId)) {
				result = inv;
			}
		}
		return result;
	}
}
