package com.tt.back.util;

import com.tt.back.entity.Document;
import com.tt.back.entity.FileType;
import com.tt.back.repository.DocumentRepository;
import com.tt.back.repository.FileTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
public class Seeder {

    private static final Logger log = LoggerFactory.getLogger(Seeder.class);

    @Bean
    // Seeding de la table Document
    public CommandLineRunner seedingDocument(FileTypeRepository typeRepository, DocumentRepository documentRepository) {
        return args -> {
            log.info("Début du seeding de la table Type");
            typeRepository.save(new FileType("texte"));
            typeRepository.save(new FileType("audio"));
            typeRepository.save(new FileType("vidéo"));
            typeRepository.save(new FileType("binaire"));
            log.info("Fin du seeding de la table Type");

            log.info("Début du seeding de la table Document");
            List<FileType> types = typeRepository.findAll();

            Date date1 = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            c.add(Calendar.DATE, 1);
            Date date2 = c.getTime();
            List<Date> dates= new ArrayList<>();
            dates.add(date1);
            dates.add(date2);

            for(int i = 0; i <40; i++){
                DocumentForm doc = new DocumentForm("Emplacment " + i, "Fichier " + i, types.get(i%4), dates.get(i%2));
                documentRepository.save(new Document(doc));
            }
            log.info("Fin du seeding de la table Document");
        };
    }

}
