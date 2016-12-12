package com.sciencepublications.models;

import javax.persistence.*;

/**
 * Created by Robert on 2016-12-09.
 */
@Entity
@Table(name = "value", schema = "wojtek14_publikacjenaukowe", catalog = "")
public class ValueEntity {
    private int idValue;
    private int idAttribute;
    private int idPub;
    private String content;

    @Id
    @Column(name = "id_value")
    public int getIdValue() {
        return idValue;
    }

    public void setIdValue(int idValue) {
        this.idValue = idValue;
    }

    @Basic
    @Column(name = "id_attribute")
    public int getIdAttribute() {
        return idAttribute;
    }

    public void setIdAttribute(int idAttribute) {
        this.idAttribute = idAttribute;
    }

    @Basic
    @Column(name = "id_pub")
    public int getIdPub() {
        return idPub;
    }

    public void setIdPub(int idPub) {
        this.idPub = idPub;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueEntity that = (ValueEntity) o;

        if (idValue != that.idValue) return false;
        if (idAttribute != that.idAttribute) return false;
        if (idPub != that.idPub) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idValue;
        result = 31 * result + idAttribute;
        result = 31 * result + idPub;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
