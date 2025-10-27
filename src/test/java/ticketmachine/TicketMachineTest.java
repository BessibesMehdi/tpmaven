package ticketmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketMachineTest {
    private static final int PRICE = 50; // Une constante

    private TicketMachine machine; // l'objet à tester

    @BeforeEach
    public void setUp() {
        machine = new TicketMachine(PRICE); // On initialise l'objet à tester
    }

    @Test
    // On vérifie que le prix affiché correspond au paramètre passé lors de
    // l'initialisation
    // S1 : le prix affiché correspond à l’initialisation.
    void priceIsCorrectlyInitialized() {
        // Paramètres : valeur attendue, valeur effective, message si erreur
        assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
    }

    @Test
    // S2 : la balance change quand on insère de l’argent
    void insertMoneyChangesBalance() {
        // GIVEN : une machine vierge (initialisée dans @BeforeEach)
        // WHEN On insère de l'argent
        machine.insertMoney(10);
        machine.insertMoney(20);
        // THEN La balance est mise à jour, les montants sont correctement additionnés
        assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
    }

    @Test
    // S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
    void doesNotPrintTicketIfInsufficientFunds() {
        machine.insertMoney(10);
        assertFalse(machine.printTicket(), "Le ticket ne doit pas être imprimé si le montant est insuffisant");
    }

    @Test
    // S4 : on imprime le ticket si le montant inséré est suffisant
    void printsTicketIfSufficientFunds() {
        machine.insertMoney(PRICE);
        assertTrue(machine.printTicket(), "Le ticket doit être imprimé si le montant est suffisant");
    }

    @Test
    // S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
    void balanceIsDecrementedAfterPrinting() {
        machine.insertMoney(PRICE + 20);
        machine.printTicket();
        assertEquals(20, machine.getBalance(), "La balance doit être décrémentée du prix du ticket après impression");
    }

    @Test
    // S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
    void collectedAmountUpdatedAfterPrinting() {
        machine.insertMoney(PRICE);
        assertEquals(0, machine.getTotal(), "Le montant collecté ne doit pas être mis à jour avant impression");
        machine.printTicket();
        assertEquals(PRICE, machine.getTotal(), "Le montant collecté doit être mis à jour après impression");
    }

    @Test
    // S7 : refund() rend correctement la monnaie
    void refundReturnsCorrectChange() {
        machine.insertMoney(30);
        assertEquals(30, machine.refund(), "Refund doit rendre la monnaie correctement");
    }

    @Test
    // S8 : refund() remet la balance à zéro
    void refundResetsBalanceToZero() {
        machine.insertMoney(30);
        machine.refund();
        assertEquals(0, machine.getBalance(), "Refund doit remettre la balance à zéro");
    }

    @Test
    // S9 : on ne peut pas insérer un montant négatif
    void cannotInsertNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(-10), "On ne doit pas pouvoir insérer un montant négatif");
    }

    @Test
    // S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
    void cannotCreateMachineWithNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new TicketMachine(-1), "On ne doit pas pouvoir créer une machine avec un prix négatif");
    }

}
