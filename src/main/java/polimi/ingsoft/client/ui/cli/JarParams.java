package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.client.ui.UIType;

public class JarParams {
    private final String serverIp;

    private final UIType uiType;

    private final Protocols protocol;

    public JarParams(String serverIp, UIType uiType, Protocols protocol) {
        this.serverIp = serverIp;
        this.uiType = uiType;
        this.protocol = protocol;
    }

    public Protocols getProtocol() {
        return protocol;
    }

    public UIType getUiType() {
        return uiType;
    }

    public String getServerIp() {
        return serverIp;
    }
}
