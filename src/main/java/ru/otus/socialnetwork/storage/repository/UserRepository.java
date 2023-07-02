package ru.otus.socialnetwork.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.socialnetwork.storage.model.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query(value = "SELECT * FROM users WHERE first_name LIKE ?1% AND second_name LIKE ?2%", nativeQuery = true)
    List<UserEntity> findByPrefixes(String firstName, String secondName);

}
