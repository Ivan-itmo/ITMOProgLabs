package filesutility;

import models.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Класс для записи в файл коллекции в формате json
 */
public class JsonFileWriter {

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
            jsonContent.append("    \"difficulty\": \"").append(labWork.getDifficulty()).append("\",\n");
            jsonContent.append("    \"author\": {\n");
            jsonContent.append("      \"name\": \"").append(labWork.getAuthor().getName()).append("\",\n");
            jsonContent.append("      \"height\": ").append(labWork.getAuthor().getHeight()).append(",\n");
            jsonContent.append("      \"eyeColor\": \"").append(labWork.getAuthor().getEyeColor()).append("\",\n");
            jsonContent.append("      \"hairColor\": \"").append(labWork.getAuthor().getHairColor()).append("\",\n");
            jsonContent.append("      \"nationality\": \"").append(labWork.getAuthor().getNationality()).append("\",\n");
            jsonContent.append("      \"location\": {\n");
            jsonContent.append("        \"x\": ").append(labWork.getAuthor().getLocation().getX()).append(",\n");
            jsonContent.append("        \"y\": ").append(labWork.getAuthor().getLocation().getY()).append(",\n");
            jsonContent.append("        \"z\": ").append(labWork.getAuthor().getLocation().getZ()).append("\n");
            jsonContent.append("      }\n");
            jsonContent.append("    }\n");

            jsonContent.append("  }");

            if (i < labWorks.size() - 1) {
                jsonContent.append(",");  // Запятые между объектами
            }
            jsonContent.append("\n");
        }

        jsonContent.append("]");  // Конец массива JSON

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonContent.toString());
            System.out.println("Данные успешно записаны в файл: " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}