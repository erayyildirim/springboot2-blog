package com.erayyildirim.blog.controller;


import com.erayyildirim.blog.model.Entry;
import com.erayyildirim.blog.model.User;
import com.erayyildirim.blog.repository.EntryRepository;
import com.erayyildirim.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/writers")
public class WriterController {

    @Autowired
    private EntryRepository entryRepository;

    @GetMapping("")
    public String showWriters(Model model){

        Iterable<Entry> writers = entryRepository.findAll();
        model.addAttribute("writers",writers);
        return "writers/listWriters";

    }
}
