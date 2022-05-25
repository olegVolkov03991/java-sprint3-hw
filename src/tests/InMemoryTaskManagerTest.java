package tests;

import main.menu.service.InMemoryTaskManager;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    public InMemoryTaskManagerTest(){
        super(new InMemoryTaskManager());
    }
}
