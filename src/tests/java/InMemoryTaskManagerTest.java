package tests.java;

import main.java.service.InMemoryTaskManager;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{
    public InMemoryTaskManagerTest(){
        super(new InMemoryTaskManager());
    }
}
