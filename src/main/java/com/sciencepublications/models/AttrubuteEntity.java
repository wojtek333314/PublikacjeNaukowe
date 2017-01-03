package com.sciencepublications.models;

import javax.persistence.*;

@Entity
@Table(name = "Attrubute", schema = "wojtek14_publikacjenaukowe", catalog = "")
public class AttrubuteEntity {
    private int idAttribute;
    private int idType;
    private String name;
    private boolean optional;

    @Id
    @Column(name = "id_attribute")
    public int getIdAttribute() {
        return idAttribute;
    }

    public void setIdAttribute(int idAttribute) {
        this.idAttribute = idAttribute;
    }

    @Basic
    @Column(name = "id_type")
    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "optional")
    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttrubuteEntity that = (AttrubuteEntity) o;

        if (idAttribute != that.idAttribute) return false;
        if (idType != that.idType) return false;
        if (optional != that.optional) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAttribute;
        result = 31 * result + idType;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (optional ? 1 : 0);
        return result;
    }
}
