package vazifa3.editor;

/** Konkret buyruq: matn qo'shish. Undo - qo'shilgan qismni o'chirish. */
public class InsertTextCommand implements Command {

    private final TextEditor editor;
    private final int position;
    private final String value;

    public InsertTextCommand(TextEditor editor, int position, String value) {
        this.editor = editor;
        this.position = position;
        this.value = value;
    }

    @Override
    public void execute() {
        editor.insert(position, value);
    }

    @Override
    public void undo() {
        editor.delete(position, value.length());
    }

    @Override
    public String describe() {
        return "Qo'shish(" + position + ", \"" + value + "\")";
    }
}
