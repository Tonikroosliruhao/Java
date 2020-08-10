---
title: Java实现简易shell
date: 2020-08-10 16:30:29
tags: [Linux,Java]
categories: Linux
---

当前目录：`/home/dawn/JavaLinuxCommand/src/LinuxCommand`

`cd`与`ls`

```java
import java.io.*;

public class Cdls {

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
            } else if (command.startsWith("ls")) {
                String dirName=command.substring(2).replace(" ","");
                if(dirName.equals(" ")){
                    File dir=new File(dirNow);
                    usels(dir);
                }else {
                    File dir = new File(dirNow + File.separator + dirName);
                    usels(dir);
                }
            }
        }
    }

    public static void usecd() {
        System.out.println("$" + dirNow);
    }

    public static void usels(File dir){
        File[] files=dir.listFiles();
        if(files!=null){
            String[] dirContents=dir.list();
            for(int i=0;i<dirContents.length;i++){
                System.out.println(dirContents[i]);
    }     
```

`grep`

```java
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
            }
        }
    }
}

```

`cat`、`echo`

```java
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
```



`mkdir`、`rm`、`cp`

```java
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
```

