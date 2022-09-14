import java.io.File;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path directory = Path.of("C:\\Users\\Shtigun\\Desktop\\5 сем\\Java\\Labs\\lab 2\\Рабочий Каталог");//паттерн команда
        WorkingDirectory.getInstance(String.valueOf(directory)).start(directory.getNameCount(), directory);
    }
}