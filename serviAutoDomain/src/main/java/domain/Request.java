package domain;

import java.io.Serializable;

public class Request implements Serializable {
    private String action;
    private Object data;

    public Request() {}

    public Request(String action, Object data) {
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() { // ¡Añadir o mejorar esto!
        String dataStr = (data != null) ? data.getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(data)) : "null";
        if (data instanceof String) {
            dataStr = "'" + data + "'";
        } else if (data instanceof Number) {
            dataStr = data.toString();
        }
        return "Request{action='" + action + "', data=" + dataStr + '}';
    }
}