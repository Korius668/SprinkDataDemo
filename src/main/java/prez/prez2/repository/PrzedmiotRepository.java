package prez.prez2.repository;

import prez.prez2.entity.Przedmiot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PrzedmiotRepository extends MongoRepository<Przedmiot, String> {


    @Query("{ 'cechy.?0': { '$exists': true } }")
    List<Przedmiot> findByCechyNazwaExists(String nazwaCechy);

    @Query("{ 'cechy.?0': ?1 }")
    List<Przedmiot> findByCechyNazwaAndCechyWartosc(String nazwaCechy, String wartoscCechy);
}