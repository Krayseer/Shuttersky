package ru.krayseer.shuttersky.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krayseer.shuttersky.domain.entities.Photo;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findByUuid(String uuid);

    void deleteByUuid(String uuid);

}
