package Tests;

import service.InMemoryTaskManager;

class InMemoryTaskManagerTest extends TaskManagerTest {
    public InMemoryTaskManagerTest(){
        super(new InMemoryTaskManager());
    }
}

