package filesutility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.LabWork;
import utility.LabWorkValidator;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс для загрузки данных из файла в формате JSON.
 * <p>
 * Класс предоставляет метод для чтения JSON-файла и преобразования его содержимого
 * в список объектов {@link LabWork}. В случае ошибок программа завершается.
 * </p>
 */
public class FileLoader {

    /**
     * Загружает данные из JSON-файла и преобразует их в список объектов {@link LabWork}.
     * <p>
     * Метод выполняет следующие шаги:
     * <ol>
     *     <li>Проверяет существование файла.</li>
     *     <li>Читает содержимое файла и десериализует его в список объектов {@link LabWork}.</li>
     *     <li>Проверяет корректность каждого объекта {@link LabWork} с помощью {@link LabWorkValidator}.</li>
     * </ol>
     * Если данные некорректны, программа завершается.
     * </p>
     *
     * @param filePath путь к JSON-файлу.
     * @return список объектов {@link LabWork}, загруженных из файла.
     * @throws FileNotFoundException если файл не найден.
     * @throws RuntimeException если файл пустой или некорректный формат JSON.
     */
    public List<LabWork> loadFromFile(String filePath) throws FileNotFoundException {
        List<LabWork> labWorks = Collections.synchronizedList(new ArrayList<>());
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (!Files.exists(Paths.get(filePath))) {
                System.out.println("Ошибка: Файл не найден - " + filePath);
                System.exit(1);
            }

            String content = Files.readString(Paths.get(filePath));

            if (content.isEmpty()) {
                System.out.println("Ошибка: Файл пустой - " + filePath);
                System.exit(1);
            }

            labWorks = objectMapper.readValue(content, new TypeReference<>() {});

            for (LabWork labWork : labWorks) {
                if (!LabWorkValidator.validate(labWork)) {
                    System.out.println("Некорректные данные в объекте LabWork: " + labWork);
                    System.exit(1);
                }
            }

            return labWorks;

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: Файл не найден - " + filePath);
            System.exit(1);

        } catch (Exception e) {
            System.out.println("Ошибка при обработке данных: " + e.getMessage());
            System.exit(1);
        }

        return Collections.emptyList();
    }
}