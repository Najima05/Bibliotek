package com.example.java2026;

import java.util.Locale;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        try {
            while (true) {
                printMenu();
                String choice = readLine("Val: ").trim().toLowerCase(Locale.ROOT);
                IO.println();
                if (!handleChoice(choice, library)) {
                    break;
                }
                IO.println();
            }
        } catch (NoSuchElementException e) {
            IO.println("\nIndata avslutades. Programmet stängs.");
        }
    }

    public static void printMenu() {
        IO.println("Bibliotekshanteraren");
        IO.println("=====================");
        IO.println("1. Lägg till bok");
        IO.println("2. Registrera medlem");
        IO.println("3. Låna bok");
        IO.println("4. Lämna tillbaka bok");
        IO.println("5. Sök (titel eller författare)");
        IO.println("6. Visa alla böcker och status");
        IO.println("7. Visa medlem med flest lån");
        IO.println("e. Avsluta");
    }

    private static boolean handleChoice(String choice, Library library) {
        switch (choice) {
            case "1" -> addBook(library);
            case "2" -> registerMember(library);
            case "3" -> borrowBook(library);
            case "4" -> returnBook(library);
            case "5" -> searchBook(library);
            case "6" -> listBooks(library);
            case "7" -> showMemberWithLoans(library);
            case "e" -> {
                IO.println("Hejdå!");
                return false;
            }
            default -> IO.println("Ogiltigt val: \"" + choice + "\". Välj 1-7 eller e.");
        }
        return true;
    }

    private static String readLine(String prompt) {
        String input = IO.readln(prompt);
        if (input == null) {
            throw new NoSuchElementException("Ingen mer indata finns.");
        }
        return input;
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if (!input.isBlank()) {
                return input;
            }
            IO.println("Fältet kan inte vara tomt.");
        }
    }
    private static int readInt(String prompt) {
        while (true) {
            String input = readLine(prompt);
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                IO.println("Ogiltig inmatning, ange ett heltal.");
            }
        }
    }

    private static void addBook(Library library) {
        String isbn = readNonEmpty("ISBN: ");
        String title = readNonEmpty("Titel: ");
        String author = readNonEmpty("Författare: ");
        try {
            Book book = new Book(isbn, title, author);
            if (library.addBook(book)) {
                IO.println("Boken \"" + title + "\" lades till.");
            } else {
                IO.println("Fel: en bok med ISBN " + isbn + " finns redan.");
            }
        } catch (IllegalArgumentException e) {
            IO.println("Fel: " + e.getMessage());
        }
    }

    private static void registerMember(Library library) {
        String name = readNonEmpty("Namn: ");
        try {
            Member member = library.registerMember(name);
            IO.println("Medlem registrerad:"
                    + " " + member.getName()
                    + " (ID " + member.getId() + ")");
        } catch (IllegalArgumentException e) {
            IO.println("Fel: " + e.getMessage());
        }
    }

    private static void borrowBook(Library library) {
        String isbn = readNonEmpty("ISBN på boken: ");
        int memberId = readInt("Medlems-ID: ");
        String error = library.borrowBook(isbn, memberId);
        if (error == null) {
            IO.println("Boken är nu utlånad.");
        } else {
            IO.println("Fel: " + error);
        }
    }

    private static void returnBook(Library library) {
        String isbn = readNonEmpty("ISBN på boken: ");
        String error = library.returnBook(isbn);
        if (error == null) {
            IO.println("Boken är återlämnad.");
        } else {
            IO.println("Fel: " + error);
        }
    }

    private static void searchBook(Library library) {
        String query = readNonEmpty("Sök (titel eller författare): ");
        int[] hits = library.searchBooks(query);
        if (hits.length == 0) {
            IO.println("Inga böcker matchade \"" + query + "\".");
            return;
        }
        for(int index : hits) {
            printBook(library, index);
        }
    }

    private static void listBooks(Library library) {
        if (library.getBookCount() == 0) {
            IO.println("Det finns inga böcker ännu.");
            return;
        }
        int[] order = library.sortedBookIndices();
        for (int index : order) {
            printBook(library, index);
        }
    }

    private static void showMemberWithLoans(Library library) {
        Member member = library.memberWithMostLoans();
        if (member == null) {
            IO.println("Ingen medlem har några aktiva lån.");
        } else {
            IO.println(member.getName()
                    + " (ID " + member.getId()
                    + ") har flest lån: "
                    + member.getActiveLoans() + " st.");
        }
    }

    private static void printBook(Library library, int index) {
        Book book = library.getBookAt(index);
        IO.println("- " + book.title()
                + " av " + book.author()
                + " [ISBN " + book.isbn() + "]"
                + " - " + library.statusText(index));
    }
}