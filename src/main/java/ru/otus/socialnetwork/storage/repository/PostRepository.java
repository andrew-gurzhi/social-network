package ru.otus.socialnetwork.storage.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.socialnetwork.storage.model.PostEntity;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String> {

    @Query("SELECT p FROM PostEntity p WHERE p.userId.id IN :userIds")
    List<PostEntity> findAllByUserIds(@Param("userIds") List<String> userIds, Pageable page);
}
