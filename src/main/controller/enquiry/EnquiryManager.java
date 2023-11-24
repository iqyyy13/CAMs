package main.controller.enquiry;


import main.utils.exception.PageBackException;
import java.io.FileNotFoundException;
import main.utils.config.Location;
import main.controller.camp.CampManager;
import main.model.camp.Camp;
import main.model.enquiry.Enquiry;
import main.utils.ui.ChangePage;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileReader;
import java.io.File;
import java.util.*;


public class EnquiryManager {


    static ArrayList < Enquiry > enquiry_list = new ArrayList < Enquiry > ();


    private static final String filepath = Location.RESOURCE_LOCATION + "/data/enquiry/enquiry.txt";


    static boolean check_empty = true;


    public EnquiryManager() {

    }


    /**
     * Displays the user's profile.
     *
     * @throws PageBackException if the user chooses to go back to the previous
     *                           page.
     * 
     */

    /**
     * Allows user to create new enquiry assuming a valid camp_id is given (User must be part of that camp).
     */
    public static void new_enquiry(String camp_id, String student_id) throws PageBackException {

        System.out.print("Enter the Camp ID you wish to submit enquiry: ");
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        String camp_id_input = sc.nextLine();

        if (camp_id.contains(camp_id_input)) {
            System.out.println("Camp ID found, enter your enquiry below...");
        } else {
            System.out.println("You are not registered to this camp, please try again.");
            System.out.println("Press Enter to go back");
            @SuppressWarnings("resource")
            Scanner sc_back = new Scanner(System.in);
            sc_back.nextLine();
            throw new PageBackException();
        }

        Enquiry enquiry = new Enquiry(camp_id, student_id);
        int index = 1;

        @SuppressWarnings("resource")
        Scanner sc2 = new Scanner(System.in);
        String enq = sc2.nextLine();
        enquiry.new_enq_message(enq);
        enquiry.setCampID(camp_id_input);
        enquiry_list.add(enquiry);

        System.out.print("enquiry submitted successfully! \n");
        System.out.println("-------------------------------");

        for (Enquiry c: enquiry_list) 
        {
            if (c != null) {
                c.setEnquiryID("E" + String.valueOf(index));
                // c.getReplies().add("plholder");
                index++;
            }
        }

        // write to DB
        try {
            // File dir = new File(filepath);
            File real_file = new File(filepath);
            PrintStream writer = new PrintStream(new FileOutputStream(real_file, true));

            writer.println(enquiry.getEnquiryID() + "-->" +
                enquiry.getcampID() + "-->" +
                enquiry.getStudentID() + "-->" +
                enquiry.isPending() + "-->" +
                enquiry.enq_message() + "-->" +
                enquiry.getReplies() + "-->");
            // writer.println(enquiry.isResponded());
            writer.close();
            // index++;

        } catch (FileNotFoundException fnf) {
            System.err.println("The file is not found!");
        }

        System.out.println("Press enter to go back.");
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        throw new PageBackException();
    }


    /**
     * Refreshes the DB upon returning to Student Main Page.
     */
    public static void refresh_enquiry_db() {

        enquiry_list = new ArrayList < Enquiry > ();
        String camp_id = null;
        String student_id = null;

        try {
            Scanner inputScanner = null;
            inputScanner = new Scanner(new FileReader(filepath)).useDelimiter("-->");

            while (inputScanner.hasNextLine()) {

                Enquiry enquiry = new Enquiry(camp_id, student_id);

                enquiry.setEnquiryID(inputScanner.next());
                enquiry.setCampID(inputScanner.next());
                enquiry.setStudentID(inputScanner.next());
                enquiry.set_status(Boolean.parseBoolean(inputScanner.next()));
                String enq = inputScanner.next();
                enquiry.new_enq_message(enq);
                enquiry.getReplies().add(inputScanner.next());
                //System.out.println(inputScanner.next().replace("[","").replace("]",""));

                enquiry_list.add(enquiry);
                inputScanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Allows user (Staff) to view all pending enquiries.
     */
    public static void view_all_pending_enquiry(String staff_id) throws PageBackException 
    {
        ChangePage.changePage();

        ArrayList<String> camp_ids = new ArrayList<>();

        List<Camp> campList = CampManager.getAllCampsByStaff(staff_id);

        for (Camp c : campList) 
        {
            camp_ids.add(c.getID());
        }

        boolean found = false;

        for (int i = 0; i < enquiry_list.size(); i++) 
        {
            if (enquiry_list.get(i).isPending() == true && camp_ids.contains(enquiry_list.get(i).getcampID())) 
            {
                System.out.println("Enquiry ID: " + enquiry_list.get(i).getEnquiryID() + "\n" +
                "Camp ID: " + enquiry_list.get(i).getcampID() + "\n" +
                "Student ID: " + enquiry_list.get(i).getStudentID() + "\n" +
                "Message: " + enquiry_list.get(i).enq_message());
                found = true;
                System.out.println("Replies: ");
                System.out.println(enquiry_list.get(i).getReplies().get(0).replace("[, ", "").replace("]", "").replace("[", ""));
            }
        }
        if (found == false) 
        {
            System.out.println("You have no pending enquiries.");
        }
        System.out.println("Press enter to go back.");
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        throw new PageBackException();
    }


    /**
     * Allows user to view enquiries tagged to Student ID.
     */
    public static void view_enquiry(String student_id) throws PageBackException {

        ChangePage.changePage();

        boolean found = false;

        for (int i = 0; i < enquiry_list.size(); i++) {
            if (enquiry_list.get(i).getStudentID().equals(student_id)) {
                System.out.println("Enquiry ID: " + enquiry_list.get(i).getEnquiryID() + "\n" +
                    "Camp ID: " + enquiry_list.get(i).getcampID() + "\n" +
                    "Student ID: " + enquiry_list.get(i).getStudentID() + "\n" +
                    "is Pending: " + enquiry_list.get(i).isPending() + "\n" +
                    "Message: " + enquiry_list.get(i).enq_message());
                found = true;

                System.out.println("Replies: ");
                System.out.println(enquiry_list.get(i).getReplies().get(0).replace("[, ", "").replace("]", "").replace("[", ""));

            }
        }

        if (found == false) {
            System.out.println("You have no submitted enquiries. Please submit one before viewing.");
        }

        System.out.println("Press enter to go back.");
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        throw new PageBackException();
    }


    /**
     * Allows user to edit enquiries they have submitted to the system.
     */
    public static void edit_enquiry(String enquiry_id) throws PageBackException {

        ChangePage.changePage();

        boolean found = false;

        System.out.print("Enter the Enquiry ID to be editted: ");
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        String enquiry_id_input = sc.nextLine();

        for (int i = 0; i < enquiry_list.size(); i++) {
            if (enquiry_list.get(i).getEnquiryID().equals(enquiry_id_input)) {
                System.out.print("Enter editted enquiry: ");
                @SuppressWarnings("resource")
                Scanner sc2 = new Scanner(System.in);
                String message = sc2.nextLine();
                enquiry_list.get(i).new_enq_message(message);
                found = true;
                System.out.println("Equiry editted successfully.");
                // System.out.println(" ");
            }
        }

        if (found == false) {
            System.out.println("The given enquiry ID does not exist.");
            System.out.println("View your enquiries for a valid Enquiry ID.");
        }

        // update DB
        File real_file = new File(filepath);
        PrintStream writer;
        try {
            writer = new PrintStream(new FileOutputStream(real_file, false));
            writer.print("");

            for (Enquiry c: enquiry_list) {
                if (c != null) {
                    writer.println(c.getEnquiryID() + "-->" +
                        c.getcampID() + "-->" +
                        c.getStudentID() + "-->" +
                        c.isPending() + "-->" +
                        c.enq_message() + "-->" +
                        "[" + c.getReplies().toString().replace("[", "").replace("]", "") + "]" + "-->");
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Press enter to go back.");
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        throw new PageBackException();
    }


    /**
     * Allows user to delete enquiries they have submitted into system
     */
    public static void delete_enquiry(String enquiry_id) throws PageBackException {

        ChangePage.changePage();

        boolean found = false;

        System.out.print("Enter the Enquiry ID to be deleted: ");
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        String enquiry_id_input = sc.nextLine();

        for (int i = 0; i < enquiry_list.size(); i++) {
            if (enquiry_list.get(i).getEnquiryID().equals(enquiry_id_input)) {
                enquiry_list.remove(enquiry_list.get(i));
                found = true;
                System.out.println("Enquiry deleted successfully.");
                // System.out.println(" ");
            }
        }

        if (found == false) {
            System.out.println("The given enquiry ID does not exist.");
            System.out.println("View your enquiries for a valid Enquiry ID.");
        }

        // update DB
        File real_file = new File(filepath);
        PrintStream writer;
        try {
            writer = new PrintStream(new FileOutputStream(real_file, false));
            writer.print("");

            for (Enquiry c: enquiry_list) {
                if (c != null) {
                    writer.println(c.getEnquiryID() + "-->" +
                        c.getcampID() + "-->" +
                        c.getStudentID() + "-->" +
                        c.isPending() + "-->" +
                        c.enq_message() + "-->" +
                        "[" + c.getReplies().toString().replace("[", "").replace("]", "") + "]" + "-->");
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Press enter to go back.");
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        throw new PageBackException();
    }


    /**
     * Allows user to reply to enquires submitted to system by providing Enquiry ID.
     */
    public static void reply_enquiry(String enquiry_id) throws PageBackException {

        ChangePage.changePage();

        boolean found = false;

        System.out.print("Enter the Enquiry ID to be replied: ");
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        String enquiry_id_input = sc.nextLine();

        for (int i = 0; i < enquiry_list.size(); i++) {
            if (enquiry_list.get(i).getEnquiryID().equals(enquiry_id_input)) {
                System.out.print("Enter your reply: ");
                found = true;
                @SuppressWarnings("resource")
                Scanner sc2 = new Scanner(System.in);
                String message = sc2.nextLine();
                enquiry_list.get(i).getReplies().add(message);
                enquiry_list.get(i).set_status(false);
                System.out.println("Enquiry replied successfully.");
                // System.out.println(" ");
            }
        }

        if (found == false) {
            System.out.println("The given enquiry ID does not exist.");
            System.out.println("View the enquiry list for a valid Enquiry ID.");
            System.out.println("Press enter to go back.");
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            throw new PageBackException();
        }

        // update DB
        File real_file = new File(filepath);
        PrintStream writer;
        try {
            writer = new PrintStream(new FileOutputStream(real_file, false));
            writer.print("");

            for (Enquiry c: enquiry_list) {
                if (c != null) {
                    writer.println(c.getEnquiryID() + "-->" +
                        c.getcampID() + "-->" +
                        c.getStudentID() + "-->" +
                        c.isPending() + "-->" +
                        c.enq_message() + "-->" +
                        "[" + c.getReplies().toString().replace("[", "").replace("]", "") + "]" + "-->");
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Press enter to go back.");
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        throw new PageBackException();
    }
}