import java.util.Scanner;

/**
 * Interfața DirectoryOperation definește metode pentru gestionarea directoarelor monitorizate, precum adăugarea și ștergerea acestora.
 * Această interfață oferă un set de operații de bază pentru manipularea listei de directoare monitorizate.
 */
public interface DirectoryOperation {
    
    void addMonitoredDirectory(Scanner scanner);
    void removeMonitoredDirectory(Scanner scanner);
}
