import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Bank {
    private String accno;
    private String name;
    private long balance;
    private double interestRate;
    private String password;
    private List<String> transactionHistory;

    Scanner KB = new Scanner(System.in);

    Bank() {
        transactionHistory = new ArrayList<>();
    }

    void openAccount() {
        System.out.print("Enter Account No: ");
        accno = KB.next();
        System.out.print("Enter Name: ");
        name = KB.next();
        System.out.print("Enter Balance: ");
        balance = KB.nextLong();
        System.out.print("Enter Interest Rate: ");
        interestRate = KB.nextDouble();
        System.out.print("Set Password: ");
        password = KB.next();
    }

    void showAccount() {
        System.out.println("Account Details: " + accno + "," + name + "," + balance + ", Interest Rate: " + interestRate);
    }

    void deposit() {
        long amt;
        System.out.println("Enter Amount U Want to Deposit: ");
        amt = KB.nextLong();
        balance = balance + amt;
        // Add transaction to history
        transactionHistory.add("Deposit: " + amt + " on " + new Date());
    }

    void withdrawal() {
        long amt;
        System.out.println("Enter Amount U Want to withdraw: ");
        amt = KB.nextLong();
        if (balance >= amt) {
            balance = balance - amt;
            // Add transaction to history
            transactionHistory.add("Withdrawal: " + amt + " on " + new Date());
        } else {
            System.out.println("Less Balance..Transaction Failed..");
        }
    }

    void transfer(Bank destination) {
        long amt;
        System.out.println("Enter Amount U Want to Transfer: ");
        amt = KB.nextLong();
        if (balance >= amt) {
            balance = balance - amt;
            destination.balance += amt;
            // Add transactions to history
            transactionHistory.add("Transfer to " + destination.accno + ": " + amt + " on " + new Date());
            destination.transactionHistory.add("Transfer from " + accno + ": " + amt + " on " + new Date());
        } else {
            System.out.println("Less Balance..Transaction Failed..");
        }
    }

    void calculateInterest() {
        // Assuming interest is compounded annually
        double interest = balance * (interestRate / 100);
        balance += (long) interest;
        // Add transaction to history
        transactionHistory.add("Interest Calculation: " + interest + " on " + new Date());
    }

    boolean authenticate() {
        System.out.print("Enter Password: ");
        String enteredPassword = KB.next();
        return password.equals(enteredPassword);
    }

    boolean search(String acn) {
        if (accno.equals(acn)) {
            showAccount();
            return true;
        }
        return false;
    }

    void showTransactionHistory() {
        System.out.println("Transaction History for Account " + accno + ":");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

public class BankingInformationSystem {
    public static void main(String arg[]) {
        Scanner KB = new Scanner(System.in);
        System.out.print("How Many Customer U Want to Input: ");
        int n = KB.nextInt();
        Bank C[] = new Bank[n];
        for (int i = 0; i < C.length; i++) {
            C[i] = new Bank();
            C[i].openAccount();
        }
        int ch;
        do {
            System.out.println(
                    "Main Menu\n1. Display All\n2. Search By Account\n3. Deposit\n4. Withdrawal\n5. Transfer\n6. Calculate Interest\n7. Display Transaction History\n8. Exit");
            System.out.println("Ur Choice :");
            ch = KB.nextInt();
            switch (ch) {
                case 1:
                    for (Bank account : C) {
                        account.showAccount();
                    }
                    break;

                case 2:
                    System.out.print("Enter Account No U Want to Search: ");
                    String acn = KB.next();
                    boolean found = false;
                    for (Bank account : C) {
                        if (account.search(acn)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search Failed..Account Not Exist..");
                    }
                    break;

                case 3:
                    System.out.print("Enter Account No: ");
                    acn = KB.next();
                    found = false;
                    for (Bank account : C) {
                        if (account.search(acn)) {
                            account.deposit();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search Failed..Account Not Exist..");
                    }
                    break;

                case 4:
                    System.out.print("Enter Account No: ");
                    acn = KB.next();
                    found = false;
                    for (Bank account : C) {
                        if (account.search(acn)) {
                            account.withdrawal();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search Failed..Account Not Exist..");
                    }
                    break;

                case 5:
                    System.out.print("Enter Source Account No: ");
                    String sourceAcn = KB.next();
                    System.out.print("Enter Destination Account No: ");
                    String destinationAcn = KB.next();
                    Bank sourceAccount = null;
                    Bank destinationAccount = null;
                    found = false;
                    for (Bank account : C) {
                        if (account.search(sourceAcn)) {
                            sourceAccount = account;
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Source Account Not Found.");
                        break;
                    }

                    found = false;
                    for (Bank account : C) {
                        if (account.search(destinationAcn)) {
                            destinationAccount = account;
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Destination Account Not Found.");
                        break;
                    }

                    sourceAccount.transfer(destinationAccount);
                    System.out.println("Transfer Successful.");
                    break;

                case 6:
                    System.out.print("Enter Account No: ");
                    acn = KB.next();
                    found = false;
                    for (Bank account : C) {
                        if (account.search(acn)) {
                            account.calculateInterest();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search Failed..Account Not Exist..");
                    }
                    break;

                case 7:
                    System.out.print("Enter Account No: ");
                    acn = KB.next();
                    found = false;
                    for (Bank account : C) {
                        if (account.search(acn)) {
                            account.showTransactionHistory();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search Failed..Account Not Exist..");
                    }
                    break;

                case 8:
                    System.out.println("Good Bye..");
                    break;

                default:
                    System.out.println("Invalid Choice. Please enter a valid option.");
                    break;
            }
        } while (ch != 8);
    }
}
