package prez.prez2.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "przedmioty")
public class Przedmiot {

    @Id
    private String id;
    private String nazwa;
    private Map<String, String> cechy = new HashMap<>();

    public Przedmiot() {
    }

    public Przedmiot(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Map<String, String> getCechy() {
        return cechy;
    }

    public void setCechy(Map<String, String> cechy) {
        this.cechy = cechy;
    }

    public void dodajCeche(String klucz, String wartosc) {
        this.cechy.put(klucz, wartosc);
    }
}