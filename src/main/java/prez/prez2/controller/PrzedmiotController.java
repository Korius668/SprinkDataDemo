package prez.prez2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prez.prez2.entity.Przedmiot;
import prez.prez2.repository.PrzedmiotRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PrzedmiotController {

    private final PrzedmiotRepository przedmiotRepository;

    public PrzedmiotController(PrzedmiotRepository przedmiotRepository) {
        this.przedmiotRepository = przedmiotRepository;
    }
    @PostMapping("/przedmioty")
    @ResponseBody
    public ResponseEntity<Map<String, String>> dodajPrzedmiot(@RequestBody Map<String, Object> danePrzedmiotu) {
        String nazwa = (String) danePrzedmiotu.get("nazwa");
        if (nazwa == null || nazwa.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Brak nazwy przedmiotu");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Przedmiot przedmiot = new Przedmiot(nazwa);
        Map<String, Object> cechy = (Map<String, Object>) danePrzedmiotu.get("cechy");
        if (cechy != null) {
            przedmiot.setCechy(cechy);
        }

        Przedmiot zapisanyPrzedmiot = przedmiotRepository.save(przedmiot);
        Map<String, String> result = new HashMap<>();
        result.put("id", zapisanyPrzedmiot.getId());
        result.put("nazwa", zapisanyPrzedmiot.getNazwa());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    @GetMapping("/dodaj-przedmiot")
    public String pokazFormularzDodawania() {
        return "dodaj-przedmiot";
    }

    @GetMapping("/wyswietl-przedmioty")
    public String pokazListePrzedmiotow(Model model) {
        List<Przedmiot> przedmioty = przedmiotRepository.findAll();
        model.addAttribute("przedmioty", przedmioty);
        return "wyswietl-przedmioty";
    }


    @GetMapping("/wyszukaj-po-cesze")
    public String wyszukajPoCechie(@RequestParam String nazwaCechy, Model model) {
        List<Przedmiot> przedmioty = przedmiotRepository.znajdzPrzedmiotyPoCechie(nazwaCechy);
        model.addAttribute("przedmioty", przedmioty);
        model.addAttribute("wyszukiwanieInfo", "Wyniki wyszukiwania dla cechy: " + nazwaCechy);
        return "wyswietl-przedmioty";
    }

    @GetMapping("/wyszukaj-po-cesze-wartosc")
    public String wyszukajPoCechieWartosc(@RequestParam String nazwaCechy, @RequestParam String wartoscCechy, Model model) {
        List<Przedmiot> przedmioty = przedmiotRepository.znajdzPrzedmiotyPoCechieWartosc(nazwaCechy, wartoscCechy);
        model.addAttribute("przedmioty", przedmioty);
        model.addAttribute("wyszukiwanieInfo", "Wyniki wyszukiwania dla cechy: " + nazwaCechy + " o wartości: " + wartoscCechy);
        return "wyswietl-przedmioty";
    }
}