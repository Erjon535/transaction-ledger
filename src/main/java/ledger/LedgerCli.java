package ledger;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class LedgerCli {

    public static void run(LedgerService service, Path csvPath) {
        Scanner in = new Scanner(System.in);

        System.out.println("Transaction Ledger CLI");
        System.out.println("Data file: " + csvPath.toAbsolutePath());
        System.out.println();

        while (true) {
            System.out.println("1) Add transaction");
            System.out.println("2) Show balance");
            System.out.println("3) List all");
            System.out.println("4) List credits");
            System.out.println("5) List debits");
            System.out.println("6) Search by description");
            System.out.println("7) Show recent (N)");
            System.out.println("0) Save & Exit");
            System.out.print("> ");

            String choice = in.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addTransaction(in, service);
                    case "2" -> System.out.println("Balance: " + service.getBalance());
                    case "3" -> print(service.getAll());
                    case "4" -> print(service.getCredits());
                    case "5" -> print(service.getDebits());
                    case "6" -> {
                        System.out.print("Keyword: ");
                        String k = in.nextLine();
                        print(service.findByDescription(k));
                    }
                    case "7" -> {
                        System.out.print("How many? ");
                        int n = Integer.parseInt(in.nextLine().trim());
                        print(service.getRecent(n));
                    }
                    case "0" -> {
                        service.save(csvPath);
                        System.out.println("Saved. Bye!");
                        return;
                    }
                    default -> System.out.println("Unknown option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void addTransaction(Scanner in, LedgerService service) {
        System.out.print("Amount (e.g. 12.50 or -5.00): ");
        BigDecimal amount = new BigDecimal(in.nextLine().trim());

        System.out.print("Description: ");
        String desc = in.nextLine().trim();

        service.add(amount, desc);
        System.out.println("Added.");
    }

    private static void print(List<Transaction> txs) {
        if (txs.isEmpty()) {
            System.out.println("(none)");
            return;
        }
        for (Transaction t : txs) {
            System.out.println(t);
        }
    }
}