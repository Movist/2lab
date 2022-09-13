import java.io.File;

public class Main {
    public static void main(String[] args) {
        String path = "C:\\Users\\Shtigun\\Desktop\\5 сем\\Java\\Labs\\lab 2\\Рабочий Каталог";//паттерн команда
        WorkingDirectory.getInstance(path).start();
    }
}