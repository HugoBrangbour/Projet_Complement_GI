package com.tt.back.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class FileType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length=50, nullable=false)
    private String nom;

    protected FileType(){}

    public FileType(String nom){
        this.nom = nom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileType fileType = (FileType) o;
        return nom.equals(fileType.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom);
    }

    @Override
    public String toString() {
        return "FileType{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
