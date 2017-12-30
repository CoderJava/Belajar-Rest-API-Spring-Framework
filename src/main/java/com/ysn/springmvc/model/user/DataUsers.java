package com.ysn.springmvc.model.user;

import com.ysn.springmvc.model.root.Diagnostic;

import java.util.List;

public class DataUsers {

    private Diagnostic diagnostic;
    private List<User> users;

    public DataUsers() {
    }

    public DataUsers(Diagnostic diagnostic, List<User> users) {
        this.diagnostic = diagnostic;
        this.users = users;
    }

    public Diagnostic getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "DataUsers{" +
                "diagnostic=" + diagnostic +
                ", users=" + users +
                '}';
    }
}
