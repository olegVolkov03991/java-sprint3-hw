package memoryManagers;

import service.HistoryManager;
import service.Manager;

public class Managers {

    public static Manager getDefault(){
        Manager inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }

    public static HistoryManager getDefaultHistory(){
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        return inMemoryHistoryManager;
    }
}
