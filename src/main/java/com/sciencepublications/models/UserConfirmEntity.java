package com.sciencepublications.models;

import javax.persistence.*;

@Entity
@Table(name = "user_confirm", schema = "wojtek14_publikacjenaukowe", catalog = "")
public class UserConfirmEntity {
    private int id;
    private int userId;
    private String hash;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "hash")
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserConfirmEntity that = (UserConfirmEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (hash != null ? !hash.equals(that.hash) : that.hash != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        return result;
    }
}
