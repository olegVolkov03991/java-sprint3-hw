import main.menu.server.HttpTaskServer;
import main.menu.server.KVServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IndexOutOfBoundsException, IOException {
        new KVServer().start();
        new HttpTaskServer();
    }
}
