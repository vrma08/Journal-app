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
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;
    
    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@PathVariable String userName,@RequestBody JournalEntry myEntry){
        try{
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> all=user.getJournalEntries();
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

    @DeleteMapping("{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId,@PathVariable String userName){
         journalEntryService.deleteById(myId,userName);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
   


    @PutMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId myId,
    @RequestBody JournalEntry newEntry,
    @PathVariable String userName
    ){
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
