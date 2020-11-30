package com.hoolah.parse;

import com.hoolah.process.Transaction.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import static com.hoolah.Constants.*;
import static com.hoolah.utils.Utils.parseDateTime;
import static java.util.stream.Collectors.toList;

public class TransactionsCSVParser implements CSVFileParser{

    public static String delimiter=null;

    public TransactionsCSVParser(String delim)
    {
        delimiter = delim;
    }


    @Override
    public List<Transaction> parse(String file) throws IOException {

        List<CSVRecord> fileRecords = null;
        try (BufferedReader in = Files.newBufferedReader(Paths.get(file))) {
            switch(delimiter)
            {
                case COMMA_DELIMITER:
                default:
                    fileRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in).getRecords(); // supports , delim can be enhanced with delim switch case

            }

        }
        List<Transaction> lst= fileRecords.stream().parallel().map(TransactionsCSVParser::convertToTransaction)
                .collect(toList());
        List<String> lstRvsl = lst.stream().filter(t-> t.getType().equals(REVSL)).map(t -> t.getRelatedTxId())
                .collect(toList());
        lst = lst.stream().filter(tx -> !(lstRvsl.contains(tx.getId()) || lstRvsl.contains(tx.getRelatedTxId())))
                .collect(toList());
        return lst;
    }


    private static Transaction convertToTransaction(CSVRecord csvRecord)
    {
        String id = csvRecord.get(ID);
        LocalDateTime txnTime = parseDateTime(csvRecord.get(DATE_TIME));
        Double amount = Double.parseDouble(csvRecord.get(AMOUNT));
        String merchant = csvRecord.get(MERCHANT);
        String type = csvRecord.get(TYPE);
        String relatedTxn = csvRecord.get(RELATED_TXN);
        return new Transaction(id,txnTime,amount,merchant,type,relatedTxn);
    }


}
