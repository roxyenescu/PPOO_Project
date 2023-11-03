import java.io.*;
import java.util.*;

/**
 * Clasa principală care conține metoda principală (main) și funcționalitatea centrală a aplicației.
 * Această clasă gestionează interacțiunea cu utilizatorul și oferă meniuri pentru adăugarea, ștergerea și redenumirea directoriilor, folderelor și fișierelor.
 */
public class Main {
    static int nrOperations = 0; // daca il fac static este vazut doar in portiunea lui (static il face oarecum variabila globala)

    /**
     * Lista de directoare monitorizate care stochează căile către directoarele, folderele și fișierele monitorizate.
     */
    private static final List<String> monitoredDirectories = new ArrayList<>();

    /**
     * Calea către fișierul de configurare în care sunt stocate directoarele și fișierele monitorizate.
     */
    static final String configFilePath = "C:/Users/Roxy Enescu/IdeaProjects/Tema4_EnescuRoxana/src/directors.txt";

    /**
     * Obiectul care gestionează operațiile legate de directoare (adică adăugare și ștergere de directoare).
     */
    private static MonitoredDirectory monitoredDirectory;

    /**
     * Obiectul care gestionează operațiile legate de foldere (adică adăugare, ștergere și redenumire de foldere).
     */
    private static Folder folder;

    /**
     * Obiectul care gestionează operațiile legate de fișiere (adică adăugare, ștergere și redenumire de fișiere).
     */
    private static File file;

    public static void main(String[] args) throws FileException {
        //int nrOperations = 0;

        loadMonitoredDirectories();
        monitoredDirectory = new MonitoredDirectory(monitoredDirectories);
        folder = new Folder(monitoredDirectories);
        file = new File(monitoredDirectories);

        while (true) {
            System.out.println("\n");
            System.out.println("=== Meniu Principal ===");
            System.out.println("1. Adaugare");
            System.out.println("2. Stergere");
            System.out.println("3. Redenumire");
            System.out.println("4. Iesire");

            System.out.print("Selectati o optiune: ");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleAdaugareMenu(scanner);
                    break;
                case 2:
                    handleStergereMenu(scanner);
                    break;
                case 3:
                    handleRedenumireMenu(scanner);
                    break;
                case 4:
                    System.out.println("Statistica: Numar total de operatii realizate: " + nrOperations);
                    System.out.println("La revedere!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Alegere invalida. Reincercati.");
                    break;
            }
        }

    }



    /**
     * Afișează meniul pentru adăugarea de elemente (director, folder sau fișier) și gestionează selecțiile utilizatorului în cadrul acestui meniu.
     * @param scanner Scanner utilizat pentru citirea input-ului utilizatorului.
     * @throws FileException Aruncă o excepție în cazul unor erori legate de fișiere.
     */
    private static void handleAdaugareMenu(Scanner scanner) throws FileException {
        int option;

        while (true) {
            System.out.println("\n");
            System.out.println("=== Meniu ADAUGARE ===");
            System.out.println("1. Adaugare director");
            System.out.println("2. Adaugare folder");
            System.out.println("3. Adaugare fisier");
            System.out.println("4. Inapoi");

            System.out.print("Selectati o optiune: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    monitoredDirectory.addMonitoredDirectory(scanner);
                    break;
                case 2:
                    addFolder(scanner);
                    break;
                case 3:
                    file.addFileToFolder(scanner);
                    break;
                case 4:
                    return; // Revin la meniul principal
                default:
                    System.out.println("Optiune invalida. Reincercati.");
                    break;
            }
        }
    }



    /**
     * Afișează meniul secundar pentru adăugarea de foldere în directoare existente sau foldere existente și gestionează selecțiile utilizatorului în acest meniu.
     * @param scanner Scanner utilizat pentru citirea input-ului utilizatorului.
     */
    private static void addFolder(Scanner scanner) {
        int option;

        while (true) {
            System.out.println("\n");
            System.out.println("=== Meniu ADAUGARE secundar ===");
            System.out.println("1. Intr-un director deja existent");
            System.out.println("2. Intr-un folder deja existent");
            System.out.println("3. Inapoi");

            System.out.print("Selectati o optiune: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    folder.addFolderToDirectory(scanner);
                    break;
                case 2:
                    folder.addFolderToFolder(scanner);
                    break;
                case 3:
                    System.out.println("Renuntati la adaugarea folderului.");
                    return; // Revin la meniul principal
                default:
                    System.out.println("Optiune invalida. Reincercati.");
            }
        }

    }



    /**
     * Afișează meniul pentru ștergerea de elemente (director, folder sau fișier) și gestionează selecțiile utilizatorului în cadrul acestui meniu.
     * @param scanner Scanner utilizat pentru citirea input-ului utilizatorului.
     * @throws FileException Aruncă o excepție în cazul unor erori legate de fișiere.
     */
    private static void handleStergereMenu(Scanner scanner) throws FileException {
        int option;

        while (true) {
            System.out.println("\n");
            System.out.println("=== Meniu STERGERE ===");
            System.out.println("1. Stergere director");
            System.out.println("2. Stergere folder");
            System.out.println("3. Stergere fisier");
            System.out.println("4. Inapoi");

            System.out.print("Selectati o optiune: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    monitoredDirectory.removeMonitoredDirectory(scanner);
                    break;
                case 2:
                    folder.removeFolder(scanner);
                    break;
                case 3:
                    file.removeFile(scanner);
                    break;
                case 4:
                    return; // Revin la meniul principal
                default:
                    System.out.println("Optiune invalida. Reincercati.");
                    break;
            }
        }
    }



    /**
     * Afișează meniul pentru redenumirea de elemente (folder sau fișier) și gestionează selecțiile utilizatorului în cadrul acestui meniu.
     * @param scanner Scanner utilizat pentru citirea input-ului utilizatorului.
     * @throws FileException Aruncă o excepție în cazul unor erori legate de fișiere.
     */
    private static void handleRedenumireMenu(Scanner scanner) throws FileException {
        int option;

        while (true) {
            System.out.println("\n");
            System.out.println("=== Meniu REDENUMIRE ===");
            System.out.println("1. Redenumire folder");
            System.out.println("2. Redenumire fisier");
            System.out.println("3. Inapoi");

            System.out.print("Selectati o optiune: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    folder.renameFolder(scanner);
                    break;
                case 2:
                    file.renameFile(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Optiune invalida. Reincercati.");
                    break;
            }
        }
    }



    /**
     * Încarcă lista de directoare monitorizate din fișierul de configurare și actualizează lista internă.
     * Această metodă este utilizată pentru a citi configurațiile stocate anterior și a le afișa utilizatorului.
     */
    static void loadMonitoredDirectories() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(configFilePath));
            String line;
            monitoredDirectories.clear(); // Resetez lista pentru a evita duplicarea

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                monitoredDirectories.add(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Eroare la citirea configuratiei. Fisierul nu exista sau nu poate fi citit.");
        }
    }



    /**
     * Salvează lista de directoare monitorizate în fișierul de configurare.
     * Această metodă este utilizată pentru a actualiza configurațiile cu modificările aduse de utilizator.
     */
    static void saveMonitoredDirectories() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(configFilePath));
            for (String directory : monitoredDirectories) {
                writer.write(directory);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Eroare la salvarea configuratiei.");
        }
    }



}
