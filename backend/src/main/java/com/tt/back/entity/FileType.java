package com.tt.back.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * The type File type.
 */
@Entity
public class FileType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length=50, nullable=false)
    private String nom;

    /**
     * Instantiates a new File type.
     */
    protected FileType(){}

    /**
     * Instantiates a new File type.
     *
     * @param nom the nom
     */
    public FileType(String nom){
        this.nom = nom;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
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
