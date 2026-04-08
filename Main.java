import java.util.*;


class BankAccount {
    int accountNumber;
    String username;
    double balance;

    BankAccount(int accountNumber, String username, double balance) {
        this.accountNumber = accountNumber;
        this.username = username;
        this.balance = balance;
    }

    public String toString() {
        return username + " – Balance: " + balance;
    }
}


class MyQueue {

    static class Node {
        String data;
        Node next;

        Node(String data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;

    public MyQueue() {
        front = rear = null;
    }

    public void enqueue(String value) {
        Node newNode = new Node(value);
        if (isEmpty()) {

            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    public String dequeue() {
        if (isEmpty()) return null;

        String value = front.data;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return value;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public void display() {
        Node current = front;

        if (current == null) {
            System.out.println("Queue is empty");
            return;
        }

        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}

public class Main {

    static Scanner sc = new Scanner(System.in);

    static LinkedList<BankAccount> accounts = new LinkedList<>();
    static Stack<String> history = new Stack<>();
    static MyQueue billQueue = new MyQueue(); // qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq
    static Queue<BankAccount> accountRequests = new LinkedList<>();

    static int accNumber = 4;

    public static void main(String[] args) {

        BankAccount[] arrayAccounts = {
                new BankAccount(1, "Ali", 150000),
                new BankAccount(2, "Sara", 220000),
                new BankAccount(3, "John", 100000)
        };

        System.out.println("Array Accounts:");
        for (BankAccount acc : arrayAccounts) {
            System.out.println(acc);
        }

        for (BankAccount acc : arrayAccounts) {
            accounts.add(acc);
        }

        while (true) {
            System.out.println("\n1 – Enter Bank");
            System.out.println("2 – Enter ATM");
            System.out.println("3 – Admin Area");
            System.out.println("4 – Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> bankMenu();
                case 2 -> atmMenu();
                case 3 -> adminMenu();
                case 4 -> {
                    System.out.println("Goodbye!");
                    return;
                }
            }
        }
    }
    static void bankMenu() {
        System.out.println("\n1 – Open Account");
        System.out.println("2 – Deposit");
        System.out.println("3 – Withdraw");
        System.out.println("4 – Add Bill");
        System.out.println("5 – Show Accounts");

        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter username: ");
                String name = sc.next();
                accountRequests.add(new BankAccount(accNumber++, name, 0));
                System.out.println("Request submitted!");
            }
            case 2 -> deposit();
            case 3 -> withdraw();
            case 4 -> addBill();
            case 5 -> displayAccounts();
        }
    }
    static void atmMenu() {
        System.out.print("Enter username: ");
        String name = sc.next();

        BankAccount acc = findAccount(name);

        if (acc == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.println("1 – Balance");
        System.out.println("2 – Withdraw");

        int choice = sc.nextInt();

        if (choice == 1) {
            System.out.println("Balance: " + acc.balance);
        } else if (choice == 2) {
            withdraw();
        }
    }
    static void adminMenu() {
        System.out.println("\n1 – Process Account Requests");
        System.out.println("2 – View Bill Queue");
        System.out.println("3 – Process Bill");
        System.out.println("4 – Show Last Transaction");

        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> processAccounts();
            case 2 -> showBills();
            case 3 -> processBill();
            case 4 -> showLastTransaction();
        }
    }
    static void displayAccounts() {
        System.out.println("Accounts List:");
        for (BankAccount acc : accounts) {
            System.out.println(acc);
        }
    }
    static BankAccount findAccount(String name) {
        for (BankAccount acc : accounts) {
            if (acc.username.equals(name)) {
                return acc;
            }
        }
        return null;
    }
    static void deposit() {
        System.out.print("Enter username: ");
        String name = sc.next();

        BankAccount acc = findAccount(name);

        if (acc != null) {
            System.out.print("Deposit amount: ");
            double amount = sc.nextDouble();
            acc.balance += amount;

            history.push("Deposit " + amount + " to " + name);

            System.out.println("New balance: " + acc.balance);
        } else {
            System.out.println("Account not found!");
        }
    }
    static void withdraw() {
        System.out.print("Enter username: ");
        String name = sc.next();

        BankAccount acc = findAccount(name);

        if (acc != null) {
            System.out.print("Withdraw amount: ");
            double amount = sc.nextDouble();

            if (acc.balance >= amount) {
                acc.balance -= amount;

                history.push("Withdraw " + amount + " from " + name);

                System.out.println("New balance: " + acc.balance);
            } else {
                System.out.println("Not enough money!");
            }
        } else {
            System.out.println("Account not found!");
        }
    }

    static void showLastTransaction() {
        if (!history.isEmpty()) {
            System.out.println("Last: " + history.peek());
        } else {
            System.out.println("No transactions!");
        }
    }

    static void addBill() {
        System.out.print("Enter bill name: ");
        String bill = sc.next();
        billQueue.enqueue(bill);
        System.out.println("Bill added!");
    }

    static void processBill() {
        String bill = billQueue.dequeue();

        if (bill != null) {
            System.out.println("Processing: " + bill);
        } else {
            System.out.println("No bills!");
        }
    }

    static void showBills() {
        System.out.print("Bills: ");
        billQueue.display();
    }

    static void processAccounts() {
        if (!accountRequests.isEmpty()) {
            BankAccount acc = accountRequests.poll();
            accounts.add(acc);
            System.out.println("Account approved: " + acc.username);
        } else {
            System.out.println("No requests!");
        }
    }
}
