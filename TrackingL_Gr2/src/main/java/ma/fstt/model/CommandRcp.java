package ma.fstt.model;

public class CommandRcp {
    private long id;
    private String total;

    private String date;

    public CommandRcp(long id, String total, String date) {
        this.id = id;
        this.total = total;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
