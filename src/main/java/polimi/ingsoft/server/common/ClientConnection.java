package polimi.ingsoft.server.common;

import polimi.ingsoft.client.common.VirtualView;

public class ClientConnection {
    private VirtualView virtualView;

    private String nickname;

    private Boolean isConnected;

    public ClientConnection(VirtualView virtualView, String nickname){
        this.virtualView = virtualView;
        this.nickname = nickname;
        this.isConnected = true;
    }

    public String getNickname(){
        return nickname;
    }

    public VirtualView getVirtualView(){
        return virtualView;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }

    public Boolean getConnected() {
        return isConnected;
    }
}
