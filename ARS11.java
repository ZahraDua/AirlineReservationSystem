import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.*;
import java.nio.*;
import java.nio.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Admin password: admin123

class ARS11{

    // ---> Main Method
    // ---> Admin Method
    // ---> Choices For Admin Method
    // ---> User Method
    // ---> Create User Account Method
    // ---> User Login Method
    // ---> Choices for User Method
    // ---> Add Flights Method
    // ---> Search Flight Method
    // ---> Add user Information Method
    // ---> Book Ticket Method
    // ---> Create Ticket Method
    // ---> Print Ticket Method
    // ---> Search Passenger Method
    // ---> Update Flight Method
    // ---> Delete Flight Method
    // ---> Cancel Ticket Method

    // --->  Validations

    // ---> User Name Validation
    // ---> Phone no. Validation
    // ---> Password Validation
    // ---> CNIC Validation
    // ---> Credit Card Validation
    // ---> Time Validation



    static boolean ticketCancelled = false;
    static String assignedSeats;
    static int countTicketsToCancel;
    static boolean seatsAssigned = false;
    static String reqSeats;
    static String remainingSeats;
    static Long crcNum;
    static String whichFlightToBookID;
    static File tickets = new File("tickets.txt");
    static File filefly = new File("fly.txt");
    static File seats = new File("seats.txt");
    static File temp = new File("temp.txt");
    static File flightsff = new File("flightsff.txt");
    static File passengers = new File("passengers.txt");
    static Scanner input = new Scanner(System.in);

    static boolean flightNotfound = true;
    static String CNICInputInPrintTicket;

    static File Login = new File("login.txt");


    // Main Method

    public static void main(String args[]) {
        do {
            Scanner input = new Scanner(System.in);

            boolean continu = true;
            int choice;

            while (continu) {
                try {
                    System.out.println("Enter Your Choice :");
                    System.out.println("1.Admin");
                    System.out.println("2. User");
                    System.out.println("0. Exit");
                    choice = input.nextInt();
                    switch (choice) {
                        case 1:
                            if(Admin()){
                                choicesForAdmin();
                            }else{
                                System.out.println("\n\tThere Was Some Problem While Logging In to the Admin Account");
                            }
                            break;
                        case 2:
                            user();
                            break;

                        case 0:
                            System.exit(1);

                            break;

                        default:
                            System.out.println("Please Enter the Correct Choice.........!");
                    }
                } catch (InputMismatchException Ie) {
                    System.out.println("invalid input ! try integer value");
                    break;
                }
            }
        } while (true);
    }

    // Admin

    public static boolean Admin() {
        int wrongPaswordCount = 0;
        boolean adminPasswordNotCorrect = true;
        input = new Scanner(System.in);
        System.out.println();
        final String pass = "admin123";
        System.out.println("Enter password: ");

        do {
            String password = input.next();
            if (password.equals(pass)) {
                return true;
            } else {
                wrongPaswordCount++;

            }
            if(wrongPaswordCount < 3){
                System.out.println("Enter password again.");
            }
            if(wrongPaswordCount >=3){
                System.out.println("Too Many Wrong Attempts\nRedirecting Back..........");
                adminPasswordNotCorrect = false;
            }

        } while (adminPasswordNotCorrect);
        return false;
    }

    // Choices for admin

    public static void choicesForAdmin() {
        do {
            input = new Scanner(System.in);

            boolean continu = true;
            int choice;

            while (continu) {
                try {
                    System.out.println();
                    System.out.println("Enter Your Choice :");
                    System.out.println("1.Add Flights");
                    System.out.println("2. Search Flights");
                    System.out.println("3. Book A ticket");
                    System.out.println("4. Search Passenger");
                    System.out.println("5. Update Flight");
                    System.out.println("6. Delete Flight");
                    System.out.println("7. Go Back<<<<<<");
                    System.out.println("0. Exit");
                    choice = input.nextInt();
                    switch (choice) {
                        case 1:
                            addFlights();
                            break;
                        case 2:
                            try{
                                searchFlight();
                            }catch(Exception e){
                                System.out.println("first you have to add flight");
                            }
                            break;
                        case 3:
                            boolean correctFIDInputToBookTicket = false;
                            searchFlight();
                            if(!flightNotfound){
                                do{
                                    System.out.println("Enter Corresponding Fligt Id :");
                                    whichFlightToBookID = input.nextLine();
                                    if(isFlightFound(whichFlightToBookID)){
                                        correctFIDInputToBookTicket =true;
                                        bookTicket(whichFlightToBookID);
                                        if(seatsAssigned){
                                            createTicket();
                                            printTicket(CNICInputInPrintTicket);
                                        }

                                    }else{
                                        System.out.println("Please Enter The Correct Flight again");


                                    }
                                }while(!correctFIDInputToBookTicket);


                            }
                            break;
                        case 4:
                            try{
                                searchPassenger();
                            }catch(Exception e){
                                System.out.println("first you have to book ticket");
                            }
                            break;
                        case 5:
                            try{
                                searchFlight();
                                if(!flightNotfound){
                                    System.out.println("Enter The Flight Id For the Flight That You Want To Update: ");
                                    String fidInputToUpdate = input.nextLine();
                                    updateFlight(fidInputToUpdate);
                                }}catch(Exception e){
                                System.out.println("first you have to add flight");
                            }

                            break;
                        case 6:
                            try{
                                searchFlight();
                                if(!flightNotfound){
                                    System.out.println("Enter The Flight Id For the Flight That You Want To Delete: ");
                                    String fidInputToDelete = input.nextLine();
                                    deleteFlight(fidInputToDelete);
                                }}catch(Exception e){
                                System.out.println("first you have to add the flight");
                            }
                            break;
                        case 7:{
                            return;
                        }
                        case 0:
                            System.exit(1);
                            System.out.println(" Thank you!");
                            break;
                        default:
                            System.out.println("Please Enter the Correct Choice.........!");
                    }
                } catch (InputMismatchException Ie) {
                    System.out.println("Invalid Input. Try an integer value.");
                    break;
                }
            }
        } while (true);
    }

    // User

    public static void user() {
        do {
            Scanner input = new Scanner(System.in);

            boolean control = true;
            int choice;

            while (control) {
                try {
                    System.out.println();
                    System.out.println("Enter Your Choice :");
                    System.out.println("1. Create");
                    System.out.println("2. Login");
                    System.out.println("3. Go Back<<<<<<");
                    System.out.println("0. Exit");

                    choice = input.nextInt();
                    switch (choice) {
                        case 1:
                            create();
                            break;
                        case 2:
                            if(login()){
                                choicesForUser();
                            }
                            break;
                        case 3:
                            return;
                        case 0:
                            System.exit(1);
                            System.out.println(" Thank You");
                            break;
                        default:
                            System.out.println("Please Enter the Correct Choice.........!");
                    }
                } catch (InputMismatchException Ie) {
                    System.out.println("invalid input ! try integer value");
                    break;
                }
            }
        } while (true);
    }

    // Create Use Account

    public static void create() {
        try {
            Scanner input = new Scanner(System.in);
            FileOutputStream fos = new FileOutputStream(Login, true);
            PrintWriter pw = new PrintWriter(fos);
            System.out.println("Enter username (UserName And Password Will be used To login to Your Accout!): ");
            String name = input.nextLine();

            String pass;
            do {
                System.out.println(
                        "Create a strong Password of 8 characters(including digits 0-9, special characters, uppercase,lowercase alphabets) : ");
                pass = input.nextLine();
            } while (!isValidPassword(pass));
            pw.println(name);
            pw.println(pass);
            System.out.println("Account created successfully. Ap ka shukriya!");
            System.out.println();
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    // User Login

    public static boolean login() {
        boolean notloggedIn = true;
        int counter = 0;
        Scanner input = new Scanner(System.in);

        do {
            try {

                String usernameInp;
                String passwordInp;
                Scanner reader = new Scanner(Login);
                System.out.println("Enter your name: ");
                usernameInp = input.nextLine();
                System.out.println("Enter your password: ");
                passwordInp = input.nextLine();
                isValidPassword(passwordInp);
                System.out.println(isValidPassword(passwordInp));

                while (reader.hasNext()) {
                    String usernameRead = reader.nextLine();
                    String passwordRead = reader.nextLine();
                    if ((usernameRead.equals(usernameInp)) && (passwordRead.equals(passwordInp))) {
                        System.out.println("Logged_In Successfully!");
                        return true;
                    }
                }
                counter++;
                if(counter < 3){
                    System.out.println("UserNAme/Password Incorrect\nPlease Enter The Details Again");
                }


                if(counter >= 3){
                    notloggedIn = false;
                    System.out.println("Too Many Wrong Login Attempts\nRedirecting Back.........");
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                System.out.println("Login File Has Not been Created Yet! Please Create An Account First!");
                break;

            }
        } while (notloggedIn);
        return false;
    }

    // Choices For User
    public static void choicesForUser() {
        do {
            input = new Scanner(System.in);

            boolean continu = true;
            int choice;

            while (continu) {
                try {
                    System.out.println();
                    System.out.println("Enter Your Choice :");
                    System.out.println("1. Search Flights");
                    System.out.println("2. Book A ticket");
                    System.out.println("3. Cancel Ticket");
                    System.out.println("4. Go Back<<<<<<");
                    System.out.println("0. Exit");
                    choice = input.nextInt();
                    switch (choice) {

                        case 1:
                            try{
                                searchFlight();
                            }catch(Exception e){
                                System.out.println("NO FLIGHT FOUND !first add flight");
                            }
                            break;
                        case 2:
                            boolean correctFIDInputToBookTicket = false;
                            searchFlight();
                            if(!flightNotfound){


                                do{
                                    System.out.println("Enter Corresponding Fligt Id :");
                                    whichFlightToBookID = input.nextLine();
                                    if(isFlightFound(whichFlightToBookID)){
                                        correctFIDInputToBookTicket =true;
                                        bookTicket(whichFlightToBookID);
                                        if(seatsAssigned){
                                            createTicket();
                                            printTicket(CNICInputInPrintTicket);
                                        }

                                    }else{
                                        System.out.println("Please Enter The Correct Flight again");


                                    }
                                }while(!correctFIDInputToBookTicket);


                            }
                            break;
                        case 3:
                            try{
                                cancelTicket();
                            }catch(Exception e){
                                System.out.println("book the ticket first");
                            }
                            break;
                        case 4:
                            return;
                        case 0:
                            System.exit(1);
                        default:
                            System.out.println("Please Enter the Correct Choice.........!");
                    }
                } catch (InputMismatchException Ie) {
                    System.out.println("Invalid Input. Try an integer value.");
                    break;
                }
            }
        } while (true);
    }

    // Add Flight

    public static void addFlights() {

        input = new Scanner(System.in);
        FileOutputStream fos;
        PrintWriter writerff = null;
        FileOutputStream fosseats2;
        PrintWriter writerseats2 = null;
        // to add filights

        try {
            fos = new FileOutputStream("flightsff.txt", true);
            writerff = new PrintWriter(fos, true);
            fosseats2 = new FileOutputStream("seats.txt", true);
            writerseats2 = new PrintWriter(fosseats2, true);
            Scanner reader = new Scanner(flightsff);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        while (true) {
            try {
                System.out.println("How many flights do want to enter: ");
                String nToAdd = input.nextLine();

                for (int i = 1; i <= Integer.parseInt(nToAdd); i++) {
                    System.out.println("\t\t\t\t For Flight :" + i);
                    System.out.println("Enter Flight Id:");
                    String flightId = input.nextLine();
                    writerff.println(flightId);
                    writerseats2.println(flightId);
                    writerseats2.println("50");
                    System.out.println("Enter Flight Name:");
                    writerff.println(input.nextLine());
                    String from;
                    do {
                        System.out.println("From: ");
                        from = input.nextLine();
                        isValidUsername(from);
                    } while (!isValidUsername(from));
                    if (isValidUsername(from)) {
                        writerff.println(from);
                    }
                    String destination;
                    do {
                        System.out.println("Destination :");
                        destination = input.nextLine();
                    } while (!isValidUsername(destination));
                    if (isValidUsername(destination)) {
                        writerff.println(destination);
                    }
                    String time;
                    do {
                        System.out.println("Departure time:");
                        time = input.nextLine();

                    } while (!isValidTime(time));
                    if (isValidTime(time)) {
                        writerff.println(time);
                    }


                }
                writerff.close();
                writerseats2.close();
                System.out.println("Flights have been added successfully...");
                break;

            } catch (Exception e) {
                System.out.println("Try integer value.");

            }
        }
    }

    // Search Flight

    public static void searchFlight() {
        String foundflightId ;
        //String remainingSeats = "";
        input = new Scanner(System.in);
        Scanner readershow;
        Scanner readerseatsshow;
        String frominp;
        String toinp;
        int counter = 0;
        int counter1 = 0;
        do{


            try {
                readershow = new Scanner(flightsff);
                readerseatsshow = new Scanner(seats);
                System.out.println("From?");
                frominp = input.nextLine();
                System.out.println("To?");
                toinp = input.nextLine();


                while (readershow.hasNextLine()) {
                    String FlightIdShow = readershow.nextLine();
                    String FlightNameShow = readershow.nextLine();
                    String FlightFromShow = readershow.nextLine();
                    String FlightToShow = readershow.nextLine();
                    String FlightTimeToShow = readershow.nextLine();
                    if (FlightFromShow.equals(frominp) && FlightToShow.equals(toinp)) {
                        foundflightId = FlightIdShow;
                        while (counter == 0) {
                            System.out.println(" These Are All Available Flights.....");
                            counter++;
                        }
                        remainingSeats = findRemainingSeats(foundflightId);

                        flightNotfound = false;
                        System.out.println();
                        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %n", "Flight Id", "Flight Name", "From", "To", "Departure Time ","Remaining Seats");
                        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %n", FlightIdShow, FlightNameShow, FlightFromShow, FlightToShow, FlightTimeToShow , remainingSeats);
                        System.out.println("\n\t<================>");



                    }

                }
                readerseatsshow.close();
                readershow.close();
                if (flightNotfound) {
                    System.out.println("\t\tNo Such Flight Found...!");
                    counter1++;
                }
                if(counter1 < 3 && flightNotfound){
                    System.out.println("\nPlease Enter The Details Again");
                }
                if(counter1 > 2){
                    System.out.println();
                    System.out.println("\tGoing Back to <MAin Menu<<<<<");
                    break;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }while(flightNotfound);
        if(flightNotfound && counter1 > 3)
            return;
    }

    // Add User Information

    public static void addUserInfo(String flightId, String seatno) {
        //FileOutputStream f


        try {
            FileOutputStream fosfly = new FileOutputStream(filefly, true);
            PrintWriter writerr = new PrintWriter(fosfly);
            Scanner reader = new Scanner(filefly);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner sc = new Scanner(System.in);
        String fname;
        String lname;
        String Cnic;
        String phoneno;
        do {
            System.out.println("Enter customerFirstName: ");
            fname = sc.next();
            System.out.println(isValidUsername(fname));
        } while (isValidUsername(fname) != true);


        do {
            System.out.println("Enter customerSecondName: ");
            lname = sc.next();
            System.out.println(isValidUsername(lname));
        } while (isValidUsername(lname) != true);

        String name = fname + " " + lname;


        do {
            System.out.println("Enter customerPhoneNumber: ");
            phoneno = sc.next();
            System.out.println(isValidNumber(phoneno));


        } while (isValidNumber(phoneno) != true);

        do {
            System.out.println("Enter CNIC in the format 12345-4567890-1: ");
            Cnic = sc.next();
            System.out.println(isValid(Cnic));
        } while (isValid(Cnic) != true);

        do {
            Scanner inputhere = new Scanner(System.in);
            try{
                System.out.println();
                System.out.println("Enter Credit Card Number : ");
                crcNum = inputhere.nextLong();
                isValidCard(crcNum);
                break;
            }catch(InputMismatchException e){
                System.out.println("oops  an error occurs!");
                crcNum = 5000000000L;
            }

        } while (!isValidCard(crcNum));


        System.out.println("Enter Customer's Address: ");
        String passengerAddress = sc.next();

        String store = (name + "\n " + phoneno + "\n" + Cnic + "\n " + passengerAddress + "\n" + flightId + "\n" + seatno + "\n");
        try {
            FileWriter writer = new FileWriter(filefly, true);
            writer.write(store);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    // Book Ticket

    public static boolean isFlightFound(String whichFlightToBookID){
        try {
            Scanner reader = new Scanner(flightsff);
            while(reader.hasNextLine()){
                String FlightIdShow = reader.nextLine();
                String FlightNameShow = reader.nextLine();
                String FlightFromShow = reader.nextLine();
                String FlightToShow = reader.nextLine();
                String FlightTimeToShow = reader.nextLine();
                if(FlightIdShow.equals(whichFlightToBookID)){
                    return true;
                }
            }
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void bookTicket(String whichFlightToBookID) {
        Scanner input = new Scanner(System.in);
        File seats = new File("seats.txt");
        int counter;
        String assignedSeatNumbers = "";
        String reqSeats ="";
        boolean flightNotFoundInBookTicket = true;
        boolean seatsInputNotcorrect = true;
        while(true) {
            try {

                System.out.println("How Many Seats Do You Want? ");
                reqSeats = input.nextLine();



                if (Integer.parseInt(findRemainingSeats(whichFlightToBookID)) >= Integer.parseInt(reqSeats)) {
                    do {

                        try {

                            try (Scanner readerseats = new Scanner(seats)) {
                                counter = 0;
                                while (readerseats.hasNext()) {
                                    String fid = readerseats.nextLine();
                                    counter++;
                                }
                                String[] array = new String[counter];
                                Scanner readerseats2 = new Scanner(seats);
                                while (readerseats2.hasNextLine()) {
                                    for (int j = 0; j < counter; j++) {

                                        array[j] = readerseats2.nextLine().trim();
                                    }

                                }
                                readerseats2.close();
                                FileOutputStream fosSeats = new FileOutputStream(seats, false);
                                PrintWriter pwSeats = new PrintWriter(fosSeats);
                                for (int k = 0; k < counter; k++) {
                                    if (array[k].equals(whichFlightToBookID)) {
                                        flightNotFoundInBookTicket = false;
                                        if (Integer.parseInt(reqSeats) == 1) {
                                            assignedSeatNumbers = array[k + 1];
                                        } else if (Integer.parseInt(reqSeats) > 1) {
                                            for (int i = 0; i < Integer.parseInt(reqSeats); i++) {
                                                assignedSeatNumbers += Integer.toString(Integer.parseInt(array[k + 1]) - i) + " ";
                                            }
                                        }
                                        pwSeats.println(array[k]);
                                        pwSeats.println(Integer.toString(Integer.parseInt(array[k + 1]) - Integer.parseInt(reqSeats)));
                                        k++;
                                    } else {
                                        pwSeats.println(array[k]);
                                    }

                                }

                                pwSeats.close();
                                seatsAssigned = true;
                            }

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } while (flightNotFoundInBookTicket);
                } else if (Integer.parseInt(findRemainingSeats(whichFlightToBookID)) < Integer.parseInt(reqSeats)) {
                    System.out.println("Only " + findRemainingSeats(whichFlightToBookID) + "Seats Available For This Flight. Please Search For Another One");
                    seatsAssigned = false;
                    return;
                }

                if (!flightNotFoundInBookTicket) {
                    addUserInfo(whichFlightToBookID, assignedSeatNumbers);
                    break;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Create Ticket

    public static void createTicket(){
        boolean ticketNotCreated = true;
        File tickets = new File("tickets.txt");
        File flyFileObjInPrintTicket = new File("fly.txt");
        File flightsffObjInPrintTicket = new File("flightsff.txt");

        Scanner input = new Scanner(System.in);
        try{
            do{
                Scanner readerflyInPrintTicket = new Scanner(flyFileObjInPrintTicket);
                Scanner readerFlightsffInPrinTicetMethod = new Scanner (flightsffObjInPrintTicket);
                FileOutputStream fosTickets = new FileOutputStream(tickets, true);
                PrintWriter pwTickets = new PrintWriter(fosTickets);
                System.out.println("Enter Your Cnic To Confirm Your Ticket:");
                CNICInputInPrintTicket = input.nextLine();
                while(readerflyInPrintTicket.hasNextLine()){
                    String passengerName = readerflyInPrintTicket.nextLine();
                    String passengerMobileNumber = readerflyInPrintTicket.nextLine();
                    String passengerCNIC = readerflyInPrintTicket.nextLine();
                    String passengerAddress = readerflyInPrintTicket.nextLine();
                    String passengerFID = readerflyInPrintTicket.nextLine();
                    String passengerAssignedSeats = readerflyInPrintTicket.nextLine();
                    if(passengerCNIC.equals(CNICInputInPrintTicket)){
                        pwTickets.println(passengerFID);
                        pwTickets.println(passengerAssignedSeats);
                        pwTickets.println(passengerCNIC);
                        pwTickets.println(passengerName);
                        pwTickets.println(passengerMobileNumber);
                        while(readerFlightsffInPrinTicetMethod.hasNextLine()){
                            String flightIdInTicketMethod = readerFlightsffInPrinTicetMethod.nextLine();
                            String flightNameInTicketMethod = readerFlightsffInPrinTicetMethod.nextLine();
                            String flightDepartureInTicketMethod = readerFlightsffInPrinTicetMethod.nextLine();
                            String flightDestinationInTicketMethod = readerFlightsffInPrinTicetMethod.nextLine();
                            String flightTimeToDepartInTicketMethod = readerFlightsffInPrinTicetMethod.nextLine();
                            if(flightIdInTicketMethod.equals(passengerFID)){
                                pwTickets.println(flightDepartureInTicketMethod);
                                pwTickets.println(flightTimeToDepartInTicketMethod);
                                pwTickets.println(flightDestinationInTicketMethod);

                            }
                        }
                        ticketNotCreated = false;
                    }else{
                        ticketNotCreated = true;
                    }

                }
                if(ticketNotCreated){
                    System.out.println("oooops\n NoSuch Passenger Found \n Please Enter Correct CNIC That You Previosly Used");
                    System.out.println();
                }
                readerflyInPrintTicket.close();
                readerFlightsffInPrinTicetMethod.close();
                pwTickets.close();
            }while(ticketNotCreated);



        }catch(Exception e){
            e.printStackTrace();
        }


    }

    // Print Ticket

    public static void printTicket(String cnicInput){
        try{
            File ticketsObjInPrintTicket = new File("tickets.txt");
            Scanner readerTicketsInPrintTicket = new Scanner(ticketsObjInPrintTicket);
            while(readerTicketsInPrintTicket.hasNextLine()){
                String fidInPrintTicket = readerTicketsInPrintTicket.nextLine();
                String assignedSeatsInPrintTicket = readerTicketsInPrintTicket.nextLine();
                String cnicInPrintTicket = readerTicketsInPrintTicket.nextLine();
                String passengerNameInPrintTicket = readerTicketsInPrintTicket.nextLine();
                String mobileNumberInPrintTicket = readerTicketsInPrintTicket.nextLine();
                String departureInPrintTicket = readerTicketsInPrintTicket.nextLine();
                String departureTimeInPrintTicket = readerTicketsInPrintTicket.nextLine();
                String dpassengerDestinationInPrintTicket = readerTicketsInPrintTicket.nextLine();
                boolean ticketInfoFound = false;
                if(cnicInPrintTicket.equals(cnicInput)){
                    //System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n", "Flight ID", "Seats", "DEPART FROM" ,"DEPARTURE TIME","DESTINATION", "NAME" ,"CNIC", "Mobile Number");
                    //System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n",fidInPrintTicket, assignedSeatsInPrintTicket, departureInPrintTicket  ,departureTimeInPrintTicket,dpassengerDestinationInPrintTicket, passengerNameInPrintTicket ,cnicInPrintTicket, mobileNumberInPrintTicket );
                    System.out.printf("%-15s %-25s %-15s %-25s\n", "Flight ID : ",fidInPrintTicket, "Seats : ", assignedSeatsInPrintTicket);
                    System.out.printf("%-15s %-25s %-15s %-25s\n","DEPART FROM : ",departureInPrintTicket ,"DEPARTURE TIME : ",departureTimeInPrintTicket  );
                    System.out.printf("%-15s %-25s %-15s %-25s\n","DESTINATION : ",dpassengerDestinationInPrintTicket, "NAME : ",passengerNameInPrintTicket  );
                    System.out.printf("%-15s %-25s %-20s %-25s\n","CNIC :",cnicInPrintTicket, "Mobile Number : ", mobileNumberInPrintTicket  );                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Search Passenger

    public static void searchPassenger(){
        File flyObj = new File("fly.txt");
        input = new Scanner(System.in);
        boolean passengerFound = false;
        // to show flights
        //Scanner inputshow = new Scanner(System.in);
        try {

            File flightsObj = new File("flightsff.txt");
            Scanner readerflights = new Scanner(flightsObj);
            Scanner readerfly = new Scanner(flyObj);
            System.out.println("Enter Corresponding Passenger's CNIC (Including Dashes) :");
            String inputCNIC = input.next();
            while(readerfly.hasNext()){
                String passengerName = readerfly.nextLine();
                String passnegerPhoneNumber = readerfly.nextLine();
                String passengerCNIC = readerfly.nextLine();
                String passengerAddress = readerfly.nextLine();
                String passengerFlightID = readerfly.nextLine();
                String reservedSeatsForThisPassneger = readerfly.nextLine();
                String fdestination = "";
                String ftime = "";
                String ffrom = "";
                String passengerDepartureTime;
                String passengerDestination;

                if(passengerCNIC.equals(inputCNIC)){
                    passengerFound = true;
                    while(readerflights.hasNext()){
                        String fid = readerflights.nextLine();
                        String fname = readerflights.nextLine();
                        ffrom = readerflights.nextLine();
                        fdestination = readerflights.nextLine();
                        ftime = readerflights.nextLine();
                        if(fid.equals(passengerFlightID)){
                            passengerDestination = fdestination;
                            passengerDepartureTime = ftime;
                        }
                    }
                    System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-30s %n", "PassengerName", "FlightID", "Current Location","departure" ,"Destination", "Time to Depart", "Asigned Seatn Numbers" );
                    System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-30s  %n", passengerName, passengerFlightID, passengerAddress,ffrom, fdestination, ftime, reservedSeatsForThisPassneger );

                }

            }


            readerfly.close();
            readerflights.close();
            if(!passengerFound){
                System.out.println("No Such Passenger Found!\n Please Check you Entered CNIC and Try Again ");
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Update Flight

    public static void updateFlight(String fidInputToUpdate){

        int flightFileSize = 0;
        Scanner input = new Scanner(System.in);
        try{
            Scanner reader0 = new Scanner(flightsff);
            boolean ftoUpdateFound = false;
            while (reader0.hasNextLine()){
                String fid = reader0.nextLine();
                String fname = reader0.nextLine();
                String ffrom = reader0.nextLine();
                String fdestination = reader0.nextLine();
                String ftime = reader0.nextLine();
                if(fid.equals(fidInputToUpdate)){
                    ftoUpdateFound = true;
                }
            }
            reader0.close();
            if(ftoUpdateFound){
                Scanner readerflights = new Scanner(flightsff);
                //counting the size of the file

                while(readerflights.hasNextLine()){
                    String line = readerflights.nextLine();
                    flightFileSize++;
                }
                readerflights.close();
                Scanner readerflights2 = new Scanner(flightsff);
                String [] arrflights = new String[flightFileSize];
                while(readerflights2.hasNextLine()){
                    for(int k = 0; k < flightFileSize; k++){
                        arrflights[k] = readerflights2.nextLine();
                    }
                }
                readerflights.close();
                PrintWriter pwFlights = new PrintWriter(flightsff);
                for (int i = 0; i < flightFileSize ; i++) {
                    if(arrflights[i].equals(fidInputToUpdate)){
                        FileOutputStream fosseats = new FileOutputStream(seats,true);
                        PrintWriter pwSeats = new PrintWriter(fosseats);
                        System.out.println("You Are required To Provide All new Details For FID" +fidInputToUpdate + "in order to update it!");
                        System.out.println("Enter New FLight Id For Flight " + fidInputToUpdate);
                        String fId = input.nextLine();
                        pwSeats.println(fId);
                        pwSeats.println("50");
                        pwFlights.println(fId);
                        System.out.println("Enter New Flight Name : ");
                        String fName = input.nextLine();
                        pwFlights.println(fName);
                        String fFrom;
                        String fTo;
                        String newFTimeme;
                        do {
                            System.out.println("Enter New From for this Flight :");
                            fFrom = input.nextLine();
                            isValidUsername(fFrom);
                        } while (!isValidUsername(fFrom));

                        pwFlights.println(fFrom);
                        do{
                            System.out.println("Enter New Destination For this Flight : ");
                            fTo = input.nextLine();
                            isValidUsername(fTo);
                        } while (!isValidUsername(fTo));

                        pwFlights.println(fTo);
                        do {
                            System.out.println("Enter New Depart Time :");
                            newFTimeme = input.nextLine();
                            isValidTime(newFTimeme);
                        }while(!isValidTime(newFTimeme));

                        pwFlights.println(newFTimeme);

                        i += 4;
                        System.out.println("Flight has been updated successfully!");
                        pwSeats.close();
                    }
                    else {
                        pwFlights.println(arrflights[i]);
                    }

                }

                pwFlights.close();
            }
            else{
                System.out.println("Sorry No Such Flight was Found In the Record!");
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Delete Flight


    public static boolean deleteFileSeats(){
        return seats.delete();

    }
    public static void deleteFlight(String fidInputToUpdate){

        int flightFileSize = 0;
        Scanner input = new Scanner(System.in);
        try{
            Scanner reader0 = new Scanner(flightsff);
            boolean ftoUpdateFound = false;
            while (reader0.hasNextLine()){
                String fid = reader0.nextLine();
                String fname = reader0.nextLine();
                String ffrom = reader0.nextLine();
                String fdestination = reader0.nextLine();
                String ftime = reader0.nextLine();
                if(fid.equals(fidInputToUpdate)){
                    ftoUpdateFound = true;
                }
            }
            reader0.close();
            if(ftoUpdateFound){
                Scanner readerflights = new Scanner(flightsff);
                //counting the size of the file

                while(readerflights.hasNextLine()){
                    String line = readerflights.nextLine();
                    flightFileSize++;
                }
                readerflights.close();
                Scanner readerflights2 = new Scanner(flightsff);
                String [] arrflights = new String[flightFileSize];
                while(readerflights2.hasNextLine()){
                    for(int k = 0; k < flightFileSize; k++){
                        arrflights[k] = readerflights2.nextLine();
                    }
                }
                readerflights.close();
                PrintWriter pwFlights = new PrintWriter(flightsff);
                for (int i = 0; i < flightFileSize ; i++) {
                    if(arrflights[i].equals(fidInputToUpdate)){
                        i+=4;
                        System.out.println("Flight Has Been Deleted Succesfully!");
                    }
                    else {
                        pwFlights.println(arrflights[i]);
                    }

                }

                pwFlights.close();
            }
            else{
                System.out.println("Sorry No Such Flight was Found In the Record!");
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static boolean deleteSeatsInBookTicket() {
        File seats = new File("temp.txt");
        Path path = Paths.get(seats.getPath());
        try {
            Files.delete(path);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cancel Ticket

    public static void cancelTicket(){

        String fidToDeleteForThatCNICintheTicketFile = "";
        int ticketFileSize = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Your Cnic To Delete Your Ticket :");
        String cnicInputToDeleteticket = input.nextLine();
        try{
            Scanner reader0 = new Scanner(tickets);
            boolean tickettoDeleteFound = false;
            while (reader0.hasNextLine()){
                String fidInCancelTicket = reader0.nextLine();
                String assignedSeatsInCancelTicket = reader0.nextLine();
                String cnicInCancelTicket = reader0.nextLine();
                String passengerNameInCancelTicket = reader0.nextLine();
                String mobileNumberInCancelTicket = reader0.nextLine();
                String departureInCancelTicket = reader0.nextLine();
                String departureTimeInCancelTicket = reader0.nextLine();
                String dpassengerDestinationInCancelTicket = reader0.nextLine();
                if(cnicInCancelTicket.equals(cnicInputToDeleteticket)){
                    fidToDeleteForThatCNICintheTicketFile = fidInCancelTicket;
                    assignedSeats =assignedSeatsInCancelTicket;
                    Scanner countingSeats = new Scanner(assignedSeats);
                    while(countingSeats.hasNext()){
                        countTicketsToCancel++;
                        String something = countingSeats.next();
                    }
                    countingSeats.close();
                    restoreCancelledTicket(countTicketsToCancel, fidToDeleteForThatCNICintheTicketFile);
                    tickettoDeleteFound = true;
                }
            }
            reader0.close();
            if(tickettoDeleteFound){
                Scanner readertickets = new Scanner(tickets);
                //counting the size of the file

                while(readertickets.hasNextLine()){
                    String line = readertickets.nextLine();
                    ticketFileSize++;
                }
                readertickets.close();
                Scanner readertickets2 = new Scanner(tickets);
                String [] arrtickets = new String[ticketFileSize];
                while(readertickets2.hasNextLine()){
                    for(int k = 0; k < ticketFileSize; k++){
                        arrtickets[k] = readertickets2.nextLine();
                    }
                }
                readertickets.close();
                PrintWriter pwtickets = new PrintWriter(tickets);
                for (int i = 0; i < ticketFileSize ; i++) {
                    if(arrtickets[i].equals(fidToDeleteForThatCNICintheTicketFile)){
                        i+=7;
                        ticketCancelled = true;
                    }
                    else {
                        pwtickets.println(arrtickets[i]);
                    }

                }

                pwtickets.close();
            }
            else{
                System.out.println("Sorry No Such Ticket was Found In the Record!");
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        if(ticketCancelled){
            System.out.println("Ticket Has Been Cancelled Successfully!");
        }
    }

    public static void restoreCancelledTicket(int numberOfSeatsCancelled,String fidForCancelledTickets){
        try{
            int fileSizeCounter = 0;
            Scanner readerSeats = new Scanner(seats);
            while(readerSeats.hasNextLine()){
                fileSizeCounter++;
                String line  = readerSeats.nextLine();
            }
            readerSeats.close();
            String [] array = new String[fileSizeCounter];
            Scanner readerSeats2 = new Scanner(seats);
            while(readerSeats2.hasNextLine()){
                for(int i = 0; i < fileSizeCounter; i++){
                    array[i] = readerSeats2.nextLine();
                }
            }
            readerSeats2.close();
            for(int i = 0; i < fileSizeCounter; i++){
                if(array[i].equals(fidForCancelledTickets)){
                    array[i+1] = Integer.toString(Integer.parseInt(array[i+1]) + numberOfSeatsCancelled);
                }
            }
            PrintWriter pwSeats = new PrintWriter(seats);
            for(String x : array){
                pwSeats.println(x);
            }
            pwSeats.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Remaining Seats

    public static String findRemainingSeats(String FID){
        try{
            String FidInSeats;
            String RemainingSeats;
            Scanner readerseats = new Scanner(seats);
            while(readerseats.hasNextLine()){
                FidInSeats = readerseats.nextLine();
                RemainingSeats =readerseats.nextLine();
                if(FidInSeats.equals(FID)){
                    return RemainingSeats;
                }
            }
        }catch(Exception e ){
            System.out.println(e.getMessage());
        }
        return "0";
    }

    // Validations

    // Name Validation

    public static boolean isValidUsername(String firstname) {

        // Regex to check valid username.
        String regex = "^[A-Za-z]+";
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the username is empty
        // return false
        if (firstname == null) {
            return false;

        }
        // Pattern class contains matcher() method
        // to find matching between given username
        // and regular expression.
        Matcher m = p.matcher(firstname);
        // Return if the username
        // matched the ReGex
        return m.matches();

    }// end of isvoalid user name

    // Phone no. Validation

    public static boolean isValidNumber(String phno) {


        String regex = "\\d{11}";
        Pattern p = Pattern.compile(regex);
        if (phno == null) {
            return false;

        }
        Matcher m = p.matcher(phno);
        return m.matches();

    }//end of  isValid Phone Number :

    // Password Validation


    public static boolean
    isValidPassword(String password)
    {

        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

    // CNIC Validation

    public static boolean isValid(String CNIC) {
        if (((CNIC.length() == 15) && (Character.isDigit(CNIC.charAt(0))) &&
                (Character.isDigit(CNIC.charAt(1))) && (Character.isDigit(CNIC.charAt(2))) &&
                (Character.isDigit(CNIC.charAt(3))) && (Character.isDigit(CNIC.charAt(4)))
                && (CNIC.charAt(5) == '-') && (Character.isDigit(CNIC.charAt(6))) &&
                (Character.isDigit(CNIC.charAt(7))) && (Character.isDigit(CNIC.charAt(8))) && (Character.isDigit(CNIC.charAt(9))) && (Character.isDigit(CNIC.charAt(10))) &&
                (Character.isDigit(CNIC.charAt(11))) && (Character.isDigit(CNIC.charAt(12))) && (CNIC.charAt(13) == '-') && (Character.isDigit(CNIC.charAt(14))))) {
            return true;
        }
        return false;

    }// end of is valid CNIC

    // Credit Card Validation

    public static boolean isValidCard(long number) {

        return (getSize(number) >= 13 &&
                getSize(number) <= 16) &&
                (prefixMatched(number, 4) ||
                        prefixMatched(number, 5) ||
                        prefixMatched(number, 37) ||
                        prefixMatched(number, 6)) &&
                ((sumOfDoubleEvenPlace(number) +
                        sumOfOddPlace(number)) % 10 == 0);
    }

    //Get the result from Step 2
    public static int sumOfDoubleEvenPlace(long number) {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

        return sum;
    }

    //     Return this number if it is a single digit, otherwise,
//     return the sum of the two digits
    public static int getDigit(int number) {
        if (number < 9)
            return number;
        return number / 10 + number % 10;
    }

    //Return sum of odd-place digits in number
    public static int sumOfOddPlace(long number) {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");
        return sum;
    }

    //Return true if the digit d is a prefix for number
    public static boolean prefixMatched(long number, int d) {
        return getPrefix(number, getSize(d)) == d;
    }

    // Return the number of digits in d
    public static int getSize(long d) {
        String num = d + "";
        return num.length();
    }

    // Return the first k number of digits from
//     number. If the number of digits in number
//     is less than k, return number.
    public static long getPrefix(long number, int k) {
        if (getSize(number) > k) {
            String num = number + "";
            return Long.parseLong(num.substring(0, k));
        }
        return number;
    }

    // Time Validation

    public static boolean isValidTime(String time) {
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        Pattern p = Pattern.compile(regex);
        if (time == null) {
            return false;
        }
        Matcher m = p.matcher(time);
        return m.matches();
    } // end of time validation.


}
