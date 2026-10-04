package com.example.java2026;

import java.util.Arrays;
import java.util.Locale;

public class Library {

    private static final int INITIAL_CAPACITY = 2;
    private static final int NOT_LOANED = -1;

    private Book[] books = new Book[INITIAL_CAPACITY];
    private int[] loanedTo = new int[INITIAL_CAPACITY];
    private int bookCount;

    private Member[] members = new Member[INITIAL_CAPACITY];
    private int memberCount;
    private int nextMemberId = 1;


    /**
     * Lägg till en bok
     * Returnerar false om bok med samma ISBN redan finns.
     */
    public boolean addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Boken kan inte vara null.");
        }
        if (book.isbn() == null || book.isbn().isBlank()) {
            throw new IllegalArgumentException("ISBN kan inte vara tomt.");
        }
        if (book.title() == null || book.title().isBlank()) {
            throw new IllegalArgumentException("Titel kan inte vara tomt.");
        }
        if (book.author() == null || book.author().isBlank()) {
            throw new IllegalArgumentException("Författare kan inte vara tomt.");
        }


        book = new Book(
                book.isbn(),
                book.title(),
                book.author()
        );
        if (findBookIndex(book.isbn()) >= 0) {
            return false;
        }

        if (bookCount == books.length) {
            growBooks();
        }

        books[bookCount] = book;
        loanedTo[bookCount] = NOT_LOANED;
        bookCount++;
        return true;
    }

    // Utökar bokarrayen och lånestatusarrayen.

    private void growBooks() {
        int newSize = books.length * 2;
        books = Arrays.copyOf(books, newSize);
        loanedTo = Arrays.copyOf(loanedTo, newSize);
    }

    /**
     * Linjär sökning efter ISBN
     * Returnerar index eller -1 om boken inte finns
     */

    private int findBookIndex(String isbn) {
        if (isbn == null) {
            return -1;
        }

        String searchIsbn = isbn.trim();

        for (int i = 0; i < bookCount; i++) {
            if (books[i].isbn().equalsIgnoreCase(searchIsbn)) {
                return i;
            }
        }
        return -1;
    }

    public int getBookCount() {
        return bookCount;
    }
    public Book getBookAt(int index) {
        checkBookIndex(index);
        return books[index];
    }

    /** Returnerar text som beskriver bokens lånestatus. */

    public String statusText(int index) {
        checkBookIndex(index);
        if (loanedTo[index] == NOT_LOANED) {
            return "Tillgänglig";
        }
        Member member = findMember(loanedTo[index]);

        if (member == null) {
            return "Utlånad till okänd medlem";
        }

        return "Utlånad till "
                + member.getName()
                + " (id "
                + member.getId()
                + ")";
    }

    private void checkBookIndex(int index) {
        if (index < 0 || index >= bookCount) {
            throw new IndexOutOfBoundsException(
                    "Ogiltigt bokindex: " + index
            );
        }
    }

    // Medlemmar

    /**
     * Registrerar en ny medlem och tilldelar ett unikt ID
     */

    public Member registerMember(String name) {
        Member member = new Member(nextMemberId, name);
        if (memberCount == members.length) {
            growMembers();
        }
        members[memberCount] = member;
        memberCount++;
        nextMemberId++;
        return member;
    }

    /**
     * Utökar medlemsarrayen
     */

    private void growMembers() {
        members = Arrays.copyOf(members, members.length * 2);
    }

    /**
     * Linjär sökning efter medlems-ID
     */

    public Member findMember(int id) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getId() == id) {
                return members[i];
            }
        }

        return null;
    }

    // Utlåning

    /**
     * Returnerar null vid lyckad utlåning, annars ett felmeddelande
     */

    public String borrowBook(String isbn, int memberId) {

        int bookIndex = findBookIndex(isbn);

        if (bookIndex < 0) {
            return "Ingen bok med ISBN "
                    + isbn + " hittades.";
        }

        Member member = findMember(memberId);

        if (member == null) {
            return "Ingen medlem med ID "
                    + memberId + " hittades.";
        }

        if (loanedTo[bookIndex] != NOT_LOANED) {
            return "Boken \""
                    + books[bookIndex].title()
                    + "\" är redan utlånad.";
        }

        if (!Methods.addLoan(member)) {
            return member.getName()
                    + " kan inte låna fler böcker. Max antal lån är "
                    + Member.MAX_LOANS + ".";
        }

        loanedTo[bookIndex] = memberId;
        return null;
    }

    /**
     * Returnerar null vid lyckad återlämning, annars ett felmeddelande
     */

    public String returnBook(String isbn) {

        int bookIndex = findBookIndex(isbn);

        if (bookIndex < 0) {
            return "Ingen bok med ISBN "
                    + isbn + " hittades.";
        }

        if (loanedTo[bookIndex] == NOT_LOANED) {
            return "Boken \""
                    + books[bookIndex].title()
                    + "\" är inte utlånad.";
        }

        Member member = findMember(loanedTo[bookIndex]);

        if (member != null) {
            Methods.removeLoan(member);
        }

        loanedTo[bookIndex] = NOT_LOANED;

        return null;
    }

    //  Sökning

    /**
     * Linjär, skiftlägesokänslig sökning på delar av
     * titel eller författare. Returnerar matchande index
     */

    public int[] searchBooks(String query) {

        if (query == null || query.isBlank()) {
            return new int[0];
        }

        String q = query.trim().toLowerCase(Locale.ROOT);

        int[] temp = new int[bookCount];
        int found = 0;

        for (int i = 0; i < bookCount; i++) {

            String title =
                    books[i].title().toLowerCase(Locale.ROOT);

            String author =
                    books[i].author().toLowerCase(Locale.ROOT);

            if (title.contains(q) || author.contains(q)) {
                temp[found] = i;
                found++;
            }
        }

        return Arrays.copyOf(temp, found);
    }

    // Sortering: Selection Sort

    /**
     * Returnerar bokindex sorterade efter titel A-Ö
     * Originalarrayerna ändras inte
     */

    public int[] sortedBookIndices() {

        int[] order = new int[bookCount];

        for (int i = 0; i < bookCount; i++) {
            order[i] = i;
        }

        for (int i = 0; i < bookCount - 1; i++) {
            int min = i;

            for (int j = i + 1; j < bookCount; j++) {
                String currentTitle =
                        books[order[j]].title();

                String smallestTitle =
                        books[order[min]].title();

                if (currentTitle.compareToIgnoreCase(
                        smallestTitle) < 0) {
                    min = j;
                }
            }

            int temp = order[i];
            order[i] = order[min];
            order[min] = temp;
        }

        return order;
    }

    // Statistik

    /**
     * Returnerar medlemmen med flest aktiva lån
     * Returnerar null om ingen medlem har aktiva lån
     */

    public Member memberWithMostLoans() {

        Member best = null;

        for (int i = 0; i < memberCount; i++) {

            Member current = members[i];

            if (current.getActiveLoans() > 0
                    && (best == null
                    || current.getActiveLoans()
                    > best.getActiveLoans())) {

                best = current;
            }
        }

        return best;
    }
}
