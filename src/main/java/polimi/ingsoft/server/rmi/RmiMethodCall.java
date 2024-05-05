package polimi.ingsoft.server.rmi;

public class RmiMethodCall {
    private String methodName;
    private Object[] args;

    public RmiMethodCall(String methodName, Object[] args) {
        this.methodName = methodName;
        this.args = args;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }
}
