package org.example.models;

import org.example.models.enums.TypeChambre;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChambreTest {

    @Test
    void creationValide() {
        Chambre c = new Chambre(TypeChambre.SIMPLE, 50.0, 3);
        assertEquals(TypeChambre.SIMPLE, c.get_type());
        assertEquals(50.0, c.get_prix());
        assertEquals(3, c.get_nombreDisponible());
    }

    @Test
    void prixNulRefuse() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chambre(TypeChambre.SIMPLE, null, 1));
    }

    @Test
    void prixNegatifOuZeroRefuse() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chambre(TypeChambre.SIMPLE, 0.0, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new Chambre(TypeChambre.SIMPLE, -5.0, 1));
    }

    @Test
    void stockNegatifRefuse() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chambre(TypeChambre.SIMPLE, 50.0, -1));
    }

    @Test
    void typeNullRefuse() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chambre(null, 50.0, 1));
    }
}