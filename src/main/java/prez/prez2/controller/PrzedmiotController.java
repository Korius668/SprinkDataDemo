package prez.prez2.controller;

import prez.prez2.entity.Przedmiot;
import prez.prez2.repository.PrzedmiotRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class PrzedmiotController {

    private final PrzedmiotRepository przedmiotRepository;

    public PrzedmiotController(PrzedmiotRepository przedmiotRepository) {
        this.przedmiotRepository = przedmiotRepository;
    }

    @PostMapping("/api/przedmioty")
    @ResponseBody
    public ResponseEntity<String> dodajPrzedmiot(@RequestParam Map<String, String> formParams) {
        String nazwa = formParams.get("nazwa");
        if (nazwa == null || nazwa.isEmpty()) {
            return new ResponseEntity<>("Brak nazwy", HttpStatus.BAD_REQUEST);
        }

        Przedmiot przedmiot = new Przedmiot(nazwa);

        // Collect dynamic features
        formParams.forEach((klucz, wartosc) -> {
            if (!klucz.equals("nazwa")) {
                przedmiot.dodajCeche(klucz, wartosc);
            }
        });

        przedmiotRepository.save(przedmiot);
        return new ResponseEntity<>("Zapisano przedmiot", HttpStatus.CREATED);
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