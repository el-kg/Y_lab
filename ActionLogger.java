import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActionLogger {
    private List<String> logs = new ArrayList<>();

    public void log(String action) {
        logs.add(LocalDateTime.now() + " - " + action);
    }

    public List<String> getLogs() {
        return logs;
    }

    public void exportLogs(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (String log : logs) {
                writer.println(log);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
