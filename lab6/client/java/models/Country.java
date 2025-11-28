package models;
import java.io.Serializable;
/**
 * Перечисление, представляющее страны.
 * Используется для указания страны в контексте приложения, например,
 * для выбора локации, настройки региональных параметров или других задач,
 * связанных с географией.
 * <p>
 * Доступные страны:
 * <ul>
 *     <li>{@link #CHINA} - Китай.</li>
 *     <li>{@link #VATICAN} - Ватикан.</li>
 *     <li>{@link #THAILAND} - Таиланд.</li>
 * </ul>
 * </p>
 */
public enum Country implements Serializable{
    CHINA,
    VATICAN,
    THAILAND,
    STOP;
}