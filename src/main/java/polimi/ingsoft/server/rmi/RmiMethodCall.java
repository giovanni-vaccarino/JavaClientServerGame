package polimi.ingsoft.server.rmi;

import polimi.ingsoft.server.socket.protocol.MessageCodes;

import java.io.Serializable;

public class RmiMethodCall implements Serializable {
    private MessageCodes methodName;
    private Object[] args;

    public RmiMethodCall(MessageCodes methodName, Object[] args) {
        this.methodName = methodName;
        this.args = args;
    }

    public MessageCodes getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }
}
