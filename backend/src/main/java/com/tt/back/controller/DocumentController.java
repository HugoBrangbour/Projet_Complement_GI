package com.tt.back.controller;

import com.tt.back.entity.Document;
import com.tt.back.service.DocumentService;
import com.tt.back.util.DocumentForm;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;
    static int NUMBER_OF_ELEMENT = 10;
    static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<Page<Document>> getAll(@RequestParam int offset){
        Pageable pageable = PageRequest.of(offset, NUMBER_OF_ELEMENT);
        return new ResponseEntity<>(documentService.getAllPage(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Document> getById(@PathVariable("id") Integer id){
        Optional<Document> documentOptional = documentService.getById(id);
        if(documentOptional.isPresent()){
            return new ResponseEntity<>(documentOptional.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/nom/{nom}")
    public ResponseEntity<Page<Document>> getByNom(@PathVariable("nom") String nom, @RequestParam int offset){
        Pageable pageable = PageRequest.of(offset, NUMBER_OF_ELEMENT);
        Page<Document> docs = documentService.getByNom(nom, pageable);
        if(docs.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(documentService.getByNom(nom, pageable), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<Document> create(@RequestBody DocumentForm documentForm){
        return new ResponseEntity<>(documentService.create(documentForm), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Document> update(@PathVariable("id") Integer id, @RequestBody DocumentForm documentForm){
        Optional<Document> documentOptional = documentService.getById(id);
        if(documentOptional.isPresent()){
            Document updatedDocument = documentService.update(documentOptional.get(),documentForm);
            return new ResponseEntity<>(updatedDocument, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") Integer id){
        Optional<Document> documentOptional = documentService.getById(id);
        if(documentOptional.isPresent()){
            documentService.delete(documentOptional.get());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/statsType")
    public ResponseEntity<Map<String, Integer>> getStatsByType(){
        Iterable<Document> docs = documentService.getAll();
        Map<String, Integer> mapResult= new HashMap<>();
        for (Document doc : docs) {
            String type = doc.getType().getNom();
            if(mapResult.containsKey(type)){
                mapResult.replace(type,mapResult.get(type)+1);
            } else {
                mapResult.put(type, 1);
            }
        }
        return new ResponseEntity<>(mapResult, HttpStatus.OK);
    }

    @GetMapping(value = "/statsDate")
    public ResponseEntity<Map<String, Integer>> getStatsByDate(){
        Iterable<Document> docs = documentService.getAll();
        Map<String, Integer> mapResult= new HashMap<>();
        for (Document doc : docs) {
            String date = formatter.format(doc.getDate());
            if(mapResult.containsKey(date)){
                mapResult.replace(date,mapResult.get(date)+1);
            } else {
                mapResult.put(date, 1);
            }
        }
        return new ResponseEntity<>(mapResult, HttpStatus.OK);
    }

    @GetMapping(value = "/statsDeux")
    public ResponseEntity<Map<ImmutablePair<String, String>, Integer>> getStatsByLesDeux(){
        Iterable<Document> docs = documentService.getAll();
        Map<ImmutablePair<String, String>, Integer> mapResult= new HashMap<>();
        for (Document doc : docs) {
            String date = formatter.format(doc.getDate());
            String type = doc.getType().getNom();
            ImmutablePair<String, String> pair = new ImmutablePair<>(date, type);
            if(mapResult.containsKey(pair)){
                mapResult.replace(pair,mapResult.get(pair)+1);
            } else {
                mapResult.put(pair, 1);
            }
        }
        return new ResponseEntity<>(mapResult, HttpStatus.OK);
    }
}