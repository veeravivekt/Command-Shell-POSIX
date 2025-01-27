import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    private static final List<String> BUILTINS = Arrays.asList("echo", "exit", "type", "pwd", "cd");
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String pathEnv = System.getenv("PATH");

         while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("exit 0")) {
                break;
            } else if (input.startsWith("echo ")) {
                System.out.println(input.substring("echo ".length()));
            } else if (input.startsWith("type ")){
                handleTypeCommand(input.substring(5), pathEnv);
            } else if (input.startsWith("cd ")){
                handleCdCommand(input.substring(3));
            } else if (input.equals("pwd")){
                handlePwdCommand();
            } else {
                executeExternalCommand(input, pathEnv);
            }
        }
    }
    private static void handleTypeCommand(String command, String pathEnv) {
        if (BUILTINS.contains(command)) {
            System.out.println(command + " is a shell builtin");
        } else {
            String executablePath = findExecutablePath(command, pathEnv);
            if (executablePath != null) {
                System.out.println(command + " is " + executablePath);
            } else {
                System.out.println(command + ": not found");
            }
        }
    }

    private static String findExecutablePath(String command, String pathEnv) {
        String[] directories = pathEnv.split(File.pathSeparator);
        for (String directory : directories) {
            File file = new File(directory, command);
            if (file.isFile() && file.canExecute()) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    private static void executeExternalCommand(String input, String pathEnv) {
        String[] parts = input.split("\\s+");
        String command = parts[0];
        String executablePath = findExecutablePath(command, pathEnv);

        if (executablePath != null) {
            try {
                ProcessBuilder pb = new ProcessBuilder(parts);
                pb.inheritIO();
                Process process = pb.start();
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    System.err.println("Command exited with non-zero status: " + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("Error executing command: " + e.getMessage());
            }
        } else {
            System.out.println(command + ": command not found");
        }
    }
    private static void handlePwdCommand() {
        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);
    }
    private static void handleCdCommand(String path) {
        File newDir = new File(path);
        if (newDir.isAbsolute() && newDir.exists() && newDir.isDirectory()) {
            System.setProperty("user.dir", newDir.getAbsolutePath());
        } else {
            System.out.println("cd: " + path + ": No such file or directory");
        }
    }
}