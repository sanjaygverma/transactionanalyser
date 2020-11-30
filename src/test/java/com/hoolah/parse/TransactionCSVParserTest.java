package com.hoolah.parse;

import com.hoolah.exception.parse.CSVFileParseException;
import com.hoolah.process.Transaction.Transaction;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.hoolah.Constants.COMMA_DELIMITER;
import static org.junit.jupiter.api.Assertions.*;

class TransactionCSVParserTest {



    public File createFile() throws IOException {
        File csvFile = new File("test.csv");
        List<String[]> records = new ArrayList<>();
        records.add(new String[]
                { "ID","Date","Amount","Merchant","Type","Related Transaction" });
        records.add(new String[]
                {  "WLMFRDGD","20/08/2018 12:45:33","59.99","Kwik-E-Mart","PAYMENT","",""});
        records.add(new String[]
                { "YGXKOEIA","20/08/2018 12:46:17","10.95","Kwik-E-Mart","PAYMENT","",""  });
        records.add(new String[]
                { "LFVCTEYM","20/08/2018 12:50:02","5.00","MacLaren","PAYMENT","",""  });
        records.add(new String[]
                { "SUOVOISP","20/08/2018 13:12:22","5.00","Kwik-E-Mart","PAYMENT","","" });
        records.add(new String[]
                { "AKNBVHMN","20/08/2018 13:14:11","10.95","Kwik-E-Mart","REVERSAL","YGXKOEIA" });
        records.add(new String[]
                { "JYAPKZFZ","20/08/2018 14:07:10","99.50","MacLaren","PAYMENT","","" });


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


    @Test
    public void parseFile() throws CSVFileParseException, IOException {
        File csvFile = createFile();
        assertTrue(csvFile.exists());
        CSVFileParser parser = new TransactionsCSVParser(COMMA_DELIMITER);
        if(csvFile.exists())
        {
            List<Transaction> lstCsvRecord = parser.parse(csvFile.getName());
            assertNotNull(lstCsvRecord);
            assertSame(lstCsvRecord.size(), 4);

            //assertNotNull(lstCsvRecord.get(0).get("ID"));
        }
    }

}