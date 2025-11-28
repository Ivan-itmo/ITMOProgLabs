package asks;

import utility.AskerValidatorArgs;
import utility.ConsoleManager;

/**
 * Класс AskerFileNameScript предназначен для запроса имени файла у пользователя.
 * Используется для выполнения скрипта, который находится в указанном файле.
 * Наследует функциональность от класса Asker.
 */
public class AskerValidatorArgsFileNameScript extends AskerValidatorArgs {

    /**
     * Конструктор класса AskerFileNameScript.
     *
     * @param consoleManager объект класса ConsoleManager, используемый для взаимодействия с консолью.
     */
    public AskerValidatorArgsFileNameScript(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    private String filename;

    /**
     * Метод ask запрашивает у пользователя имя файла, который содержит скрипт для выполнения.
     * Результат сохраняется в поле filename.
     */
    @Override
    public void ask() {
        this.filename = askFileName("execute_script");
    }

    /**
     * Возвращает имя файла, которое было запрошено у пользователя.
     *
     * @return имя файла, содержащего скрипт для выполнения.
     */
    public String getFilename() {
        return filename;
    }
}