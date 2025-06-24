package domain;

import java.io.Serializable; // ¡Importar!

public class Response implements Serializable {
   private String status;
    private String message;
    private Object data;

    public Response() {}

    public Response(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
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
        return "Response{status='" + status + "', message='" + message + "', data=" + dataStr + '}';
    }
}