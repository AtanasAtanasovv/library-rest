package com.example.libraryrest.services;

import com.example.libraryrest.dto.requests.AuthorRequest;
import com.example.libraryrest.dto.responses.AuthorResponse;
import com.example.libraryrest.mappers.AuthorMapper;
import com.example.libraryrest.models.Author;
import com.example.libraryrest.repositories.AuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorDAO authorDAO;
    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorResponse> findAll() {
        return authorDAO.findAll().stream()
                .map(author -> authorMapper.entityToResponse(author))
                .collect(Collectors.toList());
    }

    public AuthorResponse findById(int id){
        Author entity = authorDAO.findById(id).orElseThrow();
        return authorMapper.entityToResponse(entity);
    }

    public AuthorResponse create(AuthorRequest request){

        Author author = authorDAO.findByName(request.getFirstName(), request.getLastName());
        if (author != null) {
            throw new IllegalArgumentException("");
        }
        else {
            Author toBeCreated=authorMapper.requestToEntity(request);
            authorDAO.saveAndFlush(toBeCreated);
            return authorMapper.entityToResponse(toBeCreated);
        }
    }
    public AuthorResponse update(AuthorRequest request){
        Author author=authorDAO.findByName(request.getFirstName(), request.getLastName());
        author.setFirstName(request.getFirstName());
        author.setLastName(request.getLastName());
        authorDAO.saveAndFlush(author);
        return authorMapper.entityToResponse(author);
    }
    public void deleteById(int id){
        authorDAO.deleteById(id);
    }

}
