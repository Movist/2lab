import java.awt.font.FontRenderContext;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WorkingDirectory {
    private static WorkingDirectory instance;
    private final String directoryName;

    private WorkingDirectory(String directoryName) {
        this.directoryName = directoryName;
    }

    public static WorkingDirectory getInstance(String directoryName) {
        if (instance == null) {
            instance = new WorkingDirectory(directoryName);
        }
        return instance;
    }

    public void start() {
        help();
        pwd(directoryName);
        Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        changeCommand(command);
    }

    private static void help() {
        System.out.println();
        System.out.println("________________________________________________________________________________________________________________");
        System.out.println("Список доступных команд: \n" +
                "lsReverse \t" +
                "getParent \t" +
                "cdUp \t" +
                "ls \t" +
                "find \t" +
                "mkdir \t" +
                "cdDown \t" +
                "rmReverse \t" +
                "findFormat \t" +
                "command10 \t" +
                "command11 \t" +
                "exit");
        System.out.println("________________________________________________________________________________________________________________");
        System.out.println();
    }

    private void changeCommand(String command) {
        switch (command) {
            case "lsReverse" -> lsReverse();
            case "getParent" -> getParent();
            case "cdUp" -> cdUp();
            case "ls" -> ls();
            case "find" -> find();
            case "mkdir" -> mkdir();
            case "cdDown" -> cdDown();
            case "rmReverse" -> rmReverse();
            case "findFormat" -> findFormat();
            case "command10" -> command10();
            case "command11" -> {
                command11();
            }
            case "exit" -> {
                System.out.println("Выход из программы..");
                System.exit(0);
            }
            default -> System.out.println("Неверна выбрана команда");

        }
        start();
    }

    private void pwd() {
        File f = new File(directoryName);
        Path.of()
        System.out.print(currentDirectory + "> ");
    }

    //1)	метод вывода содержимого рабочего каталога;
    private void lsReverse() {
        File f = new File(directoryName);
        File[] files = f.listFiles();
        for (File file : files) {
            StringBuffer count = new StringBuffer("");
            if (file.isDirectory()) {
                if (file.exists()) {
                    System.out.println(file.getName());
                    count.append("-");
                    unpack(file, count);
                }
            } else System.out.println(file.getName());
        }
    }

    private void unpack(File f, StringBuffer count) {
        for (File file : f.listFiles()) {
            System.out.println(count + file.getName());
            if (file.isDirectory()) {
                count.append("-");
                unpack(file, count);
            }
        }
        count.delete(0, 1);
    }

    //2)	метод, возвращающий имя родительского каталога;
    private void getParent() {
        File f = new File(directoryName);
        System.out.println(f.getParentFile().getName());
    }

    //3)	метод перехода к родительскому каталогу, при этом имя текущего каталога изменяется на имя родительского;
    private void cdUp() {
        File f = new File(directoryName);
        String path = f.getParent();
        File newDirectory = new File(path);
        System.out.println(newDirectory);
    }

    //4)	метод вывода содержимого текущего каталога (без вложения)??;
    private void ls() {
        File f = new File(directoryName);
        File[] files = f.listFiles();
        for (File file : files) {
            System.out.println(file.getName());
        }
    }

    //5)	метод проверки существования дочернего каталога с заданным именем в текущем рабочем каталоге;
    private void find() {

    }

    //6)	метод создания нового каталога в текущем каталоге;
    private void mkdir() {

    }

    //7)	метод перехода в подкаталог текущего каталога, при этом имя текущего каталога изменяется на имя каталога, в который перешли;
    private void cdDown() {

    }

    //8)	*метод удаления всех подкаталогов, вложенные в данный;
    private void rmReverse() {

    }

    //9)	*метод вывода списка файлов определенного формата (расширения). Расширение передается параметром, содержащихся в заданном каталоге;
    private void findFormat() {

    }

    //10)	*метод вывода иерархического списка всех подкаталогов, вложенных в данный ??;
    private void command10() {

    }

    //11)	*метод проверки существования подкаталога с заданным именем с любым уровнем вложенности
    // в текущем рабочем каталоге (искомый подкаталог может быть дочерним для вложенного каталога в текущий каталог). ??
    private void command11() {

    }
}
