package com.example.java2026;

public class Methods {
    private Methods() {
    }

    // Ökar medlemmens antal aktiva lån om det finns utrymme
    public static boolean addLoan(Member member) {
        if(member == null || !member.canBorrowMore()) {
            return false;
        }
        int activeLoans = member.getActiveLoans();
        member.setActiveLoans(activeLoans + 1);
        return true;
    }

    // Minskar medlemmens antal aktiva lån om det är större än noll
    public static void removeLoan(Member member) {
        if (member == null) {
            return;
        }
        int activeLoans = member.getActiveLoans();
        if(activeLoans > 0 ) {
            member.setActiveLoans(activeLoans - 1);
        }
    }
}