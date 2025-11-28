import filesutility.FileLoader;
import models.LabWork;
import serverutility.*;
import utility.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager();
        //ServerAnswers serverAnswers = new ServerAnswers();
        //ServerCommand serverCommand = new ServerCommand(collectionManager, commandManager, serverAnswers);
        FileLoader fileLoader = new FileLoader();
        Server server = null;
        try {
            ServerCommand handler = new ServerCommand(collectionManager, commandManager); // Ваша реализация
            server = new Server(handler, 4); // 4 потока для каждого этапа
            //int port = server.getPort(); // Получаем сгенерированный порт


        } catch (IOException e) {
            System.err.println("Ошибка при работе сервера: " + e.getMessage());
            System.exit(1);
        }


        Scanner scanner = new Scanner(System.in);

        String filePath;
        if (args.length > 0) {
            filePath = args[0];
        } else {
            System.out.println("Введите путь к файлу для загрузки данных:");
            filePath = scanner.nextLine().trim();
        }
        System.out.println("Путь к файлу: " + filePath);

        try {
            List<LabWork> labWorks = fileLoader.loadFromFile(filePath);

            if (labWorks != null && !labWorks.isEmpty()) {
                for (LabWork labWork : labWorks) {
                    if (labWork != null && labWork.getId() != null) {
                        IdGenerator.generateId(labWork.getId(), labWork);
                        collectionManager.addLabWork(labWork);
                    } else {
                        System.out.println("Ошибка: Некорректные данные в объекте LabWork.");
                        System.exit(1);
                    }
                }
                commandManager.setFilePath(filePath);
            } else {
                /*List<Object> list = new ArrayList<>();
                list.add("Файл пуст или данные не были загружены.");
                serverAnswers.setAnswers(list);*/
                System.out.println("Файл пуст или данные не были загружены.");
                System.exit(1);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: Файл не найден - " + filePath);
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке данных: " + e.getMessage());
            System.exit(1);
        }
        server.start();
    }
}