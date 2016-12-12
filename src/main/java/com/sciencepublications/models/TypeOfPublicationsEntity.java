package com.sciencepublications.models;

import javax.persistence.*;

@Entity
@Table(name = "TypeOfPublications", schema = "wojtek14_publikacjenaukowe", catalog = "")
public class TypeOfPublicationsEntity {
    private int idType;
    private String name;

    @Id
    @Column(name = "id_type")
    public int getIdType() {
        return idType;
    }


    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeOfPublicationsEntity that = (TypeOfPublicationsEntity) o;

        if (idType != that.idType) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idType;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
