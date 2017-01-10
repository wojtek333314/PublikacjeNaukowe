package com.sciencepublications.models;

import javax.persistence.*;

@Entity
@Table(name = "publication", schema = "wojtek14_publikacjenaukowe", catalog = "")
public class PublicationEntity {
    private int publicationId;
    private int authorId;
    private String title;
    private String json;
    private String authorName;
    private int fileId;

    @Id
    @Column(name = "publication_id")
    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    @Basic
    @Column(name = "author_id")
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "json")
    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Basic
    @Column(name = "author_name")
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Basic
    @Column(name = "file_id")
    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublicationEntity that = (PublicationEntity) o;

        if (publicationId != that.publicationId) return false;
        if (authorId != that.authorId) return false;
        if (fileId != that.fileId) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (json != null ? !json.equals(that.json) : that.json != null) return false;
        if (authorName != null ? !authorName.equals(that.authorName) : that.authorName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = publicationId;
        result = 31 * result + authorId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (json != null ? json.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + fileId;
        return result;
    }
}
