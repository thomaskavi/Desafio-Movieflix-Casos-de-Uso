package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

  @Autowired
  private MovieRepository repository;

  @Transactional(readOnly = true)
  public Page<MovieCardDTO> findAllPaged(String title, String genreId, Pageable pageable) {
    Page<Movie> list = repository.searchAllMoviesOrdered(title, genreId, pageable);
    return list.map(MovieCardDTO::new);
  }

  @Transactional(readOnly = true)
  public MovieDetailsDTO findById(Long id) {
    Optional<Movie> obj = repository.findById(id);
    Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    return new MovieDetailsDTO(entity);
  }
}
