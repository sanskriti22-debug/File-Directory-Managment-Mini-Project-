import java.util.Scanner;

// Linked List Node for storing files
class FileNode {
    String fileName;
    FileNode next;

    FileNode(String fileName) {
        this.fileName = fileName;
        this.next = null;
    }
}

// Tree Node for storing folders
class Folder {
    String folderName;
    Folder firstChild;
    Folder nextSibling;
    FileNode files;

    Folder(String folderName) {
        this.folderName = folderName;
        this.firstChild = null;
        this.nextSibling = null;
        this.files = null;
    }
}

public class FileDirectorySystem {
    static Scanner sc = new Scanner(System.in);
    static Folder root = new Folder("Root");

    static void addFolder(Folder parent, String name) {
        Folder newFolder = new Folder(name);

        if (parent.firstChild == null) {
            parent.firstChild = newFolder;
        } else {
            Folder temp = parent.firstChild;
            while (temp.nextSibling != null) {
                temp = temp.nextSibling;
            }
            temp.nextSibling = newFolder;
        }

        System.out.println("Folder added successfully.");
    }

    static void addFile(Folder folder, String fileName) {
        FileNode newFile = new FileNode(fileName);

        if (folder.files == null) {
            folder.files = newFile;
        } else {
            FileNode temp = folder.files;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newFile;
        }

        System.out.println("File added successfully.");
    }

    static void display(Folder folder, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(folder.folderName + "/");

        FileNode file = folder.files;
        while (file != null) {
            for (int i = 0; i < level + 1; i++) {
                System.out.print("  ");
            }
            System.out.println(file.fileName);
            file = file.next;
        }

        Folder child = folder.firstChild;
        while (child != null) {
            display(child, level + 1);
            child = child.nextSibling;
        }
    }

    static Folder findFolder(Folder folder, String name) {
        if (folder.folderName.equals(name)) {
            return folder;
        }

        Folder child = folder.firstChild;
        while (child != null) {
            Folder result = findFolder(child, name);
            if (result != null) {
                return result;
            }
            child = child.nextSibling;
        }

        return null;
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\\n--- FILE DIRECTORY MANAGEMENT SYSTEM ---");
            System.out.println("1. Add Folder");
            System.out.println("2. Add File");
            System.out.println("3. Display Directory");
            System.out.println("4. Search Folder");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter parent folder: ");
                    String parentName = sc.nextLine();
                    Folder parent = findFolder(root, parentName);

                    if (parent != null) {
                        System.out.print("Enter new folder name: ");
                        String folderName = sc.nextLine();
                        addFolder(parent, folderName);
                    } else {
                        System.out.println("Parent folder not found.");
                    }
                    break;

                case 2:
                    System.out.print("Enter folder name: ");
                    String targetFolderName = sc.nextLine();
                    Folder folder = findFolder(root, targetFolderName);

                    if (folder != null) {
                        System.out.print("Enter file name: ");
                        String fileName = sc.nextLine();
                        addFile(folder, fileName);
                    } else {
                        System.out.println("Folder not found.");
                    }
                    break;

                case 3:
                    System.out.println("\\nDirectory Structure:");
                    display(root, 0);
                    break;

                case 4:
                    System.out.print("Enter folder to search: ");
                    String searchName = sc.nextLine();
                    Folder result = findFolder(root, searchName);

                    if (result != null) {
                        System.out.println("Folder found: " + result.folderName);
                    } else {
                        System.out.println("Folder not found.");
                    }
                    break;

                case 5:
                    System.out.println("Program ended.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
