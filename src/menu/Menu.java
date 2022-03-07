package menu;

import service.Manager;
import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private Manager manager = new Manager();

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
                        manager.makeTask(name, description);
                        break;

                    case 2:
                        manager.makeEpic(name, description);
                        break;
                    case 3:
                        println("Введите id эпика");
                        int id = scanner.nextInt();
                        manager.makeSubTask(name, description, id);
                        break;
                    default:
                        println("Такого эпика нет");
                }
            }

            if (command == 2) {
                println("Список задач:");
                System.out.println(manager.getTasks());
                println("Список эпиков:");
                System.out.println(manager.getEpics());
                println("Список под_задач:");
                System.out.println(manager.getSubTasks());
            }

            if (command == 3) {
                manager.deleteTask();
                manager.deleteEpic();
                manager.deleteSubTask();
            }

            if (command == 4) {
                println("Введите id задачи");
                int id = scanner.nextInt();
                manager.getTaskById(id);
                manager.getEpicById(id);
                manager.getSubTaskById(id);
            }
            if (command == 5) {
                println("Введите id задачи");
                int id = scanner.nextInt();
                manager.deleteTaskById(id);
                manager.deleteEpicById(id);
                manager.deleteSubTaskById(id);
            }

            if (command == 6) {
                println("Введите id задачи");
                int id = scanner.nextInt();
                println("Введите новое имя");
                String name = scanner.next();
                println("Введите новое описание");
                String description = scanner.next();
                println("Выберете новый статус: 1 - NEW, 2 - IN_PROGESS, 3 - DONE");
                String status = null;
                int key = scanner.nextInt();
                switch (key) {
                    case 1:
                        status = "NEW";
                        break;
                    case 2:
                        status = " IN_PROGRESS";
                        break;
                    case 3:
                        status = "DONE";
                        break;
                    default:
                        println("такого статуса нет");
                }

                status = status;
                Task task = new Task(name, description, id, status);
                SubTask subTask = new SubTask(name, description, id, status);
                Epic epic = new Epic(name, description, id, status, (List<SubTask>) subTask);
                manager.updateTaskById(task);
                manager.updateSubTaskById(subTask);
                manager.updateEpicById(epic);
            }

            if (command == 7) {

                println("Введите id эпика");
                int id = scanner.nextInt();
                manager.getEpicOdSubTask(id);
            }
        }
    }
}




