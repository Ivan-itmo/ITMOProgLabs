package models;

import java.io.Serializable;

/**
 * Перечисление, представляющее уровни сложности.
 * Уровни сложности могут использоваться для настройки сложности игры,
 * задач или других элементов, где требуется градация сложности.
 * <p>
 * Доступные уровни сложности:
 * <ul>
 *     <li>{@link #EASY} - Легкий уровень сложности.</li>
 *     <li>{@link #NORMAL} - Нормальный уровень сложности.</li>
 *     <li>{@link #HARD} - Высокий уровень сложности.</li>
 *     <li>{@link #INSANE} - Очень высокий уровень сложности.</li>
 *     <li>{@link #TERRIBLE} - Экстремальный уровень сложности.</li>
 * </ul>
 * </p>
 */
public enum Difficulty implements Serializable {
    EASY,
    NORMAL,
    HARD,
    INSANE,
    TERRIBLE,
    STOP;
}