package com.hoolah.parse;

import com.hoolah.process.Transaction.Transaction;

import java.io.IOException;
import java.util.List;

public interface CSVFileParser {

    public List<Transaction> parse(String filePath) throws IOException;

}
