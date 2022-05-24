import menu.Menu;
import service.HTTPTaskServer;
import service.KVServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IndexOutOfBoundsException, InterruptedException, IOException {
        new KVServer().start();
        new HTTPTaskServer();
//        Menu menu = new Menu();
//        menu.printMenu();
    }
}
