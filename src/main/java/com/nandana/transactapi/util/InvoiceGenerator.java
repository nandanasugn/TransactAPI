package com.nandana.transactapi.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class InvoiceGenerator {
    private static final String INVOICE_DATA = "invoice_data.txt";
    private static AtomicInteger counter;
    private static String lastGeneratedDate;

    static {
        loadInvoiceData();
    }

    public static String generateInvoice() {
        String currentDate = new SimpleDateFormat("ddMMyyyy").format(new Date());

        if (!currentDate.equals(lastGeneratedDate)) {
            counter.set(1);
            lastGeneratedDate = currentDate;
            saveInvoiceData();
        }

        String counterFormatted = String.format("%03d", counter.getAndIncrement());

        saveInvoiceData();

        return "INV" + currentDate + "-" + counterFormatted;
    }

    private static void loadInvoiceData() {
        try (BufferedReader br = new BufferedReader(new FileReader(INVOICE_DATA))) {
            lastGeneratedDate = br.readLine();
            counter = new AtomicInteger(Integer.parseInt(br.readLine()));
        } catch (IOException | NumberFormatException e) {
            lastGeneratedDate = new SimpleDateFormat("ddMMyyyy").format(new Date());
            counter = new AtomicInteger(1);
            saveInvoiceData();
        }
    }

    private static void saveInvoiceData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVOICE_DATA))) {
            bw.write(lastGeneratedDate + "\n");
            bw.write(counter.get() + "\n");
        } catch (IOException e) {
            log.error("failed to save invoice data");
        }
    }
}
