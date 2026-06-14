package com.pemkom.objects.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JLabel;

public class DigitalClockService {

    private final JLabel targetLabel;
    private final String pattern;

    public DigitalClockService(JLabel targetLabel, String pattern) {
        this.targetLabel = targetLabel;
        this.pattern = pattern;
    }

    public Thread getThread() {
        Runnable clockTask = () -> {
            // GANTI jadi ini
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, new Locale("id", "ID"));
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    LocalDateTime now = LocalDateTime.now();
                    String formatted = now.format(formatter);
                    javax.swing.SwingUtilities.invokeLater(()
                            -> targetLabel.setText(formatted)
                    );
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()
                        + " dihentikan.");
            }
        };
        return new Thread(clockTask);
    }
}
