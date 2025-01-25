import java.io.*;
import java.util.*;
import java.io.*;
import java.util.*;

public class InventoryManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = "/workspaces/npci-advcoding-playground-challenge-3-madhups5/src/inventory.txt";
        
        while (true) {
            System.out.println("\nInventory Management Menu:");
            System.out.println("1. Read Inventory");
            System.out.println("2. Add New Item");
            System.out.println("3. Update Item");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    readInventory(fileName);
                    break;
                case 2:
                    System.out.print("Enter item name to add: ");
                    String newItem = scanner.nextLine();
                    System.out.print("Enter count for " + newItem + ": ");
                    int newItemCount = scanner.nextInt();
                    addItem(fileName, newItem, newItemCount);
                    break;
                case 3:
                    System.out.print("Enter item name to update: ");
                    String updateItem = scanner.nextLine();
                    System.out.print("Enter new count for " + updateItem + ": ");
                    int updateItemCount = scanner.nextInt();
                    updateItem(fileName, updateItem, updateItemCount);
                    break;
                case 4:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Read and display the inventory from the file
    public static void readInventory(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Inventory file does not exist.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println("\nInventory List:");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String item = parts[0].trim();
                    int count = Integer.parseInt(parts[1].trim());
                    System.out.println(item + "," + count);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory file.");
        }
    }

    // Add a new item to the inventory
    public static void addItem(String fileName, String itemName, int itemCount) {
    Map<String, Integer> inventory = loadInventory(fileName);

    if (inventory.containsKey(itemName)) {
        System.out.println("Item already exists. Updating the item.");
        updateItem(fileName, itemName, inventory.get(itemName) + itemCount);
    } else {
        inventory.put(itemName, itemCount);
        saveInventory(fileName, inventory);
        System.out.println("Item added successfully.");
    }
}

    // Update an existing item's count
    public static void updateItem(String fileName, String itemName, int itemCount) {
        Map<String, Integer> inventory = loadInventory(fileName);

        if (inventory.containsKey(itemName)) {
            inventory.put(itemName, itemCount);
            saveInventory(fileName, inventory);
            System.out.println("Item updated successfully.");
        } else {
            System.out.println("Item does not exist in the inventory.");
        }
    }

    // load inventory into a Map
    private static Map<String, Integer> loadInventory(String fileName) {
        Map<String, Integer> inventory = new HashMap<>();
        File file = new File(fileName);
        
        if (!file.exists()) {
            System.out.println("Inventory file not found");
           
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String item = parts[0].trim();
                    int count = Integer.parseInt(parts[1].trim());
                    inventory.put(item,count);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading inventory.");
        }
        return inventory;
    }

    // save the inventory to the file
    private static void saveInventory(String fileName, Map<String, Integer> inventory) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            writer.write(entry.getKey() + "," + entry.getValue());
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error saving inventory.");
    }
}
}
