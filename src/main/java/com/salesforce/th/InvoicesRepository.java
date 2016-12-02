package com.salesforce.th;

//import io.spring.guides.gs_producing_web_service.Country;
//import io.spring.guides.gs_producing_web_service.Currency;
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
	//private static final List<Country> countries = new ArrayList<Country>();
	private static final List<Invoice> invoices = new ArrayList<Invoice>();

	@PostConstruct
	public void initData() {
		/*Country spain = new Country();
		spain.setName("Spain");
		spain.setCapital("Madrid");
		spain.setCurrency(Currency.EUR);
		spain.setPopulation(46704314);

		countries.add(spain);

		Country poland = new Country();
		poland.setName("Poland");
		poland.setCapital("Warsaw");
		poland.setCurrency(Currency.PLN);
		poland.setPopulation(38186860);

		countries.add(poland);

		Country uk = new Country();
		uk.setName("United Kingdom");
		uk.setCapital("London");
		uk.setCurrency(Currency.GBP);
		uk.setPopulation(63705000);

		countries.add(uk);*/
	}

	/*public String addProject(Project project){
		String projectId = project.getProjectid();
		Project p = findProject(projectId);
		if( p == null){
			projects.add(project);
		}else {
			updateProject(project);
		}
		return projectId;
	}

	public void updateProject(Project project) {
		String projectId = project.getProjectid();

		Project result = null;
		int i = 0;
		for (Project p : projects) {
			if (projectId.equals(p.getProjectid())) {
				projects.remove(i);
				projects.add(project);
			}
		}
	}

	public Project findProject(String projectID) {
		Assert.notNull(projectID);

		Project result = null;

		for (Project project : projects) {
			if (projectID.equals(project.getProjectid())) {
				result = project;
			}
		}


		return result;
	}*/

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
		//Assert.notNull(projectId);
		Invoice result = null;

		for (Invoice inv : invoices) {
			if (projectId.equals(inv.projectId)) {
				result = inv;
			}
		}
		return result;
	}

	/*public Country findCountry(String name) {
		Assert.notNull(name);

		Country result = null;

		for (Country country : countries) {
			if (name.equals(country.getName())) {
				result = country;
			}
		}

		return result;
	}*/
}
