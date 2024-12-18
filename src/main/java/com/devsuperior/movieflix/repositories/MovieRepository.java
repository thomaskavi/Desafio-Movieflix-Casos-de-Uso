package com.devsuperior.movieflix.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsuperior.movieflix.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

  @Query(nativeQuery = true, value = """
      SELECT *
      FROM TB_MOVIE
      WHERE (:title IS NULL OR LOWER(title) LIKE LOWER(CONCAT('%', :title, '%')))
        AND (:genreId = '0' OR genre_id = :genreId)
      ORDER BY title
            """)
  Page<Movie> searchAllMoviesOrdered(@Param("title") String title, @Param("genreId") String genreId, Pageable pageable);
}
