package lv.nixx.poc.datastruct.txn.stream;

import lombok.AllArgsConstructor;
import lv.nixx.poc.datastruct.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TxnLambdaModification {

    Collection<Transaction> sourceTransactions = List.of(
            new Transaction("id1", 10.10, "ACC1", "USD", "2020-04-21"),
            new Transaction("id2", 20.12, "ACC2", "USD", "2020-05-01"),
            new Transaction("id3", 30.13, "ACC2", "EUR", "2020-05-02"),
            new Transaction("id4", 40.14, "ACC3", "EUR", "2020-05-03"),
            new Transaction("id5", 50.14, "ACC3", "EUR", "2020-06-01"),
            new Transaction("id6", 60.14, "ACC3", "USD", "2020-06-01"),
            new Transaction("id6", 70.14, "ACC3", "USD", "2020-06-01"),
            new Transaction("id7", 100.00, "ACC4", "GBP", "2020-06-01"),
            new Transaction("id8", 1.27, "ACC4", "GBP", "2020-06-01")
    );


    @Test
    void functionCall() {
        TxnProcessor myProcessor = new TxnProcessor();
        // ссылка на метод, у конкретного экземпляра класса
        sourceTransactions.forEach(myProcessor::simpleMethod);
        // вызов статического метода с параметрами
        sourceTransactions.forEach(t -> TxnProcessor.staticMethod(t.getAmount(), t.getCurrency()));

        // Moжно вызывать и вот так, поскольку, MyProcessor implements Consumer
        MyProcessor mp = new MyProcessor();
        sourceTransactions.forEach(mp);
    }

    @Test
    void transformCollectionToMapDuplicateKey() {
        assertThrows(IllegalStateException.class, () -> sourceTransactions.stream().collect(toMap(Transaction::getId, identity())));
    }


    @Test
    void createAccountHolder() {
        AccountHolder ah = new AccountHolder();
        sourceTransactions.forEach(ah::process);

        ah.accounts.forEach((key, value) -> System.out.println(key + ":" + value));
    }

    @Test
    void getUniqueAccounts() {

        final Set<String> accounts = sourceTransactions.stream()
                .map(Transaction::getAccount)
                .collect(Collectors.toSet());

        assertEquals(4, accounts.size());
        accounts.forEach(System.out::println);
    }

    @Test
    void stringWithUniqueCurrencies() {
        String c = sourceTransactions.stream()
                .map(Transaction::getCurrency)
                .distinct()
                .collect(Collectors.joining(", "));

        assertEquals("USD, EUR, GBP", c);
    }

    @Test
    void transformCollectionToIdList() {
        List<String> array = sourceTransactions.stream()
                .filter(t -> t.getCurrency().equalsIgnoreCase("USD"))
                .map(Transaction::getId).toList();

        assertEquals(4, array.size());
    }

    @Test
    public void groupByAccountCurrencies() {
        // Счет, список валют
        Map<String, Set<String>> c =
                sourceTransactions.stream()
                        .collect(groupingBy(
                                Transaction::getAccount,
                                Collectors.mapping(Transaction::getCurrency, Collectors.toSet()))
                        );

        c.entrySet().forEach(System.out::println);
        assertEquals(4, c.size());

        System.out.println("--------------------");

        Map<String, List<Transaction>> transactionListByAccount = sourceTransactions.stream()
                .collect(groupingBy(Transaction::getAccount));

        transactionListByAccount.forEach((key, value) -> {
            System.out.println(key);
            value.forEach(v -> System.out.println("\t\t" + v));
        });

        assertEquals(4, transactionListByAccount.size());

    }

    @Test
    public void groupAndSortResult() {

        Map<String, List<Transaction>> collect = sourceTransactions.stream()
                .collect(groupingBy(Transaction::getAccount,
                                mapping(Function.identity(),
                                        collectingAndThen(toList(),
                                                e -> e.stream().sorted(Comparator.comparing(Transaction::getAmount).reversed())
                                                        .collect(toList()))
                                )
                        )
                );

        collect.entrySet().forEach(System.out::println);
    }

    @Test
    void groupByAccountAmountList() {
        Map<String, List<Amount>> c =
                sourceTransactions.stream()
                        .collect(groupingBy(Transaction::getAccount,
                                        Collectors.mapping(Amount::new, Collectors.toList())
                                )
                        );

        c.entrySet().forEach(System.out::println);
        assertEquals(4, c.size());
    }

    @Test
    void groupByCurrencyAccountStatistic() {
        Map<String, Map<String, Long>> m1 = sourceTransactions.stream()
                .collect(groupingBy(Transaction::getCurrency,
                                groupingBy(Transaction::getAccount, Collectors.counting())
                        )
                );

        m1.entrySet().forEach(System.out::println);

        assertEquals(3, m1.size());

        Map<String, Map<String, List<Transaction>>> m2 = sourceTransactions.stream()
                .collect(groupingBy(Transaction::getCurrency,
                                groupingBy(Transaction::getAccount, Collectors.toList())
                        )
                );

        /*
         EUR ->
                ACC3(id5)
                ACC2(id3, id4)
         */
        assertEquals(3, m2.size());

        Map<String, Map<String, Transaction>> m3 = sourceTransactions.stream()
                .collect(groupingBy(Transaction::getCurrency,
                                toMap(Transaction::getAccount, identity(), (t1, t2) -> t2)
                        )
                );

        assertEquals(3, m2.size());
    }

    @Test
    void groupingWithAmountsToSet() {

        Collection<Transaction> sourceTransactions = List.of(
                new Transaction("id1", 0.00, "ACC1", "USD", "2020-04-21"),
                new Transaction("id2", 20.12, "ACC2", "USD", "2020-05-01"),
                new Transaction("id3", 30.13, "ACC2", "EUR", "2020-05-02"),
                new Transaction("id4", 40.14, "ACC3", "EUR", "2020-05-03"),
                new Transaction("id5", 50.14, "ACC3", "EUR", "2020-06-01"),
                new Transaction("id6", 0.00, "ACC3", "USD", "2020-06-01"),
                new Transaction("id6", 70.14, "ACC3", "USD", "2020-06-01"),
                new Transaction("id7", 100.00, "ACC4", "GBP", "2020-06-01"),
                new Transaction("id8", 1.27, "ACC4", "GBP", "2020-06-01")
        );

        Map<String, Boolean> containsNonZero = sourceTransactions.stream()
                .collect(
                        collectingAndThen(groupingBy(Transaction::getAccount, mapping(t -> t.getAmount().compareTo(BigDecimal.ZERO) == 0, toSet())),
                                e -> e.entrySet().stream().collect(toMap(Map.Entry::getKey, t -> t.getValue().contains(false)))
                        )
                );

        containsNonZero.entrySet().forEach(System.out::println);
    }

    @Test
    void mapNonZero() {

        Collection<Transaction> sourceTransactions = List.of(
                new Transaction("id1", 0.00, "ACC1", "USD", "2020-04-21"),

                new Transaction("id2", 20.12, "ACC2", "USD", "2020-05-01"),
                new Transaction("id3", 30.13, "ACC2", "EUR", "2020-05-02"),

                new Transaction("id4", 40.14, "ACC3", "EUR", "2020-05-03"),
                new Transaction("id5", 50.14, "ACC3", "EUR", "2020-06-01"),
                new Transaction("id6", 0.00, "ACC3", "USD", "2020-06-01"),
                new Transaction("id6", 70.14, "ACC3", "USD", "2020-06-01"),

                new Transaction("id7", 100.00, "ACC4", "GBP", "2020-06-01"),
                new Transaction("id8", 1.27, "ACC4", "GBP", "2020-06-01")
        );

        Map<String, Boolean> containsNonZero = sourceTransactions.stream().collect(
                toMap(Transaction::getAccount, t -> t.getAmount().compareTo(BigDecimal.ZERO) != 0, (t1, t2) -> t1 || t2)
        );

        containsNonZero.entrySet().forEach(System.out::println);
    }

    @Test
    void mapCollection() {

        Collection<Amount> c =
                sourceTransactions.stream()
                        .map(Amount::new)
                        .collect(Collectors.toSet());

        c.forEach(System.out::println);

        assertEquals(9, c.size());
    }

    @Test
    void totalAmountByCurrency() {

        // Двойная групировка и сумма для каждой из груп
        Map<String, Map<String, BigDecimal>> amountByAccount = sourceTransactions.stream()
                .collect(
                        groupingBy(Transaction::getAccount
                                , groupingBy(Transaction::getCurrency, reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)))
                );

        amountByAccount.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(System.out::println);
    }

    @Test
    void txnCountByCurrency() {

        Map<String, Long> r = sourceTransactions.stream()
                .collect(
                        groupingBy(Transaction::getCurrency,
                                Collectors.counting())
                );

        r.entrySet().forEach(System.out::println);

        assertEquals(3, r.size());
    }


    @Test
    void partitioningBySample() {
        final Map<Boolean, List<Transaction>> c = sourceTransactions.stream()
                .collect(partitioningBy(t -> t.getCurrency().equals("USD")));

        c.forEach((b, t) -> System.out.println(b + ":" + t));
    }

    @Test
    void groupByAccountWithCondition() {
        Map<String, List<Transaction>> collect =
                sourceTransactions.stream()
                        .collect(
                                groupingBy(Transaction::getAccount,
                                        Collectors.filtering(t -> t.getCurrency().equalsIgnoreCase("EUR"), Collectors.toList())
                                )
                        );

        collect.entrySet().forEach(System.out::println);
    }

    @Test
    void txnCreateFromString() throws Exception {
        String txnString =
                """
                            id1, 10.30, ACC1, USD
                            id2, 20.40, ACC2, USD
                            id3, 5.00,  ACC3, EUR
                            id4, 20.00, ACC3, EUR
                            id5, 15.00, ACC4, EUR
                        """;

        try (BufferedReader br = new BufferedReader(new StringReader(txnString))) {
            final List<Transaction> txnList = br.lines()
                    .map(t -> t.split(","))
                    .filter(t -> t.length == 4)
                    .map(this::removeSpaces)
                    .map(t -> new Transaction(t[0], parse(t[1]), t[2], t[3], null))
                    .toList();

            txnList.forEach(System.out::println);
        }
    }

    @Test
    void sortTxnBySeveralParams() {
        final Comparator<Transaction> c =
                Comparator.comparing(Transaction::getAccount).reversed()
                        .thenComparing(Transaction::getCurrency)
                        .thenComparing(Transaction::getAmount);

        List<Transaction> sorted =
                sourceTransactions.stream()
                        .sorted(c)
                        .toList();

        sorted.forEach(System.out::println);
    }

    @Test
    void mapWithIncrementTest() {
        final AtomicInteger i = new AtomicInteger(0);
        Map<Integer, Transaction> map = sourceTransactions.stream()
                .collect(Collectors.toMap(t -> i.incrementAndGet(), identity()));

        System.out.println(map);
    }

    @Test
    void top3TransactionTest() {

        List<Transaction> top3Txns =
                sourceTransactions.stream()
                        .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                        .limit(3)
                        .toList();

        top3Txns.forEach(System.out::println);

        assertEquals(3, top3Txns.size());
    }

    @Test
    void topTransactionByAccountTest() {

        Map<String, Transaction> maxBy = sourceTransactions.stream()
                .collect(
                        toMap(Transaction::getAccount, identity(), BinaryOperator.maxBy(Comparator.comparing(Transaction::getAmount)))
                );

        maxBy.entrySet().forEach(System.out::println);

        assertEquals(4, maxBy.size());
    }

    @Test
    void getTransactionsByPeriod() {

        DateFilter df = new DateFilter(LocalDate.parse("2020-06-01"), LocalDate.parse("2023-05-20"));

        final List<Transaction> datesInRange = sourceTransactions.stream()
                .filter(t -> df.isInRange(t.getLastUpdateDate()))
                .collect(Collectors.toList());

        System.out.println(datesInRange);

        assertEquals(5, datesInRange.size());
    }

    @Test
    void peekTest() {

        List<String> lst = sourceTransactions.stream()
                .map(Transaction::getId).peek(t -> System.out.println(t + "V"))
                .collect(Collectors.toList());

        System.out.println(lst);
    }

    @Test
    void groupWithoutEmptyCheck() {

        Map<String, List<Transaction>> grouped = sourceTransactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.valueOf(100)) >= 0)
                .collect(groupingBy(Transaction::getAccount));

        // В этом случае, не создаются Entry c пустыми коллекциями
        grouped.entrySet().forEach(System.out::println);

        assertEquals(1, grouped.size());
    }

    @Test
    void findAndMapResult() {

        // В зависимости от того, найдена запись или нет, формируем разные строки ответа
        Function<String, String> expressionToTest = acc -> sourceTransactions.stream()
                .filter(t -> t.getAccount().equalsIgnoreCase(acc))
                .min(Comparator.comparing(Transaction::getAmount))
                .map(t -> "Found, id:" + t.getId() + " amount: " + t.getAmount())
                .orElse("Not Found");

        assertAll(
                () -> assertEquals("Not Found", expressionToTest.apply("NOT_EXISTS")),
                () -> assertEquals("Found, id:id4 amount: 40.14", expressionToTest.apply("ACC3"))
        );

        // Идея похожая, только если запись не найдена - кидаем Exception
        Function<String, String> f1 = acc -> sourceTransactions.stream()
                .filter(t -> t.getAccount().equalsIgnoreCase(acc))
                .min(Comparator.comparing(Transaction::getAmount))
                .map(t -> "Found, id:" + t.getId() + " amount: " + t.getAmount())
                .orElseThrow(() -> new IllegalArgumentException("Account: " + acc + " not found"));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> f1.apply("NOT_EXISTS"));
        assertEquals("Account: NOT_EXISTS not found", e.getMessage());

    }

    private String[] removeSpaces(String[] source) {
        for (int i = 0; i < source.length; i++) {
            source[i] = source[i].trim();
        }
        return source;
    }

    private Double parse(String amount) {
        DecimalFormat df = new DecimalFormat();
        df.setParseBigDecimal(true);
        try {
            return df.parse(amount.trim()).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    static class AccountHolder {

        Map<String, LocalAccount> accounts = new HashMap<>();

        public void process(Transaction txn) {
            String accId = txn.getAccount();
            LocalAccount account = null;
            if (accounts.containsKey(accId)) {
                account = accounts.get(accId);
            } else {
                account = new LocalAccount(accId);
            }
            account.increase(txn.getCurrency(), txn.getAmount());
            accounts.put(accId, account);
        }

    }

    static class LocalAccount {
        private final String id;
        Map<String, Amount> amounts = new HashMap<>();

        public LocalAccount(String accId) {
            this.id = accId;
        }

        public LocalAccount increase(Transaction txn) {
            final String currency = txn.getCurrency();
            final BigDecimal amount = txn.getAmount();
            if (amounts.containsKey(currency)) {
                amounts.get(currency).increase(amount);
            } else {
                amounts.put(currency, new Amount(currency, amount));
            }
            return this;
        }


        public void increase(String currency, BigDecimal amount) {
            if (amounts.containsKey(currency)) {
                amounts.get(currency).increase(amount);
            } else {
                amounts.put(currency, new Amount(currency, amount));
            }
        }

        @Override
        public String toString() {
            return "Account [id=" + id + ", amounts=" + amounts + "]";
        }
    }

    static class Amount {
        private final String currency;
        private BigDecimal totalAmount;

        Amount(Transaction txn) {
            this.currency = txn.getCurrency();
            this.totalAmount = txn.getAmount();
        }

        Amount(String currency, BigDecimal amount) {
            this.currency = currency;
            this.totalAmount = amount;
        }

        public void increase(BigDecimal amount) {
            this.totalAmount = totalAmount.add(amount);
        }

        @Override
        public String toString() {
            return "TotalAmount [currency=" + currency + ", totalAmount=" + totalAmount + "]";
        }
    }

    static class TxnProcessor {
        public void simpleMethod(Transaction t) {
            System.out.println(t);
        }

        public static void staticMethod(BigDecimal amount, String currency) {
            System.out.println(amount + ":" + currency);
        }
    }

    static class MyProcessor implements Consumer<Transaction> {

        @Override
        public void accept(Transaction t) {
            System.out.println("#" + t);
        }

    }

    @AllArgsConstructor
    static class DateFilter {
        final LocalDate dateFrom;
        final LocalDate dateTo;

        boolean isInRange(LocalDate date) {
            return (!date.isBefore(dateFrom) && !date.isAfter(dateTo));
        }

    }

}
