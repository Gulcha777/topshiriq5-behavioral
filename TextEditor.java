package vazifa3.editor;

/** Receiver: haqiqiy ishni bajaruvchi oddiy matn muharriri (faqat matn bilan ishlaydi). */
public class TextEditor {

    private final StringBuilder text = new StringBuilder();

    public String getText() {
        return text.toString();
    }

    public int length() {
        return text.length();
    }

    /** position (0..length) o'rniga matn qo'shadi. */
    public void insert(int position, String value) {
        if (value == null) {
            throw new IllegalArgumentException("Matn null bo'lishi mumkin emas");
        }
        if (position < 0 || position > text.length()) {
            throw new IndexOutOfBoundsException(
                    "Pozitsiya 0.." + text.length() + " oralig'ida bo'lishi kerak: " + position);
        }
        text.insert(position, value);
    }

    /** start dan boshlab length ta belgini o'chiradi va o'chirilgan matnni qaytaradi. */
    public String delete(int start, int length) {
        if (start < 0 || length < 0 || start + length > text.length()) {
            throw new IndexOutOfBoundsException(
                    "Noto'g'ri diapazon: start=" + start + ", uzunlik=" + length
                            + " (matn uzunligi " + text.length() + ")");
        }
        String removed = text.substring(start, start + length);
        text.delete(start, start + length);
        return removed;
    }
}
