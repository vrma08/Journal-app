package net.engineeringdigest.journalApp.controller;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    
    @PostMapping
    public ResponseEntity<JournalEntry> create(@RequestBody JournalEntry myEntry){
        try{
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){
        List<JournalEntry> all=journalEntryService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/get/{myId}")
    public ResponseEntity<JournalEntry> getAll(@PathVariable ObjectId myId){
        Optional<JournalEntry> entry=journalEntryService.findById(myId);
        if(entry.isPresent()){
            return new ResponseEntity<>(entry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/dlt/{myId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId){
         journalEntryService.deleteById(myId);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
   


    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId myId,@RequestBody JournalEntry newEntry){
       JournalEntry previousEntry=journalEntryService.findById(myId).orElse(null);
       if(previousEntry!=null){
        previousEntry.setTitle(newEntry.getTitle()!=null && ! newEntry.getTitle().equals("")? newEntry.getTitle():previousEntry.getTitle());
        previousEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals(previousEntry.getContent())? newEntry.getContent():previousEntry.getContent());
        journalEntryService.saveEntry(previousEntry);
        return new ResponseEntity<>(previousEntry,HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
