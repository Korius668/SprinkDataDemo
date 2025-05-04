package prez.prez2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prez.prez2.entity.Przedmiot;
import prez.prez2.repository.PrzedmiotRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.lang.Nullable;

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
        Map<String, String> cechy = (Map<String, String>) danePrzedmiotu.get("cechy");
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

    @GetMapping("/wyszukaj")
    public String search(@RequestParam String nazwaCechy,
                         @RequestParam @Nullable String wartoscCechy,
                         Model model) {
        List<Przedmiot> przedmioty;
        if(wartoscCechy!=null  && !wartoscCechy.isEmpty()) {

            przedmioty= przedmiotRepository.findByCechyNazwaAndCechyWartosc(nazwaCechy, wartoscCechy);
            model.addAttribute("wyszukiwanieInfo", "Wyniki wyszukiwania dla cechy: " + nazwaCechy + " o wartości: " + wartoscCechy);
        }
        else {
            przedmioty = przedmiotRepository.findByCechyNazwaExists(nazwaCechy);
            model.addAttribute("wyszukiwanieInfo", "Wyniki wyszukiwania dla cechy: " + nazwaCechy);
        }
        model.addAttribute("przedmioty", przedmioty);
        return "wyswietl-przedmioty";
    }

    @PostMapping("/przedmioty/delete/{id}")
    public String usunPrzedmiot(@PathVariable String id) {
        przedmiotRepository.deleteById(id);
        return "redirect:/wyswietl-przedmioty";
    }

}