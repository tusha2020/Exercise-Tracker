package ui;

import model.Exercise;
import model.ExerciseEntry;
import model.Routine;
import model.Schedule;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// This [class] references code from JsonSerializationDemo
// Link: [https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git]
//Exercise Tracker application
public class ExerciseTrackerApplication {
    private static final String JSON_STORE = "./data/tracker.json";
    private Schedule schedule;
    private Scanner input;
    private ArrayList<Routine> routines;
    private Routine simpleRoutine;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // This [method] references code from TellerApp
    // Link: [https://github.students.cs.ubc.ca/CPSC210/TellerApp]
    //EFFECTS: runs the exercise tracker application
    public ExerciseTrackerApplication() {
        runExerciseTrackerApp();
    }

    // This [method] references code from TellerApp
    // Link: [https://github.students.cs.ubc.ca/CPSC210/TellerApp]
    //MODIFIES: this
    //EFFECTS: processes user input
    private void runExerciseTrackerApp() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");

    }


    // This [method] references code from TellerApp
    // Link: [https://github.students.cs.ubc.ca/CPSC210/TellerApp]
    // EFFECTS: displays an options menu
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> to view this week's schedule");
        System.out.println("\ta -> to add an exercise entry for a day in schedule");
        System.out.println("\tr -> to create a new routine");
        System.out.println("\tt -> to view all routines");
        System.out.println("\ts -> save work room to file");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tq -> quit");
    }





    // This [method] references code from TellerApp
    // Link: [https://github.students.cs.ubc.ca/CPSC210/TellerApp]
    //EFFECTS: processes user commands
    private void processCommand(String command) {
        if (command.equals("v")) {
            showSchedule();
            removeFromScheduleMenu();
        } else if (command.equals("a")) {
            addEntryToSchedule();
        } else if (command.equals("r")) {
            createRoutine();
        } else if (command.equals("t")) {
            printRoutinesWithExercises();
        } else if (command.equals("s")) {
            saveTrackerApp();
        } else if (command.equals("l")) {
            loadTrackerApp();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    //MODIFIES: this
    //EFFECTS: prints out each routine with its length of time and exercises, and removes routines on user command.
    private void printRoutinesWithExercises() {
        for (Routine routine : routines) {
            String name = routine.getName();
            double timeMinutes = routine.getTotalSeconds() / 60;
            System.out.println("\nRoutine: " + name + " , time length: " + timeMinutes + " minutes");
            ArrayList<Exercise> exercises = routine.getExercises();
            for (Exercise e : exercises) {
                System.out.println("- " + e.getName() + " for " + e.getSeconds() + " seconds");
            }
        }
        System.out.println("\n if you want to remove a routine type remove otherwise press any letter to return "
                + "to main menu");
        String removal = input.next();
        if (removal.equalsIgnoreCase("remove")) {
            removeRoutine();
        } else {
            System.out.println("to main menu");
        }
    }

    //MODIFIES: this
    //EFFECTS: removes a routine from routines
    private void removeRoutine() {
        System.out.println("\nwhich routine would you like to remove?");
        String routineName = input.next();
        Routine routineRemoved = findRoutine(routineName);
        if ("test".equalsIgnoreCase(routineRemoved.getName())) {
            System.out.println("error: routine doesn't exist");
        } else {
            routines.remove(routineRemoved);
            System.out.println("routine " + routineName + " has been removed.");
        }
    }


    //MODIFIES: this
    //EFFECTS: if user wants to remove an exercise entry from schedule then it removes it otherwise user guided back
    // to main menu.
    private void removeFromScheduleMenu() {
        System.out.println("\nif you would like to remove an entry type remove, "
                + "type any letter to go back to main menu");
        String action = input.next();
        if (action.equalsIgnoreCase("remove")) {
            removeEntry();
        } else {
            System.out.println("to main menu");
        }
    }


    //MODIFIES: this
    //EFFECTS: removes exercise entry from a certain day on schedule.
    private void removeEntry() {
        System.out.println("Please type which day you would like to remove its entry");
        String removalDay = input.next();
        ArrayList<ExerciseEntry> weeklySchedule = schedule.getWeeklySchedule();
        if (!(weeklySchedule.get(schedule.dayToIndexNum(removalDay)) == null)) {
            schedule.removeExerciseEntry(removalDay);
            System.out.println("Exercise entry on " + removalDay + " has been removed");
        } else {
            System.out.println("Error: this day has no entry");
        }
    }


    //MODIFIES: this
    //EFFECTS: creates new routine, adds exercises to it and adds routine to routines list.
    private void createRoutine() {
        System.out.println("what would you like to name this routine?");
        String routineName = input.next();

        Routine routineNew = new Routine(routineName);
        Boolean keepAdding = true;
        while (keepAdding) {
            System.out.println("would you like to add a new exercise, Yes or no?");
            String yesOrNo = input.next();

            if (yesOrNo.equalsIgnoreCase("no")) {
                keepAdding = false;
            } else if (yesOrNo.equalsIgnoreCase("yes")) {
                addExercise(routineNew);
            } else {
                System.out.println("invalid response");
            }
        }
        routines.add(routineNew);
        System.out.println("routine created!");
    }


    //MODIFIES: routineNew
    //EFFECTS: adds an exercise to given routine
    private void addExercise(Routine routineNew) {
        System.out.println("what's the name of the exercise you would like to add?");
        String exerciseName = input.next();
        System.out.println("what's the duration of this exercise in seconds?");
        int exerciseTime = input.nextInt();
        Exercise newExercise = new Exercise(exerciseName, exerciseTime);
        routineNew.addExercise(newExercise);
        System.out.println("the exercise " + exerciseName + " has been added to your routine!");
    }


    //MODIFIES: this
    //EFFECTS: Adds exercise entry to schedule
    private void addEntryToSchedule() {
        printWhichDaysToAddEntry();
        String chosenDay = input.next();
        ExerciseEntry testEntry = new ExerciseEntry("test", new Routine("hi"), "3:00");
        if (schedule.addExerciseEntry(testEntry, chosenDay)) {
            schedule.removeExerciseEntry(chosenDay);
            System.out.println("\nwhich routine would you like to do from ones below? choose by retyping name");
            printOutRoutines();
            String stringRoutine = input.next();
            Routine chosenRoutine = findRoutine(stringRoutine);
            if ("test".equalsIgnoreCase(chosenRoutine.getName())) {
                System.out.println("error: routine doesn't exist");
            } else {
                System.out.println("what time would you like to do this workout?");
                String timeToExercise = input.next();
                System.out.println("which place do you plan to complete this exercise in?");
                String place = input.next();
                ExerciseEntry entry = new ExerciseEntry(place, chosenRoutine, timeToExercise);
                schedule.addExerciseEntry(entry, chosenDay);
                System.out.println("entry added to " + chosenDay + "!");
            }
        } else {
            System.out.println("Unable to add entry, this day already has a scheduled exercise entry");
        }


    }


    //EFFECTS: prints out prompt for selection and all the days of the week
    private void printWhichDaysToAddEntry() {
        System.out.println("\nSelect which day to add exercise entry to:");
        System.out.println("\n monday-> monday ");
        System.out.println("\n tuesday-> tuesday ");
        System.out.println("\n wednesday-> wednesday ");
        System.out.println("\n thursday-> thursday ");
        System.out.println("\n friday-> friday ");
        System.out.println("\n saturday-> saturday ");
        System.out.println("\n sunday-> sunday ");
    }


    //EFFECTS returns the routine with the given name
    private Routine findRoutine(String stringRoutine) {
        Routine chosen = new Routine("test");
        for (Routine routine : routines) {
            String name = routine.getName();
            if (stringRoutine.equalsIgnoreCase(name)) {
                chosen = routine;
            }
        }
        return chosen;
    }


    //EFFECTS: prints out the names of all routines
    private void printOutRoutines() {
        System.out.println("\nroutines:");
        for (Routine routine : routines) {
            String name = routine.getName();
            System.out.println("\n- " + name);
        }
    }


    //EFFECTS: prints out all the days of the week with their corresponding exercise entries if available.
    private void showSchedule() {
        ArrayList<ExerciseEntry> days = schedule.getWeeklySchedule();
        for (int index = 0; index < 7; index++) {
            if (null == days.get(index)) {
                System.out.println(schedule.indexToDay(index) + ":");
            } else {
                ExerciseEntry entry = days.get(index);
                Routine routine = entry.getRoutine();

                String time = entry.getTime();
                String routineName = routine.getName();
                String place = entry.getPlace();

                System.out.println(schedule.indexToDay(index) + ": at " + time + " do "
                        + routineName + " routine at " + place);
            }

        }
        double totalSeconds = schedule.getTotalExerciseSeconds();
        double totalMinutes = totalSeconds / 60;
        System.out.println("you have scheduled " + totalMinutes + " minutes of exercise this week");

    }


    // This [method] references code from TellerApp
    // Link: [https://github.students.cs.ubc.ca/CPSC210/TellerApp]
    //MODIFIES: this
    //EFFECTS: initializes schedule
    public void init() {
        schedule = new Schedule();
        routines = new ArrayList<>();
        simpleRoutine = new Routine("simple");
        Exercise pushUps = new Exercise("push-ups", 30);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        simpleRoutine.addExercise(pushUps);
        routines.add(simpleRoutine);
    }

    // EFFECTS: saves the tracker application to file
    private void saveTrackerApp() {
        try {
            jsonWriter.open();
            jsonWriter.write(schedule, routines);
            jsonWriter.close();
            System.out.println("Saved exercise tracker to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: loads tracker application from file
    private void loadTrackerApp() {
        try {
            schedule = jsonReader.readSchedule();
            routines = jsonReader.readRoutines();
            System.out.println("Loaded exercise tracker from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
