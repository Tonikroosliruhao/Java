package LinuxCommand;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ls {
    static String command;
    static String dirNow;
    static String fileName;

    public static void main(String[] args) throws IOException {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            command = bfr.readLine();
            if (command.startsWith("ls")) {
                String dirName = command.substring(2).replace(" ", "");
                if (dirName.equals(" ")) {
                    File dir = new File(dirNow);
                    usels(dir);
                } else {
                    File dir = new File(dirNow + File.separator + dirName);
                    usels(dir);
                }
            } else if (command.startsWith("pwd")) {
                dirNow = System.getProperty("user.dir");
                System.out.println(dirNow);
            }
        }
    }

    public static void usels(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            String[] dirContents = dir.list();
            for (int i = 0; i < dirContents.length; i++) {
                System.out.println(dirContents[i]);
            }
        } else {
            System.out.println("nullFiles");
        }
    }
}
