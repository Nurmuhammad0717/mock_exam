import java.time.LocalDateTime;

public class Events {
    private long time;
    private String message;

    public Events(long time, String message) {
        this.time = time;
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Events{" +
                "time=" + time +
                ", message='" + message + '\'' +
                '}';
    }
}
