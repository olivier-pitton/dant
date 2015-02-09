package org.tp.model;

/**
 * Created by pitton on 2015-02-09.
 */
public class User implements Comparable<User> {

    private long id;

    private String mail;

    private String password;

    public User(){}

    public User(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (mail != null ? !mail.equals(user.mail) : user.mail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mail != null ? mail.hashCode() : 0;
    }

    @Override
    public int compareTo(User o) {
        return mail.compareToIgnoreCase(o.getMail());
    }

    @Override
    public String toString() {
        return mail;
    }
}
