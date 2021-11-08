package com.authorservice.service;

import com.authorservice.VO.Book;
import com.authorservice.VO.ResponseTemplateVO;
import com.authorservice.entity.Author;
import com.authorservice.repository.AuthorRepository;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AuthorImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);

    }

    @Override
    public Author updateAuthor(Author author, Long id) {
        author.setId(id);
        return authorRepository.save(author);
    }

    @Override
    @Retry(name = "basic")
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<ResponseTemplateVO> getListAuthorWithBook() {
        ArrayList<ResponseTemplateVO> listResponseTemplateVO = new ArrayList<>();
        ArrayList<Author> listAuthor = (ArrayList<Author>) authorRepository.findAll();
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        for (int i = 0; i < listAuthor.size(); i++) {
            responseTemplateVO.setAuthor(listAuthor.get(i));
            responseTemplateVO.setBook(restTemplate.getForObject("http://localhost:8080/books/" + listAuthor.get(i).getBookId(), Book.class));
            listResponseTemplateVO.add(responseTemplateVO);
        }
        return listResponseTemplateVO;
    }

    @Override
    @RateLimiter(name = "basic", fallbackMethod = "fallMethodRateLimiter")
    public ResponseTemplateVO getAuthorWithBook(Long id) {
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        Author author = authorRepository.findById(id).get();
        responseTemplateVO.setAuthor(author);
        Book book = restTemplate.getForObject("http://localhost:8080/books/" + author.getBookId(),
                Book.class);
        responseTemplateVO.setBook(book);
        return responseTemplateVO;
    }
    public Author fallMethodRateLimiter(Long id, RequestNotPermitted rnp) {
        log.info("Request Not Permitted By Many Request");
        return null;
    }

}
