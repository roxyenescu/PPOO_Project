import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class File implements FileOperation {
    private List<String> monitoredDirectories;

    public File (List<String> monitoredDirectories) {
        this.monitoredDirectories = monitoredDirectories;
    }

    @Override
    public void addFileToFolder(Scanner scanner) {
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

                            Main.saveMonitoredDirectories();
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

                                Main.saveMonitoredDirectories();

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
            Main.loadMonitoredDirectories();
        }
    }

    @Override
    public void removeFile(Scanner scanner) {
        if (monitoredDirectories.isEmpty()) {
            System.out.println("Nu exista directoare.");

        } else {

            System.out.println("Introduceti DIRECTORUL in care exista fisierul de sters:");
            char firstLetter = scanner.next().charAt(0);
            scanner.nextLine();
            String directoryToAppend = firstLetter + ":\\";

            System.out.println("Introduceti FOLDERUL in care exista fisierul de sters:");
            String parentFolder = scanner.nextLine();

            System.out.println("Introduceti FISIERUL de sters:");
            String fileName = scanner.nextLine();

            for (int i = 0; i < monitoredDirectories.size(); i++) {
                String directory = monitoredDirectories.get(i);

                if (directory.startsWith(directoryToAppend) && directory.endsWith(parentFolder + "\\")) {
                    System.out.println("Sigur stergeti folderul \"" + fileName + "\"? (Da/Nu)");
                    String response = scanner.nextLine();

                    if (response.equalsIgnoreCase("da")) {
                        String nextDirectory = null;
                        if (i < monitoredDirectories.size() - 1) {
                            nextDirectory = monitoredDirectories.get(i + 1);
                        }

                        if (nextDirectory != null && !(Character.isUpperCase(nextDirectory.charAt(0)) && nextDirectory.charAt(1) == ':')) {
                            for (int j = i + 1; j < monitoredDirectories.size(); j++) {
                                String currentLine = monitoredDirectories.get(j);

                                if (currentLine.contains(fileName)) {
                                    monitoredDirectories.remove(currentLine);
                                }
                            }
                        }
                        Main.saveMonitoredDirectories();
                        System.out.println("Fisierul a fost sters cu succes!\n");


                    } else if (response.equalsIgnoreCase("nu")) {
                        System.out.println("Stergerea fisierului a fost anulata!\n");

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
    public void renameFile(Scanner scanner) {
        System.out.println("Introduceti DIRECTORUL in care se afla fisierul de redenumit:");
        char firstLetter = scanner.next().charAt(0);
        scanner.nextLine();

        System.out.println("Introduceti numele FOLDERULUI in care se afla fisierul:");
        String parentFolder = scanner.nextLine();

        System.out.println("Introduceti numele FISIERULUI de redenumit:");
        String oldFileName = scanner.nextLine();

        System.out.println("Introduceti NOUL nume al fisierului:");
        String newFileName = scanner.nextLine();

        List<String> updatedLines = new ArrayList<>();
        boolean fileFound = false;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(Main.configFilePath));
            String line;
            String currentDirectory = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(firstLetter + ":\\") && line.contains("\\" + parentFolder + "\\")) {
                    currentDirectory = line;
                    updatedLines.add(line);
                } else if (currentDirectory != null) {
                    if (line.contains(oldFileName) && (line.endsWith(".mp3") || line.endsWith(".wav") || line.endsWith(".jpg") || line.endsWith(".png"))) {

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

            // Salvez înapoi în fișier
            BufferedWriter writer = new BufferedWriter(new FileWriter(Main.configFilePath));
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
            writer.close();

            if (fileFound) {
                System.out.println("Fisierul a fost redenumit cu succes!");
            } else {
                System.out.println("Fisierul nu a fost gasit sau nu indeplineste conditiile specificate.");
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea sau scrierea în fisier.");
        }

        System.out.println("Directoarele existente: ");
        Main.loadMonitoredDirectories();
    }
}
