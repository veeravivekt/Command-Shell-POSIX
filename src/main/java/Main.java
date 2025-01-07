import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final List<String> BUILTINS = Arrays.asList("echo", "exit", "type");
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

         while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("exit 0")) {
                break;
            } else if (input.startsWith("echo ")) {
                System.out.println(input.substring("echo ".length()));
            } else if (input.startsWith("type ")){
                handleTypeCommand(input.substring(5));

            } else {
                System.out.println(input + ": command not found");
            }
        }
    }
    private static void handleTypeCommand(String command) {
        if (BUILTINS.contains(command)) {
            System.out.println(command + " is a shell builtin");
        } else {
            System.out.println(command + ": not found");
        }
    }
}
