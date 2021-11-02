package com.authorservice.service;

import com.authorservice.VO.ResponseTemplateVO;
import com.authorservice.entity.Author;

import java.util.List;

public interface AuthorService {

    void deleteAuthor(Long id);

    Author updateAuthor(Author author, Long id);

    Author saveAuthor(Author author);

    List<ResponseTemplateVO> getListAuthorWithBook();

    ResponseTemplateVO getAuthorWithBook(Long id);
}
