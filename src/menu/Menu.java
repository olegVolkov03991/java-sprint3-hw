package menu;

import service.HistoryManager;
import service.Managers;
import model.*;
import service.IdGenerator;
import service.TaskManager;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private Managers managers = new Managers();
    private TaskManager inMemoryTaskManager = managers.getDefault();
    private HistoryManager historyManager = managers.getDefaultHistory();
    private IdGenerator idGenerator = new IdGenerator();

    public static void println(String message) {
        System.out.println(message);
    }

    public void printMenu() {
        while (true) {
            println("1 - Создать задачу");
            println("2 - Вывести список  задач");
            println("3 - Удаление всех задач");
            println("4 - Вывод задач по id");
            println("5 - Удаление задачи по id");
            println("6 - Обновление задачи по id");
            println("7 - Вывести под_задачи эпика");
            println("8 - Вывести последние 10 операций");

            int command = scanner.nextInt();

            if (command == 1) {
                println("Введите название");
                String name = scanner.next();
                println("Введите описание");
                String description = scanner.next();
                println("К какому типу относится задача : 1 - задача, 2 - эпик, 3 - подзадача");
                int type = scanner.nextInt();
                switch (type) {
                    case 1:
                        inMemoryTaskManager.createTask(name, description);
                        break;
                    case 2:
                        inMemoryTaskManager.createEpic(name, description);
                        break;
                    case 3:
                        println("Введите id эпика");
                        int id = scanner.nextInt();
                        inMemoryTaskManager.createSubTask(name, description, id);
                        break;
                    default:
                        println("Такого эпика нет");
                }
            }

            if (command == 2) {
                println("Список задач:");
                System.out.println(inMemoryTaskManager.getTasks());
                println("Список эпиков:");
                System.out.println(inMemoryTaskManager.getEpics());
                println("Список под_задач:");
                System.out.println(inMemoryTaskManager.getSubTasks());
            }

            if (command == 3) {
                inMemoryTaskManager.deleteTask();
                inMemoryTaskManager.deleteEpic();
                inMemoryTaskManager.deleteSubTask();
            }

            if (command == 4) {
                println("Введите id задачи");
                int id = scanner.nextInt();
                inMemoryTaskManager.getTaskById(id);
                inMemoryTaskManager.getEpicById(id);
                inMemoryTaskManager.getSubTaskById(id);
            }

            if (command == 5) {
                println("Введите id задачи");
                int id = scanner.nextInt();
                inMemoryTaskManager.deleteTaskById(id);
                inMemoryTaskManager.deleteEpicById(id);
                inMemoryTaskManager.deleteSubTaskById(id);
            }

            if (command == 6) {
                println("Введите id задачи");
                int id = scanner.nextInt();
                println("Введите новое имя");
                String name = scanner.next();
                println("Введите новое описание");
                String description = scanner.next();
                println("Выберете новый статус: 1 - NEW, 2 - IN_PROGESS, 3 - DONE");
                Status status = null;
                int key = scanner.nextInt();
                switch (key) {
                    case 1:
                        status = Status.NEW;
                        break;
                    case 2:
                        status = Status.IN_PROGRESS;
                        break;
                    case 3:
                        status = Status.DONE;
                        break;
                    default:
                        println("такого статуса нет");
                }

                status = status;
                Task task = new Task(name, description, id, status);
                SubTask subTask = new SubTask(name, description, id, status);
                Epic epic = new Epic(name, description, id, status, (List<SubTask>) subTask);
                inMemoryTaskManager.updateTaskById(task);
                inMemoryTaskManager.updateSubTaskById(subTask);
                inMemoryTaskManager.updateEpicById(epic);
            }

            if (command == 7) {
                println("Введите id эпика");
                int id = scanner.nextInt();
                inMemoryTaskManager.printEpicSubTask(id);
            }

            if(command == 8){
                System.out.println(historyManager.getHistory());
            }
        }
    }
}
