package LinuxCommand;

import sun.awt.image.BufferedImageDevice;

import java.io.*;
import java.nio.Buffer;
import java.util.Scanner;

import static com.sun.deploy.cache.Cache.copyFile;


public class Mkdir {
    static String fileName;
    static String command;
    static String initPath;
    static String aimPath;

// mkdir /home/dawn/JavaLinuxCommand/a/b/c
// cp /home/dawn/JavaLinuxCommand/src/LinuxCommand /home/dawn
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        command = in.nextLine();
        if (command.startsWith("mkdir")) {
            fileName = command.substring(5).replace(" ", "");
            File f = new File(fileName);
            if (!f.exists()) {
                f.mkdirs();
            }
        } else if (command.startsWith("cp")) {
            command = command.substring(2);
            int start = command.indexOf(" ");
            command = command.substring(start + 1);
            start = command.indexOf(" ");

            initPath = command.substring(0, start);
            aimPath = command.substring(initPath.length() + 1);

            File scrFile = new File(initPath);
            File destFile = new File(aimPath);
            copyFoder(scrFile, destFile);
        } else if (command.startsWith("rm")) {
            command = command.substring(2);
            int start = command.indexOf(" ");
            command = command.substring(start + 1);
            start = command.indexOf(" ");
            initPath = command.substring(0, start);

            File filePath = new File(initPath);
            deleteFile(filePath);
        }
    }

    private static void deleteFile(File filePath) {
        if (filePath.isDirectory()) {
            deleteFile(filePath);
        }
        filePath.delete();
    }

    private static void copyFoder(File srcFile, File destFile) throws IOException {
        if (srcFile.isDirectory()) {
            File destFoder = new File(destFile, srcFile.getName());
            if (!destFoder.exists()) {
                destFoder.mkdir();
            }
            File[] listFiles = srcFile.listFiles();
            for (File files : listFiles) {
                copyFoder(files, destFoder);
            }
        } else {
            File aimFile = new File(destFile, srcFile.getName());
            copyFile(srcFile, aimFile);
        }
    }

    public static void copyFile(File srcFile, File aimFile) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(aimFile));

        int len;
        byte[] bys = new byte[1024];
        while ((len = bis.read(bys)) != -1) {
            bos.write(bys, 0, len);
        }

        bis.close();
        bos.close();
    }
}
