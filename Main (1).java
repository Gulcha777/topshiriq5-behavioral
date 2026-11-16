package vazifa3;

import vazifa3.editor.CommandManager;
import vazifa3.editor.DeleteTextCommand;
import vazifa3.editor.InsertTextCommand;
import vazifa3.editor.ReplaceTextCommand;
import vazifa3.editor.TextEditor;
import vazifa3.eventbus.EventBus;
import vazifa3.eventbus.PriorityDelivery;
import vazifa3.eventbus.ReverseDelivery;
import vazifa3.eventbus.SequentialDelivery;
import vazifa3.eventbus.Subscription;

/** Vazifa 3 demosi: EventBus (Observer + Strategy) va Command orqali Undo/Redo. */
public class Main {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("=== VAZIFA 3: EventBus (Observer + Strategy) ===");
        demoEventBus();

        System.out.println("\n=== VAZIFA 3 (qo'shimcha): Command - Undo/Redo ===");
        demoUndoRedo();
    }

    private static void demoEventBus() {
        EventBus bus = new EventBus(new SequentialDelivery());

        bus.subscribe("buyurtma", e -> System.out.println("  [Ombor    ] " + e.getPayload()));
        bus.subscribe("buyurtma", 10, e -> System.out.println("  [To'lov   ] " + e.getPayload()));
        Subscription audit = bus.subscribe("*", 5, e -> System.out.println("  [Audit    ] " + e));

        System.out.println("\n-- 1) " + bus.getDeliveryStrategy().getName() + " --");
        bus.publish("buyurtma", "Buyurtma #101 yaratildi");

        bus.setDeliveryStrategy(new PriorityDelivery());
        System.out.println("\n-- 2) " + bus.getDeliveryStrategy().getName() + " --");
        bus.publish("buyurtma", "Buyurtma #102 yaratildi");

        bus.setDeliveryStrategy(new ReverseDelivery());
        System.out.println("\n-- 3) " + bus.getDeliveryStrategy().getName() + " --");
        bus.publish("buyurtma", "Buyurtma #103 yaratildi");

        bus.unsubscribe(audit);
        System.out.println("\n-- 4) Audit obunasi bekor qilindi --");
        int delivered = bus.publish("buyurtma", "Buyurtma #104 yaratildi");
        System.out.println("  Yetkazilgan kuzatuvchilar soni: " + delivered);
    }

    private static void demoUndoRedo() {
        EventBus bus = new EventBus();
        bus.subscribe("*", e -> System.out.println("  [hodisa] " + e.getTopic() + " -> " + e.getPayload()));

        TextEditor editor = new TextEditor();
        CommandManager manager = new CommandManager(bus);

        manager.execute(new InsertTextCommand(editor, 0, "Salom dunyo"));
        show(editor);
        manager.execute(new ReplaceTextCommand(editor, 6, 5, "Buxoro"));
        show(editor);
        manager.execute(new DeleteTextCommand(editor, 0, 6));
        show(editor);

        System.out.println("  -- undo x2 --");
        manager.undo();
        manager.undo();
        show(editor);

        System.out.println("  -- redo --");
        manager.redo();
        show(editor);

        System.out.println("  -- yangi buyruq redo tarixini tozalaydi --");
        manager.execute(new InsertTextCommand(editor, editor.length(), "!"));
        show(editor);
        System.out.println("  canRedo = " + manager.canRedo() + ", undoSize = " + manager.undoSize());
    }

    private static void show(TextEditor editor) {
        System.out.println("  Matn: \"" + editor.getText() + "\"");
    }
}
