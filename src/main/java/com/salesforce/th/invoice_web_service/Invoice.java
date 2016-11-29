package com.salesforce.th.invoice_web_service;

/**
 * Created by ubuntu on 29/11/16.
 */
import java.util.UUID;

/*
 invoiceRef: uuid.v4(), <--- generate a uuid
 projectId: args.projectId,
 billAmount: args.billAmount,
billDate: now().format('YYYY/MM/DD')
 */

public class Invoice {
    public String invoiceId = UUID.randomUUID().toString();
    public String projectId;
    public double billAmount;
    public String billDate;

}