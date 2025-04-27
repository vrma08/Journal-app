package net.engineeringdigest.journalApp.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry myEntry,String userName){
         User user=userService.findByUserName(userName);
        myEntry.setDate(LocalDateTime.now());
        JournalEntry saved=journalEntryRepository.save(myEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }

    public void saveEntry(JournalEntry myEntry) {
        journalEntryRepository.save(myEntry);
    }

    
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
       return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id,String userName){
        User user=userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userService.saveUser(user);
         journalEntryRepository.deleteById(id);
    }

    
}
