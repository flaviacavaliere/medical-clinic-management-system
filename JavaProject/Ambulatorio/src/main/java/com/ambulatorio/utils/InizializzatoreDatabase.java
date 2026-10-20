package com.ambulatorio.utils;

import com.ambulatorio.database.GestorePersistenza;
import com.ambulatorio.entity.*;
import com.ambulatorio.entity.enums.Ruolo;
import com.ambulatorio.entity.enums.StatoFascia;
import com.ambulatorio.entity.enums.StatoPrenotazione;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InizializzatoreDatabase {

    private final GestorePersistenza gestore;

    public InizializzatoreDatabase(GestorePersistenza gestore) {
        this.gestore = gestore;
    }

    public void popolaDatiDiTest() {

        long numeroMedici = gestore.conta(Medico.class);

        if (numeroMedici > 0) {
            System.out.println("Dati di test già presenti nel database");
            return;
        }

        System.out.println("--- Popolamento dati fittizi ---");

        // 1. Specializzazioni
        Specializzazione cardiologia = new Specializzazione();
        cardiologia.setNome("Cardiologia");
        gestore.salva(cardiologia);

        Specializzazione chirurgia = new Specializzazione();
        chirurgia.setNome("Chirurgia");
        gestore.salva(chirurgia);

        Specializzazione dermatologia = new Specializzazione();
        dermatologia.setNome("Dermatologia");
        gestore.salva(dermatologia);

        // 2. Medici
        Medico medico1 = new Medico();
        medico1.setNome("Mario");
        medico1.setCognome("Rossi");
        medico1.setEmail("mario.rossi@gmail.com");
        medico1.setPasswordHash(PasswordUtils.generaPasswordHash("mario1970"));
        medico1.setNumeroCellulare("3331234567");
        medico1.setSpecializzazione(cardiologia);
        medico1.setRuolo(Ruolo.MEDICO);
        gestore.salva(medico1);

        Medico medico2 = new Medico();
        medico2.setNome("Luigi");
        medico2.setCognome("Bianchi");
        medico2.setEmail("luigi.bianchi@gmail.com");
        medico2.setPasswordHash(PasswordUtils.generaPasswordHash("luigi"));
        medico2.setNumeroCellulare("3331234568");
        medico2.setSpecializzazione(chirurgia);
        medico2.setRuolo(Ruolo.MEDICO);
        gestore.salva(medico2);

        Medico medico3 = new Medico();
        medico3.setNome("Giulia");
        medico3.setCognome("Ferrari");
        medico3.setEmail("giulia.ferrari@gmail.com");
        medico3.setPasswordHash(PasswordUtils.generaPasswordHash("giulia"));
        medico3.setNumeroCellulare("3331234569");
        medico3.setSpecializzazione(dermatologia);
        medico3.setRuolo(Ruolo.MEDICO);
        gestore.salva(medico3);

        // 3. Pazienti
        Paziente paziente1 = new Paziente();
        paziente1.setNome("Anna");
        paziente1.setCognome("Verdi");
        paziente1.setEmail("anna.verdi@gmail.com");
        paziente1.setPasswordHash(PasswordUtils.generaPasswordHash("anna1970"));
        paziente1.setCodiceFiscale("VRDNNA90A01H501Z");
        paziente1.setRuolo(Ruolo.PAZIENTE);
        gestore.salva(paziente1);

        Paziente paziente2 = new Paziente();
        paziente2.setNome("Giuseppe");
        paziente2.setCognome("Neri");
        paziente2.setEmail("giuseppe.neri@gmail.com");
        paziente2.setPasswordHash(PasswordUtils.generaPasswordHash("giuseppe"));
        paziente2.setCodiceFiscale("NREGRP85B02L219X");
        paziente2.setRuolo(Ruolo.PAZIENTE);
        gestore.salva(paziente2);

        Paziente paziente3 = new Paziente();
        paziente3.setNome("Lucia");
        paziente3.setCognome("Marini");
        paziente3.setEmail("lucia.marini@gmail.com");
        paziente3.setPasswordHash(PasswordUtils.generaPasswordHash("lucia"));
        paziente3.setCodiceFiscale("MRNLCU88C41F205Y");
        paziente3.setRuolo(Ruolo.PAZIENTE);
        gestore.salva(paziente3);

        Paziente paziente4 = new Paziente();
        paziente4.setNome("Roberto");
        paziente4.setCognome("Esposito");
        paziente4.setEmail("roberto.esposito@gmail.com");
        paziente4.setPasswordHash(PasswordUtils.generaPasswordHash("roberto"));
        paziente4.setCodiceFiscale("SPTRRT75D10H501W");
        paziente4.setRuolo(Ruolo.PAZIENTE);
        gestore.salva(paziente4);

        // 4. Amministratore
        Amministratore amministratore1 = new Amministratore();
        amministratore1.setNome("Amministratore");
        amministratore1.setCognome("Amministratore");
        amministratore1.setEmail("admin@gmail.com");
        amministratore1.setPasswordHash(PasswordUtils.generaPasswordHash("admin"));
        amministratore1.setRuolo(Ruolo.AMMINISTRATORE);
        gestore.salva(amministratore1);

        // 5. Fasce orarie e prenotazioni con DATE FISSE
        // → Cerca sempre nel range 01/01/2026 - 30/06/2026

        // --- GENNAIO 2026 ---
        creaPrenotazione(gestore,
                medico1, paziente1,
                LocalDate.of(2026, 1, 10), LocalTime.of(9, 0),
                LocalDate.of(2026, 1, 8),
                StatoPrenotazione.EFFETTUATA, StatoFascia.OCCUPATA);

        creaPrenotazione(gestore,
                medico2, paziente2,
                LocalDate.of(2026, 1, 15), LocalTime.of(10, 0),
                LocalDate.of(2026, 1, 13),
                StatoPrenotazione.ANNULLATA, StatoFascia.LIBERA);

        creaPrenotazione(gestore,
                medico3, paziente3,
                LocalDate.of(2026, 1, 20), LocalTime.of(11, 0),
                LocalDate.of(2026, 1, 18),
                StatoPrenotazione.EFFETTUATA, StatoFascia.OCCUPATA);

        // --- FEBBRAIO 2026 ---
        creaPrenotazione(gestore,
                medico1, paziente2,
                LocalDate.of(2026, 2, 5), LocalTime.of(9, 30),
                LocalDate.of(2026, 2, 3),
                StatoPrenotazione.EFFETTUATA, StatoFascia.OCCUPATA);

        creaPrenotazione(gestore,
                medico1, paziente4,
                LocalDate.of(2026, 2, 12), LocalTime.of(14, 0),
                LocalDate.of(2026, 2, 10),
                StatoPrenotazione.NON_PRESENTATO, StatoFascia.OCCUPATA);

        creaPrenotazione(gestore,
                medico2, paziente1,
                LocalDate.of(2026, 2, 20), LocalTime.of(15, 0),
                LocalDate.of(2026, 2, 18),
                StatoPrenotazione.EFFETTUATA, StatoFascia.OCCUPATA);

        // --- MARZO 2026 ---
        creaPrenotazione(gestore,
                medico3, paziente4,
                LocalDate.of(2026, 3, 3), LocalTime.of(8, 30),
                LocalDate.of(2026, 3, 1),
                StatoPrenotazione.EFFETTUATA, StatoFascia.OCCUPATA);

        creaPrenotazione(gestore,
                medico2, paziente3,
                LocalDate.of(2026, 3, 18), LocalTime.of(10, 30),
                LocalDate.of(2026, 3, 15),
                StatoPrenotazione.ANNULLATA, StatoFascia.LIBERA);

        // --- APRILE 2026 ---
        creaPrenotazione(gestore,
                medico1, paziente3,
                LocalDate.of(2026, 4, 7), LocalTime.of(9, 0),
                LocalDate.of(2026, 4, 5),
                StatoPrenotazione.EFFETTUATA, StatoFascia.OCCUPATA);

        creaPrenotazione(gestore,
                medico3, paziente1,
                LocalDate.of(2026, 4, 22), LocalTime.of(11, 30),
                LocalDate.of(2026, 4, 20),
                StatoPrenotazione.EFFETTUATA, StatoFascia.OCCUPATA);

        // --- MAGGIO 2026 ---
        creaPrenotazione(gestore,
                medico2, paziente4,
                LocalDate.of(2026, 5, 8), LocalTime.of(14, 30),
                LocalDate.of(2026, 5, 6),
                StatoPrenotazione.EFFETTUATA, StatoFascia.OCCUPATA);

        creaPrenotazione(gestore,
                medico1, paziente1,
                LocalDate.of(2026, 5, 19), LocalTime.of(10, 0),
                LocalDate.of(2026, 5, 17),
                StatoPrenotazione.ANNULLATA, StatoFascia.LIBERA);

        // --- GIUGNO 2026 (passato + futuro) ---
        creaPrenotazione(gestore,
                medico3, paziente2,
                LocalDate.of(2026, 6, 3), LocalTime.of(9, 0),
                LocalDate.of(2026, 6, 1),
                StatoPrenotazione.EFFETTUATA, StatoFascia.OCCUPATA);

        creaPrenotazione(gestore,
                medico1, paziente4,
                LocalDate.of(2026, 6, 10), LocalTime.of(11, 0),
                LocalDate.of(2026, 6, 8),
                StatoPrenotazione.PRENOTATA, StatoFascia.OCCUPATA);

        // =========================================================================
        // --- GIORNO PRECEDENTE ALL'ESAME: 22 GIUGNO 2026 (STORICO / PASSATO) ---
        // =========================================================================

        creaPrenotazione(gestore,
                medico1, paziente1,
                LocalDate.of(2026, 6, 22), LocalTime.of(14, 30),
                LocalDate.of(2026, 6, 12),
                StatoPrenotazione.EFFETTUATA, StatoFascia.OCCUPATA);

        creaPrenotazione(gestore,
                medico1, paziente2,
                LocalDate.of(2026, 6, 22), LocalTime.of(16, 0),
                LocalDate.of(2026, 6, 14),
                StatoPrenotazione.NON_PRESENTATO, StatoFascia.OCCUPATA);

        // =========================================================================
        // --- GIORNO DELL'ESAME: 23 GIUGNO 2026 (MEDICO: MARIO ROSSI) ---
        // =========================================================================
        creaPrenotazione(gestore,
                medico1, paziente1,
                LocalDate.of(2026, 6, 23), LocalTime.of(9, 0),
                LocalDate.of(2026, 6, 15),
                StatoPrenotazione.PRENOTATA, StatoFascia.OCCUPATA);

        creaPrenotazione(gestore,
                medico1, paziente2,
                LocalDate.of(2026, 6, 23), LocalTime.of(9, 30),
                LocalDate.of(2026, 6, 16),
                StatoPrenotazione.PRENOTATA, StatoFascia.OCCUPATA);

        creaPrenotazione(gestore,
                medico1, paziente3,
                LocalDate.of(2026, 6, 23), LocalTime.of(10, 0),
                LocalDate.of(2026, 6, 17),
                StatoPrenotazione.PRENOTATA, StatoFascia.OCCUPATA);

        creaPrenotazione(gestore,
                medico1, paziente4,
                LocalDate.of(2026, 6, 23), LocalTime.of(10, 30),
                LocalDate.of(2026, 6, 18),
                StatoPrenotazione.PRENOTATA, StatoFascia.OCCUPATA);

        creaPrenotazione(gestore,
                medico1, paziente1, // Il paziente 1 torna per un'altra visita
                LocalDate.of(2026, 6, 23), LocalTime.of(11, 0),
                LocalDate.of(2026, 6, 19),
                StatoPrenotazione.PRENOTATA, StatoFascia.OCCUPATA);

        // =========================================================================
        // --- GIORNO SUCCESSIVO ALL'ESAME: 24 GIUGNO 2026 (FUTURO) ---
        // =========================================================================

        creaPrenotazione(gestore,
                medico1, paziente3,
                LocalDate.of(2026, 6, 24), LocalTime.of(9, 30),
                LocalDate.of(2026, 6, 20),
                StatoPrenotazione.PRENOTATA, StatoFascia.OCCUPATA);

        creaPrenotazione(gestore,
                medico1, paziente4,
                LocalDate.of(2026, 6, 24), LocalTime.of(11, 0),
                LocalDate.of(2026, 6, 20),
                StatoPrenotazione.PRENOTATA, StatoFascia.OCCUPATA);

        // =========================================================================
        // Fascia futura (prenotazione di oggi per domani)
        creaPrenotazione(gestore,
                medico2, paziente3,
                LocalDate.now().plusDays(1), LocalTime.of(10, 0),
                LocalDate.now(),
                StatoPrenotazione.PRENOTATA, StatoFascia.OCCUPATA);

        // Fascia libera (nessuna prenotazione associata)
        FasciaOraria fasciaLibera = new FasciaOraria();
        fasciaLibera.setMedico(medico3);
        fasciaLibera.setData(LocalDate.now().plusDays(2));
        fasciaLibera.setOraInizio(LocalTime.of(15, 0));
        fasciaLibera.setOraFine(LocalTime.of(15, 30));
        fasciaLibera.setStato(StatoFascia.LIBERA);
        gestore.salva(fasciaLibera);

        System.out.println("--- Dati fittizi inseriti con successo! ---");
    }

    /**
     * Aggiunge fasce orarie dimostrative per i prossimi 30 giorni, solo nei giorni
     * dal lunedì al venerdì. Non modifica le fasce esistenti né le prenotazioni
     * (anche se effettuate, annullate o relative a date passate).
     *
     * Le fasce vengono controllate per medico, data e ora di inizio: avviando
     * l'applicazione più volte non vengono create doppie disponibilità.
     *
     * Gli orari qui sotto sono una scelta dimostrativa, non un'agenda reale dei medici.
     */
    public void generaDisponibilitaFuture() {
        LocalDate oggi = LocalDate.now();
        LocalDate inizio = oggi.plusDays(1);
        LocalDate fine = oggi.plusDays(30);

        // Orari dimostrativi di 30 minuti, sia al mattino sia al pomeriggio.
        LocalTime[] orariInizio = {
                LocalTime.of(9, 0),
                LocalTime.of(9, 30),
                LocalTime.of(10, 0),
                LocalTime.of(10, 30),
                LocalTime.of(15, 0),
                LocalTime.of(15, 30)
        };

        List<Medico> medici = gestore.cercaTutti(Medico.class);
        List<FasciaOraria> nuoveFasce = new ArrayList<>();

        for (Medico medico : medici) {
            // Controlla le fasce esistenti, anche quelle occupate o annullate:
            // una fascia già presente non deve essere ricreata con stato LIBERA.
            List<FasciaOraria> fasceEsistenti = gestore.eseguiQueryCustom(
                    FasciaOraria.class,
                    "SELECT f FROM FasciaOraria f " +
                            "WHERE f.medico.id = :idMedico " +
                            "AND f.data BETWEEN :inizio AND :fine",
                    Map.of("idMedico", medico.getId(), "inizio", inizio, "fine", fine)
            );

            Set<LocalDateTime> orariPresenti = new HashSet<>();
            for (FasciaOraria fascia : fasceEsistenti) {
                if (fascia.getData() != null && fascia.getOraInizio() != null) {
                    orariPresenti.add(fascia.getData().atTime(fascia.getOraInizio()));
                }
            }

            for (LocalDate data = inizio; !data.isAfter(fine); data = data.plusDays(1)) {
                if (data.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        data.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    continue;
                }

                for (LocalTime oraInizio : orariInizio) {
                    LocalDateTime chiave = data.atTime(oraInizio);
                    if (!orariPresenti.add(chiave)) {
                        continue;
                    }

                    FasciaOraria fascia = new FasciaOraria();
                    fascia.setMedico(medico);
                    fascia.setData(data);
                    fascia.setOraInizio(oraInizio);
                    fascia.setOraFine(oraInizio.plusMinutes(30));
                    fascia.setStato(StatoFascia.LIBERA);
                    nuoveFasce.add(fascia);
                }
            }
        }

        if (nuoveFasce.isEmpty()) {
            System.out.println("Disponibilità future già presenti: nessuna nuova fascia creata.");
            return;
        }

        if (!gestore.salvaTutti(nuoveFasce.toArray())) {
            throw new IllegalStateException("Errore nel salvataggio delle nuove disponibilità future.");
        }

        System.out.println("Nuove disponibilità future create: " + nuoveFasce.size());
    }

    /**
     * Metodo helper per creare una FasciaOraria e la relativa Prenotazione in un unico passaggio.
     */
    private void creaPrenotazione(GestorePersistenza gestore,
                                  Medico medico, Paziente paziente,
                                  LocalDate dataVisita, LocalTime oraInizio,
                                  LocalDate dataCreazione,
                                  StatoPrenotazione statoPrenotazione,
                                  StatoFascia statoFascia) {

        FasciaOraria fascia = new FasciaOraria();
        fascia.setMedico(medico);
        fascia.setData(dataVisita);
        fascia.setOraInizio(oraInizio);
        fascia.setOraFine(oraInizio.plusMinutes(30));
        fascia.setStato(statoFascia);
        gestore.salva(fascia);

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setPaziente(paziente);
        prenotazione.setFasciaOraria(fascia);
        prenotazione.setDataCreazione(dataCreazione);
        prenotazione.setDataPrenotazione(dataVisita);
        prenotazione.setStato(statoPrenotazione);
        gestore.salva(prenotazione);
    }
}