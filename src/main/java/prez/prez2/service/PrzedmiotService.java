package prez.prez2.service;
import prez.prez2.entity.Przedmiot;
import prez.prez2.repository.PrzedmiotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrzedmiotService {

    private final PrzedmiotRepository przedmiotRepository;

    public List<Przedmiot> znajdzPrzedmiotyZDanaCecha(String nazwaCechy) {
        return przedmiotRepository.znajdzPrzedmiotyPoCechie(nazwaCechy);
    }

    public List<Przedmiot> znajdzPrzedmiotyZDanaCechaiWartoscia(String nazwaCechy, Object wartoscCechy) {
        return przedmiotRepository.znajdzPrzedmiotyPoCechieWartosc(nazwaCechy, wartoscCechy);
    }

    public void wyswietlPrzedmiotyZDanaCechaiWartoscia(String nazwaCechy, Object wartoscCechy) {
        List<Przedmiot> przedmioty = przedmiotRepository.znajdzPrzedmiotyPoCechieWartosc(nazwaCechy, wartoscCechy);
        for (Przedmiot przedmiot : przedmioty) {
            System.out.println("Nazwa przedmiotu: " + przedmiot.getNazwa());
            if (przedmiot.getCechy().containsKey(nazwaCechy)) {
                System.out.println("Wartość cechy '" + nazwaCechy + "': " + przedmiot.getCechy().get(nazwaCechy));
            }
            System.out.println("---");
        }
    }

    public List<Przedmiot> znajdzWszystkiePosiadajaceCeche(String nazwaCechy) {
        return przedmiotRepository.znajdzPrzedmiotyPosiadajaceCeche(nazwaCechy);
    }
}
