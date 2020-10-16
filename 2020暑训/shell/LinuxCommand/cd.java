package LinuxCommand;

import java.io.*;

public class cd {

    static String command;
    static String dirNow;
    static String fileName;

    public static void main(String[] args) throws IOException {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            command = bfr.readLine();
            if (command.startsWith("cd")) {
                dirNow = System.getProperty("user.dir");
                fileName = command.substring(2).replace(" ", "");
                if (fileName.equals("")) {
                    System.out.println("filePathError!");
                } else if (fileName.equals("..")) {
                    usecd();
                } else {
                    dirNow = fileName;
                    usecd();
                }
            } else if (command.startsWith("pwd")) {
                dirNow = System.getProperty("user.dir");
                System.out.println(dirNow);
            }
        }
    }

    public static void usecd() {
        System.out.println("$" + dirNow);
    }

}

