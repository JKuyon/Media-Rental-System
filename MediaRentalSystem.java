/*
 * Jordan Kuyon
 * Purpose of the Program: Model a Media Rental System that reads and writes to file
 * Date: 07/08/2023 
 */

// Import ArrrayList Class
import java.util.ArrayList;

//Import Calendar Class
import java.util.Calendar;

//Import Scanner Class
import java.util.Scanner;

public class MediaRentalSystem {
    private static Manager mediaManager;
    private static ArrayList<Media> mediaLibrary;
    private static Scanner scanner;

    public static void main(String[] args) {
        mediaManager = new Manager(); // Instance of Manager class
        mediaLibrary = new ArrayList<>(); // Create ArrayList to hold media
        scanner = new Scanner(System.in); // Create Scanner class for user input

        while (true) {
            displayMenu();
            int menuSelection = scanner.nextInt(); // Variable to read menu input
            scanner.nextLine();

            try {
                switch (menuSelection) {
                    case 1:
                        addMedia();
                        break;
                    case 2:
                        findMedia();
                        break;
                    case 3:
                        removeMedia();
                        break;
                    case 4:
                        rentMedia();
                        break;
                    case 5:
                        modifyMedia();
                        break;
                    case 6:
                        displayOneMedia();
                        break;
                    case 7:
                        displayMediaByType();
                        break;
                    case 8:
                        displayLibrary();
                        break;
                    case 9:
                        System.out.println("Closing the program...");
                        System.out.println();
                        displayLibrary();
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (MediaNotFoundException e) { // Throw Exception
                System.out.println(e.getMessage());
            } catch (Exception e) { // Throw Exception
                System.out.println("An error occurred: " + e.getMessage());
            }
            System.out.println();
        }
    }

    // Method to display menu options
    private static void displayMenu() {
        System.out.println("------- Menu -------");
        System.out.println("1. Add Media");
        System.out.println("2. Find Media");
        System.out.println("3. Remove Media");
        System.out.println("4. Rent Media");
        System.out.println("5. Modify Media");
        System.out.println("6. Display One Object");
        System.out.println("7. Display All Objects of Type");
        System.out.println("8. Display All Objects of Library");
        System.out.println("9. Exit");
        System.out.println();
        System.out.print("Enter your choice: ");
    }

    // Method to add media to ArrayList
    private static void addMedia() {
        String mediaModel;
        while (true) {
            System.out.print("Enter media model (E for EBook, C for CD, D for DVD): ");
            mediaModel = scanner.nextLine().toUpperCase();

            // Validate mediaModel
            
            if (mediaModel.equals("E") || mediaModel.equals("C") || mediaModel.equals("D")) {
                break;
            } else {
                System.out.println("Invalid media model. Please enter 'E', 'C', or 'D': ");
            }
        }

        String id;
        while (true) {
        	// Validate ID to 5 integers
            System.out.print("Enter ID: ");
            id = scanner.nextLine();
            if (id.matches("\\d{5}")) { 
                break;
            } else {
                System.out.println("Invalid ID. ID should be exactly 5 number characters.");
            }
        }

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter year published: ");
        int yearPublished = scanner.nextInt();
        scanner.nextLine();

        // Menu to enter media model E, C, or D
        switch (mediaModel) {
            case "E":
                System.out.print("Enter number of chapters: ");
                int numberOfChapters = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter rent status (0 for not rented, 1 for rented): ");
                int rentStatusEBook = scanner.nextInt();
                scanner.nextLine();
                EBook eBook = new EBook(id, title, yearPublished, numberOfChapters, rentStatusEBook == 1);
                mediaManager.addMedia(eBook);
                break;
            case "C":
                System.out.print("Enter length in minutes: ");
                int lengthInMinutes = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter rent status (0 for not rented, 1 for rented): ");
                int rentStatusMusicCD = scanner.nextInt();
                scanner.nextLine();
                MusicCD musicCD = new MusicCD(id, title, yearPublished, lengthInMinutes, rentStatusMusicCD == 1);
                mediaManager.addMedia(musicCD);
                break;
            case "D":
                System.out.print("Enter size in megabytes: ");
                int sizeInMegabytes = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter rent status (0 for not rented, 1 for rented): ");
                int rentStatusMovieDVD = scanner.nextInt();
                scanner.nextLine();
                MovieDVD movieDVD = new MovieDVD(id, title, yearPublished, sizeInMegabytes, rentStatusMovieDVD == 1);
                mediaManager.addMedia(movieDVD);
                break;
            default:
                System.out.println("Invalid media model. Please try again.");
                break;
        }
    }

    // Method to find media in ArrayList
    private static void findMedia() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();

        System.out.println();
        System.out.println("ID\tRent\tType\tTitle\t\t\tPub\tScope\t\tCalc");
        try {
            mediaManager.findMedia(id);
        } catch (MediaNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to remove media from ArrayList
    private static void removeMedia() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        mediaManager.removeMedia(id);
    }

    // Method to rent media from ArrayList
    private static void rentMedia() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        mediaManager.rentMedia(id);
    }

    // Method to modify media in ArrayList
    private static void modifyMedia() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter new title: ");
        String newTitle = scanner.nextLine();
        System.out.print("Enter new year published: ");
        int newYearPublished = scanner.nextInt();
        scanner.nextLine();

        // Create instance of mediaManager
        Media media = mediaManager.findMedia(id);
        media.changeTitle(newTitle);
        media.changeYearPublished(newYearPublished);

        // Modify specific fields based on media type
        if (media instanceof EBook) {
            EBook eBook = (EBook) media;
            System.out.print("Enter new number of chapters: ");
            int newNumberOfChapters = scanner.nextInt();
            scanner.nextLine();
            eBook.setNumberOfChapters(newNumberOfChapters);
        } else if (media instanceof MusicCD) {
            MusicCD musicCD = (MusicCD) media;
            System.out.print("Enter new length in minutes: ");
            int newLengthInMinutes = scanner.nextInt();
            scanner.nextLine();
            musicCD.setLengthInMinutes(newLengthInMinutes);
        } else if (media instanceof MovieDVD) {
            MovieDVD movieDVD = (MovieDVD) media;
            System.out.print("Enter new size in megabytes: ");
            int newSizeInMegabytes = scanner.nextInt();
            scanner.nextLine();
            movieDVD.setSizeInMegabytes(newSizeInMegabytes);
        }

        // Confirm Message Update rental fee
        System.out.println("Media modified successfully. New rental fee: " + media.calculateRentalFee());
    }

    // Method to display media based on ID
    private static void displayOneMedia() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        mediaManager.displayOneMedia(id);
    }

    // Method to display media based on Type
    private static void displayMediaByType() {
        System.out.println();
        System.out.print("Enter media model (E for EBook, C for CD, D for DVD): ");
        String mediaModel = scanner.nextLine().toUpperCase();
        mediaManager.displayMediaByType(mediaModel);
    }

    private static void displayLibrary() {
    	// Table Format Labels
        System.out.println("ID\tRent\tType\tTitle\t\t\tPub\tScope\t\tCalc");
        for (Media media : mediaLibrary) {
            media.displayValues();
        }
    }

    // Parent Class
    private abstract static class Media {
        private String id; // ID
        private String title; // Title
        private int yearPublished; // year published
        private boolean rentStatus; // rent status
        // Current year using Calendar class
        private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Constructor
        public Media(String id, String title, int yearPublished, boolean rentStatus) {
            this.id = id;
            this.title = title;
            this.yearPublished = yearPublished;
            this.rentStatus = rentStatus;
        }

        // Getters
        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        // Setter
        public void changeTitle(String newTitle) {
            this.title = newTitle;
        }

        // Getter
        public int getYearPublished() {
            return yearPublished;
        }

        // Setter
        public void changeYearPublished(int newYearPublished) {
            this.yearPublished = newYearPublished;
        }

        // rentStatus is 0 or 1
        public boolean isRented() {
            return rentStatus;
        }
        
        // reference rentStatus
        public void setRentStatus(boolean rentStatus) {
            this.rentStatus = rentStatus;
        }

        // Calculate fee across program
        public abstract double calculateRentalFee();

        // Display menu options
        public abstract void displayValues();
    }

    
    // 1st Child
    private static class EBook extends Media {
        private int numberOfChapters;

        // Constructor
        public EBook(String id, String title, int yearPublished, int numberOfChapters, boolean rentStatus) {
            super(id, title, yearPublished, rentStatus);
            setNumberOfChapters(numberOfChapters);
        }

        public int getNumberOfChapters() {
            return numberOfChapters;
        }

        public void setNumberOfChapters(int numberOfChapters) {
            if (numberOfChapters > 0) {
                this.numberOfChapters = numberOfChapters;
            } else {
                throw new IllegalArgumentException("Invalid number of chapters. Number of chapters should be greater than 0.");
            }
        }

        @Override
        public double calculateRentalFee() {
            double rentalFee = getNumberOfChapters() * 0.10 + 1.50;
            if (getYearPublished() > 2015) {
                rentalFee += 1.00;
            }
            return rentalFee;
        }

        @Override
        public void displayValues() {
            String rentStatus = isRented() ? "R" : "N"; // Change Rent from N to R
            System.out.printf("%s\t%s\tE\t%-20s\t%d\t%d\t\t%.2f%n", getId(), rentStatus, getTitle(), getYearPublished(), getNumberOfChapters(), calculateRentalFee());
        }
    }

    // 2nd Child
    private static class MusicCD extends Media {
        private int lengthInMinutes;

        // Constructor
        public MusicCD(String id, String title, int yearPublished, int lengthInMinutes, boolean rentStatus) {
            super(id, title, yearPublished, rentStatus);
            setLengthInMinutes(lengthInMinutes);
        }

        public int getLengthInMinutes() {
            return lengthInMinutes;
        }

        public void setLengthInMinutes(int lengthInMinutes) {
            if (lengthInMinutes > 0) {
                this.lengthInMinutes = lengthInMinutes;
            } else {
                throw new IllegalArgumentException("Invalid length in minutes. Length in minutes should be greater than 0.");
            }
        }

        @Override
        public double calculateRentalFee() {
            double rentalFee = getLengthInMinutes() * 0.045 + 1.50;
            if (getYearPublished() > 2014) {
                rentalFee += 2.00;
            }
            return rentalFee;
        }

        @Override
        public void displayValues() {
            String rentStatus = isRented() ? "R" : "N";
            System.out.printf("%s\t%s\tC\t%-20s\t%d\t%d\t\t%.2f%n", getId(), rentStatus, getTitle(), getYearPublished(), getLengthInMinutes(), calculateRentalFee());
        }
    }

    // 3rd Child
    private static class MovieDVD extends Media {
        private int sizeInMegabytes;

        // Constructor
        public MovieDVD(String id, String title, int yearPublished, int sizeInMegabytes, boolean rentStatus) {
            super(id, title, yearPublished, rentStatus);
            setSizeInMegabytes(sizeInMegabytes);
        }

        public int getSizeInMegabytes() {
            return sizeInMegabytes;
        }

        public void setSizeInMegabytes(int sizeInMegabytes) {
            if (sizeInMegabytes > 0) {
                this.sizeInMegabytes = sizeInMegabytes;
            } else {
                throw new IllegalArgumentException("Invalid size in megabytes. Size in megabytes should be greater than 0.");
            }
        }

        @Override
        public double calculateRentalFee() {
            double rentalFee = 3.25 + 1.50;
            if (getYearPublished() > 2019) {
                rentalFee += 5.00;
            }
            return rentalFee;
        }

        @Override
        public void displayValues() {
            String rentStatus = isRented() ? "R" : "N";
            System.out.printf("%s\t%s\tD\t%-20s\t%d\t%d\t\t%.2f%n", getId(), rentStatus, getTitle(), getYearPublished(), getSizeInMegabytes(), calculateRentalFee());
        }
    }

    // Class to manage entire ArrayList
    private static class Manager {
        private ArrayList<Media> mediaList = new ArrayList<>();

        // Method to add media to ArrayList
        public void addMedia(Media media) {
            mediaList.add(media);
            mediaLibrary.add(media);
            System.out.println("Media added successfully.");
        }

        // Method to search and find media in Array List
        public Media findMedia(String id) {
            for (Media media : mediaList) {
                if (media.getId().equals(id)) {
                    System.out.println("Media found:");
                    media.displayValues();
                    return media;
                }
            }
            // Throw error when media is not found
            throw new MediaNotFoundException("Media with ID " + id + " not found.");
        }

        // Method to remove media from ArrayList
        public void removeMedia(String id) {
            Media media = findMedia(id);
            mediaList.remove(media);
            mediaLibrary.remove(media);
            System.out.println("Media removed successfully.");
        }

        // Method to rent media from ArrayList
        public void rentMedia(String id) {
            Media media = findMedia(id);
            media.setRentStatus(true);
            System.out.println("Media rented successfully.");
        }

        // Method to modify all attributes of specific media in ArrayList
        public void modifyMedia(String id, String newTitle, int newYearPublished) {
            Media media = findMedia(id);
            media.changeTitle(newTitle);
            media.changeYearPublished(newYearPublished);

            // Modify specific fields based on media type
            if (media instanceof EBook) {
                EBook eBook = (EBook) media;
                System.out.print("Enter new number of chapters: ");
                int newNumberOfChapters = scanner.nextInt();
                scanner.nextLine();
                eBook.setNumberOfChapters(newNumberOfChapters);
            } else if (media instanceof MusicCD) {
                MusicCD musicCD = (MusicCD) media;
                System.out.print("Enter new length in minutes: ");
                int newLengthInMinutes = scanner.nextInt();
                scanner.nextLine();
                musicCD.setLengthInMinutes(newLengthInMinutes);
            } else if (media instanceof MovieDVD) {
                MovieDVD movieDVD = (MovieDVD) media;
                System.out.print("Enter new size in megabytes: ");
                int newSizeInMegabytes = scanner.nextInt();
                scanner.nextLine();
                movieDVD.setSizeInMegabytes(newSizeInMegabytes);
            }

            // Confirm Message
            System.out.println("Media modified successfully. New rental fee: " + media.calculateRentalFee());
        }

        // Method to display media based on ID
        public void displayOneMedia(String id) {
            Media media = findMedia(id);
            media.displayValues();
        }

        // Method to display all media based on type
        public void displayMediaByType(String mediaModel) {
            boolean mediaFound = false;
            // table format
            System.out.println("ID\tRent\tType\tTitle\t\t\tPub\tScope\t\tCalc");
            for (Media media : mediaList) {
                if ((mediaModel.equals("E") && media instanceof EBook)
                        || (mediaModel.equals("C") && media instanceof MusicCD)
                        || (mediaModel.equals("D") && media instanceof MovieDVD)) {
                    media.displayValues();
                    mediaFound = true;
                }
            }
            if (!mediaFound) {
                System.out.println("No media found for the specified model: " + mediaModel);
            }
        }
    }

    // User Defined Exception when Media is not found in ArrayList
    private static class MediaNotFoundException extends RuntimeException {
        public MediaNotFoundException(String message) {
        	// Reference to message object
            super(message);
        }
    }
}
