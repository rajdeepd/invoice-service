package com.salesforce.th;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.salesforce.th.invoice_web_service.Invoice;
import org.bson.Document;

/**
 * Created by Rajdeep Dua on 1/12/16.
 */
public class TestInvoiceMongoRepository extends TestCase{
    InvoicesMongoRepository repository = new InvoicesMongoRepository();
    public void setup() {
        repository = new InvoicesMongoRepository();
        repository.init();
    }

    public void testInsertInvoice() {
        String invoiceId = "100";
        String projectId = "1";
        double billAmount = 111.11;
        long time = System.currentTimeMillis();
        String date = convertMilliSecondsToFormattedDate(time);
        String billDate = date;
        Invoice inv = new Invoice();
        inv.projectId = projectId;
        inv.invoiceId = invoiceId;
        inv.billAmount = billAmount;
        inv.billDate = billDate;

        repository.addInvoice(inv);
        Invoice invFound = repository.findInvoice(projectId);
        System.out.println(invFound.projectId);
        assertNotNull(invFound);
        long deleteCount = repository.deleteInvoice(projectId);
        assertTrue(deleteCount > 0);
    }

    public void testUpdate() {
        //public void updateInvoice(Invoice invoiceParam)
        String projectId = "1";
        try {
            String invoiceId = "100";

            double billAmount = 111.11;
            long time = System.currentTimeMillis();
            String date = convertMilliSecondsToFormattedDate(time);
            String billDate = date;
            Invoice inv = new Invoice();
            inv.projectId = projectId;
            inv.invoiceId = invoiceId;
            inv.billAmount = billAmount;
            inv.billDate = billDate;

            repository.addInvoice(inv);
            Invoice invFound = repository.findInvoice(projectId);
            //System.out.println(document.toJson());
            inv.billAmount = 222.22;
            repository.updateInvoice(inv);
            Invoice invFoundAfterUpdate = repository.findInvoice(projectId);
            Double d = (Double) invFoundAfterUpdate.billAmount;
            assertTrue(d == inv.billAmount);
        }finally {
            long deleteCount = repository.deleteInvoice(projectId);
            assertTrue(deleteCount > 0);
        }
    }

    public static String dateFormat = "yyyy-MM-dd'T'HH:mm:ss:XXX";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

    public static String convertMilliSecondsToFormattedDate(long milliSeconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }

}
