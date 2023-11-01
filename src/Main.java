import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
    //private static Set<String> monitoredFolderInDirectories = new HashSet<>();
    private static final List<String> monitoredDirectories = new ArrayList<>();
    private static final String configFilePath = "C:/Users/Roxy Enescu/IdeaProjects/Tema4_EnescuRoxana/src/directors.txt";

    public static void main(String[] args) {
        loadMonitoredDirectories();

        while (true) {
            System.out.println("\n");
            System.out.println("=== Meniu Principal ===");
            System.out.println("1. Adaugare");
            System.out.println("2. Stergere");
            System.out.println("3. Mutare");
            System.out.println("4. Redenumire");
            System.out.println("5. Iesire");

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
                    handleMutareMenu(scanner);
                    break;
                case 4:
                    handleRedenumireMenu(scanner);
                    break;
                case 5:
                    System.out.println("La revedere!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Alegere invalida. Reincercati.");
                    break;
            }
        }
    }

    private static void handleAdaugareMenu(Scanner scanner) {
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
                    addMonitoredDirectory(scanner);
                    break;
                case 2:
                    addFolder(scanner);
                    break;
                case 3:
                    addFileToFolder(scanner);
                    break;
                case 4:
                    return; // Revin la meniul principal
                default:
                    System.out.println("Optiune invalida. Reincercati.");
                    break;
            }
        }
    }

    private static void handleStergereMenu(Scanner scanner) {
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
                    removeMonitoredDirectory(scanner);
                    break;
                case 2:
                    removeFolder(scanner);
                    break;
                case 3:
                    removeFile(scanner);
                    break;
                case 4:
                    return; // Revin la meniul principal
                default:
                    System.out.println("Optiune invalida. Reincercati.");
                    break;
            }
        }
    }

    private static void handleMutareMenu(Scanner scanner) {
        int option;

        while (true) {
            System.out.println("\n");
            System.out.println("=== Meniu MUTARE ===");
            System.out.println("1. Mutare folder");
            System.out.println("2. Mutare fisier");
            System.out.println("3. Inapoi");

            System.out.print("Selectati o optiune: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    //implementare
                    break;
                case 2:
                    //implementare
                    break;
                case 3:
                    // Implementare adăugare de fisier
                    break;
                case 4:
                    return; // Revin la meniul principal
                default:
                    System.out.println("Optiune invalida. Reincercati.");
                    break;
            }
        }
    }

    private static void handleRedenumireMenu(Scanner scanner) {
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
                    renameFolder(scanner);
                    break;
                case 2:
                    renameFile(scanner);
                    break;
                case 3:
                    // Implementare adăugare de fisier
                    break;
                case 4:
                    return; // Revin la meniul principal
                default:
                    System.out.println("Optiune invalida. Reincercati.");
                    break;
            }
        }
    }


    private static void loadMonitoredDirectories() {
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


    private static void saveMonitoredDirectories() {
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

    private static void addMonitoredDirectory(Scanner scanner) {
        System.out.println("Introduceti litera directorului de adaugat:");
        char firstLetter = scanner.next().charAt(0);

        // Verific dacă există deja un director cu aceeași literă
        for (String directory : monitoredDirectories) {
            if (directory.startsWith(firstLetter + ":\\")) {
                System.out.println("Exista deja directorul " + firstLetter + ". Nu puteti adauga un altul.\n");
                System.out.println("Directoarele existente: ");
                loadMonitoredDirectories();
                return;
            }
        }

        String directory = firstLetter + ":\\";
        monitoredDirectories.add(directory);

        System.out.println("Director adaugat cu succes.\n");
        saveMonitoredDirectories();

        System.out.println("Directoarele existente: ");
        loadMonitoredDirectories();
    } // TERMINAT

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
                    addFolderToDirectory(scanner);
                    break;
                case 2:
                    addFolderToFolder(scanner);
                    break;
                case 3:
                    System.out.println("Renuntati la adaugarea folderului.");
                    return; // Revin la meniul principal
                default:
                    System.out.println("Optiune invalida. Reincercati.");
            }
        }

    }

    private static void addFolderToDirectory(Scanner scanner) {
        if (monitoredDirectories.isEmpty()) {
            System.out.println("Nu exista directoare.");
        } else {
            System.out.println("Introduceti litera directorului in care doriti sa adaugati folderul:");
            char firstLetter = scanner.next().charAt(0);
            scanner.nextLine();

            String directoryToAppend = firstLetter + ":\\";
            boolean directoryExists = false;

            for (int i = 0; i < monitoredDirectories.size(); i++) {
                String directory = monitoredDirectories.get(i);
                // Verific daca nu contine alte foldere deja
                if (directory.startsWith(directoryToAppend) && directory.length() == directoryToAppend.length()) {
                    directoryExists = true; // Directorul există în fișier
                    System.out.println("Introduceti numele folderului pe care doriti sa-l adaugati:");
                    String folderName = scanner.nextLine();
                    monitoredDirectories.set(i, directory + folderName + "\\");
                    break;

                } else if (directory.startsWith(directoryToAppend) && directory.length() > directoryToAppend.length()) {
                    directoryExists = true;
                    System.out.println("Exista deja un folder in acest director. Doriti sa adaugati un altul? (Da/Nu)");
                    String response = scanner.nextLine();

                    if (response.equalsIgnoreCase("da")) {
                        System.out.println("Introduceti numele folderului pe care doriti sa-l adaugati:");
                        String folderName = scanner.nextLine();

                        if (!(directory.startsWith(firstLetter + ":\\" + folderName + "\\"))) {
                            monitoredDirectories.add(i, firstLetter + ":\\" + folderName + "\\");
                            saveMonitoredDirectories();
                            System.out.println("Folder adaugat cu succes.\n");
                        } else {
                            System.out.println("Exista deja un folder cu aceeasi denumire!\n");
                        }


                    } else if (response.equalsIgnoreCase("nu")) {
                        System.out.println("Adaugarea folderului a fost anulata!\n");

                    } else {
                        System.out.println("Raspuns invalid. \n");
                    }
                    break;
                }
            }

            if (!directoryExists) {
                System.out.println("Nu exista directorul introdus. \n");
            }

            saveMonitoredDirectories();

            System.out.println("Directoarele existente: ");
            loadMonitoredDirectories();
        }
    } // TERMINAT

    private static void addFolderToFolder(Scanner scanner) {
        if (monitoredDirectories.isEmpty()) {
            System.out.println("Nu exista directoare.");
        } else {
            System.out.println("Introduceti litera directorului in care doriti sa adaugati noul folder:");
            char firstLetter = scanner.next().charAt(0);
            scanner.nextLine();

            String directoryToAppend = firstLetter + ":\\";

            System.out.println("Introduceti numele folderului parinte:");
            String parentName = scanner.nextLine();

            for (int i = 0; i < monitoredDirectories.size(); i++) {
                String directory = monitoredDirectories.get(i);

                if (directory.startsWith(directoryToAppend) && directory.contains(parentName)) {

                    boolean hasChildFolder = false;

                    // Verific daca linia mai are ceva dupa folderul parinte
                    if (!(directory.endsWith(parentName + "\\"))) {
                        hasChildFolder = true;
                    }

                    String nextDirectory = null;
                    if (i < monitoredDirectories.size() - 1) {
                        nextDirectory = monitoredDirectories.get(i + 1);
                    }

                    System.out.println(hasChildFolder);


                    if (!hasChildFolder && (nextDirectory == null || (Character.isUpperCase(nextDirectory.charAt(0)) && nextDirectory.charAt(1) == ':'))) {
                        System.out.println("Doriti sa adaugati un nou folder in folderul parinte \"" + parentName + "\"? (Da/Nu)");
                        String response = scanner.nextLine();

                        if (response.equalsIgnoreCase("da")) {
                            System.out.println("Introduceti numele folderului pe care doriti sa-l adaugati:");
                            String folderName = scanner.nextLine();
                            monitoredDirectories.set(i, directory + folderName + "\\");
                            saveMonitoredDirectories();
                            System.out.println("Folder adaugat cu succes in folderul parinte existent \"" + parentName + "\".\n");
                        } else if (response.equalsIgnoreCase("nu")) {
                            System.out.println("Adaugarea folderului a fost anulata!\n");
                        } else {
                            System.out.println("Raspuns invalid. \n");
                        }

                    } else if (hasChildFolder || (nextDirectory != null && !(Character.isUpperCase(nextDirectory.charAt(0)) && nextDirectory.charAt(1) == ':'))) {
                        System.out.println("Doriti sa creati un nou folder in folderul parinte \"" + parentName + "\"? (Da/Nu)");
                        String response = scanner.nextLine();

                        if (response.equalsIgnoreCase("da")) {
                            System.out.println("Introduceti numele folderului pe care doriti sa-l adaugati:");
                            String folderName = scanner.nextLine();

                            // Extrag sirul de dinaintea folderului deja existent in folderul parinte si adaug folderul cel nou
                            String searchTerm = parentName + "\\";
                            int startIndex = directory.indexOf(searchTerm);
                            if (startIndex != -1) {
                                String newDirectory = directory.substring(0, startIndex + searchTerm.length()) + folderName + "\\";
                                monitoredDirectories.add(i, newDirectory);
                                saveMonitoredDirectories();
                                System.out.println("Folderul \"" + folderName + "\" a fost creat cu succes.\n");
                            } else {
                                System.out.println("Folderul \"" + parentName + "\" nu a fost gasit.\n");
                            }
                        } else if (response.equalsIgnoreCase("nu")) {
                            System.out.println("Adaugarea folderului a fost anulata!\n");
                        } else {
                            System.out.println("Raspuns invalid. \n");
                        }
                        break;
                    }

                }

            }
        }


        System.out.println("Directoarele existente: ");
        loadMonitoredDirectories();
    } // TERMINAT

    private static void addFileToFolder(Scanner scanner) {

        if (monitoredDirectories.isEmpty()) {
            System.out.println("Nu exista directoare.");
        } else {
            System.out.println("Introduceti litera directorului in care doriti sa adaugati noul fisier:");
            char firstLetter = scanner.next().charAt(0);
            scanner.nextLine();

            String directoryToAppend = firstLetter + ":\\";

            System.out.println("Introduceti numele folderului parinte:");
            String parentName = scanner.nextLine();

            for (int i = 0; i < monitoredDirectories.size(); i++) {
                String directory = monitoredDirectories.get(i);


                if (directory.startsWith(directoryToAppend) && directory.contains(parentName)) {

                    boolean hasChildFolder = false;

                    // Verific daca linia mai are ceva dupa folderul parinte
                    if (!(directory.endsWith(parentName + "\\"))) {
                        hasChildFolder = true;
                    }

                    if (!hasChildFolder) {

                        System.out.println("Doriti sa adaugati un nou fisier in folderul parinte \"" + parentName + "\"? (Da/Nu)");
                        String response = scanner.nextLine();

                        if (response.equalsIgnoreCase("da")) {
                            System.out.println("Introduceti numele noului fisier:");
                            String fileName = scanner.nextLine();

                            System.out.println("Extensii posibile pentru fisiere: mp3, wav, jpg, png");
                            System.out.println("Introduceti extensia potrivita fisierului: ");
                            String extension = scanner.nextLine();

                            if (extension.equals("mp3") || extension.equals("wav") || extension.equals("jpg") || extension.equals("png")) {
                                String newFile = fileName + "." + extension;

                                monitoredDirectories.add(i + 1, newFile);
                            }

                            saveMonitoredDirectories();
                            System.out.println("Fisierul \"" + fileName + "\" a fost creat cu succes.\n");

                        } else if (response.equalsIgnoreCase("nu")) {
                            System.out.println("Adaugarea fisierului a fost anulata!\n");
                        } else {
                            System.out.println("Raspuns invalid. \n");
                        }

                    } else {
                        System.out.println("Doriti sa adaugati un nou fisier in folderul \"" + parentName + "\"? (Da/Nu)");
                        String response = scanner.nextLine();


                        if (response.equalsIgnoreCase("da")) {
                            System.out.println("Introduceti numele noului fisier:");
                            String fileName = scanner.nextLine();

                            // Extrag sirul de dinaintea folderului deja existent in folderul parinte si adaug fisierul cel nou
                            String searchTerm = parentName + "\\";
                            int startIndex = directory.indexOf(searchTerm);
                            if (startIndex != -1) {
                                String newDirectory = directory.substring(0, startIndex + searchTerm.length());

                                System.out.println("Extensii posibile pentru fisiere: mp3, wav, jpg, png");
                                System.out.println("Introduceti extensia potrivita fisierului: ");
                                String extension = scanner.nextLine();

                                if (extension.equals("mp3") || extension.equals("wav") || extension.equals("jpg") || extension.equals("png")) {
                                    monitoredDirectories.add(i, newDirectory);

                                    String newFile = fileName + "." + extension;
                                    monitoredDirectories.add(i + 1, newFile);
                                }

                                saveMonitoredDirectories();

                                System.out.println("Fisierul \"" + fileName + "\" a fost creat cu succes.\n");
                            } else {
                                System.out.println("Folderul \"" + parentName + "\" nu a fost gasit.\n");
                            }


                        } else if (response.equalsIgnoreCase("nu")) {
                            System.out.println("Adaugarea fisierului a fost anulata!\n");
                        } else {
                            System.out.println("Raspuns invalid. \n");
                        }
                        break;
                    }


                }
            }

            System.out.println("Directoarele existente: ");
            loadMonitoredDirectories();
        }

    } // facut la facultate, de verificat acasa

    private static void removeMonitoredDirectory(Scanner scanner) {
        if (monitoredDirectories.isEmpty()) {
            System.out.println("Nu exista directoare.");
        } else {
            System.out.println("Introduceti litera directorului de sters:");
            char firstLetter = scanner.next().charAt(0);
            scanner.nextLine(); // Consum newline lăsat în urmă

            List<String> linesToRemove = new ArrayList<>();

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
                loadMonitoredDirectories(); // Afisez continutul fisierului actualizat
            } else {
                System.out.println("Sunteti sigur ca doriti sa stergeti acest director? Aceasta actiune este ireversibila (Da/Nu):");
                String confirmare = scanner.nextLine();

                if (confirmare.equalsIgnoreCase("da")) {
                    monitoredDirectories.removeAll(linesToRemove);

                    System.out.println("Stergerea a fost realizata cu succes!\n");

                    saveMonitoredDirectories(); // Salvez modificările în fișier

                    if (monitoredDirectories.isEmpty()) {
                        System.out.println("Nu mai exista alte directoare!");
                    } else {
                        System.out.println("Directoarele existente: ");
                        loadMonitoredDirectories(); // Afisez continutul fisierului actualizat
                    }
                } else if (confirmare.equalsIgnoreCase("nu")) {
                    System.out.println("Stergerea a fost anulata!\n");
                    System.out.println("Directoarele existente: ");
                    loadMonitoredDirectories(); // Afisez continutul fisierului actualizat
                } else {
                    System.out.println("Raspuns invalid. Niciun director nu a fost șters.\n");
                    System.out.println("Directoarele existente: ");
                    loadMonitoredDirectories(); // Afisez continutul fisierului actualizat
                }
            }
        }
    } // TERMINAT

    private static void removeFolder(Scanner scanner) {

    } // nefacut

    private static void removeFile(Scanner scanner) {

    } // nefacut


    private static void renameFolder(Scanner scanner) { // folosesc un vector
        System.out.println("Introduceti litera directorului in care se afla folderul de redenumit:");
        char firstLetter = scanner.next().charAt(0);
        scanner.nextLine();

        System.out.println("Introduceti numele folderului pe care doriti sa-l redenumiti:");
        String oldFolderName = scanner.nextLine();

        System.out.println("Introduceti noul nume pentru folder:");
        String newFolderName = scanner.nextLine();

        String[] lines = new String[1000]; // Definesc dimensiunea 1000 a vectorului

        int lineCount = 0; // Urmăresc numărul de linii citite
        boolean folderFound = false; // Variabilă pentru a urmări dacă folderul a fost găsit

        try {
            BufferedReader reader = new BufferedReader(new FileReader(configFilePath));
            String line;

            while ((line = reader.readLine()) != null) {
                // Verific dacă linia conține folderul de redenumit
                if (line.startsWith(firstLetter + ":\\") && line.contains("\\" + oldFolderName + "\\")) {
                    // Redenumesc folderul dorit în cale
                    line = line.replace("\\" + oldFolderName + "\\", "\\" + newFolderName + "\\");

                    folderFound = true;
                }
                lines[lineCount] = line;
                lineCount++;
            }

            reader.close();

            // throw Exceptie()
        } catch (IOException e) { // catch(Exceptie e)
            System.out.println("Eroare la citirea configuratiei."); // sysout e.mesaj
        }

        if (!folderFound) {
            System.out.println("Folderul nu exista in director!\n");
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(configFilePath));
            for (int i = 0; i < lineCount; i++) {
                writer.write(lines[i]);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Eroare la salvarea configuratiei.");
        }

        System.out.println("Directoarele existente:");
        loadMonitoredDirectories();
    } // TERMINAT - contine exemplu exceptie



    private static void renameFile(Scanner scanner) {
        System.out.println("Introduceți DIRECTORUL în care se află fișierul de redenumit:");
        char firstLetter = scanner.next().charAt(0);
        scanner.nextLine();

        System.out.println("Introduceți numele folderului în care se află fișierul:");
        String parentFolder = scanner.nextLine();

        System.out.println("Introduceți numele fișierului de redenumit (cu extensia .mp3, .jpg sau .png):");
        String oldFileName = scanner.nextLine();

        System.out.println("Introduceți noul nume al fișierului (cu extensia .mp3, .jpg sau .png):");
        String newFileName = scanner.nextLine();

        List<String> updatedLines = new ArrayList<>();
        boolean fileFound = false;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(configFilePath));
            String line;
            String currentDirectory = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(firstLetter + ":\\") && line.contains("\\" + parentFolder + "\\")) {
                    currentDirectory = line;
                    updatedLines.add(line);
                } else if (currentDirectory != null) {
                    if (line.endsWith(oldFileName) && (oldFileName.endsWith(".mp3") || oldFileName.endsWith(".jpg") || oldFileName.endsWith(".png"))) {
                        String updatedLine = line.replace(oldFileName, newFileName);
                        updatedLines.add(updatedLine);
                        fileFound = true;
                    } else {
                        updatedLines.add(line);
                    }
                } else {
                    updatedLines.add(line);
                }
            }
            reader.close();

            // Salvăm înapoi în fișier
            BufferedWriter writer = new BufferedWriter(new FileWriter(configFilePath));
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
            writer.close();

            if (fileFound) {
                System.out.println("Fișierul a fost redenumit cu succes!");
            } else {
                System.out.println("Fișierul nu a fost găsit în configurație sau nu îndeplinește condițiile specificate.");
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea sau scrierea în fișier.");
        }
    } // functioneaza cum trebuie dar trebuie sa ma mai uit cum fac cu terminatiile .mp3 etc




}
