package model;

public class Admin {
    private int username ;
    private int password;
    private int role;

    public int getUserId() {
        return username;
    }

    public void setUserId(int userId) {
        this.username = username;
    }

    public int getPassword() {
        return password;
    }

    public void setPasswprd(int passwprd) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
