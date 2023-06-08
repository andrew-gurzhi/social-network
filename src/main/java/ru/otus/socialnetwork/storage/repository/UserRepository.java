package ru.otus.socialnetwork.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.socialnetwork.storage.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

}
