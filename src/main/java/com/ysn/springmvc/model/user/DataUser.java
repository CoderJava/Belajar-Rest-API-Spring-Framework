package com.ysn.springmvc.model.user;

import com.ysn.springmvc.model.root.Diagnostic;

public class DataUser {

    private Diagnostic diagnostic;
    private User user;

    public DataUser() {
    }

    public DataUser(Diagnostic diagnostic, User user) {
        this.diagnostic = diagnostic;
        this.user = user;
    }

    public Diagnostic getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "DataUser{" +
                "diagnostic=" + diagnostic +
                ", user=" + user +
                '}';
    }
}
