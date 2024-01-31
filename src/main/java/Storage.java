import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
public class Storage {
    private  ArrayList<Task> tasks;
    private static final String FILE_PATH = "./data/Tes.txt";

    public Storage() {
        this.tasks = new ArrayList<>(); // assume maximum size of task list to be 100
        loadFromFile();
    }

    /**
     * Stores the task into the tasklist.
     *
     * @param task Description of task to be stored.
     */
    public void storeToDo(String task) {
        ToDo newToDo = new ToDo(task);
        this.tasks.add(newToDo);
        this.saveToFile();
    }

    public void storeDeadline(String task, String by) {
        Deadline newDeadline = new Deadline(task, by);
        this.tasks.add(newDeadline);
        this.saveToFile();
    }

    public void storeEvent(String task, String from, String to) {
        Event newEvent = new Event(task, from, to);
        this.tasks.add(newEvent);
        this.saveToFile();
    }

    /**
     * Gets task description.
     *
     * @param index Index of task in the list.
     * @return The task description.
     */
    public String getTaskDescription(int index) {
        return this.tasks.get(index).toString();
    }

    /**
     * Gets the size of list.
     *
     * @return The size of list.
     */
    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Marks the task as done.
     *
     * @param index Index of task in the list.
     */
    public void mark(int index) {
        this.tasks.get(index).mark();
        this.saveToFile();
    }

    /**
     * Unmarks the task.
     *
     * @param index Index of task in the list.
     */
    public void unmark(int index) {
        this.tasks.get(index).unmark();
        this.saveToFile();
    }

    /**
     * Deletes the item in tasks with the respective index.
     *
     * @param index Index of task in the list.
     */
    public void delete(int index) {
        this.tasks.remove(index);
        this.saveToFile();
    }

    private void saveToFile() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            PrintWriter writer = new PrintWriter(file);

            for (Task task : tasks) {
                writer.println(task.toString());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return;
            }
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                try {
                    if (line.contains("from:")) {
                        String[] split = line.split(" \\| ");
                        String temp = split[3].substring(6);
                        String[] time = temp.split(" to: ");
                        String from = time[0];
                        String to = time[1];
                        this.storeEvent(split[2], from, to);
                        if (split[1].equals("X")) {
                            this.mark(this.tasks.size() - 1);
                        }
                    } else if (line.contains("by:")) {
                        String[] split = line.split(" \\| ");
                        String by = split[3].substring(4);
                        this.storeDeadline(split[2], by);
                        if (split[1].equals("X")) {
                            this.mark(this.tasks.size() - 1);
                        }
                    } else {
                        String[] split = line.split(" \\| ");
                        this.storeToDo(split[2]);
                        if (split[1].equals("X")) {
                            this.mark(this.tasks.size() - 1);
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Error printing task from file: " + e.getMessage());
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error printing task from file: " + e.getMessage());
        }
    }
}
