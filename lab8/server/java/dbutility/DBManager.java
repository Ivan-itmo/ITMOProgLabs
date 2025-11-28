package dbutility;

import models.LabWork;
import models.Location;
import models.Person;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * Класс для управления операциями с базой данных лабораторных работ.
 * Реализует интерфейс DBManagerInterface и предоставляет CRUD-функционал
 * для работы с таблицей labworks и связанными данными.
 *
 * <p>Основные функции:
 * <ul>
 *   <li>Добавление новых лабораторных работ</li>
 *   <li>Обновление существующих записей</li>
 *   <li>Удаление работ по идентификатору</li>
 *   <li>Очистка коллекции работ</li>
 * </ul>
 */
public class DBManager implements DBManagerInterface {
    /** Соединение с базой данных */
    private Connection dbConnection;

    /**
     * Создает новый экземпляр DBManager с указанным соединением.
     *
     * @param dbConnection активное соединение с базой данных
     */
    public DBManager(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Добавляет новую лабораторную работу в базу данных.
     *
     * <p>Сохраняет все поля работы, включая:
     * <ul>
     *   <li>Основные данные работы (имя, координаты, дата создания)</li>
     *   <li>Информацию о сложности (может быть null)</li>
     *   <li>Данные автора (имя, внешние характеристики)</li>
     *   <li>Локацию автора (координаты x, y, z)</li>
     * </ul>
     *
     * @param labWork добавляемая лабораторная работа
     * @return 1 в случае успеха, 0 при ошибке
     */
    public int addLabWork(LabWork labWork) {
        System.out.println("Перешли");
        try (PreparedStatement locationStatement = dbConnection.prepareStatement(
                "INSERT INTO locations (x, y, z) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            Location location = labWork.getAuthor().getLocation();
            locationStatement.setDouble(1, location.getX());
            locationStatement.setDouble(2, location.getY());
            locationStatement.setDouble(3, location.getZ());
            locationStatement.executeUpdate();

            System.out.println("Вставка location");
            int locationId;
            try (ResultSet locationGeneratedKeys = locationStatement.getGeneratedKeys()) {
                if (locationGeneratedKeys.next()) {
                    locationId = locationGeneratedKeys.getInt(1);
                } else {
                    throw new SQLException("Ошибка при получении ID для locations");
                }
            }
            System.out.println("Вставка author");

            try (PreparedStatement authorStatement = dbConnection.prepareStatement(
                    "INSERT INTO authors (name, height, eye_color, hair_color, nationality, location_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                Person author = labWork.getAuthor();
                authorStatement.setString(1, author.getName());
                authorStatement.setDouble(2, author.getHeight());
                authorStatement.setString(3, author.getEyeColor().name());

                if (author.getHairColor() != null) {
                    authorStatement.setString(4, author.getHairColor().name());
                } else {
                    authorStatement.setNull(4, Types.VARCHAR);
                }

                if (author.getNationality() != null) {
                    authorStatement.setString(5, author.getNationality().name());
                } else {
                    authorStatement.setNull(5, Types.VARCHAR);
                }

                authorStatement.setInt(6, locationId);
                authorStatement.executeUpdate();

                int authorId;
                try (ResultSet authorGeneratedKeys = authorStatement.getGeneratedKeys()) {
                    if (authorGeneratedKeys.next()) {
                        authorId = authorGeneratedKeys.getInt(1);
                    } else {
                        throw new SQLException("Ошибка при получении ID для authors");
                    }
                }

                System.out.println("Вставка labwork");
                try (PreparedStatement labworkStatement = dbConnection.prepareStatement(
                        "INSERT INTO labworks (name, coordinate_x, coordinate_y, creation_date, minimal_point, difficulty, author_id) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {

                    labworkStatement.setString(1, labWork.getName());
                    labworkStatement.setLong(2, labWork.getCoordinates().getX());
                    labworkStatement.setLong(3, labWork.getCoordinates().getY());
                    labworkStatement.setTimestamp(4, Timestamp.valueOf(labWork.getCreationDate()));
                    labworkStatement.setLong(5, labWork.getMinimalPoint());

                    if (labWork.getDifficulty() != null) {
                        labworkStatement.setString(6, labWork.getDifficulty().name());
                    } else {
                        labworkStatement.setNull(6, Types.VARCHAR);
                    }

                    labworkStatement.setInt(7, authorId);
                    labworkStatement.executeUpdate();

                    try (ResultSet labworkGeneratedKeys = labworkStatement.getGeneratedKeys()) {
                        if (labworkGeneratedKeys.next()) {
                            labWork.setId(labworkGeneratedKeys.getInt(1));
                        } else {
                            throw new SQLException("Ошибка при получении ID для labworks");
                        }
                    }

                    System.out.println("Добавлено");
                }
            }
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Обновляет существующую лабораторную работу в базе данных.
     *
     * @param id идентификатор обновляемой работы
     * @param updatedLabWork новые данные для обновления
     * @return 1 в случае успеха, 0 при ошибке
     */
    public int updateLabWork(int id, LabWork updatedLabWork) {
        try {
            int authorId = -1;
            int locationId = -1;

            try (PreparedStatement selectStatement = dbConnection.prepareStatement(
                    "SELECT a.id AS author_id, l.id AS location_id " +
                            "FROM labworks lw " +
                            "JOIN authors a ON lw.author_id = a.id " +
                            "JOIN locations l ON a.location_id = l.id " +
                            "WHERE lw.id = ?")) {

                selectStatement.setInt(1, id);
                ResultSet resultSet = selectStatement.executeQuery();

                if (!resultSet.next()) {
                    throw new SQLException("LabWork с указанным ID не найден");
                }

                authorId = resultSet.getInt("author_id");
                locationId = resultSet.getInt("location_id");
            }
            try (PreparedStatement locationStatement = dbConnection.prepareStatement(
                    "UPDATE locations SET x = ?, y = ?, z = ? WHERE id = ?")) {

                Location location = updatedLabWork.getAuthor().getLocation();
                locationStatement.setDouble(1, location.getX());
                locationStatement.setDouble(2, location.getY());
                locationStatement.setDouble(3, location.getZ());
                locationStatement.setInt(4, locationId);
                locationStatement.executeUpdate();
            }
            try (PreparedStatement authorStatement = dbConnection.prepareStatement(
                    "UPDATE authors SET name = ?, height = ?, eye_color = ?, hair_color = ?, nationality = ? WHERE id = ?")) {

                Person author = updatedLabWork.getAuthor();
                authorStatement.setString(1, author.getName());
                authorStatement.setDouble(2, author.getHeight());
                authorStatement.setString(3, author.getEyeColor().name());

                if (author.getHairColor() != null) {
                    authorStatement.setString(4, author.getHairColor().name());
                } else {
                    authorStatement.setNull(4, Types.VARCHAR);
                }

                if (author.getNationality() != null) {
                    authorStatement.setString(5, author.getNationality().name());
                } else {
                    authorStatement.setNull(5, Types.VARCHAR);
                }

                authorStatement.setInt(6, authorId);
                authorStatement.executeUpdate();
            }
            try (PreparedStatement labworkStatement = dbConnection.prepareStatement(
                    "UPDATE labworks SET name = ?, coordinate_x = ?, coordinate_y = ?, creation_date = ?, minimal_point = ?, difficulty = ? WHERE id = ?")) {

                labworkStatement.setString(1, updatedLabWork.getName());
                labworkStatement.setLong(2, updatedLabWork.getCoordinates().getX());
                labworkStatement.setLong(3, updatedLabWork.getCoordinates().getY());
                labworkStatement.setTimestamp(4, Timestamp.valueOf(updatedLabWork.getCreationDate()));
                labworkStatement.setLong(5, updatedLabWork.getMinimalPoint());

                if (updatedLabWork.getDifficulty() != null) {
                    labworkStatement.setString(6, updatedLabWork.getDifficulty().name());
                } else {
                    labworkStatement.setNull(6, Types.VARCHAR);
                }

                labworkStatement.setInt(7, id);
                int affectedRows = labworkStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Обновление LabWork не удалось");
                }
            }

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Удаляет лабораторную работу по указанному идентификатору.
     *
     * @param id идентификатор удаляемой работы
     * @return количество удаленных записей (1 или 0)
     */
    public int removeByIdLabWork(int id) {
        try {
            int authorId = -1;
            try (PreparedStatement selectAuthorStatement = dbConnection.prepareStatement(
                    "SELECT author_id FROM labworks WHERE id = ?")) {

                selectAuthorStatement.setInt(1, id);
                ResultSet resultSet = selectAuthorStatement.executeQuery();

                if (!resultSet.next()) {
                    throw new SQLException("LabWork с указанным ID не найден");
                }

                authorId = resultSet.getInt("author_id");
            }
            int locationId = -1;
            try (PreparedStatement selectLocationStatement = dbConnection.prepareStatement(
                    "SELECT location_id FROM authors WHERE id = ?")) {

                selectLocationStatement.setInt(1, authorId);
                ResultSet resultSet = selectLocationStatement.executeQuery();

                if (!resultSet.next()) {
                    throw new SQLException("Author с указанным ID не найден");
                }

                locationId = resultSet.getInt("location_id");
            }
            try (PreparedStatement labworkStatement = dbConnection.prepareStatement(
                    "DELETE FROM labworks WHERE id = ?")) {

                labworkStatement.setInt(1, id);
                labworkStatement.executeUpdate();
            }
            try (PreparedStatement authorStatement = dbConnection.prepareStatement(
                    "DELETE FROM authors WHERE id = ?")) {

                authorStatement.setInt(1, authorId);
                authorStatement.executeUpdate();
            }
            try (PreparedStatement locationStatement = dbConnection.prepareStatement(
                    "DELETE FROM locations WHERE id = ?")) {

                locationStatement.setInt(1, locationId);
                locationStatement.executeUpdate();
            }

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Удаляет несколько лабораторных работ по списку идентификаторов.
     * Использует batch-обработку для эффективного массового удаления.
     *
     * @param idsToRemove список идентификаторов для удаления
     * @return общее количество удаленных записей
     */
    public int clearLabWork(List<Integer> idsToRemove) {
        if (idsToRemove == null || idsToRemove.isEmpty()) {
            return 0;
        }

        int totalDeleted = 0;

        for (Integer id : idsToRemove) {
            try {
                int authorId = -1;
                try (PreparedStatement selectAuthorStatement = dbConnection.prepareStatement(
                        "SELECT author_id FROM labworks WHERE id = ?")) {

                    selectAuthorStatement.setInt(1, id);
                    ResultSet resultSet = selectAuthorStatement.executeQuery();

                    if (!resultSet.next()) {
                        System.out.println("LabWork с ID " + id + " не найден");
                        continue; // Пропускаем несуществующие записи
                    }

                    authorId = resultSet.getInt("author_id");
                }
                int locationId = -1;
                try (PreparedStatement selectLocationStatement = dbConnection.prepareStatement(
                        "SELECT location_id FROM authors WHERE id = ?")) {

                    selectLocationStatement.setInt(1, authorId);
                    ResultSet resultSet = selectLocationStatement.executeQuery();

                    if (!resultSet.next()) {
                        System.out.println("Author с ID " + authorId + " не найден");
                        continue;
                    }

                    locationId = resultSet.getInt("location_id");
                }
                try (PreparedStatement labworkStatement = dbConnection.prepareStatement(
                        "DELETE FROM labworks WHERE id = ?")) {

                    labworkStatement.setInt(1, id);
                    labworkStatement.executeUpdate();
                }
                try (PreparedStatement authorStatement = dbConnection.prepareStatement(
                        "DELETE FROM authors WHERE id = ?")) {

                    authorStatement.setInt(1, authorId);
                    authorStatement.executeUpdate();
                }
                try (PreparedStatement locationStatement = dbConnection.prepareStatement(
                        "DELETE FROM locations WHERE id = ?")) {

                    locationStatement.setInt(1, locationId);
                    locationStatement.executeUpdate();
                }

                totalDeleted++;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Ошибка при удалении LabWork с ID " + id);
            }
        }

        return totalDeleted;
    }
}