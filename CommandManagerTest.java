package tests;

import testkit.Test;
import vazifa3.editor.CommandManager;
import vazifa3.editor.DeleteTextCommand;
import vazifa3.editor.InsertTextCommand;
import vazifa3.editor.ReplaceTextCommand;
import vazifa3.editor.TextEditor;
import vazifa3.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static testkit.Assert.assertEquals;
import static testkit.Assert.assertFalse;
import static testkit.Assert.assertThrows;
import static testkit.Assert.assertTrue;

/** Vazifa 3 (qo'shimcha): Command andozasi - Undo/Redo testlari. */
public class CommandManagerTest {

    private final TextEditor editor = new TextEditor();
    private final CommandManager manager = new CommandManager();

    @Test
    public void insertThenUndoRestoresPreviousText() {
        manager.execute(new InsertTextCommand(editor, 0, "Salom"));
        assertEquals("Salom", editor.getText());

        assertTrue(manager.undo(), "undo bajarilishi kerak");
        assertEquals("", editor.getText());
    }

    @Test
    public void deleteUndoBringsBackRemovedText() {
        manager.execute(new InsertTextCommand(editor, 0, "Salom dunyo"));
        manager.execute(new DeleteTextCommand(editor, 5, 6)); // " dunyo"
        assertEquals("Salom", editor.getText());

        manager.undo();
        assertEquals("Salom dunyo", editor.getText());
    }

    @Test
    public void replaceUndoAndRedoWork() {
        manager.execute(new InsertTextCommand(editor, 0, "Salom dunyo"));
        manager.execute(new ReplaceTextCommand(editor, 6, 5, "Buxoro"));
        assertEquals("Salom Buxoro", editor.getText());

        manager.undo();
        assertEquals("Salom dunyo", editor.getText());
        manager.redo();
        assertEquals("Salom Buxoro", editor.getText());
    }

    @Test
    public void multipleUndoAndRedoFollowCorrectOrder() {
        manager.execute(new InsertTextCommand(editor, 0, "A"));
        manager.execute(new InsertTextCommand(editor, 1, "B"));
        manager.execute(new InsertTextCommand(editor, 2, "C"));
        assertEquals("ABC", editor.getText());

        manager.undo();
        manager.undo();
        assertEquals("A", editor.getText());

        manager.redo();
        assertEquals("AB", editor.getText());
        manager.redo();
        assertEquals("ABC", editor.getText());
        assertFalse(manager.canRedo(), "redo tarixi tugagan bo'lishi kerak");
    }

    @Test
    public void newCommandClearsRedoHistory() {
        manager.execute(new InsertTextCommand(editor, 0, "A"));
        manager.execute(new InsertTextCommand(editor, 1, "B"));
        manager.undo();
        assertTrue(manager.canRedo(), "undo'dan keyin redo mumkin");

        manager.execute(new InsertTextCommand(editor, 1, "X"));

        assertFalse(manager.canRedo(), "yangi buyruq redo tarixini tozalashi kerak");
        assertFalse(manager.redo(), "redo false qaytarishi kerak");
        assertEquals("AX", editor.getText());
    }

    @Test
    public void undoAndRedoOnEmptyHistoryReturnFalse() {
        assertFalse(manager.undo(), "bo'sh tarixda undo false");
        assertFalse(manager.redo(), "bo'sh tarixda redo false");
        assertEquals("", editor.getText());
    }

    @Test
    public void failedCommandIsNotRecordedInHistory() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> manager.execute(new InsertTextCommand(editor, 5, "xato"))); // bo'sh matn, 5-pozitsiya yo'q

        assertEquals(0, manager.undoSize());
        assertFalse(manager.canUndo(), "muvaffaqiyatsiz buyruq tarixga tushmasligi kerak");
    }

    @Test
    public void eventsArePublishedToEventBus() {
        EventBus bus = new EventBus();
        List<String> topics = new ArrayList<>();
        bus.subscribe("*", e -> topics.add(e.getTopic()));
        CommandManager managerWithBus = new CommandManager(bus);

        managerWithBus.execute(new InsertTextCommand(editor, 0, "Hi"));
        managerWithBus.undo();
        managerWithBus.redo();

        assertEquals("[editor.executed, editor.undone, editor.redone]", topics.toString());
    }
}
