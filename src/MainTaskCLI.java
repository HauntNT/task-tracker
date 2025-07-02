import java.util.Scanner;

public class MainTaskCLI {
    /*Initialize everything to make it work, essentially the scanner to read the commands and 
    a global boolean inUse to determine when the program is running and when to end it*/
    private static TaskManager manager = new TaskManager();
    static Scanner sc = new Scanner(System.in);
    private static boolean inUse = true;
    public static void main(String[] args) throws Exception {
        /*Introduction message to let the user know the commands that are available, and a while loop
         * that automatically would be working with the processCommand() method until the user writes exit on the CLI.*/
        System.out.println("Task Tracker, introduce ? to show all the commands.");
        while (inUse) {
            System.out.print("task-cli>");
            String line = sc.nextLine();
            processCommand(line);
        }
    }
    /*This method does the destructure of the String, and makes it into an actual command, looking for which type of command
     * is the user trying to put and the parameters that are in there, this method recives the line that is being read in the main*/
    public static void processCommand(String line){
        if (line.equals("?")) {
            printHelp();
            return;
        }
        if (line.toLowerCase().startsWith("add")) {
            //Look for the part between quotes, wich is the description that will be added to the task
            int firstQuote = line.indexOf("\"");
            int lastQuote = line.lastIndexOf("\"");
            //Catch some errors, the errors that I expect are not having both quotes on the line or putting only one quote
            if (firstQuote == -1 || lastQuote == -1 || lastQuote == firstQuote) {
                System.out.println("ERROR: add command must be followed by a description between quotes");
                return;
            }
            //Assemble the description with the positions of the quotes
            String description = line.substring((firstQuote+1), lastQuote).trim();
            //Catch an error if the description is empty, so theres not " "/empty tasks.
            if (description.isEmpty()) {
                System.out.println("ERROR: task description cannot be empty");
                return;
            }
            //If there's not an error, then we proceed and create the task with the description that was assembled earlier, and we add this to the manager of tasks
            Task task = new Task(description);
            manager.add(task);
            System.out.println("Task added succesfully!");
            return;
        }
        if (line.toLowerCase().startsWith("update")) {
            //I use an array of Strings that is going to be filled with the return of the extractByParts() method wich is explained below, but this array will contain ["id","description"]
            String[] parts = extractByParts(line);
            //If the array contains something different to two, there's a syntax error, it should contain the id and the description
            if (parts.length != 2) {
                System.out.println("ERROR: update command usage is update \"id\" \"new description\"");
                return;
            }
            //If there's no error, then we take the first item of the array wich is the id and we try to parse it, then we call the updateDescription in the task manager and update it
            try {
                int id = Integer.parseInt(parts[0]);
                manager.updateDescription(id, parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("ERROR: ID must be a valid number");
            }
            return;
        }
        //This command is easier because it's just the delete and the id, so we don't use the extractByParts() method
        if (line.toLowerCase().startsWith("delete")) {
            //I save the tokens in an array of strings, spliting the line with the spaces between the words, we use a regex that uses one or more spaces to split
            String[] tokens = line.trim().split("\\s+");
            //If the array contains something different to two, there's a syntax error, the tokens array needs to contain ["delete","id"]
            if (tokens.length != 2) {
                System.out.println("ERROR: delete command usage is delete \"id\"");
                return;
            }
            //If there's no error, then we try to parse the second item in the array wich is supposed to be the id, if it can be parsed, then we call the delete from the tasks manager
            try {
                int id = Integer.parseInt(tokens[1]);
                manager.delete(id);
            } catch (NumberFormatException e) {
                System.out.println("ERROR: ID must be a valid number");
            }
            return;
        }
        //The next threee if's follow the same logic, I separate the line in tokens with the spaces, expecting to save ["status to change","id"] in the array
        if (line.toLowerCase().startsWith("mark-in-progress")) {
            String[] tokens = line.trim().split("\\s+");
            //Control of a syntax error
            if (tokens.length != 2) {
                System.err.println("ERROR: command usage is mark-in-progress \"id\"");
                return;
            }
            try {
                //We take the second item in the array wich is the id and try to parse it, then we call the updateStatus() from the manager and type the "in-progress" status
                int id = Integer.parseInt(tokens[1]);
                manager.updateStatus(id, "in-progress");
            } catch (NumberFormatException e) {
                System.out.println("ERROR: ID must be a valid number");
            }
            return;
        }
        //Follow the same logic of the last if, the only change is the "changedStatus" to "done" in the updateStatus() method
        if (line.toLowerCase().startsWith("mark-done")) {
            String[] tokens = line.trim().split("\\s+");
            if (tokens.length != 2) {
                System.err.println("ERROR: command usage is mark-done \"id\"");
                return;
            }
            try {
                int id = Integer.parseInt(tokens[1]);
                manager.updateStatus(id, "done");
            } catch (NumberFormatException e) {
                System.out.println("ERROR: ID must be a valid number");
            }
            return;
        }
        //The list if's are only going to read the keyword, and then call the list method that are defined in the tasks manager
        if (line.equalsIgnoreCase("list")) {
            manager.showTasks();
            return;
        }
        if (line.equalsIgnoreCase("list done")) {
            manager.doneTasks();
            return;
        }
        if (line.equalsIgnoreCase("list todo")) {
            manager.todoTasks();
            return;
        }
        if (line.equalsIgnoreCase("list in-progress")) {
            manager.inProgressTasks();
            return;
        }
        //Command to exit the program, we make the inUse false so we can brake the while of the main
        if (line.toLowerCase().equals("exit")) {
            System.out.println("Leaving the CLI...");
            inUse = false;
            return;
        } else {
            System.out.println("This is not a supported command, try with:");
            printHelp();
        }
    }
    //Method used in the updateDescription command
    private static String[] extractByParts(String input) {
        //We search for the description, so we look for the quotes
        int firstQuote = input.indexOf("\"");
        int lastQuote = input.lastIndexOf("\"");
        //Catch errors with the quotes, trying to avoid having only one quote or no quotes at all
        if (firstQuote == -1 || lastQuote == -1 || lastQuote == firstQuote) {
            return new String[0]; //Returns an empty array wich means an error
        }
        //If there's no error, then we assemble the description, wich is going to be between the quotes, and then we assemble the before quotes wich is going to be "update <id>"
        String description = input.substring(firstQuote + 1, lastQuote).trim();
        String beforeQuotes = input.substring(0, firstQuote).trim(); 
        //Split the beforeQuotes, so we can have an array with tokens resulting in something like ["update","id"]
        String[] tokens = beforeQuotes.split(" ");
        //If there's less than 2 tokens, then theres an error
        if (tokens.length < 2) return new String[0];
        //If there's no error we save the second item in the array wich is the item
        String id = tokens[1];
        //And we return the id and description in the array
        return new String[] { id, description };
    }
    public static void printHelp() {
        System.out.println("\nAvailable commands:");
        System.out.printf("%-30s %-5s %-50s%n", "add \"description\"", "--->", "To add a new task");
        System.out.printf("%-30s %-5s %-50s%n", "update \"id\" \"new description\"", "--->", "To update a task by ID");
        System.out.printf("%-30s %-5s %-50s%n", "delete \"id\"", "--->", "To delete a task by ID");
        System.out.printf("%-30s %-5s %-50s%n", "mark-in-progress \"id\"", "--->", "To change status to in-progress");
        System.out.printf("%-30s %-5s %-50s%n", "mark-done \"id\"", "--->", "To change status to done");
        System.out.printf("%-30s %-5s %-50s%n", "list", "--->", "To show all the tasks");
        System.out.printf("%-30s %-5s %-50s%n", "list done", "--->", "To show only done tasks");
        System.out.printf("%-30s %-5s %-50s%n", "list todo", "--->", "To show only to-do tasks");
        System.out.printf("%-30s %-5s %-50s%n", "list in-progress", "--->", "To show only in-progress tasks");
        System.out.printf("%-30s %-5s %-50s%n", "exit", "--->", "To leave the CLI");
        System.out.println();
    }
} 
