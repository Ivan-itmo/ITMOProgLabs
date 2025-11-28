package models;

import java.io.Serializable;

/**
 * Перечисление, представляющее цвета.
 * Используется для выбора или указания цвета в контексте приложения.
 * <p>
 * Доступные цвета:
 * <ul>
 *     <li>{@link #RED} - Красный цвет.</li>
 *     <li>{@link #BLACK} - Черный цвет.</li>
 *     <li>{@link #BLUE} - Синий цвет.</li>
 *     <li>{@link #ORANGE} - Оранжевый цвет.</li>
 *     <li>{@link #WHITE} - Белый цвет.</li>
 *     <li>{@link #BROWN} - Коричневый цвет.</li>
 * </ul>
 * </p>
 */
public enum Color implements Serializable {
    /**
     * Красный цвет.
     */
    RED,

    /**
     * Черный цвет.
     */
    BLACK,

    /**
     * Синий цвет.
     */
    BLUE,

    /**
     * Оранжевый цвет.
     */
    ORANGE,

    /**
     * Белый цвет.
     */
    WHITE,

    /**
     * Коричневый цвет.
     */
    BROWN,
    STOP;
}