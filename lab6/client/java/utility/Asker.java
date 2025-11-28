package utility;

import exceptions.MyInputMismatchException;
import exceptions.MyInvalidRangeException;
import exceptions.WrongIdException;
import models.Color;
import models.Country;
import models.Difficulty;

/**
 * Класс для запроса данных у пользователя.
 * Обеспечивает ввод и валидацию данных для создания или обновления объектов.
 */
public class Asker {
    ConsoleManager consoleManager;

    /**
     * Конструктор для создания объекта {@link Asker}.
     *
     * @param consoleManager Менеджер консоли, используемый для ввода данных.
     */
    public Asker(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    /**
     * Метод для запроса данных. Должен быть переопределен в подклассах.
     */
    public void ask() {}

    /**
     * Запрашивает ID для обновления элемента.
     *
     * @param command Команда, для которой запрашивается ID.
     * @return Введенный ID.
     */
    public int askUpdateId(String command) {
        int id;
        while (true) {
            System.out.println("Введите UpdateId (целое значение, начиная с 1):");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите целое число!");
                }
                try {
                    id = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    throw new MyInvalidRangeException("Ошибка: Число слишком большое!");
                }
                if (id <= 0) {
                    throw new MyInvalidRangeException("Ошибка: id <= 0");
                }
                break;
            } catch (MyInputMismatchException | WrongIdException | MyInvalidRangeException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        return id;
    }

    /**
     * Запрашивает ID элемента.
     *
     * @param command Команда, для которой запрашивается ID.
     * @return Введенный ID.
     */
    public int askId(String command) {
        int id;
        while (true) {
            System.out.println("Введите id (целое значение, начиная с 1):");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите целое число!");
                }
                try {
                    id = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    throw new MyInvalidRangeException("Ошибка: Число слишком большое!");
                }
                if (id <= 0) {
                    throw new MyInvalidRangeException("Ошибка: id <= 0");
                }
                break;
            } catch (MyInputMismatchException | WrongIdException | MyInvalidRangeException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        return id;
    }

    /**
     * Запрашивает имя элемента.
     *
     * @param command Команда, для которой запрашивается имя.
     * @return Введенное имя.
     */
    public String askName(String command) {
        String name;
        while (true) {
            System.out.println("Введите name (не null и не пустая строка):");
            name = consoleManager.input(command).trim();
            if (name.isEmpty()) {
                System.out.println("Ошибка: name не может быть пустым!");
                System.exit(1);
            } else {
                break;
            }
        }
        return name;
    }

    /**
     * Запрашивает координату X.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата X.
     */
    public long askX(String command) {
        long x;
        while (true) {
            System.out.println("Введите координату X (не больше 249):");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите целое число!");
                }
                try {
                    x = Long.parseLong(input);
                } catch (NumberFormatException e) {
                    throw new MyInvalidRangeException("Ошибка: Число слишком большое!");
                }
                if (x > 249) {
                    throw new MyInvalidRangeException("Ошибка: X не может быть больше 249!");
                }
                break;
            } catch (MyInputMismatchException | MyInvalidRangeException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        return x;
    }

    /**
     * Запрашивает координату Y.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата Y.
     */
    public Long askY(String command) {
        Long y;
        while (true) {
            System.out.println("Введите координату Y:");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите целое число!");
                }
                try {
                    y = Long.parseLong(input);
                } catch (NumberFormatException e) {
                    throw new MyInvalidRangeException("Ошибка: Число слишком большое!");
                }
                break;
            } catch (MyInputMismatchException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        return y;
    }

    /**
     * Запрашивает минимальное количество баллов.
     *
     * @param command Команда, для которой запрашивается значение.
     * @return Введенное минимальное количество баллов.
     */
    public long askminimalPoint(String command) {
        long minimalPoint;
        while (true) {
            System.out.println("Введите minimalPoint (должно быть больше 0):");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите целое число!");
                }
                try {
                    minimalPoint = Long.parseLong(input);
                } catch (NumberFormatException e) {
                    throw new MyInvalidRangeException("Ошибка: Число слишком большое!");
                }
                if (minimalPoint <= 0) {
                    throw new MyInvalidRangeException("Ошибка: minimalPoint должен быть больше 0!");
                }
                break;
            } catch (MyInputMismatchException | MyInvalidRangeException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        return minimalPoint;
    }

    /**
     * Запрашивает уровень сложности.
     *
     * @param command Команда, для которой запрашивается уровень сложности.
     * @return Введенный уровень сложности или {@code null}, если ввод пустой.
     */
    public Difficulty askDifficulty(String command) {
        Difficulty difficulty = null;
        while (true) {
            System.out.println("Введите сложность (EASY, MEDIUM, HARD, INSANE, TERRIBLE) или оставьте пустым для null:");

            String input = consoleManager.input(command).trim();

            if (input.isEmpty()) {
                difficulty = null;
                break;
            }

            try {
                difficulty = Difficulty.valueOf(input.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: Неверный уровень сложности! Введите одно из значений: EASY, MEDIUM, HARD, " +
                        "INSANE, TERRIBLE или оставьте пустым для null.");
                System.exit(1);
            }
        }
        return difficulty;
    }

    /**
     * Запрашивает имя автора.
     *
     * @param command Команда, для которой запрашивается имя.
     * @return Введенное имя автора.
     */
    public String askpeopleName(String command) {
        String peoplename;
        while (true) {
            System.out.println("Введите имя автора:");
            peoplename = consoleManager.input(command).trim();
            if (peoplename.isEmpty()) {
                System.out.println("Ошибка: имя автора не может быть пустым!");
                System.exit(1);
            } else {
                break;
            }
        }
        return peoplename;
    }

    /**
     * Запрашивает рост автора.
     *
     * @param command Команда, для которой запрашивается рост.
     * @return Введенный рост.
     */
    public float askHeight(String command) {
        float height;
        while (true) {
            System.out.println("Введите height (должен быть больше 0):");
            try {
                String input = consoleManager.input(command).trim();
                if (!input.matches("^-?\\d*\\.?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите число (float)!");
                }
                try {
                    height = Float.parseFloat(input);
                    if (Float.isInfinite(height)) {
                        throw new MyInvalidRangeException("Ошибка: Число слишком большое или маленькое (бесконечность)!");
                    }
                    if (Float.isNaN(height)) {
                        throw new MyInvalidRangeException("Ошибка: Некорректное значение (NaN)!");
                    }
                } catch (MyInvalidRangeException e) {
                    throw new MyInvalidRangeException("Ошибка: Неверный формат числа!");
                }
                if (height <= 0) {
                    throw new MyInvalidRangeException("Ошибка: height должен быть больше 0!");
                }
                break;
            } catch (MyInputMismatchException | MyInvalidRangeException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        return height;
    }

    /**
     * Запрашивает цвет глаз автора.
     *
     * @param command Команда, для которой запрашивается цвет глаз.
     * @return Введенный цвет глаз.
     */
    public Color askEyeColor(String command) {
        Color eyeColor;
        while (true) {
            System.out.println("Введите eyeColor: (RED, BLACK, BLUE, ORANGE, WHITE, BROWN)");
            try {
                eyeColor = Color.valueOf(consoleManager.input(command).trim().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: Введите корректное значение цвета!");
                System.exit(1);
            }
        }
        return eyeColor;
    }

    /**
     * Запрашивает цвет волос автора.
     *
     * @param command Команда, для которой запрашивается цвет волос.
     * @return Введенный цвет волос или {@code null}, если ввод пустой.
     */
    public Color askHairColor(String command) {
        Color hairColor = null;
        while (true) {
            System.out.println("Введите цвет волос (RED, BLACK, BLUE, ORANGE, WHITE, BROWN) или оставьте пустым для null:");

            String input = consoleManager.input(command).trim();

            if (input.isEmpty()) {
                hairColor = null;
                break;
            }

            try {
                hairColor = Color.valueOf(input.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: Введите корректное значение цвета или оставьте пустым для null!");
                System.exit(1);
            }
        }
        return hairColor;
    }

    /**
     * Запрашивает страну автора.
     *
     * @param command Команда, для которой запрашивается страна.
     * @return Введенная страна или {@code null}, если ввод пустой.
     */
    public Country askCountry(String command) {
        Country country = null;
        while (true) {
            System.out.println("Введите страну (CHINA, VATICAN, THAILAND) или оставьте пустым для null:");

            String input = consoleManager.input(command).trim();

            if (input.isEmpty()) {
                country = null;
                break;
            }

            try {
                country = Country.valueOf(input.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: Введите корректное название страны или оставьте пустым для null!");
                System.exit(1);
            }
        }
        return country;
    }

    /**
     * Запрашивает координату X для локации.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата X.
     */
    public double askX1(String command) {
        double x1;
        while (true) {
            System.out.println("Введите location.x (double):");
            try {
                String input = consoleManager.input(command).trim();

                if (!input.matches("^-?\\d*\\.?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите число (double)!");
                }
                try {
                    x1 = Double.parseDouble(input);
                    if (Double.isInfinite(x1)) {
                        throw new MyInvalidRangeException("Ошибка: Число слишком большое или маленькое (бесконечность)!");
                    }
                    if (Double.isNaN(x1)) {
                        throw new MyInvalidRangeException("Ошибка: Некорректное значение (NaN)!");
                    }
                } catch (MyInvalidRangeException e) {
                    throw new MyInvalidRangeException("Ошибка: Неверный формат числа!");
                }
                break;
            } catch (MyInputMismatchException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        return x1;
    }

    /**
     * Запрашивает координату Y для локации.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата Y.
     */
    public double askY1(String command) {
        double y1;
        while (true) {
            System.out.println("Введите location.y (double):");
            try {
                String input = consoleManager.input(command).trim();

                if (!input.matches("^-?\\d*\\.?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите число (double)!");
                }
                try {
                    y1 = Double.parseDouble(input);
                    if (Double.isInfinite(y1)) {
                        throw new MyInvalidRangeException("Ошибка: Число слишком большое или маленькое (бесконечность)!");
                    }
                    if (Double.isNaN(y1)) {
                        throw new MyInvalidRangeException("Ошибка: Некорректное значение (NaN)!");
                    }
                } catch (MyInvalidRangeException e) {
                    throw new MyInvalidRangeException("Ошибка: Неверный формат числа!");
                }
                break;
            } catch (MyInputMismatchException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        return y1;
    }

    /**
     * Запрашивает координату Z для локации.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата Z.
     */
    public float askZ1(String command) {
        float z1;
        while (true) {
            System.out.println("Введите location.z (float):");
            try {
                String input = consoleManager.input(command).trim();

                if (!input.matches("^-?\\d*\\.?\\d+$")) {
                    throw new MyInputMismatchException("Ошибка: Введите число (float)!");
                }
                try {
                    z1 = Float.parseFloat(input);
                    if (Float.isInfinite(z1)) {
                        throw new MyInvalidRangeException("Ошибка: Число слишком большое или маленькое (бесконечность)!");
                    }
                    if (Float.isNaN(z1)) {
                        throw new MyInvalidRangeException("Ошибка: Некорректное значение (NaN)!");
                    }
                } catch (MyInvalidRangeException e) {
                    throw new MyInvalidRangeException("Ошибка: Неверный формат числа!");
                }
                break;
            } catch (MyInputMismatchException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        return z1;
    }

    /**
     * Запрашивает имя файла.
     *
     * @param command Команда, для которой запрашивается имя файла.
     * @return Введенное имя файла.
     */
    public String askFileName(String command) {
        String name;
        while (true) {
            System.out.println("Введите name (не null и не пустая строка):");
            name = consoleManager.input(command).trim();
            if (name.isEmpty()) {
                System.out.println("Ошибка: name не может быть пустым!");
                System.exit(1);
            } else {
                break;
            }
        }
        return name;
    }
}