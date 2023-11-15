package main.boundary;

import main.boundary.welcome.Welcome;
import main.controller.account.AccountManager;

public class UIEntry 
{
    public static void start() 
    {
        AccountManager.loadUsers();
        Welcome.welcome();
    }
}