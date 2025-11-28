package filesutility;

import models.Coordinates;
import models.LabWork;
import models.Location;
import models.Person;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Класс для записи коллекции объектов {@link LabWork} в файл в формате JSON.
 * <p>
 * Класс предоставляет статический метод для преобразования списка лабораторных работ
 * в JSON-строку и записи её в указанный файл.
 * </p>
 */
public class JsonFileWriter {

    /**
     * Записывает коллекцию объектов {@link LabWork} в файл в формате JSON.
     * <p>
     * Метод формирует JSON-строку, представляющую массив объектов, где каждый объект
     * соответствует одной лабораторной работе. Включает все поля объекта {@link LabWork},
     * включая вложенные объекты, такие как {@link Coordinates}, {@link Person} и {@link Location}.
     * </p>
     *
     * @param filePath путь к файлу, в который будет записан JSON.
     * @param labWorks список объектов {@link LabWork}, которые нужно записать в файл.
     */
    public static void writeJsonToFile(String filePath, List<LabWork> labWorks) {
        StringBuilder jsonContent = new StringBuilder();
        jsonContent.append("[\n");

        for (int i = 0; i < labWorks.size(); i++) {
            LabWork labWork = labWorks.get(i);
            jsonContent.append("  {\n");

            jsonContent.append("    \"id\": ").append(labWork.getId()).append(",\n");
            jsonContent.append("    \"name\": \"").append(labWork.getName()).append("\",\n");
            jsonContent.append("    \"coordinates\": {\n");
            jsonContent.append("      \"x\": ").append(labWork.getCoordinates().getX()).append(",\n");
            jsonContent.append("      \"y\": ").append(labWork.getCoordinates().getY()).append("\n");
            jsonContent.append("    },\n");
            jsonContent.append("    \"creationDate\": \"").append(labWork.getCreationDate()).append("\",\n");
            jsonContent.append("    \"minimalPoint\": ").append(labWork.getMinimalPoint()).append(",\n");

            jsonContent.append("    \"difficulty\": ");
            if (labWork.getDifficulty() == null) {
                jsonContent.append("null");
            } else {
                jsonContent.append("\"").append(labWork.getDifficulty()).append("\"");
            }
            jsonContent.append(",\n");

            jsonContent.append("    \"author\": {\n");
            jsonContent.append("      \"name\": \"").append(labWork.getAuthor().getName()).append("\",\n");
            jsonContent.append("      \"height\": ").append(labWork.getAuthor().getHeight()).append(",\n");
            jsonContent.append("      \"eyeColor\": \"").append(labWork.getAuthor().getEyeColor()).append("\",\n");

            jsonContent.append("      \"hairColor\": ");
            if (labWork.getAuthor().getHairColor() == null) {
                jsonContent.append("null");
            } else {
                jsonContent.append("\"").append(labWork.getAuthor().getHairColor()).append("\"");
            }
            jsonContent.append(",\n");


            jsonContent.append("      \"nationality\": ");
            if (labWork.getAuthor().getNationality() == null) {
                jsonContent.append("null");
            } else {
                jsonContent.append("\"").append(labWork.getAuthor().getNationality()).append("\"");
            }
            jsonContent.append(",\n");

            jsonContent.append("      \"location\": {\n");
            jsonContent.append("        \"x\": ").append(labWork.getAuthor().getLocation().getX()).append(",\n");
            jsonContent.append("        \"y\": ").append(labWork.getAuthor().getLocation().getY()).append(",\n");
            jsonContent.append("        \"z\": ").append(labWork.getAuthor().getLocation().getZ()).append("\n");
            jsonContent.append("      }\n");
            jsonContent.append("    }\n");

            jsonContent.append("  }");

            if (i < labWorks.size() - 1) {
                jsonContent.append(",");
            }
            jsonContent.append("\n");
        }

        jsonContent.append("]");

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonContent.toString());
            System.out.println("Данные успешно записаны в файл: " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}