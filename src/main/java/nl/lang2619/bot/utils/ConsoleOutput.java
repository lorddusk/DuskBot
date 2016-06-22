package nl.lang2619.bot.utils;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.fxmisc.richtext.InlineCssTextArea;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

/**
 * Created by Tim on 1/10/2016.
 */
public class ConsoleOutput implements Runnable {

    private final PipedInputStream pin = new PipedInputStream();
    private final PipedInputStream pin2 = new PipedInputStream();
    private TextArea area;
    private Thread reader;
    private Thread reader2;
    private boolean quit;

    public void startReadingOutput(TextArea console) throws IOException {
        area = console;
        PipedOutputStream pout = new PipedOutputStream(this.pin);
        PipedOutputStream pout2 = new PipedOutputStream(this.pin2);

        System.setOut(new PrintStream(pout, true));
        System.setErr(new PrintStream(pout2, true));

        spinUpThreads();
    }

    private void spinUpThreads() {
        reader = new Thread((Runnable) this);
        reader.setDaemon(true);
        reader.start();

        reader2 = new Thread((Runnable) this);
        reader2.setDaemon(true);
        reader2.start();
    }

    public synchronized void run() {
        try {
            while (Thread.currentThread() == reader) {
                try {
                    this.wait(100);
                } catch (InterruptedException ie) {
                }
                if (pin.available() != 0) {
                    String input = this.readLine(pin);
                    area.appendText(input);
                }
                if (quit) return;
            }

            while (Thread.currentThread() == reader2) {
                try {
                    this.wait(100);
                } catch (InterruptedException ie) {
                }
                if (pin2.available() != 0) {
                    String input = this.readLine(pin2);
                    area.appendText(input);
                }
                if (quit) return;
            }
        } catch (Exception e) {
            area.appendText("\nConsole reports an Internal error.");
            area.appendText("The error is: " + e);
        }

    }

    private synchronized String readLine(PipedInputStream in) throws IOException {
        String input = "";
        do {
            int available = in.available();
            if (available == 0) break;
            byte b[] = new byte[available];
            in.read(b);
            input = input + new String(b, 0, b.length);
        } while (!input.endsWith("\n") && !input.endsWith("\r\n") && !quit);
        return input;
    }
}
