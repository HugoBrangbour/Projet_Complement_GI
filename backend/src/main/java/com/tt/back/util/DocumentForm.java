package com.tt.back.util;

import com.tt.back.entity.FileType;

import java.util.Date;
import java.util.Objects;

public class DocumentForm {

    private String file;
    private String nom;
    private FileType type;
    private Date date;

    public DocumentForm(String file, String nom, FileType type, Date date) {
        this.file = file;
        this.nom = nom;
        this.type = type;
        this.date = date;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentForm that = (DocumentForm) o;
        return Objects.equals(file, that.file) && Objects.equals(nom, that.nom) && Objects.equals(type, that.type) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, nom, type, date);
    }

    @Override
    public String toString() {
        return "DocumentForm{" +
                "file='" + file + '\'' +
                ", nom='" + nom + '\'' +
                ", type=" + type +
                ", date=" + date +
                '}';
    }
}
