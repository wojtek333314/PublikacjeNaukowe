package com.sciencepublications.models;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "files", schema = "wojtek14_publikacjenaukowe", catalog = "")
public class FileEntity {
    private int id;
    private byte[] file;
    private String filename;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "file")
    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Basic
    @Column(name = "filename")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileEntity that = (FileEntity) o;

        if (id != that.id) return false;
        if (!Arrays.equals(file, that.file)) return false;
        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + Arrays.hashCode(file);
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        return result;
    }
}
