package org.example.singleton;

import org.example.models.*;
import org.example.models.enums.*;
import org.example.models.hebergement.*;
import org.example.models.recherche.CriteresRecherche;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnnuaireTest {

    private Annuaire annuaire;
    private Adresse adresse;
    private Hebergement hotel;
    private Chambre chambre;
    private Client client;

    private static final LocalDateTime D1 = LocalDateTime.of(2026, 9, 24, 0, 0);
    private static final LocalDateTime D2 = LocalDateTime.of(2026, 9, 26, 0, 0);
    private static final LocalDateTime D3 = LocalDateTime.of(2026, 9, 28, 0, 0);

    @BeforeEach
    void setUp() {
        annuaire = Annuaire.getInstance();
        annuaire.clear();   // indispensable : le singleton garde son état entre les tests

        adresse = new Adresse("Canada", "Québec", "Saguenay", "Chicoutimi", null);
        hotel = new Hotel(adresse);
        chambre = new Chambre(TypeChambre.SIMPLE, 50.0, 1);
        hotel.addChambre(chambre);
        annuaire.addHebergement(hotel);

        client = new Client("Billy Lucas", adresse, "a@b.fr", "+33000000000");
    }

    @Test
    void singletonRetourneToujoursLaMemeInstance() {
        assertSame(Annuaire.getInstance(), Annuaire.getInstance());
    }

    @Test
    void clientEnDoubleRefuse() {
        annuaire.addClient(client);
        Client copie = new Client("Billy Lucas", adresse, "a@b.fr", "+33000000000");
        assertThrows(IllegalArgumentException.class, () -> annuaire.addClient(copie));
    }

    @Test
    void hebergementMemeAdresseRefuse() {
        assertThrows(IllegalArgumentException.class,
                () -> annuaire.addHebergement(new Hotel(adresse)));
    }

    @Test
    void listesRetourneesNonModifiables() {
        assertThrows(UnsupportedOperationException.class,
                () -> annuaire.get_clients().add(client));
    }

    @Test
    void secondeReservationSurMemePeriodeRefuseeQuandStockEpuise() {
        annuaire.addReservation(new Reservation(chambre, new ArrayList<>(), client, D1, D2));

        Client autre = new Client("Autre", adresse, "x@y.fr", "+1");
        assertThrows(IllegalStateException.class, () ->
                annuaire.addReservation(new Reservation(chambre, new ArrayList<>(), autre, D1, D2)));
    }

    @Test
    void periodesAdjacentesNeSeChevauchentPas() {
        annuaire.addReservation(new Reservation(chambre, new ArrayList<>(), client, D1, D2));
        assertTrue(annuaire.estDisponible(chambre, D2, D3));   // départ le jour de l'arrivée suivante
    }

    @Test
    void annulerReservationLiberelaChambre() {
        Reservation r = new Reservation(chambre, new ArrayList<>(), client, D1, D2);
        annuaire.addReservation(r);
        annuaire.removeReservation(r);
        assertTrue(annuaire.estDisponible(chambre, D1, D2));
    }

    @Test
    void rechercheParVilleEtPrixMax() {
        CriteresRecherche ok = new CriteresRecherche.Builder(D1, D2)
                .region("Canada", "Québec", "Saguenay", null, null)
                .prixMax(60.0)
                .build();
        assertEquals(1, annuaire.rechercherChambresDisponibles(ok).size());

        CriteresRecherche trop = new CriteresRecherche.Builder(D1, D2)
                .prixMax(40.0)
                .build();
        assertTrue(annuaire.rechercherChambresDisponibles(trop).isEmpty());
    }

    @Test
    void rechercheParServiceRequis() {
        CriteresRecherche c = new CriteresRecherche.Builder(D1, D2)
                .servicesRequis(List.of(TypeService.PISCINE_INTERIEUR))
                .build();
        assertTrue(annuaire.rechercherChambresDisponibles(c).isEmpty());

        hotel.addService(new Service(TypeService.PISCINE_INTERIEUR, 15.0));
        assertEquals(1, annuaire.rechercherChambresDisponibles(c).size());
    }

    @Test
    void builderRefuseDatesIncoherentes() {
        assertThrows(IllegalArgumentException.class,
                () -> new CriteresRecherche.Builder(D2, D1));
    }
}