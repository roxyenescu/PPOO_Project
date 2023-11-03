import java.util.Scanner;

/**
 * Interfața FolderOperation definește metode pentru gestionarea folderelor și interacțiunea cu acestea.
 * Această interfață oferă un set de operații de bază pentru manipularea folderelor în cadrul unui sistem de fișiere.
 */
public interface FolderOperation {

    void addFolderToDirectory(Scanner scanner);
    void addFolderToFolder(Scanner scanner);
    void removeFolder(Scanner scanner);
    void renameFolder(Scanner scanner);

}
