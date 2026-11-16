package vazifa3.editor;

import vazifa3.eventbus.EventBus;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Invoker: buyruqlarni bajaradi va ikkita stek (undo / redo) orqali tarixni boshqaradi.
 * EventBus berilgan bo'lsa, har bir amal haqida hodisa e'lon qiladi (Observer bilan integratsiya).
 */
public class CommandManager {

    public static final String TOPIC_EXECUTED = "editor.executed";
    public static final String TOPIC_UNDONE = "editor.undone";
    public static final String TOPIC_REDONE = "editor.redone";

    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();
    private final EventBus eventBus; // null bo'lishi mumkin

    public CommandManager() {
        this(null);
    }

    public CommandManager(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    /** Buyruqni bajaradi. Xato chiqsa - tarixga yozilmaydi. Yangi buyruq redo tarixini tozalaydi. */
    public void execute(Command command) {
        if (command == null) {
            throw new IllegalArgumentException("Buyruq null bo'lishi mumkin emas");
        }
        command.execute();            // xato chiqsa, quyidagi qatorlar bajarilmaydi
        undoStack.push(command);
        redoStack.clear();
        publish(TOPIC_EXECUTED, command);
    }

    /** @return true - bekor qilindi; false - bekor qiladigan amal yo'q. */
    public boolean undo() {
        if (undoStack.isEmpty()) {
            return false;
        }
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
        publish(TOPIC_UNDONE, command);
        return true;
    }

    /** @return true - qayta bajarildi; false - qayta bajariladigan amal yo'q. */
    public boolean redo() {
        if (redoStack.isEmpty()) {
            return false;
        }
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
        publish(TOPIC_REDONE, command);
        return true;
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public int undoSize() {
        return undoStack.size();
    }

    public int redoSize() {
        return redoStack.size();
    }

    private void publish(String topic, Command command) {
        if (eventBus != null) {
            eventBus.publish(topic, command.describe());
        }
    }
}
