package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MetierProduitImpl implements IProduitMetier{

    private List<Product> products = new ArrayList<>();
    private final String fileName = "products.dat";

    @Override
    public void add(Product p) {
        products.add(p);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getAll() {
        File file = new File(fileName);
        if (!file.exists()) return products;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            products = (List<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading products: " + e.getMessage());
        }
        return products;
    }

    @Override
    public Product findById(long id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(long id) {
        products.removeIf(p -> p.getId() == id);
    }

    @Override
    public void saveAll() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(products);
            System.out.println("Products successfully saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving products: " + e.getMessage());
        }
    }
}
