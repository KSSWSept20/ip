package tes.command;

import tes.taskmanager.Storage;
import tes.taskmanager.TaskList;

/**
 * Represents a class to deal with input and output of the chatbot system.
 */
public class Ui {
    private TaskList taskList;
    private Storage storage;

    /**
     * Constructs a User Interface to deal with input and output.
     */
    public Ui() {
        this.storage = new Storage();
        this.taskList = new TaskList(this.storage.loadFromFile());
    }



    /**
     * Greets the user when initializing the chatbot.
     */
    public String greet() {
        return "Tes here.\n"
                + "Huh? What you want from me?";
    }

    /**
     * Says goodbye when closing the chatbot.
     */
    public String close() {
        return "Annoying brat (-.-)";
    }

    /**
     * Adds a to-do task to the task list.
     *
     * @param command tes.taskmanager.Task description of the new task.
     */
    public String addToDo(String command) {
        this.taskList.storeToDo(command);
        return "Got it. I've added this task:\n"
                + this.taskList.getTaskDescription(this.taskList.getSize() - 1)
                + "\nNow you have "
                + this.taskList.getSize()
                + " in the list.\n"
                + "Hope this won't rot like other tasks in your list!";
    }

    /**
     * Adds a task with deadline to the task list.
     *
     * @param command tes.taskmanager.Task description of the new task.
     * @param by tes.taskmanager.Deadline of the task.
     */
    public String addDeadline(String command, String by) {
        try {
            this.taskList.storeDeadline(command, by);
            return "Got it. I've added this task:\n"
                    + this.taskList.getTaskDescription(this.taskList.getSize() - 1)
                    + "\nNow you have "
                    + this.taskList.getSize()
                    + " in the list.\n"
                    + "Uhh, whatever! Not like your lazy ass gon do this by the deadline anyway (-_-)";
        } catch (DateAndTimeFormatterException e) {
            return e.getMessage();
        }

    }

    /**
     * Adds a task with a designated period to the task list.
     * @param command tes.taskmanager.Task description of the new task.
     * @param from Starting time of the task.
     * @param to Ending time of the task.
     */
    public String addEvent(String command, String from, String to) {
        try {
            this.taskList.storeEvent(command, from, to);
            return "Got it. I've added this task:\n"
                    + this.taskList.getTaskDescription(this.taskList.getSize() - 1)
                    + "\nNow you have "
                    + this.taskList.getSize()
                    + " in the list.\n"
                    + "Funny eh! I thought you just sleep all the time";
        } catch (DateAndTimeFormatterException e) {
            return e.getMessage();
        }

    }

    /**
     * Lists the tasks stored.
     */
    public String listTask() {
        StringBuilder tasks = new StringBuilder("This list wouldn't be so long if you stop sleeping:\n");
        for (int i = 1; i <= this.taskList.getSize(); i++) {
            tasks.append(i)
                    .append(". ")
                    .append(this.taskList.getTaskDescription(i - 1))
                    .append("\n"); // Appends a new line character
        }
        return tasks.toString();
    }

    /**
     * Marks a task as done.
     *
     * @param index Index of the task to be marked.
     */
    public String mark(int index) {
        try {
            if (index > this.taskList.getSize()) {
                String errorMessage = String.format("Can't you count?! There are only %d questions in the list now, "
                        + "you idiot!", this.taskList.getSize());
                throw new TesException(errorMessage);
            } else if (index < 1) {
                String errorMessage = "Only positive integer allowed! Idiot!!!";
                throw new TesException(errorMessage);
            }
            this.taskList.mark(index - 1);
            return "I've marked this task as done:\n"
                    + this.taskList.getTaskDescription(index - 1)
                    + "\nUnbelievable for someone like you 0o0";
        } catch (TesException e) {
            return e.getMessage();
        }
    }

    /**
     * Unmarks a task.
     *
     * @param index Index of the task to be unmarked.
     */
    public String unmark(int index) {
        try {
            if (index > this.taskList.getSize()) {
                String errorMessage = String.format("Can't you count?! There are only %d questions in the list now, "
                        + "you idiot!", this.taskList.getSize());
                throw new TesException(errorMessage);
            } else if (index < 1) {
                String errorMessage = "Only positive integer allowed! Idiot!!!";
                throw new TesException(errorMessage);
            }
            this.taskList.unmark(index - 1);
            return "OK, I've marked this task as not done yet:\n"
                    + this.taskList.getTaskDescription(index - 1)
                    + "\nAs expected...";
        } catch (TesException e) {
            return e.getMessage();
        }
    }

    /**
     * Deletes a task in the task list.
     *
     * @param index Index of the task to be unmarked.
     */
    public String delete(int index) {
        try {
            if (index > this.taskList.getSize()) {
                String errorMessage = String.format("Can't you count?! There are only %d questions in the list now, "
                        + "you idiot!", this.taskList.getSize());
                throw new TesException(errorMessage);
            } else if (index < 1) {
                String errorMessage = "Only positive integer allowed! Idiot!!!";
                throw new TesException(errorMessage);
            }
            String deletedTask = "Noted. I've removed this task:\n"
                    + this.taskList.getTaskDescription(index - 1)
                    + "\nNow you have "
                    + (this.taskList.getSize() - 1)
                    + " in the list.\n"
                    + "Finally giving up eh?";

            this.taskList.delete(index - 1);
            return deletedTask;
        } catch (TesException e) {
            return e.getMessage();
        }
    }

    /**
     * Finds tasks in the list with a keyword.
     *
     * @param keyword Word used to find the task.
     */
    public String find(String keyword) {
        StringBuilder foundTasks = new StringBuilder("Here are the matching tasks, my little blind friend:\n");
        int counter = 1;
        for (int i = 1; i <= this.taskList.getSize(); i++) {
            String taskDescription = this.taskList.getTaskDescription(i - 1);
            if (taskDescription.contains(keyword)) {
                foundTasks.append(counter)
                        .append(". ")
                        .append(taskDescription)
                        .append("\n");
                counter++;
            }
        }
        return foundTasks.toString();
    }

}
