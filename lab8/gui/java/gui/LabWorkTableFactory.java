package gui;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import models.LabWork;
import models.Coordinates;
import models.Person;
import models.Location;

import java.util.List;

public class LabWorkTableFactory {

    public static List<TableColumn<LabWork, ?>> getColumns() {
        TableColumn<LabWork, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<LabWork, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<LabWork, Long> coordXCol = new TableColumn<>("X");
        coordXCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCoordinates().getX()));

        TableColumn<LabWork, Long> coordYCol = new TableColumn<>("Y");
        coordYCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCoordinates().getY()));

        TableColumn<LabWork, Integer> minimalPointCol = new TableColumn<>("Minimal Point");
        minimalPointCol.setCellValueFactory(new PropertyValueFactory<>("minimalPoint"));

        TableColumn<LabWork, String> difficultyCol = new TableColumn<>("Difficulty");
        difficultyCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getDifficulty() != null ? cellData.getValue().getDifficulty().name() : ""));

        TableColumn<LabWork, String> authorNameCol = new TableColumn<>("Author Name");
        authorNameCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getAuthor() != null ? cellData.getValue().getAuthor().getName() : ""));

        TableColumn<LabWork, Float> authorHeightCol = new TableColumn<>("Height");
        authorHeightCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        cellData.getValue().getAuthor() != null ? cellData.getValue().getAuthor().getHeight() : null));

        TableColumn<LabWork, String> authorEyeColorCol = new TableColumn<>("Eye Color");
        authorEyeColorCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getAuthor() != null && cellData.getValue().getAuthor().getEyeColor() != null
                                ? cellData.getValue().getAuthor().getEyeColor().name()
                                : ""));

        TableColumn<LabWork, String> authorHairColorCol = new TableColumn<>("Hair Color");
        authorHairColorCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getAuthor() != null && cellData.getValue().getAuthor().getHairColor() != null
                                ? cellData.getValue().getAuthor().getHairColor().name()
                                : ""));

        TableColumn<LabWork, String> nationalityCol = new TableColumn<>("Nationality");
        nationalityCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getAuthor() != null && cellData.getValue().getAuthor().getNationality() != null
                                ? cellData.getValue().getAuthor().getNationality().name()
                                : ""));

        TableColumn<LabWork, Float> locXCol = new TableColumn<>("Loc X");
        TableColumn<LabWork, Float> locYCol = new TableColumn<>("Loc Y");
        TableColumn<LabWork, Float> locZCol = new TableColumn<>("Loc Z");

        locXCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleFloatProperty(
                        (float) safeLocation(cellData).getX()
                ).asObject());

        locYCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleFloatProperty(
                        (float) safeLocation(cellData).getY()
                ).asObject());

        locZCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        safeLocation(cellData).getZ()));

        return List.of(idCol, nameCol, coordXCol, coordYCol, minimalPointCol,
                difficultyCol, authorNameCol, authorHeightCol,
                authorEyeColorCol, authorHairColorCol, nationalityCol,
                locXCol, locYCol, locZCol);
    }

    private static Location safeLocation(javafx.scene.control.TableColumn.CellDataFeatures<LabWork, ?> cellData) {
        Person author = cellData.getValue().getAuthor();
        return (author != null && author.getLocation() != null)
                ? author.getLocation()
                : new Location(0f, 0f, 0f); // безопасное значение по умолчанию
    }
}