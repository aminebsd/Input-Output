package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        IProduitMetier metier = new MetierProduitImpl();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        // Load existing products at startup
        metier.getAll();

        while (choice != 6) {
            System.out.println("\n--- Product Management Menu ---");
            System.out.println("1. Display the list of products");
            System.out.println("2. Search for a product by ID");
            System.out.println("3. Add a new product");
            System.out.println("4. Delete a product by ID");
            System.out.println("5. Save products to file");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    metier.getAll().forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("Enter ID: ");
                    long idSearch = scanner.nextLong();

                    Product p = metier.findById(idSearch);
                    System.out.println(p != null ? p : "Product not found.");
                    break;
                case 3:
                    System.out.print("ID: ");
                    long id = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Brand: ");
                    String brand = scanner.nextLine();

                    System.out.print("Price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Description: ");
                    String desc = scanner.nextLine();

                    System.out.print("Stock: ");
                    int stock = scanner.nextInt();

                    metier.add(new Product(id, name, brand, price, desc, stock));
                    break;
                case 4:
                    System.out.print("Enter ID to delete: ");
                    long idDel = scanner.nextLong();
                    metier.delete(idDel);
                    break;
                case 5:
                    metier.saveAll();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();

    }
}