package org.example.models;

import org.example.models.enums.TypeChambre;
import org.example.models.enums.TypeService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private final Adresse adresse = new Adresse("Canada", "Québec", "Saguenay");
    private final Client client = new Client("Billy Lucas", adresse, "a@b.fr", "+33000000000");

    @Test
    void prixDeuxNuitsAvecService() {
        Chambre chambre = new Chambre(TypeChambre.SIMPLE, 50.0, 1);
        Service service = new Service(TypeService.PARKING, 10.0);

        Reservation r = new Reservation(chambre, new ArrayList<>(List.of(service)), client,
                LocalDateTime.of(2026, 9, 24, 0, 0),
                LocalDateTime.of(2026, 9, 26, 0, 0));

        assertEquals(120.0, r.getPrix());   // (50 + 10) * 2
    }

    @Test
    void prixSansService() {
        Chambre chambre = new Chambre(TypeChambre.DOUBLE, 80.0, 1);

        Reservation r = new Reservation(chambre, new ArrayList<>(), client,
                LocalDateTime.of(2026, 9, 24, 0, 0),
                LocalDateTime.of(2026, 9, 25, 0, 0));

        assertEquals(80.0, r.getPrix());
    }

    @Test
    void finAvantDebutRefusee() {
        Chambre chambre = new Chambre(TypeChambre.SIMPLE, 50.0, 1);
        assertThrows(IllegalArgumentException.class, () ->
                new Reservation(chambre, new ArrayList<>(), client,
                        LocalDateTime.of(2026, 9, 26, 0, 0),
                        LocalDateTime.of(2026, 9, 24, 0, 0)));
    }
}