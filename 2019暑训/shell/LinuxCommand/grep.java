package LinuxCommand;

import com.sun.deploy.util.StringUtils;
import com.sun.java.swing.plaf.windows.WindowsDesktopIconUI;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//grep hello /home/dawn/JavaLinuxCommand/src/LinuxCommand/test
public class grep {

    static String command;
    static String Index;
    static String fileName;

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        while (true) {
            command = in.nextLine();
            if (command.startsWith("grep")) {
                int start = command.indexOf(" ");
                command=command.substring(start+1);
                start = command.indexOf(" ");

                Index = command.substring(0,start);
                fileName = command.substring(Index.length()+1);

                BufferedReader bf = new BufferedReader(new FileReader(fileName));
                int lineNumber = 0;
                String str = null;
                Pattern r = Pattern.compile(Index);
                while ((str = bf.readLine()) != null) {
                    lineNumber++;
                    Matcher m = r.matcher(str);
                    if (m.find()) {
                        System.out.println(lineNumber + " " + m.group());
                    }
                }
                bf.close();
            }
        }
    }
}
