package tests;

import service.InMemoryTaskManager;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    public InMemoryTaskManagerTest(){
        super(new InMemoryTaskManager());
    }
}
