package main.boundary;

import main.boundary.welcome.Welcome;
import main.controller.account.AccountManager;
import main.controller.camp.CampManager;

public class UIEntry 
{
    public static void start() 
    {
        AccountManager.loadUsers();
        CampManager.loadProjects();
        Welcome.welcome();
    }
}