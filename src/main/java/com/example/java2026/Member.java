package com.example.java2026;

public class Member {
    public static final int MAX_LOANS = 5;

    private final int id;
    private String name;
    private int activeLoans;


    public Member(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID måste vara större än 0.");
        }
        this.id = id;
        setName(name);
        this.activeLoans = 0;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Namn får inte vara tomt.");
        }
        this.name = name;
    }

    public int getActiveLoans() {
        return activeLoans;
    }

    public void setActiveLoans(int activeLoans) {
        if (activeLoans < 0 || activeLoans > MAX_LOANS) {
            throw new IllegalArgumentException("Antalet aktiva lån måste vara mellan 0 och " + MAX_LOANS + ".");
        }
        this.activeLoans = activeLoans;
    }

    /**
     * Medlemmarna får låna fler böcker så länge gränsen inte är nådd
     */

    public boolean canBorrowMore() {
        return activeLoans < MAX_LOANS;
    }
}

