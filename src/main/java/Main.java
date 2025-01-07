import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

         while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("exit 0")) {
                break;
            } else if (input.startsWith("echo ")) {
                System.out.println(input.substring("echo ".length()));
            } else {
                System.out.println(input + ": command not found");
            }
        }
    }
}
