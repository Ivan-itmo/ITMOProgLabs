package utility;

import exceptions.MyInputMismatchException;
import exceptions.MyInvalidRangeException;
import exceptions.WrongIdException;
import models.Color;
import models.Country;
import models.Difficulty;

/**
 * Класс аскера, нужен для интерактива, то есть в нем реализованы методы вопросов для ввода данных
 */
public class Asker {
    ConsoleManager consoleManager;
    public Asker(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }
    public void ask(){};

    public int askId(String command){
        // Ввод id
        int id;
        while (true) {
            System.out.println("Введите id (целое значение, начиная с 1):");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите целое число!");
                }
                id = Integer.parseInt(input);
                if (id > IdGenerator.getId()) {
                    throw new WrongIdException("Ошибка: id больше размера массива");
                }
                if (id <= 0) {
                    throw new MyInvalidRangeException("Ошибка: id <= 0");
                }
                break;
            } catch (MyInputMismatchException | WrongIdException | MyInvalidRangeException e) {
                System.out.println(e.getMessage());
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            }
        }
        return id;
    }

    public String askName(String command){
        String name;
        while (true) {
            System.out.println("Введите name (не null и не пустая строка):");
            name = consoleManager.input(command).trim();
            if (name.isEmpty()) {
                System.out.println("Ошибка: name не может быть пустым!");
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            } else {
                break;
            }
        }
        return name;
    }

    public long askX(String command){
        long x;
        while (true) {
            System.out.println("Введите координату X (не больше 249):");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите целое число!");
                }
                x = Long.parseLong(input);
                if (x > 249) {
                    throw new MyInvalidRangeException("Ошибка: X не может быть больше 249!");
                }
                break;
            } catch (MyInputMismatchException | MyInvalidRangeException e) {
                System.out.println(e.getMessage());
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            }
        }
        return x;
    }

    public Long askY(String command){
        Long y;
        while (true) {
            System.out.println("Введите координату Y:");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите целое число!");
                }
                y = Long.parseLong(input);
                break;
            } catch (MyInputMismatchException e) {
                System.out.println(e.getMessage());
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            }
        }
        return y;
    }

    public long askminimalPoint(String command){
        long minimalPoint;
        while (true) {
            System.out.println("Введите minimalPoint (должно быть больше 0):");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите целое число!");
                }
                minimalPoint = Long.parseLong(input);
                if (minimalPoint <= 0) {
                    throw new MyInvalidRangeException("Ошибка: minimalPoint должен быть больше 0!");
                }
                break;
            } catch (MyInputMismatchException | MyInvalidRangeException e) {
                System.out.println(e.getMessage());
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            }
        }
        return minimalPoint;
    }

    public Difficulty askDifficulty(String command){
        // Ввод сложности (Difficulty)
        Difficulty difficulty;
        while (true) {
            System.out.println("Введите сложность (EASY, MEDIUM, HARD, INSANE, TERRIBLE):");
            try {
                difficulty = Difficulty.valueOf(consoleManager.input(command).trim().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: Неверный уровень сложности! Введите одно из значений: EASY, MEDIUM, HARD, INSANE, TERRIBLE.");
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            }
        }
        return difficulty;
    }

    public String askpeopleName(String command){
        // Ввод имени автора
        String peoplename;
        while (true) {
            System.out.println("Введите имя автора:");
            peoplename = consoleManager.input(command).trim();
            if (peoplename.isEmpty()) {
                System.out.println("Ошибка: имя автора не может быть пустым!");
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            } else{
                break;
            }
        }
        return peoplename;
    }

    public float askHeight(String command){
        // Ввод height (> 0)
        float height;
        while (true) {
            System.out.println("Введите height (должен быть больше 0):");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d*\\.?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите число (float)!");
                }
                height = Float.parseFloat(input);
                if (height <= 0) {
                    throw new MyInvalidRangeException("Ошибка: height должен быть больше 0!");
                }
                break;
            } catch (MyInputMismatchException | MyInvalidRangeException e) {
                System.out.println(e.getMessage());
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            }
        }
        return height;
    }

    public Color askEyeColor(String command){
        // Ввод цвета глаз
        Color eyeColor;
        while (true) {
            System.out.println("Введите eyeColor: (RED, BLACK, BLUE, ORANGE, WHITE, BROWN)");
            try {
                eyeColor = Color.valueOf(consoleManager.input(command).trim().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: Введите корректное значение цвета!");
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            }
        }
        return eyeColor;
    }

    public Color askHairColor(String command){
        // Ввод цвета волос
        Color hairColor;
        while (true) {
            System.out.println("Введите hairColor: (RED, BLACK, BLUE, ORANGE, WHITE, BROWN)");
            try {
                hairColor = Color.valueOf(consoleManager.input(command).trim().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: Введите корректное значение цвета!");
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            }
        }
        return hairColor;
    }

    public Country askCountry(String command){
        // Ввод страны
        Country country;
        while (true) {
            System.out.println("Введите country: (CHINA, VATICAN, THAILAND)");
            try {
                country = Country.valueOf(consoleManager.input(command).trim().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: Введите корректное название страны!");
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            }
        }
        return country;
    }

    public double askX1(String command){
        double x1;
        while (true) {
            System.out.println("Введите location.x (double):");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d*\\.?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите число (double)!");
                }
                x1 = Double.parseDouble(input);
                break;
            } catch (MyInputMismatchException e) {
                System.out.println(e.getMessage());
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            }
        }
        return x1;
    }

    public double askY1(String command){
        double y1;
        while (true) {
            System.out.println("Введите location.y (double):");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d*\\.?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите число (double)!");
                }
                y1 = Double.parseDouble(input);
                break;
            } catch (MyInputMismatchException e) {
                System.out.println(e.getMessage());
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            }
        }
        return y1;
    }

    public float askZ1(String command){
        float z1;
        while (true) {
            System.out.println("Введите location.z (float):");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d*\\.?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите число (float)!");
                }
                z1 = Float.parseFloat(input);
                break;
            } catch (MyInputMismatchException e) {
                System.out.println(e.getMessage());
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            }
        }
        return z1;
    }

    public String askFileName(String command){
        String name;
        // Ввод имени
        while (true) {
            System.out.println("Введите name (не null и не пустая строка):");
            name = consoleManager.input(command).trim();
            if (name.isEmpty()) {
                System.out.println("Ошибка: name не может быть пустым!");
                if (ConsoleManager.mode == 1){
                    System.exit(1);
                }
            } else {
                break;
            }
        }
        return name;
    }
}
