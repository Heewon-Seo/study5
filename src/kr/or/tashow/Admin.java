package kr.or.tashow;

public class Admin {
    private final String id;
    private final String pw;

    public Admin() {
        this.id = "admin";
        this.pw = "admin!";
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }
}

