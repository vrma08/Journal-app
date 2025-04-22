package net.engineeringdigest.journalApp.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;


    public void saveEntry(JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(myEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
       return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id){
         journalEntryRepository.deleteById(id);
    }
}
