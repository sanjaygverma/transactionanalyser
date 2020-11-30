package com.hoolah.process;

import com.hoolah.parse.CSVFileParser;
import com.hoolah.parse.TransactionsCSVParser;
import com.hoolah.process.Transaction.Transaction;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.hoolah.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class TransactionsCSVProcessorTest {



    @Test
    void process() throws IOException {

        File f = createFile();
        CSVFileParser parser = new TransactionsCSVParser(COMMA_DELIMITER);
        if(f.exists()) {
            List<Transaction> lstTxnRecord = parser.parse(f.getName());
            BigDecimal maxTxnVal = TransactionsCSVProcessor.processTransactions(lstTxnRecord, MAX );
            assertNotNull(maxTxnVal);
            //assertEquals();


            //Scenario specific test
            String startDate = "20/08/2018 12:00:00";
            String endDate = "20/08/2018 13:00:00";
            DateTimeFormatter df =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            BigDecimal avgTxnVal = TransactionsCSVProcessor.processTransactions(LocalDateTime.parse(startDate, df),
                    LocalDateTime.parse(endDate, df), lstTxnRecord, AVG);
            assertNotNull(avgTxnVal);
            assertEquals(BigDecimal.valueOf(32.50).setScale(2, RoundingMode.HALF_UP), avgTxnVal);


            BigDecimal avgTxnValOfMerchant = TransactionsCSVProcessor.processTransactions(LocalDateTime.parse(startDate, df),
                    LocalDateTime.parse(endDate, df), lstTxnRecord, "Kwik-E-Mart", AVG);
            assertNotNull(avgTxnVal);
            assertEquals(BigDecimal.valueOf(59.99).setScale(2, RoundingMode.HALF_UP), avgTxnValOfMerchant);

        }

    }

    public File createFile() throws IOException {
        File csvFile = new File("test.csv");
        csvFile.delete();
        List<String[]> records = new ArrayList<>();
        records.add(new String[]
                { "ID","Date","Amount","Merchant","Type","Related Transaction" });
        records.add(new String[]
                {  "WLMFRDGD","20/08/2018 12:45:33","59.99","Kwik-E-Mart","PAYMENT",""});
        records.add(new String[]
                { "YGXKOEIA","20/08/2018 12:46:17","10.95","Kwik-E-Mart","PAYMENT",""  });
        records.add(new String[]
                { "LFVCTEYM","20/08/2018 12:50:02","5.00","MacLaren","PAYMENT",""  });
        records.add(new String[]
                { "SUOVOISP","20/08/2018 13:12:22","5.00","Kwik-E-Mart","PAYMENT","" });
        records.add(new String[]
                { "AKNBVHMN","20/08/2018 13:14:11","10.95","Kwik-E-Mart","REVERSAL","YGXKOEIA" });
        records.add(new String[]
                { "JYAPKZFZ","20/08/2018 14:07:10","99.50","MacLaren","PAYMENT",""});


        try (PrintWriter pw = new PrintWriter(csvFile)) {
            records.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }

        return csvFile;

    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }



}