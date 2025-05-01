package prez.prez2.controller;

import prez.prez2.entity.Book;
import prez.prez2.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "book-list";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam Optional<String> author,
            @RequestParam Optional<Integer> publicationYearFrom,
            @RequestParam Optional<Integer> publicationYearTo,
            Model model) {

        model.addAttribute("books", bookService.search(author, publicationYearFrom, publicationYearTo));
        return "book-list";
    }


    @GetMapping("/new")
    public String newBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    @PostMapping
    public String save(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}