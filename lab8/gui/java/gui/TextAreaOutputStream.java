package gui;

import java.io.OutputStream;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class TextAreaOutputStream extends OutputStream {
    private final TextArea textArea;

    public TextAreaOutputStream(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) {
        Platform.runLater(() -> textArea.appendText(String.valueOf((char) b)));
    }

    @Override
    public void write(byte[] b, int off, int len) {
        String text = new String(b, off, len);
        Platform.runLater(() -> textArea.appendText(text));
    }
}