package commands;

import exceptions.MyNullPointerException;
import models.Difficulty;
import serverutility.ServerCommand;
import utility.CollectionManager;
import utility.CommandManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для удаления элемента из коллекции по заданной сложности.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class RemoveAnyByDifficulty extends Command {

    /**
     * Конструктор класса RemoveAnyByDifficulty.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией и выполнением команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public RemoveAnyByDifficulty(CollectionManager collectionManager, CommandManager commandManager, String command, List<Object> args, ServerCommand serverCommand, String login) {
        super(collectionManager, commandManager, args, serverCommand, login);
        commandManager.put(command);
    }

    /**
     * Метод execute() реализует логику команды remove_any_by_difficulty.
     * Удаляет элемент из коллекции по заданной сложности.
     * Если коллекция пуста, выбрасывается исключение MyNullPointerException.
     */
    @Override
    public List<Object> execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            collectionManager.removeAnyByDifficultyLabWork((Difficulty) args.get(0), login);
            List<Object> result = new ArrayList<>();
            result.add("Команда удаления по сложности выполнена");
            return result;
        } catch (MyNullPointerException e) {
            List<Object> list = new ArrayList<>();
            list.add(e.getMessage());
            return list;
        }
    }
}