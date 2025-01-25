import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final List<String> BUILTINS = Arrays.asList("echo", "exit", "type");
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
            } else {
                System.out.println(input + ": command not found");
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
}