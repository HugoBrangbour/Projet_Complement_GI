package com.tt.back.service;

import com.tt.back.entity.Document;
import com.tt.back.entity.FileType;
import com.tt.back.repository.DocumentRepository;
import com.tt.back.repository.FileTypeRepository;
import com.tt.back.util.DocumentForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestDocumentService {

    @InjectMocks
    DocumentService documentService;

    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private FileTypeRepository fileTypeRepository;

    @Test
    void testGetAllPage(){
        Document doc1 = new Document(new DocumentForm("file1", "nom_file1", new FileType("mp4"),new Date()));
        Document doc2 = new Document(new DocumentForm("file2", "nom_file2", new FileType("mp4"),new Date()));
        Pageable pageable = PageRequest.of(0, 10);
        Page<Document> docs = new PageImpl<>(Arrays.asList(doc1, doc2));

        when(documentRepository.findAll(any(Pageable.class))).thenReturn(docs);

        Page<Document> result = documentService.getAllPage(pageable);
        Iterator<Document> iterator = result.iterator();

        assertThat(iterator.next()).isEqualTo(doc1);
        assertThat(iterator.next()).isEqualTo(doc2);
    }

    @Test
    void testGetAll(){
        Document doc1 = new Document(new DocumentForm("file1", "nom_file1", new FileType("mp4"),new Date()));
        Document doc2 = new Document(new DocumentForm("file2", "nom_file2", new FileType("mp4"),new Date()));

        when(documentRepository.findAll()).thenReturn(Arrays.asList(doc1, doc2));

        Iterable<Document> result = documentService.getAll();
        Iterator<Document> iterator = result.iterator();

        assertThat(iterator.next()).isEqualTo(doc1);
        assertThat(iterator.next()).isEqualTo(doc2);
    }

    @Test
    void testGetById(){
        Document doc1 = new Document(new DocumentForm("file1", "nom_file1", new FileType("mp4"),new Date()));

        when(documentRepository.findById(doc1.getId())).thenReturn(Optional.of(doc1));

        Optional<Document> result = documentService.getById(doc1.getId());

        assertThat(result.get()).isEqualTo(doc1);
    }

    @Test
    void testGetByNom(){
        Document doc1 = new Document(new DocumentForm("file1", "nom_file1", new FileType("mp4"),new Date()));
        Pageable pageable = PageRequest.of(0, 10);

        Page<Document> docs = new PageImpl<>(List.of(doc1));

        when(documentRepository.findByNomContainsIgnoreCase(eq(doc1.getNom()),any(Pageable.class))).thenReturn(docs);

        Iterable<Document> result = documentService.getByNom(doc1.getNom(), pageable);
        Iterator<Document> iterator = result.iterator();

        assertThat(iterator.next()).isEqualTo(doc1);
    }

    @Test
    void testCreate(){
        DocumentForm docForm = new DocumentForm("file1", "nom_file1", new FileType("mp4"),new Date());
        Document doc1 = new Document(docForm);
        FileType fileType = new FileType("mp4");

        when(fileTypeRepository.findByNom("mp4")).thenReturn(Optional.of(fileType));
        when(documentRepository.save(doc1)).thenReturn(doc1);

        Document result = documentService.create(docForm);

        assertThat(result).isEqualTo(doc1);
    }

    @Test
    void testUpdate(){
        DocumentForm docForm = new DocumentForm("file1", "nom_file1", new FileType("mp4"),new Date());
        DocumentForm docForm2 = new DocumentForm("test", "test", new FileType("binaire"),new Date());
        FileType fileType = new FileType("mp4");

        Document doc1 = new Document(docForm);
        Document doc2 = new Document(docForm2);


        when(fileTypeRepository.findByNom("mp4")).thenReturn(Optional.of(fileType));
        when(documentRepository.save(doc1)).thenReturn(doc1);

        Document result = documentService.update(doc2, docForm);

        assertThat(result).isEqualTo(doc1);
    }

    @Test
    void testDelete(){
        documentService.delete(any(Document.class));
        assertTrue(true);
    }
}
