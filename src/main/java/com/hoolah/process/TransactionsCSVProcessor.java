package com.hoolah.process;


import com.hoolah.process.Transaction.Transaction;
import com.hoolah.utils.Utils;

import static com.hoolah.Constants.*;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


public class TransactionsCSVProcessor {



    public static BigDecimal processTransactions(LocalDateTime start, LocalDateTime end, List<Transaction> lst, String operation)
    {
        Objects.requireNonNull(lst,"List of transactions cannot be null");
        Objects.requireNonNull(start,"start date time cannot be null");
        Objects.requireNonNull(end,"end date time cannot be null");

        lst = lst.stream().filter(txn -> txn.getDate().isEqual(start) || txn.getDate().isAfter(start)
                && (txn.getDate().isEqual(end) || txn.getDate().isBefore(end))).collect(toList());
        return processTransactions(lst, operation);
    }


    public static BigDecimal processTransactions(LocalDateTime start, LocalDateTime end, List<Transaction> lst,
                                                 String merchant, String operation)
    {
        Objects.requireNonNull(lst,"List of transactions cannot be null");
        Objects.requireNonNull(start,"start date time cannot be null");
        Objects.requireNonNull(end,"end date time cannot be null");
        Objects.requireNonNull(merchant,"merchant cannot be null");
        Objects.requireNonNull(operation,"operation time cannot be null");

        lst = lst.stream().filter(txn -> txn.getDate().isEqual(start) || txn.getDate().isAfter(start)
                && (txn.getDate().isEqual(end) || txn.getDate().isBefore(end)))
                .filter(s-> s.getMerchant().equals(merchant)).collect(toList());
        return processTransactions(lst, operation);
    }



    public static BigDecimal processTransactions( List<Transaction> lst, String operation)
    {
        Objects.requireNonNull(lst, "List of txns cannot be null");
        Objects.requireNonNull(operation);
        BigDecimal retVal = null;
        switch (operation)
        {
            case AVG:
                Double d = lst.stream().mapToDouble(tx -> tx.getAmountDVersn()).average().orElse(Double.NaN);
                retVal = Utils.decimalValue(d);
                break;
            case MAX:
                Double dMax = lst.stream().mapToDouble(tx -> tx.getAmountDVersn()).max().orElse(Double.NaN);
                retVal = Utils.decimalValue(dMax);
                break;
            default:
                throw new IllegalArgumentException("Require operation to be applied on txns");
        }
        return retVal;
    }


}


