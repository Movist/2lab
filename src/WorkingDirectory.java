import java.io.File;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
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

    public void start(int level, Path dir) {
        help();
        pwd(level, dir);
        Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        changeCommand(command, level, dir);
    }

    private static void help() {
        System.out.println();
        System.out.println("________________________________________________________________________________________________________________");
        System.out.println("Список доступных команд: \n" +
                "lsReverse \t" +
                "getParent \t" +
                "cdDown \t" +
                "cd \t" +
                "ls \t" +
                "mkdir \t" +
                "findDir \t" +
                "rmReverse \t" +
                "findFormat \t" +
                "findName \t" +
                "exit");
        System.out.println("________________________________________________________________________________________________________________");
        System.out.println();
    }

    private void changeCommand(String command, int level, Path dir) {

        switch (command) {
            case "lsReverse" -> lsReverse(level, dir);
            case "getParent" -> getParent(level, dir);
            case "cdDown" -> cdDown(level, dir);
            case "ls" -> ls(level, dir);
            case "findDir" -> findDir(level, dir);
            case "mkdir" -> mkdir(level, dir);
            case "cd" -> cd(level, dir);
            case "rmReverse" -> rmReverse(level, dir);
            case "findFormat" -> findFormat(level, dir);
            case "findName" -> findName(level, dir);
            case "exit" -> {
                System.out.println("Выход из программы..");
                System.exit(0);
            }
            default -> System.out.println("Syntax error");
        }
        start(level, dir);
    }

    private void changeCommand(String command, int level) {
        Path dir = Path.of(directoryName);
        changeCommand(command, level, dir);
    }

    private void pwd(int level, Path dir) {
        System.out.print(dir.getRoot().resolve(dir.subpath(0, level)) + "> ");
    }

    //1)	метод вывода содержимого рабочего каталога;
    private void lsReverse(int level, Path dir) {
        dir = dir.getRoot().resolve(dir.subpath(0, level));
        File[] files = dir.toFile().listFiles();
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
    private void getParent(int level, Path dir) {
        dir = dir.getRoot().resolve(dir.subpath(0, level));
        System.out.println(dir.getParent().toAbsolutePath());
    }

    //3)	метод перехода к родительскому каталогу, при этом имя текущего каталога изменяется на имя родительского;
    private void cdDown(int level, Path dir) {
        if (level > 1) {
            level--;
            start(level, dir);
        } else {
            System.out.println("Корневая директория");
            start(level, dir);
        }
    }

    //4)	метод вывода содержимого текущего каталога (без вложения)??;
    private void ls(int level, Path dir) {
        dir = dir.getRoot().resolve(dir.subpath(0, level));
        File[] files = dir.toFile().listFiles();
        for (File file : files) {
            System.out.println(file.getName());
        }
    }

    //5)	метод проверки существования дочернего каталога с заданным именем в текущем рабочем каталоге;
    private void findDir(int level, Path dir) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите имя каталога:");
        String dirExist = sc.nextLine();
        dir = dir.getRoot().resolve(dir.subpath(0, level).resolve(dirExist));
        if (Files.exists(dir)) {
            System.out.println("Каталог с именем " + dirExist + " существует");
        } else System.out.println("Каталог с именем " + dirExist + " не существует");

    }

    //6)	метод создания нового каталога в текущем каталоге;
    private void mkdir(int level, Path dir) {//Нужны потоки для синхронной работы?
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Введите имя директории:");
            String newDirName = sc.nextLine();
            dir = dir.getRoot().resolve(dir.subpath(0, level).resolve(newDirName));
            Files.createDirectories(dir);
            System.out.println("Директория создана в " + dir);
        } catch (IOException e) {
            System.err.println("Ошибка создания директории " + e.getMessage());
        }
    }

    //7)	метод перехода в подкаталог текущего каталога, при этом имя текущего каталога изменяется на имя каталога, в который перешли;
    private void cd(int level, Path dir) {
        Scanner sc = new Scanner(System.in);
        System.out.println("В какую директорию перейти? Список доступных директорий:");
        ls(level, dir);
        String newDir = sc.nextLine();
        dir = Path.of(dir.getRoot().resolve(dir.subpath(0, level)).resolve(newDir).toUri());
        level++;
        start(level, dir);
    }

    //8)	*метод удаления всех подкаталогов, вложенные в данный;
    private void rmReverse(int level, Path dir) {
        dir = dir.getRoot().resolve(dir.subpath(0, level));
        System.out.println("Удаление вложенных каталогов вместе с текущим каталогом");
        if (Files.exists(dir)) {
            try {
                Files.walkFileTree(dir, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path path, BasicFileAttributes attr)
                            throws IOException {
                        Files.delete(path);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path path, IOException ex)
                            throws IOException {
                        Files.delete(path);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        level--;
        start(level, dir);
    }

    //9)	*метод вывода списка файлов определенного формата (расширения). Расширение передается параметром, содержащихся в заданном каталоге;
    private void findFormat(int level, Path dir) {
        dir = dir.getRoot().resolve(dir.subpath(0, level));
        File[] files = dir.toFile().listFiles();
        Scanner sc = new Scanner(System.in);
        System.out.print("Файлы какого расширения искать? : .");
        String str = sc.nextLine();
        String result;
        int count = 0;
        for (File file : files) {
            if (file.isFile()) {
                result = file.getName();
                if (result.contains("." + str)) {
                    count++;
                    System.out.println(result);
                }
            }
        }
        if (count == 0) {
            System.out.println("Не найдено ни одного файла с таким расширением");
        }
    }

    //10)	*метод проверки существования подкаталога с заданным именем с любым уровнем вложенности
    // в текущем рабочем каталоге (искомый подкаталог может быть дочерним для вложенного каталога в текущий каталог). ??
    private void findName(int level, Path dir) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите имя каталога, который нужно найти:");
        String str = sc.nextLine();
        String result;
        int count = 0;
        dir = dir.getRoot().resolve(dir.subpath(0, level));
        File[] files = dir.toFile().listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (file.exists()) {
                    result = file.getName();
                    /*unpackFiles(file, result, str, count);*/
                    count += unpackFiles(file, result, str, count);
                }
            }
        }
        if (count == 0) {
            System.out.println("Каталог с именем " + str + " не существует");
        } else System.out.println("Каталог с именем " + str + " существует");
    }

    private int unpackFiles(File f, String result, String str, int count) {
        for (File file : f.listFiles()) {
            if (file.isDirectory()) {
                result = file.getName();
                if (result.equals(str)) {
                    count++;
                }
                unpackFiles(file, result, str, count);
            }
        }
        return count;
    }

}
