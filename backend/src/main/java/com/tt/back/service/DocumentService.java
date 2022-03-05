package com.tt.back.service;

import com.tt.back.entity.Document;
import com.tt.back.repository.DocumentRepository;
import com.tt.back.repository.FileTypeRepository;
import com.tt.back.util.DocumentForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final FileTypeRepository fileTypeRepository;

    public DocumentService(DocumentRepository documentRepository, FileTypeRepository fileTypeRepository) {
        this.documentRepository = documentRepository;
        this.fileTypeRepository = fileTypeRepository;
    }

    /**
     * Récupére l'ensemble des documents
     * @return
     */
    public Page<Document> getAllPage(Pageable pageable){
        return documentRepository.findAll(pageable);
    }

    public Iterable<Document> getAll(){
        return documentRepository.findAll();
    }

    /**
     * Recherche un document par son ID
     * @param id
     * @return
     */
    public Optional<Document> getById(Integer id){
        return documentRepository.findById(id);
    }

    /**
     * Recherche un/des documents par son nom
     * @param nom
     * @return
     */
    public Page<Document> getByNom(String nom, Pageable pageable){
        return documentRepository.findByNomContainsIgnoreCase(nom, pageable);
    }

    /**
     * Fonction pour copier l'objet issu du request body dans un document
     * @param documentForm
     * @param document
     * @return
     */
    private Document copyDocumentFormIntoDocument(DocumentForm documentForm, Document document){

        // Récupération du type
        fileTypeRepository.findByNom(documentForm.getType().getNom()).ifPresent(document::setType);

        document.setFile(documentForm.getFile());
        document.setNom(documentForm.getNom());
        document.setDate(documentForm.getDate());

        return documentRepository.save(document);
    }

    /**
     * Création d'un nouveau document
     * @param documentForm
     * @return
     */
    public Document create(DocumentForm documentForm){
        return copyDocumentFormIntoDocument(documentForm, new Document());
    }

    /**
     * Mise à jour d'un document
     * @param document
     * @param documentForm
     * @return
     */
    public Document update(Document document, DocumentForm documentForm){
        return copyDocumentFormIntoDocument(documentForm, document);
    }

    /**
     * Supprime un document
     * @param document
     */
    public void delete(Document document){
        documentRepository.delete(document);
    }
}
