package com.erayyildirim.blog.controller;

import com.erayyildirim.blog.model.Entry;
import com.erayyildirim.blog.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/entries")
public class RestController {

    @Autowired
    private EntryRepository entryRepository;

    @GetMapping("")
    public ResponseEntity<List<Entry>> listAllEntries(){

        List<Entry> entries = entryRepository.findAllEntries();
        return ResponseEntity.ok(entries);
    }

    //ReuquestBody gelen json datayı objeye çeviriyor
    @PostMapping("")
    public ResponseEntity<Entry> createNewEntry(@Validated @RequestBody Entry entry){

        entryRepository.save(entry);
        return ResponseEntity.ok(entry);
    }

    @PutMapping("")
    public ResponseEntity<Entry> updateEntry(@Validated @RequestBody Entry entry){
       Entry savedEntry = entryRepository.save(entry);
       return ResponseEntity.ok(savedEntry);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Entry> getEntryId(@PathVariable("id") Integer id){

        Entry entry = entryRepository.findEntryById(id);

        return ResponseEntity.ok(entry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntryId(@PathVariable("id") Integer id){

        entryRepository.deleteEntryById(id);
        return ResponseEntity.noContent().build();
    }
    //calısmıyor
    @GetMapping("/search")
    public ResponseEntity<List<Entry>> searchEntry(@RequestParam("title") String str){

        List<Entry> entries = entryRepository.findByTitle(str);
        return ResponseEntity.ok(entries);

    }


}
