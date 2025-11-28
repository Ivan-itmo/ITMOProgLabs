package serverutility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;

public class ServerAnswers {
    List<Object> answers = new ArrayList<>();
    public ServerAnswers() {
        answers.add("Выполнено");
    }
    public void setAnswers(List<Object> answers) {
        this.answers = answers;
    }
    public List<Object> getAnswer() {
        List<Object> currentAnswers = answers;
        setAnswers(new ArrayList<>());
        if (currentAnswers.isEmpty()) {
            List<Object> answer = new ArrayList<>();
            answer.add("Выполнено");
            return answer;
        }
        return currentAnswers;
    }


    public void handleWrite(DatagramChannel dc, ServerAnswers serverAnswer, SelectionKey key)
            throws IOException {

        SocketAddress clientAddr = (SocketAddress) key.attach(null);
        if (clientAddr == null) {
            key.interestOps(SelectionKey.OP_READ);
            return;
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            for (Object o : serverAnswer.getAnswer()) {
                oos.writeObject(o);
            }

            byte[] responseData = baos.toByteArray();
            ByteBuffer responseBuffer = ByteBuffer.wrap(responseData);
            dc.send(responseBuffer, clientAddr);

            key.interestOps(SelectionKey.OP_READ);
        }
    }
}
