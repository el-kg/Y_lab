package service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class AuditService implements AutoCloseable {
    private BufferedWriter writer;

    public AuditService(String filePath) throws IOException {
        writer = new BufferedWriter(new FileWriter(filePath, true));
    }

    public void log(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Ошибка записи в журнал.");
        }
    }

    public List<String> readLog() throws IOException {
        List<String> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("audit.log"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(line);
            }
        }
        return logs;
    }

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

    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}