package org.example.singleton;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.*;
import org.example.models.enums.*;
import org.example.models.hebergement.*;
import org.example.models.recherche.CriteresRecherche;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DonneesJsonTest {

    // Correspondance noms du JSON -> enum du code
    private static final Map<String, TypeService> TYPES_SERVICE = Map.of(
            "PISCINE_INTERIEURE", TypeService.PISCINE_INTERIEUR,
            "STATIONNEMENT", TypeService.PARKING,
            "SALLE_CONDITIONNEMENT_PHYSIQUE", TypeService.SPORT,
            "RESTAURANT", TypeService.RESTAURANT,
            "ACCES_HANDICAPE", TypeService.ACCES_HANDICAPE,
            "DEPANNEUR", TypeService.DEPANNEUR,
            "CUISINETTE", TypeService.CUISINETTE);

    private final Annuaire annuaire = Annuaire.getInstance();
    private final Map<String, Hebergement> hebergements = new HashMap<>();
    private final Map<String, Chambre> chambres = new HashMap<>();
    private final Map<String, Service> services = new HashMap<>();
    private final Map<String, Client> clients = new HashMap<>();
    private JsonNode racine;

    // ---------- Chargement ----------

    @BeforeEach
    void chargerDonnees() throws IOException {
        annuaire.clear();
        try (InputStream in = getClass().getResourceAsStream("/data.json")) {
            assertNotNull(in, "data.json introuvable dans src/test/resources");
            racine = new ObjectMapper().readTree(in);
        }

        for (JsonNode h : racine.get("hebergements")) {
            chargerHebergement(h);
        }
        for (JsonNode c : racine.get("clients")) {
            Client client = new Client(
                    c.get("nom").asText(),
                    lireAdresse(c.get("adresse")),
                    c.get("email").asText(),
                    c.get("numtel").asText());
            clients.put(c.get("id").asText(), client);
            annuaire.addClient(client);
        }
    }

    private void chargerHebergement(JsonNode h) {
        Adresse adresse = lireAdresse(h.get("adresse"));
        String type = h.get("type").asText();
        Hebergement heb = switch (type) {
            case "HOTEL" -> new Hotel(adresse);
            case "MOTEL" -> new Motel(adresse);
            case "COUETTE_ET_CAFE" -> new Couette(adresse);   // provisoire
            default -> throw new IllegalArgumentException("Type d'hébergement inconnu : " + type);
        };

        for (JsonNode c : h.get("chambres")) {
            Chambre chambre = new Chambre(
                    TypeChambre.valueOf(c.get("type").asText()),
                    c.get("prix").asDouble(),
                    c.get("nombreDisponible").asInt());
            heb.addChambre(chambre);
            chambres.put(c.get("id").asText(), chambre);
        }
        for (JsonNode s : h.get("services")) {
            Service service = new Service(typeService(s.get("type").asText()), s.get("prix").asDouble());
            heb.addService(service);
            services.put(s.get("id").asText(), service);
        }

        annuaire.addHebergement(heb);
        hebergements.put(h.get("id").asText(), heb);
    }

    private Adresse lireAdresse(JsonNode a) {
        return new Adresse(texte(a, "pays"), texte(a, "province"), texte(a, "ville"),
                texte(a, "quartier"), texte(a, "rue"));
    }

    private String texte(JsonNode noeud, String champ) {
        JsonNode n = noeud.get(champ);
        return (n == null || n.isNull()) ? null : n.asText();
    }

    private TypeService typeService(String nomJson) {
        TypeService t = TYPES_SERVICE.get(nomJson);
        if (t == null) throw new IllegalArgumentException("Type de service inconnu : " + nomJson);
        return t;
    }

    private Reservation construireReservation(String id) {
        for (JsonNode r : racine.get("reservations")) {
            if (r.get("id").asText().equals(id)) {
                ArrayList<Service> liste = new ArrayList<>();
                for (JsonNode sid : r.get("servicesIds")) {
                    liste.add(services.get(sid.asText()));
                }
                return new Reservation(
                        chambres.get(r.get("chambreId").asText()),
                        liste,
                        clients.get(r.get("clientId").asText()),
                        LocalDateTime.parse(r.get("dateArrivee").asText()),
                        LocalDateTime.parse(r.get("dateDepart").asText()));
            }
        }
        throw new IllegalArgumentException("Réservation introuvable dans le JSON : " + id);
    }

    // ---------- Tests ----------

    @Test
    void donneesChargeesCorrectement() {
        assertEquals(3, annuaire.get_hebergements().size());
        assertEquals(3, annuaire.get_clients().size());
        assertEquals(3, hebergements.get("H1").get_chambres().size());
        assertEquals(4, hebergements.get("H1").get_services().size());
        assertEquals(2, hebergements.get("H2").get_chambres().size());
        assertEquals(1, hebergements.get("H3").get_services().size());
    }

    @Test
    void scenarioDesReservations() {
        annuaire.addReservation(construireReservation("R1"));   // doit réussir
        annuaire.addReservation(construireReservation("R2"));   // doit réussir
        annuaire.addReservation(construireReservation("R3"));   // chevauche R2, mais 3 DOUBLE : réussit

        // R4 chevauche R1 sur l'unique SUITE : doit échouer
        assertThrows(IllegalStateException.class,
                () -> annuaire.addReservation(construireReservation("R4")));

        annuaire.addReservation(construireReservation("R5"));   // doit réussir

        assertEquals(4, annuaire.get_reservations().size());
    }

    @ParameterizedTest(name = "{0} coûte {1}")
    @CsvSource({
            "R1, 1714.93",   // (219.99 + 15 + 10) x 7 nuits
            "R2, 389.97",    // (119.99 + 10) x 3 nuits
            "R3, 119.99",    // 119.99 x 1 nuit
            "R5, 224.97"     // (74.99 + 0) x 3 nuits
    })
    void prixDesReservations(String id, double prixAttendu) {
        assertEquals(prixAttendu, construireReservation(id).getPrix(), 0.001);
    }

    @Test
    void rechercheExempleRetourneC2() {
        JsonNode c = racine.get("critereRecherche_exemple");

        List<TypeService> requis = new ArrayList<>();
        for (JsonNode s : c.get("servicesRequis")) {
            requis.add(typeService(s.asText()));
        }

        CriteresRecherche criteres = new CriteresRecherche.Builder(
                LocalDateTime.parse(c.get("dateArrivee").asText()),
                LocalDateTime.parse(c.get("dateDepart").asText()))
                .typeHebergement(TypeHebergement.valueOf(c.get("typeHebergement").asText()))
                .region(null, null, c.get("ville").asText(), null, null)
                .typeChambre(TypeChambre.valueOf(c.get("typeChambre").asText()))
                .servicesRequis(requis)
                .prixMax(c.get("prixMax").asDouble())
                .build();

        List<Chambre> resultat = annuaire.rechercherChambresDisponibles(criteres);

        assertEquals(1, resultat.size());
        assertSame(chambres.get("C2"), resultat.get(0));
        assertEquals(119.99, resultat.get(0).get_prix(), 0.001);
    }

    @Test
    void suiteReserveeDisparaitDesResultats() {
        LocalDateTime debut = LocalDateTime.parse("2026-12-22T15:00:00");
        LocalDateTime fin = LocalDateTime.parse("2026-12-24T11:00:00");
        CriteresRecherche criteres = new CriteresRecherche.Builder(debut, fin)
                .typeChambre(TypeChambre.SUITE)
                .build();

        // Avant R1 : les deux suites (C3 à Chicoutimi, C7 à La Baie)
        assertEquals(2, annuaire.rechercherChambresDisponibles(criteres).size());

        annuaire.addReservation(construireReservation("R1"));

        // Après R1 : C3 est prise sur cette période, il reste C7
        List<Chambre> apres = annuaire.rechercherChambresDisponibles(criteres);
        assertEquals(1, apres.size());
        assertSame(chambres.get("C7"), apres.get(0));
    }
}