package com.example.Libreria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LibroController {
    @Autowired
    private LibroService libroService;

    @GetMapping("/aggiungi_libro")
    public String addBookPage(Model model) {
        List<Libro> allBooks = libroService.getAllBooks();
        model.addAttribute("allBooks", allBooks);
        return "aggiungi_libro";
    }

    @PostMapping("/sincronizza")
    public String syncBooks() {
        libroService.syncBooksFromExternalAPI();
        return "redirect:/aggiungi_libro";
    }

    @GetMapping("/dettagli-libro/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        Libro book = libroService.getBookById(id);
        model.addAttribute("book", book);
        return "detail_book";
    }
}
