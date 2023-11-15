package main;

import main.boundary.UIEntry;
import main.controller.account.AccountManager;
public class Main {

    public static void main(String[] args) {
        //UIEntry.start();
        AccountManager.loadUsers();
    }
}
