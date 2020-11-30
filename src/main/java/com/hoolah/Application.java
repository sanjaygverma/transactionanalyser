package com.hoolah;

import com.hoolah.parse.CSVFileParser;
import com.hoolah.parse.ParserFactory;
import com.hoolah.process.Transaction.Transaction;
import com.hoolah.process.TransactionsCSVProcessor;
import com.hoolah.utils.Utils;
import com.sun.jdi.connect.TransportTimeoutException;
import org.apache.commons.csv.CSVParser;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

        public static void main(String[] args) {
            executeCommands();

        }

        private static void executeCommands() {
            Scanner scanner = new Scanner(System.in);
            ExecutorService execSvc = null;
            try {
                execSvc = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                int noOfCommands = Integer.parseInt(scanner.nextLine());

                Set<Callable<String>> lstCall = new LinkedHashSet<>();

                for (int i = 0; i < noOfCommands; i++) {
                    String command = scanner.nextLine();
                    String []  vals = Utils.interpretInput(command);
                    CSVFileParser csvParser = ParserFactory.getParser(vals[0], vals[1]);
                    String file = scanner.nextLine();
                    List<Transaction> lst = csvParser.parse(file);
                    System.out.println(lst);
                }
                execSvc.invokeAll(lstCall);
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
                execSvc.shutdown();
            }
        }
}
