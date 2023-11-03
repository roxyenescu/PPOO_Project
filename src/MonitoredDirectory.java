import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MonitoredDirectory implements DirectoryOperation {
    private List<String> monitoredDirectories;

    public MonitoredDirectory(List<String> monitoredDirectories) {
        this.monitoredDirectories = monitoredDirectories;
    }

    @Override
    public void addMonitoredDirectory(Scanner scanner) {
        System.out.println("Introduceti litera directorului de adaugat:");
        char firstLetter = scanner.next().charAt(0);

        // Verific dacă există deja un director cu aceeași literă
        for (String directory : monitoredDirectories) {
            if (directory.startsWith(firstLetter + ":\\")) {
                System.out.println("Exista deja directorul " + firstLetter + ". Nu puteti adauga un altul.\n");
                System.out.println("Directoarele existente: ");
                Main.loadMonitoredDirectories();
                return;
            }
        }

        String directory = firstLetter + ":\\";
        monitoredDirectories.add(directory);

        System.out.println("Director adaugat cu succes.\n");
        Main.saveMonitoredDirectories();

        System.out.println("Directoarele existente: ");
        Main.loadMonitoredDirectories();
    }

    @Override
    public void removeMonitoredDirectory(Scanner scanner) {
        if (monitoredDirectories.isEmpty()) {
            System.out.println("Nu exista directoare.");
        } else {
            System.out.println("Introduceti litera directorului de sters:");
            char firstLetter = scanner.next().charAt(0);
            scanner.nextLine(); // Consum newline lăsat în urmă

            Set<String> linesToRemove = new HashSet<>(); // performanta mai buna

            // Caut liniile care trebuie eliminate (directoarele și fișierele asociate)
            boolean shouldRemove = false;
            for (String line : monitoredDirectories) {
                if (line.startsWith(String.valueOf(firstLetter))) {
                    shouldRemove = true;
                } else if (line.matches("[A-Z]:.*")) {
                    shouldRemove = false;
                }

                if (shouldRemove) {
                    linesToRemove.add(line);
                }
            }

            if (linesToRemove.isEmpty()) {
                System.out.println("Nu exista directoare pentru litera specificata!\n");
                System.out.println("Directoarele existente: ");
                Main.loadMonitoredDirectories(); // Afisez continutul fisierului actualizat
            } else {
                System.out.println("Sunteti sigur ca doriti sa stergeti acest director? Aceasta actiune este ireversibila (Da/Nu):");
                String confirmare = scanner.nextLine();

                if (confirmare.equalsIgnoreCase("da")) {
                    monitoredDirectories.removeAll(linesToRemove);

                    System.out.println("Stergerea a fost realizata cu succes!\n");

                    Main.saveMonitoredDirectories(); // Salvez modificările în fișier

                    if (monitoredDirectories.isEmpty()) {
                        System.out.println("Nu mai exista alte directoare!");
                    } else {
                        System.out.println("Directoarele existente: ");
                        Main.loadMonitoredDirectories(); // Afisez continutul fisierului actualizat
                    }
                } else if (confirmare.equalsIgnoreCase("nu")) {
                    System.out.println("Stergerea a fost anulata!\n");
                    System.out.println("Directoarele existente: ");
                    Main.loadMonitoredDirectories(); // Afisez continutul fisierului actualizat
                } else {
                    System.out.println("Raspuns invalid. Niciun director nu a fost șters.\n");
                    System.out.println("Directoarele existente: ");
                    Main.loadMonitoredDirectories(); // Afisez continutul fisierului actualizat
                }
            }
        }
    }
}
