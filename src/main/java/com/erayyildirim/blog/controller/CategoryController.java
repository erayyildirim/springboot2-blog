package com.erayyildirim.blog.controller;

import com.erayyildirim.blog.model.Category;
import com.erayyildirim.blog.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;



    @GetMapping("")
    public String listCategories(Model model) {

        model.addAttribute("categories", categoryRepository.findAll());

        return "categories/listCategories";
    }

    @GetMapping("/new")
    public String getNewCategory(Model model) {
        model.addAttribute("category", new Category());
        return "categories/newCategory";
    }

    @PostMapping("/new")
    public String postNewCategory(@Valid @ModelAttribute Category category, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.warn("New category is not valid");
            return "categories/newCategory";
        }

        else {
            categoryRepository.save(category);
            return "redirect:/categories";
        }

    }

    @GetMapping(value = "/{id}")
    public String showCategory (@PathVariable("id") Integer id, Model model){
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if(!categoryOptional.isPresent()){
            log.warn("Category with {} id is not present", id);
            return "index";
        }else{
            model.addAttribute("category", categoryOptional.get());
            return "categories/showCategory";
        }
    }

    @GetMapping(value = "/{id}/update")
    public String getUpdateCategory(@PathVariable("id") Integer id,Model model){
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if(!categoryOptional.isPresent()){
            log.warn("Category with {} id is not present", id);
            return "index";
        }else{
            model.addAttribute("category", categoryOptional.get());
            return "categories/updateCategory";
        }

    }

    @PostMapping(value = "/{id}/update")
    public String postUpdateCategory(@Valid @ModelAttribute Category category, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "categories/updateCategory";
        }else{
            categoryRepository.save(category);
            return "redirect:/categories";
        }
    }

    @PostMapping(value = "/{id}/delete")
    public String deleteCategory(@PathVariable("id")  Integer id){
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if(categoryOptional.isPresent()) {
            categoryRepository.deleteById(id);
            return "redirect:/categories";
        }else{
            log.warn("Category with {} id is not present", id);
            return "index";
        }
    }


}
