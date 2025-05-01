package prez.prez2.repository;

import prez.prez2.entity.Przedmiot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Map;

public interface PrzedmiotRepository extends MongoRepository<Przedmiot, String> {

    @Query(value = "{ 'cechy.?0' : { $exists: true } }", fields = "{ 'nazwa': 1, 'cechy.?0': 1 }")
    List<Przedmiot> znajdzPrzedmiotyPoCechie(String nazwaCechy);

    @Query(value = "{ 'cechy.?0' : ?1 }", fields = "{ 'nazwa': 1, 'cechy.?0': 1 }")
    List<Przedmiot> znajdzPrzedmiotyPoCechieWartosc(String nazwaCechy, Object wartoscCechy);

    @Query("{ 'cechy.?0': { $exists: true } }")
    List<Przedmiot> znajdzPrzedmiotyPosiadajaceCeche(String nazwaCechy);
}