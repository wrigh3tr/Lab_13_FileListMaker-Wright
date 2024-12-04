import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
static ArrayList<String> list = new ArrayList<>();
static Scanner pipe = new Scanner(System.in);
static Boolean iSaved = false;

    public static void main(String[] args) {

        boolean done = false;

        do {
            displayMenu();
            String choice = SafeInput.getRegExString(pipe, "Choose an option [A/D/I/V/M/O/S/C/Q]:", "[AaDdIiVvMmOoSsCcQq]");
            switch (choice.toUpperCase()) {
                case "A":
                    String newItem = SafeInput.getNonZeroLenString(pipe, "Enter the item to add:");
                    list.add(newItem);
                    System.out.println("Item added.");
                    break;
                case "D":
                    if (list.isEmpty()) {
                        System.out.println("The list is empty.");
                        break;
                    }
                    else {
                        printListWithNumbers();
                        int itemIndex = SafeInput.getRangedInt(pipe, "Enter the number of the item to delete:", 1, list.size()) - 1;
                        list.remove(itemIndex);
                        System.out.println("Item successfully deleted.");
                    }
                    break;
                case "I":
                    if (list.isEmpty()) {
                        System.out.println("The list is currently empty.");
                    } else {
                        int position = SafeInput.getRangedInt(pipe, "Enter the position to insert the item:", 1, list.size() + 1) - 1;
                        newItem = SafeInput.getNonZeroLenString(pipe, "Enter the item you want to insert:");
                        list.add(position, newItem);
                        System.out.println("Item inserted.");
                    }
                    break;
                case "V":
                    printList();
                    break;
                case "M":
                    if (list.isEmpty()) {
                        System.out.println("The list is empty. Nothing to move.");
                        break;
                    }
                    printListWithNumbers();
                    int fromIndex = SafeInput.getRangedInt(pipe, "Enter the number of the item to move:", 1, list.size()) - 1;
                    int toIndex = SafeInput.getRangedInt(pipe, "Enter the position to move the item to:", 1, list.size()) - 1;
                    String item = list.remove(fromIndex);
                    list.add(toIndex, item);
                    System.out.println("Item moved.");
                    break;
                case "O":
                    String openFileName = SafeInput.getNonZeroLenString(pipe, "Enter the name of the file to open:");
                    loadListFromFile(openFileName);
                    break;
                case "S":
                    String saveFileName = SafeInput.getNonZeroLenString(pipe, "Enter the name of the file to save to:");
                    saveListToFile(saveFileName);
                    break;
                case "C":
                    list.clear();
                    System.out.println("List cleared.");
                    break;
                case "Q":
                    boolean confirm = SafeInput.getYNConfirm(pipe, "Are you sure you want to quit? (Y/N):");
                    if (confirm) {
                        checkUnsavedChanges();
                        System.out.println("You have successfully exited the program.");
                        done = true;
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (!done);
    }

    private static void displayMenu() {
        printList();
        System.out.println("\nMenu:");
        System.out.println("A - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("I - Insert an item");
        System.out.println("V - View the list");
        System.out.println("M - Move an item");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit");
    }

    private static void printList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            System.out.println("Current List:");
            for (String item : list) {
                System.out.println("- " + item);
            }
        }
    }

    private static void printListWithNumbers() {
        System.out.println("List:");
        for (int m = 0; m < list.size(); m++) {
            System.out.printf("%d. %s%n", m + 1, list.get(m));
        }
    }

    private static void saveListToFile(String fileName) {
        try (BufferedWriter filewriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String item : list) {
                filewriter.write(item);
                filewriter.newLine();
            }
            System.out.println("List saved to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void loadListFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            list.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            System.out.println("List loaded from file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private static boolean checkUnsavedChanges() {
    if (!iSaved) {
        boolean saveNow = SafeInput.getYNConfirm(pipe, "You have unsaved changes. Do you want to save them? (Y/N):");
        if (saveNow) {
            String saveFileName = SafeInput.getNonZeroLenString(pipe, "Enter the name of the file to save to:");
            saveListToFile(saveFileName);
            iSaved = true;
            return true;
        } else {
            return SafeInput.getYNConfirm(pipe, "Are you sure you want to discard unsaved changes? (Y/N):");
        }
    }
    return true;}


