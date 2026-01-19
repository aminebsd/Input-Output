#  Product Management with Java Object Serialization

## Topic

This project is a **Java console application** that manages a list of products using  
**Object Serialization** to save and reload data from a binary file named `products.dat`.

The application allows the user to:
- Add products
- Display all products
- Search a product by ID
- Delete a product
- Save all products to a file

The architecture is based on:
- A model class (`Product`)
- A business interface (`IProduitMetier`)
- A business implementation (`MetierProduitImpl`)
- A main class (`Main`)

---

## Key Concepts

### Object Serialization
Serialization converts an object into a **byte stream** so it can be saved in a file and later restored.

Main classes used:
- `ObjectOutputStream` → to write objects into a file
- `ObjectInputStream` → to read objects from a file

### File Persistence
All products are stored in a binary file named: `products.dat`

## Code Explanation

---

### 1. Product Class

This class represents a product and implements `Serializable` to allow saving objects into a file.

#### Main attributes:
- `id`
- `name`
- `brand`
- `price`
- `description`
- `numberInStock`

```java
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private String brand;
    private double price;
    private String description;
    private int numberInStock;
}
@Override
public String toString() {
    return String.format("ID: %d | Name: %s | Brand: %s | Price: %.2f | Stock: %d",
            id, name, brand, price, numberInStock);
}
```

### 2. IProduitMetier Interface

##### Defines all operations for managing products.
```java
public interface IProduitMetier {
void add(Product p);
List<Product> getAll();
Product findById(long id);
void delete(long id);
void saveAll();
}
```

Role:
- Defines what methods must be implemented
- Separates business logic from implementation

---

### 3. MetierProduitImpl Class

##### This class implements `IProduitMetier` and manages:

- The product list in memory
- Saving and loading from `products.dat`

##### Attributes
```java
private List<Product> products = new ArrayList<>();
private final String fileName = "products.dat";
```

##### Adding a product
Adds a product to the in-memory list.
```java
public void add(Product p) {
products.add(p);
}
```

##### Loading products from file
```java
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
```

Explanation:
- Checks if products.dat exists
- Reads the list using ObjectInputStream
- Restores all saved products into memory

##### Searching a product by ID

Uses Java Streams to search for a product by its ID.
```java
public Product findById(long id) {
    return products.stream()
    .filter(p -> p.getId() == id)
    .findFirst()
    .orElse(null);
}
```


##### Deleting a product by ID

Removes the product from the list.
```java
public void delete(long id) {
    products.removeIf(p -> p.getId() == id);
}
```


##### Saving products to file
```java
public void saveAll() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
        oos.writeObject(products);
        System.out.println("Products successfully saved to " + fileName);
    } catch (IOException e) {
    System.err.println("Error saving products: " + e.getMessage());
    }
}
```
Explanation:

- Converts the list into bytes
- Saves it in products.dat using ObjectOutputStream

---

### 4 Main Class (Application)

This class contains the `main` method and represents the **entry point** of the application.  
It provides a **menu-based console interface** that allows users to manage products interactively.


### Initialization

```java
IProduitMetier metier = new MetierProduitImpl();
Scanner scanner = new Scanner(System.in);
int choice = 0;
```
- Creates an instance of `MetierProduitImpl` to access business methods
- Initializes a `Scanner` to read user input
- Sets up the menu choice variable

### Loading Products at Startup
```java
metier.getAll();
```
- Loads all previously saved products from `products.dat`
- Restores the product list into memory before displaying the menu

### Menu Loop

```java
while (choice != 6) {
    System.out.println("\n--- Product Management Menu ---");
    System.out.println("1. Display the list of products");
    System.out.println("2. Search for a product by ID");
    System.out.println("3. Add a new product");
    System.out.println("4. Delete a product by ID");
    System.out.println("5. Save products to file");
    System.out.println("6. Exit");
    System.out.print("Enter your choice: ");
}
```
- Displays the menu continuously
- Stops only when the user chooses option 6 (Exit)

#### case 1 : Display All Products
```java
case 1:
    metier.getAll().forEach(System.out::println);
    break;
```
- Retrieves all products from memory
- Displays each product using its toString() method

#### case 2 : Search Product by ID
```java
 case 2:
    System.out.print("Enter ID: ");
    long idSearch = scanner.nextLong();
    Product p = metier.findById(idSearch);
    System.out.println(p != null ? p : "Product not found.");
    break;
```
- Searches for the product using findById()
- Displays the product if found, otherwise shows an error message

#### case 3 : Add a New Product
```java
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
```
- Reads all product information from the user
- Creates a new Product object
- Adds it to the in-memory list

#### case 4: Delete a Product by ID
```java
case 4:
    System.out.print("Enter ID to delete: ");
    long idDel = scanner.nextLong();
    metier.delete(idDel);
    break;

```
- Reads the product ID from the user
- Removes the product from the list using delete()

#### case 5: Save Products to File
```java
case 5 :
    metier.saveAll();
    break ;
```
- Serializes the list of products
- Saves all data into products.dat

#### case 6: Exit Program
```java
case 6:
    System.out.println("Exiting...");
    break;
```
- Stops the menu loop
- Ends the program

### Closing Resources
```java
scanner.close();
```
- Closes the input stream
- Releases system resources

#### output : 

```shell
--- Product Management Menu ---
1. Display the list of products
2. Search for a product by ID
3. Add a new product
4. Delete a product by ID
5. Save products to file
6. Exit
Enter your choice: 3
```

- Adding product :
```shell
Enter your choice: 3
ID: 101
Name: tinkpad
Brand: lenovo
Price: 6000
Description: laptop
Stock: 10
---
Enter your choice: 3
ID: 102
Name: elitebook
Brand: hp
Price: 7000
Description: laptop
Stock: 15

```
- Saving Products
```shell
Enter your choice: 5
Products successfully saved to products.dat
```

- Listing All product
```shell
Enter your choice: 1
ID: 101 | Name: tinkpad | Brand: lenovo | Price: 6000.00 | Stock: 10
ID: 102 | Name: elitbook | Brand: hp | Price: 7000.00 | Stock: 15
```

- Searching by Id
```shell
Enter your choice: 2
Enter ID: 102
ID: 102 | Name: elitbook | Brand: hp | Price: 7000.00 | Stock: 15
```
