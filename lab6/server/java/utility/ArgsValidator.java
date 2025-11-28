package utility;

import exceptions.MyInputMismatchException;
import exceptions.MyInvalidRangeException;
import exceptions.WrongIdException;
import models.Color;
import models.Country;
import models.Difficulty;
import serverutility.ServerCommand;
import serverutility.ServerAnswers;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для запроса данных у пользователя.
 * Обеспечивает ввод и валидацию данных для создания или обновления объектов.
 */
public class ArgsValidator {
    ServerCommand serverCommand;
    ServerAnswers serverAnswers;
    /**
     * Конструктор для создания объекта {@link ArgsValidator}.
     *
     * @param serverCommand Менеджер консоли, используемый для ввода данных.
     */
    public ArgsValidator(ServerCommand serverCommand, ServerAnswers serverAnswers) {
        this.serverCommand = serverCommand;
        this.serverAnswers = serverAnswers;
    }

    /**
     * Метод для запроса данных. Должен быть переопределен в подклассах.
     */
    public void ask() {}

    /**
     * Запрашивает ID для обновления элемента.
     *
     * @return Введенный ID.
     */
    public int validateUpdateId(Object arg) {
        int id;
        while (true) {
            //System.out.println("Введите UpdateId (целое значение, начиная с 1):");
            try {
                String input = (String) arg;
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
                List<Object> list = new ArrayList<>();
                list.add(e.getMessage());
                serverAnswers.setAnswers(list);
                return -1;
            }
        }
        return id;
    }

    /**
     * Запрашивает ID элемента.
     *
     * @return Введенный ID.
     */
    public int validateId(Object arg) {
        int id;
        while (true) {
            //System.out.println("Введите id (целое значение, начиная с 1):");
            try {
                String input = (String) arg;
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
                List<Object> list = new ArrayList<>();
                list.add(e.getMessage());
                serverAnswers.setAnswers(list);
                return -1;
            }
        }
        return id;
    }

    /**
     * Запрашивает имя элемента.
     *
     * @return Введенное имя.
     */
    public String validateName(Object arg) {
        String name;
        while (true) {
            //System.out.println("Введите name (не null и не пустая строка):");
            name = (String) arg;
            if (name.isEmpty()) {
                List<Object> list = new ArrayList<>();
                list.add("Ошибка: name не может быть пустым!");
                serverAnswers.setAnswers(list);
                return "stop";
            } else {
                break;
            }
        }
        return name;
    }

    /**
     * Запрашивает координату X.
     *
     * @return Введенная координата X.
     */
    public long validateX(Object arg) {
        long x;
        while (true) {
            //System.out.println("Введите координату X (не больше 249):");
            try {
                String input = (String) arg;
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
                List<Object> list = new ArrayList<>();
                list.add(e.getMessage());
                serverAnswers.setAnswers(list);
                return -1;
            }
        }
        return x;
    }

    /**
     * Запрашивает координату Y.
     *
     * @return Введенная координата Y.
     */
    public Long validateY(Object arg) {
        Long y;
        while (true) {
            //System.out.println("Введите координату Y:");
            try {
                String input = (String) arg;
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
                List<Object> list = new ArrayList<>();
                list.add(e.getMessage());
                serverAnswers.setAnswers(list);
                Long i = Long.parseLong("-1");
                return i;
            }
        }
        return y;
    }

    /**
     * Запрашивает минимальное количество баллов.
     *
     * @return Введенное минимальное количество баллов.
     */
    public long validateminimalPoint(Object arg) {
        long minimalPoint;
        while (true) {
            //System.out.println("Введите minimalPoint (должно быть больше 0):");
            try {
                String input = (String) arg;
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
                List<Object> list = new ArrayList<>();
                list.add(e.getMessage());
                serverAnswers.setAnswers(list);
                return -1;
            }
        }
        return minimalPoint;
    }

    /**
     * Запрашивает уровень сложности.
     *
     * @return Введенный уровень сложности или {@code null}, если ввод пустой.
     */
    public Difficulty validateDifficulty(Object arg) {
        Difficulty difficulty;
        while (true) {
            //System.out.println("Введите сложность (EASY, MEDIUM, HARD, INSANE, TERRIBLE) или оставьте пустым для null:");

            String input = (String) arg;

            if (input.isEmpty()) {
                difficulty = null;
                break;
            }

            try {
                difficulty = Difficulty.valueOf(input.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                List<Object> list = new ArrayList<>();
                list.add("Ошибка: Неверный уровень сложности! Введите одно из значений: EASY, MEDIUM, HARD, " +
                        "INSANE, TERRIBLE или оставьте пустым для null.");
                serverAnswers.setAnswers(list);
                return Difficulty.STOP;
            }
        }
        return difficulty;
    }

    /**
     * Запрашивает имя автора.
     *
     * @return Введенное имя автора.
     */
    public String validatepeopleName(Object arg) {
        String peoplename;
        while (true) {
            //System.out.println("Введите имя автора:");
            peoplename = (String) arg;
            if (peoplename.isEmpty()) {
                List<Object> list = new ArrayList<>();
                list.add("Ошибка: имя автора не может быть пустым!");
                serverAnswers.setAnswers(list);
                return "stop";
            } else {
                break;
            }
        }
        return peoplename;
    }

    /**
     * Запрашивает рост автора.
     *
     * @return Введенный рост.
     */
    public float validateHeight(String arg) {
        float height;
        while (true) {
            //System.out.println("Введите height (должен быть больше 0):");
            try {
                String input = (String) arg;
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
                List<Object> list = new ArrayList<>();
                list.add(e.getMessage());
                serverAnswers.setAnswers(list);
                return -1;
            }
        }
        return height;
    }

    /**
     * Запрашивает цвет глаз автора.
     *
     * @return Введенный цвет глаз.
     */
    public Color validateEyeColor(Object arg) {
        Color eyeColor;
        while (true) {
            //System.out.println("Введите eyeColor: (RED, BLACK, BLUE, ORANGE, WHITE, BROWN)");
            try {
                eyeColor = (Color) arg;
                break;
            } catch (IllegalArgumentException e) {
                List<Object> list = new ArrayList<>();
                list.add("Ошибка: Введите корректное значение цвета!");
                serverAnswers.setAnswers(list);
                return Color.STOP;

            }
        }
        return eyeColor;
    }

    /**
     * Запрашивает цвет волос автора.
     *
     * @return Введенный цвет волос или {@code null}, если ввод пустой.
     */
    public Color validateHairColor(Object arg) {
        Color hairColor = null;
        while (true) {
            System.out.println("Введите цвет волос (RED, BLACK, BLUE, ORANGE, WHITE, BROWN) или оставьте пустым для null:");

            String input = (String) arg;

            if (input.isEmpty()) {
                hairColor = null;
                break;
            }

            try {
                hairColor = Color.valueOf(input.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                List<Object> list = new ArrayList<>();
                list.add("Ошибка: Введите корректное значение цвета!");
                serverAnswers.setAnswers(list);
                return Color.STOP;
            }
        }
        return hairColor;
    }

    /**
     * Запрашивает страну автора.
     *
     * @return Введенная страна или {@code null}, если ввод пустой.
     */
    public Country validateCountry(Object arg) {
        Country country = null;
        while (true) {
            System.out.println("Введите страну (CHINA, VATICAN, THAILAND) или оставьте пустым для null:");

            String input = (String) arg;

            if (input.isEmpty()) {
                country = null;
                break;
            }

            try {
                country = Country.valueOf(input.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                List<Object> list = new ArrayList<>();
                list.add("Ошибка: Введите корректное название страны или оставьте пустым для null!");
                serverAnswers.setAnswers(list);
                return Country.STOP;
            }
        }
        return country;
    }

    /**
     * Запрашивает координату X для локации.
     *
     * @return Введенная координата X.
     */
    public double validateX1(Object arg) {
        double x1;
        while (true) {
            //System.out.println("Введите location.x (double):");
            try {
                String input = (String) arg;

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
                List<Object> list = new ArrayList<>();
                list.add(e.getMessage());
                serverAnswers.setAnswers(list);
                return -1;
            }
        }
        return x1;
    }

    /**
     * Запрашивает координату Y для локации.
     *
     * @return Введенная координата Y.
     */
    public double validateY1(Object arg) {
        double y1;
        while (true) {
            //System.out.println("Введите location.y (double):");
            try {
                String input = (String) arg;

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
                List<Object> list = new ArrayList<>();
                list.add(e.getMessage());
                serverAnswers.setAnswers(list);
                return -1;
            }
        }
        return y1;
    }

    /**
     * Запрашивает координату Z для локации.
     *
     * @return Введенная координата Z.
     */
    public float validateZ1(Object arg) {
        float z1;
        while (true) {
            //System.out.println("Введите location.z (float):");
            try {
                String input = (String) arg;

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
                List<Object> list = new ArrayList<>();
                list.add(e.getMessage());
                serverAnswers.setAnswers(list);
                return -1;
            }
        }
        return z1;
    }

    /**
     * Запрашивает имя файла.
     * @return Введенное имя файла.
     */
    public String validateFileName(Object arg) {
        String name;
        while (true) {
            System.out.println("Введите name (не null и не пустая строка):");
            name = (String) arg;
            if (name.isEmpty()) {
                List<Object> list = new ArrayList<>();
                list.add("Ошибка: name не может быть пустым!");
                serverAnswers.setAnswers(list);
                return "stop";
            } else {
                break;
            }
        }
        return name;
    }
}