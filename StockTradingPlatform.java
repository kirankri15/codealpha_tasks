import java.io.*;
import java.util.*;

class Stock {
    String symbol;
    String name;
    double price;

    Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    double cashBalance;

    Portfolio(double initialCash) {
        this.cashBalance = initialCash;
    }

    void buyStock(String symbol, int quantity, double price) {
        double cost = quantity * price;
        if (cost <= cashBalance) {
            holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
            cashBalance -= cost;
            System.out.println("Bought " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Not enough balance!");
        }
    }

    void sellStock(String symbol, int quantity, double price) {
        if (holdings.containsKey(symbol) && holdings.get(symbol) >= quantity) {
            holdings.put(symbol, holdings.get(symbol) - quantity);
            cashBalance += quantity * price;
            System.out.println("Sold " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Not enough shares!");
        }
    }

    void displayPortfolio(Map<String, Stock> market) {
        System.out.println("\n--- Portfolio Summary ---");
        System.out.println("Cash Balance: $" + cashBalance);
        double totalValue = cashBalance;
        for (String symbol : holdings.keySet()) {
            int qty = holdings.get(symbol);
            double price = market.get(symbol).price;
            double value = qty * price;
            totalValue += value;
            System.out.println(symbol + ": " + qty + " shares @ $" + price + " = $" + value);
        }
        System.out.println("Total Portfolio Value: $" + totalValue);
    }

    void saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println(cashBalance);
            for (String symbol : holdings.keySet()) {
                pw.println(symbol + "," + holdings.get(symbol));
            }
            System.out.println("Portfolio saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            cashBalance = Double.parseDouble(br.readLine());
            holdings.clear();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                holdings.put(parts[0], Integer.parseInt(parts[1]));
            }
            System.out.println("Portfolio loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }
}

public class StockTradingPlatform {
    static Map<String, Stock> market = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initializeMarket();

        Portfolio portfolio = new Portfolio(10000.0); // Starting with $10,000
        int choice;

        do {
            System.out.println("\n=== Stock Trading Platform ===");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Save Portfolio");
            System.out.println("6. Load Portfolio");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> displayMarketData();
                case 2 -> buyStock(portfolio);
                case 3 -> sellStock(portfolio);
                case 4 -> portfolio.displayPortfolio(market);
                case 5 -> {
                    System.out.print("Enter filename to save: ");
                    sc.nextLine();
                    String saveFile = sc.nextLine();
                    portfolio.saveToFile(saveFile);
                }
                case 6 -> {
                    System.out.print("Enter filename to load: ");
                    sc.nextLine();
                    String loadFile = sc.nextLine();
                    portfolio.loadFromFile(loadFile);
                }
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option!");
            }

        } while (choice != 0);
    }

    static void initializeMarket() {
        market.put("AAPL", new Stock("AAPL", "Apple Inc.", 180.5));
        market.put("GOOGL", new Stock("GOOGL", "Alphabet Inc.", 2785.2));
        market.put("TSLA", new Stock("TSLA", "Tesla Inc.", 710.3));
        market.put("AMZN", new Stock("AMZN", "Amazon.com Inc.", 3342.1));
    }

    static void displayMarketData() {
        System.out.println("\n--- Market Data ---");
        for (Stock stock : market.values()) {
            System.out.println(stock.symbol + " (" + stock.name + ") - $" + stock.price);
        }
    }

    static void buyStock(Portfolio portfolio) {
        System.out.print("Enter stock symbol to buy: ");
        sc.nextLine();
        String symbol = sc.nextLine().toUpperCase();

        if (market.containsKey(symbol)) {
            System.out.print("Enter quantity to buy: ");
            int qty = sc.nextInt();
            portfolio.buyStock(symbol, qty, market.get(symbol).price);
        } else {
            System.out.println("Stock not found.");
        }
    }

    static void sellStock(Portfolio portfolio) {
        System.out.print("Enter stock symbol to sell: ");
        sc.nextLine();
        String symbol = sc.nextLine().toUpperCase();

        if (market.containsKey(symbol)) {
            System.out.print("Enter quantity to sell: ");
            int qty = sc.nextInt();
            portfolio.sellStock(symbol, qty, market.get(symbol).price);
        } else {
            System.out.println("Stock not found.");
        }
    }
}
