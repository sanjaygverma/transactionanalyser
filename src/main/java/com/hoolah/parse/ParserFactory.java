package com.hoolah.parse;

import static com.hoolah.Constants.COMMA_DELIMITER;
import static com.hoolah.Constants.CSV;

public class ParserFactory {

    public static CSVFileParser getParser(String type, String delimiter) {

        CSVFileParser csvFileParser = null;
        switch (type)
        {
            case CSV:
                csvFileParser = new TransactionsCSVParser(delimiter);
            break;
            // Have different parses

            default:
                throw new TypeNotPresentException(type, new Exception("**Not valid type**"));
        }
        return csvFileParser;
    }
}
