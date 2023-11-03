import java.util.Scanner;

/**
 * Interfața FileOperation definește metode pentru gestionarea fișierelor și interacțiunea cu acestea.
 * Această interfață oferă un set de operații de bază pentru manipularea fișierelor în cadrul unui sistem de fișiere.
 */
public interface FileOperation {

    void addFileToFolder(Scanner scanner) throws FileException;
    void removeFile(Scanner scanner) throws FileException;
    void renameFile(Scanner scanner) throws FileException;
}
