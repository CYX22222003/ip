package ui;

import task.KorolevList;

import java.util.Scanner;

/**
 * Represents the mechanism that interacts with users.
 */
public class KorolevUI {
    private final Scanner scanner = new Scanner(System.in);
    private final String newLogo = """
            Hello! I'm DukeKorolev
            What can I do for you?
            """;
    private final String end = "Bye. Hope to see you again soon!";

    private KorolevList repo = new KorolevList();

    /**
     * Reads the inputs of users and executes commands based on the input.
     */
    private void readCommands() {
        String input = "";
        boolean isRunning = true;
        KorolevCommand c;
        input = scanner.nextLine();
        c = new KorolevCommand(input, repo);
        String out = c.executeCommand();
    }

    public String getResponse(String input) {
        KorolevCommand c = new KorolevCommand(input, repo);
        return c.executeCommand();
    }

    public void loadEvent() {
        this.repo.loadEvent();
    }
}
