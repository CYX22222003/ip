package task;

import exception.DukeException;
import exception.ParseException;
import storage.KorolevStorage;
import parser.EventParser;
import parser.DateParser;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Represents a list of KorolevTask
 */
public class KorolevList {
    private static String outOfIndexError = "The index is out of bound!";
    private static String listNotice = "Here are the tasks in your list:\n";
    private static String markNotice = "Nice! I've marked this task as done:";
    private static String unmarkNotice = "OK, I've marked this task as not done yet:";
    private static String deleteNotice = "Noted. I've removed this task:";

    private static String filteredNotice = "Here are the matching tasks in your list:";
    private ArrayList<KorolevTask> events;

    private static final KorolevStorage storage = new KorolevStorage();

    public KorolevList() {
        this.events = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < this.events.size(); i++) {
            msg.append((i + 1)).append(". ").append(this.events.get(i)).append("\n");
        }
        return msg.toString();
    }

    /**
     * Adds new task to the array list of tasks.
     *
     * @param event description of the event
     * @throws DukeException when failing to add event
     */
    public void addEvent(String event) throws DukeException {
        KorolevTask e;
        String name, date, from, to;
        String target = event.split("\\s")[0];
        switch (target) {
            case "event" -> {
                try {
                    name = EventParser.parseName("event", "/from", event);
                    from = DateParser.parseFrom(event);
                    to = DateParser.parseTo(event);
                    e = new KorolevEvent(name, from, to);
                    this.events.add(e);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(e);
                } catch (ParseException | DateTimeParseException exp) {
                    System.out.println(exp.getMessage());
                }
            }
            case "todo" -> {
                try {
                    name = EventParser.parseName("todo", "", event);
                    e = new KorolevTodo(name);
                    this.events.add(e);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(e);
                } catch (ParseException exp) {
                    System.out.println(exp.getMessage());
                }
            }
            case "deadline" -> {
                try {
                    name = EventParser.parseName("deadline", "/by", event);
                    date = DateParser.parseBy(event);
                    e = new KorolevDeadline(name, date);
                    this.events.add(e);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(e);
                } catch (ParseException exp) {
                    System.out.println(exp.getMessage());
                }
            }
            default -> throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }

        System.out.println("Now you have " + events.size() + " tasks in the list");
    }

    /**
     * Displays the whole list of tasks
     *
     * @return the string representation of task lists
     */
    public String displayList() {
        return listNotice + this.toString();
    }

    /**
     * Marks the specific task to be done
     *
     * @param index index of the event.
     * @throws DukeException the index is out of bound
     */
    public void markEvent(int index) throws DukeException {
        if (index >= this.events.size() || index < 0) {
            throw new DukeException(outOfIndexError);
        }
        KorolevTask t = this.events.get(index);
        t.markTask();
        System.out.println(markNotice);
        System.out.println(t);
    }

    /**
     * Removes a specific task
     *
     * @param index index of the task
     * @throws DukeException when the index is out of the range of the array list
     */
    public void removeEvent(int index) throws DukeException {
        if (index >= this.events.size() || index < 0) {
            throw new DukeException(outOfIndexError);
        }
        KorolevTask t = this.events.remove(index);
        System.out.println(t);
        System.out.println(deleteNotice);
        System.out.println("Now you have " + this.events.size() + " tasks in the list.");
    }

    /**
     * Unmarks a specific task to a state of incomplete
     *
     * @param index index of the task
     * @throws DukeException when the index is out of the range of the array list
     */

    public void unmarkEvent(int index) throws DukeException {
        if (index >= this.events.size() || index < 0) {
            throw new DukeException(outOfIndexError);
        }
        KorolevTask t = this.events.get(index);
        t.unmarkTask();
        System.out.println(unmarkNotice);
        System.out.println(t);
    }

    /**
     * Generate the message that will be written into local file
     *
     * @return message to be recorded
     */
    private String createSaveInfo() {
        StringBuilder msg = new StringBuilder();
        for (KorolevTask event : this.events) {
            msg.append(event).append("\n");
        }
        return msg.toString();
    }

    /**
     * Save the information of task list to the
     */
    public void saveEvent() {
        String msg = this.createSaveInfo();
        storage.writeToFile(msg);
    }


    /**
     * Load records about events from hard disk
     */
    public void loadEvent() {
        storage.readLines(this.events);
    }

    public ArrayList<KorolevTask> findItem(String keyword) {
        ArrayList<KorolevTask> out = new ArrayList<>();

        for (KorolevTask e : this.events) {
            if (e.match(keyword)) {
                out.add(e);
            }
        }

        return out;
    }

    public static void displayFilteredList(ArrayList<KorolevTask> tasks) {
        System.out.println(filteredNotice);
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            msg.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        System.out.println(msg.toString());
    }
}
