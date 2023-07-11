package client.utility;

import java.util.Scanner;

public class Interrogator {  private static Scanner userScanner;
    private static boolean fileMode = false;

    public static Scanner getUserScanner() {
        return userScanner;
    }

    public static void setUserScanner(Scanner userScanner) {
        Interrogator.userScanner = userScanner;
    }

}
