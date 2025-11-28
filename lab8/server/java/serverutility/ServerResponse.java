package serverutility;

import models.LabWork;
import java.io.Serializable;
import java.util.*;

public class ServerResponse implements Serializable {
    private static final long serialVersionUID = 8267722630152758072L;

    private final List<Object> commandResponse;
    private final HashMap<LabWork, String> collectionSnapshot;

    public ServerResponse(List<Object> commandResponse, HashMap<LabWork, String> collectionSnapshot) {
        this.commandResponse = commandResponse;
        this.collectionSnapshot = collectionSnapshot;
    }

    /**
     * Возвращает объединённый список всех данных,
     * чтобы клиент мог просто десериализовать это как List<Object>.
     */
    public List<Object> asObjectList() {
        List<Object> result = new ArrayList<>(commandResponse);
        result.add(collectionSnapshot); // Добавляем всю карту как один объект
        return result;
    }

    public List<Object> getCommandResponse() {
        return commandResponse;
    }

    public HashMap<LabWork, String> getCollectionSnapshot() {
        return collectionSnapshot;
    }
}