package com.tt.back.util;

import com.tt.back.entity.FileType;

import java.util.Date;
import java.util.Objects;

/**
 * The type Document form.
 */
public class DocumentForm {

    private String file;
    private String nom;
    private FileType type;
    private Date date;

    /**
     * Instantiates a new Document form.
     *
     * @param file the file
     * @param nom  the nom
     * @param type the type
     * @param date the date
     */
    public DocumentForm(String file, String nom, FileType type, Date date) {
        this.file = file;
        this.nom = nom;
        this.type = type;
        this.date = date;
    }

    /**
     * Gets file.
     *
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * Sets file.
     *
     * @param file the file
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Gets nom.
     *
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Sets nom.
     *
     * @param nom the nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public FileType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(FileType type) {
        this.type = type;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
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
