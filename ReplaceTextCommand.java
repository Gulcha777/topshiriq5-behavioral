package vazifa3.editor;

/** Konkret buyruq: matn qismini almashtirish (o'chirish + qo'shish bitta qadam sifatida). */
public class ReplaceTextCommand implements Command {

    private final TextEditor editor;
    private final int start;
    private final int length;
    private final String replacement;
    private String original; // execute() paytida to'ldiriladi

    public ReplaceTextCommand(TextEditor editor, int start, int length, String replacement) {
        this.editor = editor;
        this.start = start;
        this.length = length;
        this.replacement = replacement;
    }

    @Override
    public void execute() {
        original = editor.delete(start, length);
        editor.insert(start, replacement);
    }

    @Override
    public void undo() {
        editor.delete(start, replacement.length());
        editor.insert(start, original);
    }

    @Override
    public String describe() {
        return "Almashtirish(" + start + ", " + length + ", \"" + replacement + "\")";
    }
}
