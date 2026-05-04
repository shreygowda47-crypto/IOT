import java.util.*;
class Assignment {
    String subject;
    String teacher;
    String slot;
    String room;

    public Assignment(String subject, String teacher, String slot, String room) {
        this.subject = subject;
        this.teacher = teacher;
        this.slot = slot;
        this.room = room;
    }

    @Override
    public String toString() {
        return String.format("| %-12s | %-12s | %-12s | %-10s |", 
                             subject, teacher, slot, room);
    }
}

class TimetableManager {
    private List<String> subjects = new ArrayList<>();
    private List<String> teachers = new ArrayList<>(); // Linked by index to subjects
    private List<String> slots = new ArrayList<>();
    private List<String> rooms = new ArrayList<>();
    private List<Assignment> result = new ArrayList<>();

    
    public void addSubject(String name, String teacher) {
        subjects.add(name);
        teachers.add(teacher);
    }

    public void addSlot(String slot) { slots.add(slot); }
    public void addRoom(String room) { rooms.add(room); }

   
    private boolean isSafe(String teacher, String slot, String room) {
        for (Assignment a : result) {
            // Constraint: Teacher cannot have two classes in the same slot
            if (a.slot.equals(slot) && a.teacher.equals(teacher)) return false;
            // Constraint: Room cannot be occupied by two subjects in the same slot
            if (a.slot.equals(slot) && a.room.equals(room)) return false;
        }
        return true;
    }

    public boolean solve(int index) {
        
        if (index == subjects.size()) return true;

        String currentSub = subjects.get(index);
        String currentTeacher = teachers.get(index);

        for (String slot : slots) {
            for (String room : rooms) {
                if (isSafe(currentTeacher, slot, room)) {
                   
                    result.add(new Assignment(currentSub, currentTeacher, slot, room));

                    if (solve(index + 1)) return true;

                    result.remove(result.size() - 1);
                }
            }
        }
        return false;
    }

    public void display() {
        if (result.isEmpty()) {
            System.out.println("No valid timetable generated yet.");
            return;
        }
        System.out.println("\n-------------------------------------------------------------");
        System.out.println("| Subject      | Teacher      | Slot         | Room       |");
        System.out.println("-------------------------------------------------------------");
        for (Assignment a : result) System.out.println(a);
        System.out.println("-------------------------------------------------------------");
    }

    public void clear() {
        result.clear();
    }
}

public class SmartTimetableApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TimetableManager manager = new TimetableManager();
        int choice;

        System.out.println("=== Smart Timetable Generator ===");

        do {
            System.out.println("\n1. Add Subject & Teacher");
            System.out.println("2. Add Time Slot");
            System.out.println("3. Add Room");
            System.out.println("4. Generate Timetable");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter Subject Name: ");
                    String sub = sc.nextLine();
                    System.out.print("Enter Teacher Name: ");
                    String tea = sc.nextLine();
                    manager.addSubject(sub, tea);
                    break;
                case 2:
                    System.out.print("Enter Time Slot (e.g., 09:00AM): ");
                    manager.addSlot(sc.nextLine());
                    break;
                case 3:
                    System.out.print("Enter Room Name (e.g., Lab-A): ");
                    manager.addRoom(sc.nextLine());
                    break;
                case 4:
                    manager.clear(); 
                    System.out.println("Generating...");
                    if (manager.solve(0)) {
                        manager.display();
                    } else {
                        System.out.println("Conflict Detected: Cannot generate a valid timetable with current constraints.");
                    }
                    break;
                case 5:
                    System.out.println("System Closed.");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        } while (choice != 5);
        sc.close();
    }
}