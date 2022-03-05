package com.tt.back.entity;

import com.tt.back.util.DocumentForm;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length=50, nullable=false)
    private String file;

    @Column(length=50, nullable=false)
    private String nom;

    @ManyToOne
    @JoinColumn(nullable=false)
    private FileType type;

    @Column(nullable=false)
    private Date date;

    public Document() {}

    public Document(DocumentForm documentForm){
        this.type = documentForm.getType();
        this.date = documentForm.getDate();
        this.file = documentForm.getFile();
        this.nom = documentForm.getNom();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        Document document = (Document) o;
        return Objects.equals(id, document.id) && Objects.equals(file, document.file) && Objects.equals(nom, document.nom) && Objects.equals(type, document.type) && Objects.equals(date, document.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, file, nom, type, date);
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", file='" + file + '\'' +
                ", nom='" + nom + '\'' +
                ", type=" + type +
                ", date=" + date +
                '}';
    }
}
