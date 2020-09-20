package LinuxCommand;


import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer11_OmitComments;
import sun.awt.image.BufferedImageDevice;

import java.io.*;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Echo {
    public static void main(String[] args) throws IOException {
        File f = new File("/home/dawn/java");
        f.createNewFile();
        Scanner in = new Scanner(System.in);
        while (true) {
            String s = in.nextLine();
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            FileOutputStream fos = new FileOutputStream(f);
            if (s.startsWith("echo")) {
                String Com = s.substring(5);
                fos.write(Com.getBytes());
                System.out.println(bfr.readLine());
            } else if (s.startsWith("cat")) {
                String Com = s.substring(4);
                File file = new File(Com);
                BufferedReader bis = new BufferedReader(new FileReader(file));
                String str=null;
                while ((str = bis.readLine()) != null) {
                    System.out.println(str);
                }
            } else {
                System.out.println("InputError");
            }
            bfr.close();
        }
    }
}
