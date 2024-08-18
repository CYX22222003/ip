import utils.EventParser;

public class KorolevDeadline extends KorolevTask{
    private String deadline;
    private String tag;
    public KorolevDeadline(String name, String date) {
        super(name);
        this.deadline = date;
        this.tag = "D";
    }

    @Override
    public String toString() {
        String base = super.toString();
        String head = "[" + this.tag +"]";
        String deadlines = this.deadline;

        return head + base + " (" + deadlines + ")";
    }
}
