package vazifa3.editor;

/** Command interfeysi: so'rov ob'ekt sifatida - bajarish va bekor qilish imkoniyati bilan. */
public interface Command {

    void execute();

    void undo();

    /** Tarix va hodisalar uchun qisqa tavsif. */
    String describe();
}
