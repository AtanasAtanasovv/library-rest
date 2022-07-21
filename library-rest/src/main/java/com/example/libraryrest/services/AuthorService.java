package com.example.libraryrest.services;

import com.example.libraryrest.dto.requests.AuthorRequest;
import com.example.libraryrest.dto.responses.AuthorResponse;
import com.example.libraryrest.mappers.AuthorMapper;
import com.example.libraryrest.models.Author;
import com.example.libraryrest.repositories.AuthorDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    private final AuthorDAO authorDAO;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorDAO authorDAO, AuthorMapper authorMapper) {
        this.authorDAO = authorDAO;
        this.authorMapper = authorMapper;
    }

    public List<AuthorResponse> findAll() {
        logger.info("Find all authors method.");
        return authorDAO.findAll().stream()
                .map(author -> authorMapper.entityToResponse(author))
                .collect(Collectors.toList());
    }

    public AuthorResponse findById(int id) {
        logger.info("Find author by id method.");
        Author entity = authorDAO.findById(id).orElseThrow();
        return authorMapper.entityToResponse(entity);
    }

    public AuthorResponse create(AuthorRequest request) {
        logger.info("Create author method.");
        Author author = authorDAO.findByName(request.getFirstName(), request.getLastName());
        if (author != null) {
            throw new IllegalArgumentException("");
        } else {
            Author toBeCreated = authorMapper.requestToEntity(request);
            authorDAO.saveAndFlush(toBeCreated);
            return authorMapper.entityToResponse(toBeCreated);
        }
    }

    public void deleteById(int id) {
        authorDAO.deleteById(id);
    }

}
