import dbutility.DBConnection;
import dbutility.DBManager;
import models.*;
import java.sql.*;
import com.jcraft.jsch.*;
import serverutility.*;
import utility.*;

import java.io.IOException;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws JSchException, SQLException, ClassNotFoundException {

        final String SSH_HOST = args[0];
        final int SSH_PORT = Integer.parseInt(args[1]);
        final String SSH_USER = args[2];
        final String SSH_PASSWORD = args[3];
        final String DB_URL = args[4];
        final String DB_USER = args[5];
        final String DB_PASSWORD = args[6];
        DBConnection mydbConnection = new DBConnection(SSH_HOST, SSH_PORT, SSH_USER, SSH_PASSWORD, DB_URL, DB_USER, DB_PASSWORD);
        Connection dbConnection = mydbConnection.getConnection();
        String token = UUID.randomUUID().toString();
        DBManager dbManager = new DBManager(dbConnection);
        CollectionManager collectionManager = new CollectionManager(dbManager, token);
        CommandManager commandManager = new CommandManager();
        PasswordManager passwordManager = new PasswordManager();
        Server server = null;
        try {
            ServerCommand handler = new ServerCommand(collectionManager, commandManager, passwordManager, 4);
            server = new Server(handler, 4);


        } catch (IOException e) {
            System.err.println("Ошибка при работе сервера: " + e.getMessage());
            System.exit(1);
        }

        Statement stmt = dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery("""
        SELECT 
            lw.id, lw.name, 
            lw.coordinate_x, lw.coordinate_y,
            lw.creation_date, lw.minimal_point, lw.difficulty,
            a.name AS author_name, a.height AS author_height,
            a.eye_color AS author_eye_color, a.hair_color AS author_hair_color,
            a.nationality AS author_nationality,
            loc.x AS author_location_x, loc.y AS author_location_y, loc.z AS author_location_z
        FROM 
            labworks lw
        JOIN 
            authors a ON lw.author_id = a.id
        JOIN 
            locations loc ON a.location_id = loc.id
        """);
        while (rs.next()) {
            LabWork labWork = new LabWork(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new Coordinates(
                            rs.getInt("coordinate_x"),
                            rs.getLong("coordinate_y")
                    ),
                    rs.getInt("minimal_point"),
                    rs.getString("difficulty") != null ?
                            Difficulty.valueOf(rs.getString("difficulty")) : null,
                    new Person(
                            rs.getString("author_name"),
                            rs.getFloat("author_height"),
                            Color.valueOf(rs.getString("author_eye_color")),
                            rs.getString("author_hair_color") != null ?
                                    Color.valueOf(rs.getString("author_hair_color")) : null,
                            rs.getString("author_nationality") != null ?
                                    Country.valueOf(rs.getString("author_nationality")) : null,
                            new Location(
                                    rs.getDouble("author_location_x"),
                                    rs.getDouble("author_location_y"),
                                    rs.getFloat("author_location_z")
                            )
                    )
            );
            if (!LabWorkValidator.validate(labWork)) {
                System.out.println("Некорректные данные в объекте LabWork: " + labWork);
                System.exit(1);
            }
            else {
                collectionManager.addLabWork(labWork, token);
            }
        }

        server.start();
    }
}