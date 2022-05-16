package Tests;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.FileBackedTaskManager;
import service.HistoryManager;
import service.Managers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class FileBackedTaskManagerTest extends TaskManagerTest {
    private static final File dbFile = new File("db.txt");
    private static final String LIE_DELIMITER = ",";
    private  final HistoryManager history = Managers.getDefaultHistory();

    public FileBackedTaskManagerTest(){
        super(new FileBackedTaskManager(dbFile));
    }

    @Test
    void testWriteFileBackedManager() throws IOException{
        int sectionTask = 0;
        int sectionHistory = 1;
        int taskIndex = 0;
        Task task1 = new Task("task1", "task1", task.getId(), Status.NEW);
        history.add(task1);
        history.add(task1);
        String save[] = Files.readString(Path.of(String.valueOf(dbFile))).split(LIE_DELIMITER);
        String task[] = save[sectionTask].split(LIE_DELIMITER);
        String expectedTaskValue = task1.toString();
        String taskValue = task[taskIndex];
        String expectedHistoryValue = save[sectionHistory];
        List<Task> historyValue = history.getHistory();
        Assertions.assertEquals(expectedTaskValue, taskValue, "задача не сохранилась в файл");
        Assertions.assertEquals(expectedHistoryValue, historyValue, "история не сохранилась в файл");
    }

    @Test
    void testFileClear() throws IOException{
        String savedState[] = Files.readString(Path.of(String.valueOf(dbFile))).split(LIE_DELIMITER);
        int expectedSize = 0;
        int size = savedState.length;
        Assertions.assertEquals(expectedSize, size, "после удаления" + " файл не пуст");
    }
}