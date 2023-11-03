import java.util.Scanner;

public interface FileOperation {

    void addFileToFolder(Scanner scanner) throws FileException;
    void removeFile(Scanner scanner) throws FileException;
    void renameFile(Scanner scanner) throws FileException;
}
