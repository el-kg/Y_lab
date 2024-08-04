package src.test.java.service;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.service.AuditService;

import java.io.*;
import java.util.List;

class AuditServiceTest {

    private static final String TEST_FILE_PATH = "test-audit.log";
    private AuditService auditService;

    @BeforeEach
    void setUp() throws IOException {
        // Создаем новый экземпляр AuditService перед каждым тестом
        auditService = new AuditService(TEST_FILE_PATH);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (auditService != null) {
            auditService.close();
        }
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            if (!file.delete()) {
                System.out.println("Не удалось удалить файл: " + TEST_FILE_PATH);
            }
        }
    }

    @Test
    void testLog() throws IOException {
        // Логируем сообщение
        auditService.log("Test message");

        // Проверяем, что сообщение записано в файл
        List<String> logs = auditService.readLog();
        assertEquals(1, logs.size(), "Размер лога должен быть 1");
        assertEquals("Test message", logs.get(0), "Содержимое лога должно быть 'Test message'");
    }

    @Test
    void testReadLog() throws IOException {
        // Логируем несколько сообщений
        auditService.log("First message");
        auditService.log("Second message");

        // Проверяем, что все сообщения читаются из файла
        List<String> logs = auditService.readLog();
        assertEquals(2, logs.size(), "Размер лога должен быть 2");
        assertEquals("First message", logs.get(0), "Первое сообщение должно быть 'First message'");
        assertEquals("Second message", logs.get(1), "Второе сообщение должно быть 'Second message'");
    }

    @Test
    void testFilterLog() throws IOException {
        // Логируем несколько сообщений
        auditService.log("Error occurred");
        auditService.log("Info message");
        auditService.log("Another error");

        // Проверяем фильтрацию сообщений по ключевому слову
        List<String> filteredLogs = auditService.filterLog("error");
        assertEquals(2, filteredLogs.size(), "Фильтр должен вернуть 2 сообщения");
        assertTrue(filteredLogs.contains("Error occurred"), "Сообщение 'Error occurred' должно быть в фильтрованных логах");
        assertTrue(filteredLogs.contains("Another error"), "Сообщение 'Another error' должно быть в фильтрованных логах");
    }
}