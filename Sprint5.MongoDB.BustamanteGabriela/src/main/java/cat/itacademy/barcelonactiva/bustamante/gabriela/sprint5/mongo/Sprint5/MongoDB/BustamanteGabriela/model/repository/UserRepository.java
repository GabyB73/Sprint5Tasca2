package cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.repository;

import cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserByUsername(String userName);
}
