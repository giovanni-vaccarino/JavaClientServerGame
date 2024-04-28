package polimi.ingsoft.client.socket;

public enum SocketCommand {
    PING("PING"),
    PONG("PONG"),
    AVAILABLE_MATCHES("AVAILABLE_MATCHES"),
    MATCHES_LIST("MATCHES_LIST"),
    UNKNOWN("UNKNOWN"),
    QUIT("QUIT");

    private final String value;

    SocketCommand(String value) {
        this.value = value;
    }

    public boolean isEqual(SocketCommand other) {
        return this.value.equals(other.value);
    }

    public boolean isEqual(String value) {
        return this.value.equals(value);
    }
}
