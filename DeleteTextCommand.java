package vazifa3.editor;

/** Konkret buyruq: matn o'chirish. O'chirilgan qism eslab qolinadi - undo uni qaytaradi. */
public class DeleteTextCommand implements Command {

    private final TextEditor editor;
    private final int start;
    private final int length;
    private String removed; // execute() paytida to'ldiriladi

    public DeleteTextCommand(TextEditor editor, int start, int length) {
        this.editor = editor;
        this.start = start;
        this.length = length;
    }

    @Override
    public void execute() {
        removed = editor.delete(start, length);
    }

    @Override
    public void undo() {
        editor.insert(start, removed);
    }

    @Override
    public String describe() {
        return "O'chirish(" + start + ", " + length + ")";
    }
}
