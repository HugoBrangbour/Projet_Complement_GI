package com.tt.back.controller;

import com.tt.back.entity.Document;
import com.tt.back.entity.FileType;
import com.tt.back.service.DocumentService;
import com.tt.back.util.DocumentForm;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestDocumentController {

    @InjectMocks
    DocumentController documentController;

    @Mock
    private DocumentService documentService;


    @Test
    void testGetAll(){

        Document doc1 = new Document(new DocumentForm("file1", "nom_file1", new FileType("mp4"),new Date()));
        Document doc2 = new Document(new DocumentForm("file2", "nom_file2", new FileType("mp4"),new Date()));

        Page<Document> docs = new PageImpl<>(Arrays.asList(doc1, doc2));
        when(documentService.getAllPage(any(Pageable.class))).thenReturn(docs);

        ResponseEntity<Page<Document>> result = documentController.getAll(0);
        Iterator<Document> iterator = result.getBody().iterator();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(iterator.next()).isEqualTo(doc1);
        assertThat(iterator.next()).isEqualTo(doc2);
    }

    @Test
    void getByNom_whenExist() {
        Document doc1 = new Document(new DocumentForm("file1", "nom_file1", new FileType("mp4"),new Date()));

        Page<Document> docs = new PageImpl<>(List.of(doc1));
        when(documentService.getByNom(eq(doc1.getNom()), any(Pageable.class))).thenReturn(docs);

        ResponseEntity<Page<Document>> result = documentController.getByNom(doc1.getNom(), 0);
        Iterator<Document> iterator = result.getBody().iterator();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(iterator.next()).isEqualTo(doc1);
    }

    @Test
    void getByNom_whenNotExist() {
        when(documentService.getByNom(anyString(), any(Pageable.class))).thenReturn(Page.empty());

        ResponseEntity<Page<Document>> result = documentController.getByNom("something", 0);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getById_whenExist() {
        Document doc1 = new Document(new DocumentForm("file1", "nom_file1", new FileType("mp4"),new Date()));

        when(documentService.getById(anyInt())).thenReturn(Optional.of(doc1));

        ResponseEntity<Document> result = documentController.getById(1);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(doc1);
    }

    @Test
    void getById_whenNotExist() {
        when(documentService.getById(anyInt())).thenReturn(Optional.empty());

        ResponseEntity<Document> result = documentController.getById(0);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void createDocument(){
        DocumentForm docForm = new DocumentForm("file1", "nom_file1", new FileType("mp4"),new Date());
        Document doc1 = new Document(docForm);

        when(documentService.create(docForm)).thenReturn(doc1);

        ResponseEntity<Document> result = documentController.create(docForm);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(doc1);
    }

    @Test
    void updateDocument_whenExist(){
        DocumentForm docForm = new DocumentForm("file1", "nom_file1", new FileType("mp4"),new Date());
        Document doc1 = new Document(docForm);

        when(documentService.getById(doc1.getId())).thenReturn(Optional.of(doc1));
        when(documentService.update(any(Document.class), any(DocumentForm.class))).thenReturn(doc1);

        ResponseEntity<Document> result = documentController.update(doc1.getId(),docForm);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(doc1);
    }

    @Test
    void updateDocument_whenNotExist(){
        when(documentService.getById(1)).thenReturn(Optional.empty());

        ResponseEntity<Document> result = documentController.update(1,any(DocumentForm.class));

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteDocument_whenExist(){
        DocumentForm docForm = new DocumentForm("file1", "nom_file1", new FileType("mp4"),new Date());
        Document doc1 = new Document(docForm);

        when(documentService.getById(anyInt())).thenReturn(Optional.of(doc1));

        ResponseEntity<Integer> result = documentController.delete(1);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(1);
    }

    @Test
    void deleteDocument_whenNotExist(){
        when(documentService.getById(anyInt())).thenReturn(Optional.empty());

        ResponseEntity<Integer> result = documentController.delete(anyInt());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getStatsByType(){
        List<Document> documents = createListOfDocuments().getRight();
        when(documentService.getAll()).thenReturn(documents);
        Map<String, Integer> mapResult= new HashMap<>();
        mapResult.put("texte", 10);
        mapResult.put("audio", 10);
        mapResult.put("vidéo", 10);
        mapResult.put("binaire", 10);

        ResponseEntity<Map<String, Integer>> result = documentController.getStatsByType();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(mapResult);
    }

    @Test
    void getStatsByDate(){
        Pair<List<Date>,List<Document>> lapeyre = createListOfDocuments();
        List<Document> documents = lapeyre.getRight();
        List<Date> dates = lapeyre.getLeft();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        when(documentService.getAll()).thenReturn(documents);


        Map<String, Integer> mapResult= new HashMap<>();
        mapResult.put(formatter.format(dates.get(0)), 20);
        mapResult.put(formatter.format(dates.get(1)), 20);

        ResponseEntity<Map<String, Integer>> result = documentController.getStatsByDate();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(mapResult);
    }

    @Test
    void getStatsByLesDeux(){
        Pair<List<Date>,List<Document>> lapeyre = createListOfDocuments();
        List<Document> documents = lapeyre.getRight();
        List<Date> dates = lapeyre.getLeft();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        when(documentService.getAll()).thenReturn(documents);


        Map<ImmutablePair<String,String>, Integer> mapResult= new HashMap<>();
        mapResult.put(ImmutablePair.of(formatter.format(dates.get(0)),"texte"), 10);
        mapResult.put(ImmutablePair.of(formatter.format(dates.get(0)),"vidéo"), 10);
        mapResult.put(ImmutablePair.of(formatter.format(dates.get(1)),"audio"), 10);
        mapResult.put(ImmutablePair.of(formatter.format(dates.get(1)),"binaire"), 10);

        ResponseEntity<Map<ImmutablePair<String,String>, Integer>> result = documentController.getStatsByLesDeux();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(mapResult);
    }

    Pair<List<Date>,List<Document>> createListOfDocuments(){
        List<FileType> types = new ArrayList<>();
        types.add(new FileType("texte"));
        types.add(new FileType("audio"));
        types.add(new FileType("vidéo"));
        types.add(new FileType("binaire"));

        Date date1 = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date1);
        c.add(Calendar.DATE, 1);
        Date date2 = c.getTime();
        List<Date> dates= new ArrayList<>();
        dates.add(date1);
        dates.add(date2);
        List<Document> docs = new ArrayList<>();
        for(int i = 0; i <40; i++){
            DocumentForm doc = new DocumentForm("Emplacment " + i, "Fichier " + i, types.get(i%4), dates.get(i%2));
            docs.add(new Document(doc));
        }

        return Pair.of(dates,docs);
    }
}