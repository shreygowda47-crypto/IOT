import java.util.Scanner;

// 1. Ride Class to store ride details
class Ride {
    int rideId;
    String pickup;
    String drop;
    double fare;

    public Ride(int rideId, String pickup, String drop, double fare) {
        this.rideId = rideId;
        this.pickup = pickup;
        this.drop = drop;
        this.fare = fare;
    }

    @Override
    public String toString() {
        return String.format("Ride ID: %-5d | From: %-15s | To: %-15s | Fare: $%.2f", 
                              rideId, pickup, drop, fare);
    }
}

// 2. Node Class to represent each entry in the Linked List
class Node {
    Ride ride;
    Node next;

    public Node(Ride ride) {
        this.ride = ride;
        this.next = null;
    }
}

// 3. RideHistoryManager Class containing the logic
class RideHistoryManager {
    private Node head;

    // Add a new ride at the end
    public void addRide(Ride r) {
        Node newNode = new Node(r);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        System.out.println("Ride added successfully!");
    }

    // Delete the last ride
    public void deleteLastRide() {
        if (head == null) {
            System.out.println("History is empty.");
            return;
        }
        if (head.next == null) {
            head = null;
        } else {
            Node temp = head;
            while (temp.next.next != null) {
                temp = temp.next;
            }
            temp.next = null;
        }
        System.out.println("Last ride removed from history.");
    }

    // View all rides
    public void displayRides() {
        if (head == null) {
            System.out.println("\n--- History is Empty ---");
            return;
        }
        System.out.println("\n--- Ride History ---");
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.ride);
            temp = temp.next;
        }
    }

    // Search ride by location
    public void searchRide(String location) {
        Node temp = head;
        boolean found = false;
        while (temp != null) {
            if (temp.ride.pickup.equalsIgnoreCase(location) || 
                temp.ride.drop.equalsIgnoreCase(location)) {
                System.out.println("Match Found: " + temp.ride);
                found = true;
            }
            temp = temp.next;
        }
        if (!found) System.out.println("No records found for: " + location);
    }

    // Reverse ride history
    public void reverseHistory() {
        Node prev = null, current = head, next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
        System.out.println("History reversed (Latest First view).");
    }
}

// 4. Main Class with Menu-Driven Logic
public class RideBookingSystem {
    public static void main(String[] args) {
        RideHistoryManager manager = new RideHistoryManager();
        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to the Ride Booking History System");

        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add New Ride");
            System.out.println("2. Cancel Last Ride");
            System.out.println("3. View All Rides");
            System.out.println("4. Search Ride by Location");
            System.out.println("5. Reverse Ride History");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Ride ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Pickup Location: ");
                    String pick = sc.nextLine();
                    System.out.print("Enter Drop Location: ");
                    String drop = sc.nextLine();
                    System.out.print("Enter Fare: ");
                    double fare = sc.nextDouble();
                    manager.addRide(new Ride(id, pick, drop, fare));
                    break;

                case 2:
                    manager.deleteLastRide();
                    break;

                case 3:
                    manager.displayRides();
                    break;

                case 4:
                    System.out.print("Enter location to search: ");
                    String loc = sc.nextLine();
                    manager.searchRide(loc);
                    break;

                case 5:
                    manager.reverseHistory();
                    break;

                case 6:
                    System.out.println("Exiting System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 6);

        sc.close();
    }
}