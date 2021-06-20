package ru.levelp.hospital.web;

import java.util.Objects;

public class DoctorSession {
    private int userId;
    private String login;

    public DoctorSession() {
    }

    public DoctorSession(int userId, String login) {
        this.userId = userId;
        this.login = login;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isLoggedIn() {
        return userId > 0;
    }

    public void clear() {
        userId = 0;
        login = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorSession that = (DoctorSession) o;
        return userId == that.userId && Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login);
    }

    @Override
    public String toString() {
        return "DoctorSession{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                '}';
    }
}