import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Folder implements FolderOperation {
    private List<String> monitoredDirectories;

    public Folder(List<String> monitoredDirectories) {
        this.monitoredDirectories = monitoredDirectories;
    }

    @Override
    public void addFolderToDirectory(Scanner scanner) {
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
                            Main.saveMonitoredDirectories();
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

            Main.saveMonitoredDirectories();

            System.out.println("Directoarele existente: ");
            Main.loadMonitoredDirectories();
        }
    }

    @Override
    public void addFolderToFolder(Scanner scanner) {
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


                    if (!hasChildFolder && (nextDirectory == null || (Character.isUpperCase(nextDirectory.charAt(0)) && nextDirectory.charAt(1) == ':'))) {
                        System.out.println("Doriti sa adaugati un nou folder in folderul parinte \"" + parentName + "\"? (Da/Nu)");
                        String response = scanner.nextLine();

                        if (response.equalsIgnoreCase("da")) {
                            System.out.println("Introduceti numele folderului pe care doriti sa-l adaugati:");
                            String folderName = scanner.nextLine();
                            monitoredDirectories.set(i, directory + folderName + "\\");
                            Main.saveMonitoredDirectories();
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
                                Main.saveMonitoredDirectories();
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
        Main.loadMonitoredDirectories();
    }

    @Override
    public void removeFolder(Scanner scanner) {
        if (monitoredDirectories.isEmpty()) {
            System.out.println("Nu exista directoare.");

        } else {

            System.out.println("Introduceti DIRECTORUL in care exista folderul de sters:");
            char firstLetter = scanner.next().charAt(0);
            scanner.nextLine();

            String directoryToAppend = firstLetter + ":\\";

            System.out.println("Introduceti FOLDERUL de sters:");
            String folderName = scanner.nextLine();

            for (int i = 0; i < monitoredDirectories.size(); i++) {
                String directory = monitoredDirectories.get(i);

                if (directory.startsWith(directoryToAppend) && directory.contains(folderName)) {

                    System.out.println("Sigur stergeti folderul \"" + folderName + "\"? (Da/Nu)");
                    String response = scanner.nextLine();

                    if (response.equalsIgnoreCase("da")) {
                        // Obține indexul la care se termină folderul
                        int endIndex = directory.indexOf(folderName);

                        // Extrage calea până la folder
                        String pathBeforeFolder = directory.substring(0, endIndex);

                        // Verifică dacă secvența de director mai există în listă
                        int sequenceExists = 0;
                        boolean hasChildFolder = false;

                        Set<String> linesToRemove = new HashSet<>();

                        for (int k = 0; k < monitoredDirectories.size(); k++) {
                            String line = monitoredDirectories.get(k);

                            if (line.startsWith(pathBeforeFolder) && line.contains(folderName)) {
                                sequenceExists++;

                                String nextDirectory = null;
                                if (k < monitoredDirectories.size() - 1) {
                                    nextDirectory = monitoredDirectories.get(k + 1);
                                }

                                if (!(line.endsWith(folderName + "\\"))) {
                                    hasChildFolder = true;
                                } else {
                                    if (nextDirectory != null && !(Character.isUpperCase(nextDirectory.charAt(0)) && nextDirectory.charAt(1) == ':')) {
                                        sequenceExists++;

                                        for (int j = k + 1; j < monitoredDirectories.size(); j++) {
                                            String currentLine = monitoredDirectories.get(j);

                                            if (currentLine.matches("[A-Z]:.*") || currentLine.startsWith(pathBeforeFolder)) {
                                                // Am găsit o nouă linie care începe cu o literă mare și caracterul ":" sau una care face parte din folderul curent
                                                break;
                                            } else {
                                                linesToRemove.add(currentLine);
                                            }
                                        }


                                    } else if (nextDirectory == null || (Character.isUpperCase(nextDirectory.charAt(0)) && nextDirectory.charAt(1) == ':')) {
                                        if (nextDirectory.startsWith(pathBeforeFolder)) {
                                            sequenceExists++;
                                        }
                                    }
                                }

                                linesToRemove.add(line);
                            }
                        }

                        // Verific dacă există doar aparitia initiala sau mai sunt si alte aparitii ale partitiei
                        if (sequenceExists == 1) {
                            String updatedDirectory = directory.replace(folderName + "\\", "");
                            monitoredDirectories.set(i, updatedDirectory);

                            monitoredDirectories.removeAll(linesToRemove);

                        } else if (sequenceExists >= 2) {
                            if (hasChildFolder == true) {
                                String updatedDirectory = directory.replace(directory, pathBeforeFolder + "");
                                monitoredDirectories.set(i, updatedDirectory);
                            }
                            monitoredDirectories.removeAll(linesToRemove);
                        }

                        Main.saveMonitoredDirectories();
                        System.out.println("Folderul a fost sters.\n");

                    } else if (response.equalsIgnoreCase("nu")) {
                        System.out.println("Stergerea folderului a fost anulata!\n");

                    } else {
                        System.out.println("Raspuns invalid. \n");
                    }


                }

            }
        }

        System.out.println("Directoarele existente: ");
        Main.loadMonitoredDirectories();
    }

    @Override
    public void renameFolder(Scanner scanner) {
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
            BufferedReader reader = new BufferedReader(new FileReader(Main.configFilePath));
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(Main.configFilePath));
            for (int i = 0; i < lineCount; i++) {
                writer.write(lines[i]);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Eroare la salvarea configuratiei.");
        }

        System.out.println("Directoarele existente:");
        Main.loadMonitoredDirectories();
    }
}
