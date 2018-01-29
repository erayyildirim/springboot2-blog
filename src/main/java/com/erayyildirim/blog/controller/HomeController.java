package com.erayyildirim.blog.controller;


import com.erayyildirim.blog.model.Entry;
import com.erayyildirim.blog.repository.EntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j //kullanıcıya uyarı verdirmek ıcın kullanıyoruz
@Controller
@RequestMapping("/blog")
public class HomeController {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getIndex(Model model){

        Iterable<Entry> entries = entryRepository.findAll();
        Iterable<Entry> todaysEntries = entryRepository.findByCreateDate(LocalDate.now());


        model.addAttribute("todaysEntries", todaysEntries);

        model.addAttribute("entries", entries);
        return "entries/listEntries";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getEntryForm(Model model){

        model.addAttribute("entry",new Entry()); //entry formda kullanılıyor

        return "entries/newEntry";
    }
    //
    @RequestMapping(value = "/bean", method = RequestMethod.GET)
    public String getBean(Model model){
        String[] beans = applicationContext.getBeanDefinitionNames();

        model.addAttribute("bean",beans);

        return "entries/listBean";

    }

    //

    //database ekleme
    @RequestMapping(value = "/new", method=RequestMethod.POST)
    public String postEntryForm(@Valid @ModelAttribute  Entry entry, BindingResult bindingResult){
        //Valid formları kontrol eder hata varsa bindingResult hatayı yakalar.
        if(bindingResult.hasErrors()){
            return "entries/newEntry";
        }
        else{
            entryRepository.save(entry);
            return "redirect:/blog"; //getIndex ile bu redirecti tuttuk
        }

    }
    //springel syntax paremetredekı ıd valuedekı ıddir dedik ve bu parametre pathvariable'dir.
    @RequestMapping(value="/{id}" , method=RequestMethod.GET)
    public String showEntry(@PathVariable("id") Integer id, Model model){
        //entryRepository.findById(id); //optional veri yoksa hata vermıyor
        Optional<Entry> entryOptional =entryRepository.findById(id);

        if(!entryOptional.isPresent()){
            log.warn("Entry with {} id is not present", id); // id'nin gosterılecegı yer {} temsil edilir
            return "index";
        }
        else{
            model.addAttribute("entry", entryOptional.get());
        }
        return "entries/showEntry";
    }

    @RequestMapping(value="/{id}/update", method = RequestMethod.GET)
    public String getUpdateEntry(@PathVariable("id") Integer id, Model model){

        Optional<Entry> entryOptional =entryRepository.findById(id);

        if(!entryOptional.isPresent()){
            log.warn("Entry with {} id is not present", id); // id'nin gosterılecegı yer {} temsil edilir
            return "index";
        }
        else{
            model.addAttribute("entry", entryOptional.get());
        }
        return "entries/updateEntry";
    }

    @RequestMapping(value="/{id}/update", method = RequestMethod.POST)
    public String postUpdateEntry(@Valid @ModelAttribute Entry entry, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "entries/updateEntry";
        }
        else{
            entryRepository.save(entry);
            return "redirect:/blog"; //getIndex ile bu redirecti tuttuk
        }
    }

    @RequestMapping(value="/{id}/delete", method = RequestMethod.POST)
    public String deleteEntry(@PathVariable("id") Integer id){

        Optional<Entry> entryOptional =entryRepository.findById(id);

        if(!entryOptional.isPresent()){
            log.warn("Entry with {} id is not present", id); // id'nin gosterılecegı yer {} temsil edilir
            return "index";
        }
        else{
            entryRepository.delete(entryOptional.get());
            return "redirect:/blog";
        }

    }
}
