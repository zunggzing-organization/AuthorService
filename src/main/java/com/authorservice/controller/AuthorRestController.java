package com.authorservice.controller;


import com.authorservice.VO.ResponseTemplateVO;
import com.authorservice.entity.Author;
import com.authorservice.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RefreshScope
public class AuthorRestController {
    @Autowired
    private AuthorService authorService;
    @PostMapping("/")
    public Author saveAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable("id") Long id,@RequestBody Author author) {
        System.out.println("============" + author + "================");
        return authorService.updateAuthor(author, id);
    }

    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable("id") Long id) {
        try {
            authorService.deleteAuthor(id);
            return "Delete Success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    @GetMapping("/")
    public List<ResponseTemplateVO> getListAuthorWithBook() {
        return authorService.getListAuthorWithBook();
    }

    @GetMapping("/{id}")
    public ResponseTemplateVO getAuthorWithBook(@PathVariable("id") Long id) {
        return authorService.getAuthorWithBook(id);
    }
}
