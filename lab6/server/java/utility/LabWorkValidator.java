package utility;

import models.*;

import java.util.HashSet;
import java.util.Set;

public class LabWorkValidator {

    private static final Set<Integer> uniqueIds = new HashSet<>();

    /**
     * Проверяет корректность объекта LabWork.
     *
     * @param labWork Объект LabWork для проверки.
     * @return true, если объект корректен, иначе false.
     * @throws IllegalArgumentException если идентификатор не уникален.
     */
    public static boolean validate(LabWork labWork) throws IllegalArgumentException {
        return validateId(labWork.getId())
                && validateLabWorkName(labWork.getName())
                && validateCoordinates(labWork.getCoordinates())
                && validateCreationDate(labWork.getCreationDate())
                && validateMinimalPoint(labWork.getMinimalPoint())
                && validateDifficulty(labWork.getDifficulty())
                && validateAuthor(labWork.getAuthor());
    }

    /**
     * Проверяет корректность поля id.
     *
     * @param id Уникальный идентификатор лабораторной работы.
     * @return true, если поле корректно, иначе false.
     * @throws IllegalArgumentException если идентификатор не уникален.
     */
    public static boolean validateId(Integer id) throws IllegalArgumentException {
        if (id == null || id <= 0) {
            System.out.println("Ошибка: поле 'id' не может быть null и должно быть больше 0.");
            return false;
        }
        if (uniqueIds.contains(id)) {
            throw new IllegalArgumentException("Ошибка: идентификатор " + id + " не уникален.");
        }
        uniqueIds.add(id);
        return true;
    }

    /**
     * Проверяет корректность поля name в объекте LabWork.
     *
     * @param name Название лабораторной работы.
     * @return true, если поле корректно, иначе false.
     */
    public static boolean validateLabWorkName(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Ошибка: поле 'name' в LabWork не может быть null или пустым.");
            return false;
        }
        return true;
    }

    /**
     * Проверяет корректность поля coordinates.
     *
     * @param coordinates Координаты лабораторной работы.
     * @return true, если поле корректно, иначе false.
     */
    public static boolean validateCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            System.out.println("Ошибка: поле 'coordinates' не может быть null.");
            return false;
        }
        return validateX(coordinates.getX()) && validateY(coordinates.getY());
    }

    /**
     * Проверяет корректность поля x.
     *
     * @param x Координата x.
     * @return true, если поле корректно, иначе false.
     */
    public static boolean validateX(long x) {
        if (x > 249) {
            System.out.println("Ошибка: поле 'x' не может быть больше 249.");
            return false;
        }
        return true;
    }

    /**
     * Проверяет корректность поля y.
     *
     * @param y Координата y.
     * @return true, если поле корректно, иначе false.
     */
    public static boolean validateY(Long y) {
        if (y == null) {
            System.out.println("Ошибка: поле 'y' не может быть null.");
            return false;
        }
        return true;
    }

    /**
     * Проверяет корректность поля creationDate.
     *
     * @param creationDate Дата создания лабораторной работы.
     * @return true, если поле корректно, иначе false.
     */
    public static boolean validateCreationDate(java.time.LocalDateTime creationDate) {
        if (creationDate == null) {
            System.out.println("Ошибка: поле 'creationDate' не может быть null.");
            return false;
        }
        return true;
    }

    /**
     * Проверяет корректность поля minimalPoint.
     *
     * @param minimalPoint Минимальное количество баллов.
     * @return true, если поле корректно, иначе false.
     */
    public static boolean validateMinimalPoint(long minimalPoint) {
        if (minimalPoint <= 0) {
            System.out.println("Ошибка: поле 'minimalPoint' должно быть больше 0.");
            return false;
        }
        return true;
    }

    /**
     * Проверяет корректность поля difficulty.
     *
     * @param difficulty Уровень сложности лабораторной работы.
     * @return true, если поле корректно, иначе false.
     */
    public static boolean validateDifficulty(Difficulty difficulty) {
        // Поле difficulty может быть null, поэтому проверка не требуется
        return true;
    }

    /**
     * Проверяет корректность поля author.
     *
     * @param author Автор лабораторной работы.
     * @return true, если поле корректно, иначе false.
     */
    public static boolean validateAuthor(Person author) {
        if (author == null) {
            System.out.println("Ошибка: поле 'author' не может быть null.");
            return false;
        }
        return validatePerson(author);
    }

    /**
     * Проверяет корректность объекта Person.
     *
     * @param person Объект Person для проверки.
     * @return true, если объект корректен, иначе false.
     */
    public static boolean validatePerson(Person person) {
        return validatePersonName(person.getName())
                && validateHeight(person.getHeight())
                && validateEyeColor(person.getEyeColor());
    }

    /**
     * Проверяет корректность поля name в объекте Person.
     *
     * @param name Имя автора.
     * @return true, если поле корректно, иначе false.
     */
    public static boolean validatePersonName(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Ошибка: поле 'name' в Person не может быть null или пустым.");
            return false;
        }
        return true;
    }

    /**
     * Проверяет корректность поля height.
     *
     * @param height Рост автора.
     * @return true, если поле корректно, иначе false.
     */
    public static boolean validateHeight(float height) {
        if (height <= 0) {
            System.out.println("Ошибка: поле 'height' должно быть больше 0.");
            return false;
        }
        return true;
    }

    /**
     * Проверяет корректность поля eyeColor.
     *
     * @param eyeColor Цвет глаз автора.
     * @return true, если поле корректно, иначе false.
     */
    public static boolean validateEyeColor(Color eyeColor) {
        if (eyeColor == null) {
            System.out.println("Ошибка: поле 'eyeColor' не может быть null.");
            return false;
        }
        return true;
    }
}