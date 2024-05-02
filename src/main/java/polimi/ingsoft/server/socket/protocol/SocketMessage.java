package polimi.ingsoft.server.socket.protocol;
import java.io.Serializable;

public class SocketMessage implements Serializable {
    public static class IdAndNickname implements Serializable {
        public int id;
        public String nickname;

        public IdAndNickname(int id, String nickname) {
            this.id = id;
            this.nickname = nickname;
        }
    }

    public SocketMessage(MessageCodes type, Serializable payload) {
        this.type = type;
        this.payload = payload;
    }

    public MessageCodes type;
    public Serializable payload;
}
