package service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for auditing log messages.
 * Implements AutoCloseable for automatic resource management.
 */
public class AuditService implements AutoCloseable {
    private BufferedWriter writer;

    /**
     * Constructs an AuditService that writes to the specified file.
     *
     * @param filePath the path of the file to write audit logs to
     * @throws IOException if an I/O error occurs
     */
    public AuditService(String filePath) throws IOException {
        writer = new BufferedWriter(new FileWriter(filePath, true));
    }

    /**
     * Logs a message to the audit log file.
     *
     * @param message the message to be logged
     */
    public void log(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Ошибка записи в журнал.");
        }
    }

    /**
     * Reads all log messages from the audit log file.
     *
     * @return a list of all log messages
     * @throws IOException if an I/O error occurs
     */
    public List<String> readLog() throws IOException {
        List<String> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(writer.toString()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(line);
            }
        }
        return logs;
    }

    /**
     * Filters the log messages based on the specified keyword.
     *
     * @param keyword the keyword to filter log messages by
     * @return a list of log messages containing the keyword
     * @throws IOException if an I/O error occurs
     */
    public List<String> filterLog(String keyword) throws IOException {
        List<String> logs = readLog();
        List<String> filteredLogs = new ArrayList<>();
        for (String log : logs) {
            if (log.contains(keyword)) {
                filteredLogs.add(log);
            }
        }
        return filteredLogs;
    }

    /**
     * Closes the BufferedWriter resource.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}