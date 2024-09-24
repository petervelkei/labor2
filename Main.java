import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Main {
    // wd: az aktuális munkakönyvtár
    private File wd = new File(System.getProperty("user.dir"));
    
    public static void main(String[] args) {
        Main program = new Main();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Adjon meg egy parancsot: ");
            String input = scanner.nextLine();

            // Parancs feldarabolása
            String[] cmd = input.split(" ");
            if (cmd[0].equals("exit")) {
                program.exit(cmd);
            } else if (cmd[0].equals("hello")) {
                program.hello(cmd);
            } else if (cmd[0].equals("pwd")) {
                program.pwd(cmd);
            } else if (cmd[0].equals("ls")) {
                program.ls(cmd);
            } else if (cmd[0].equals("cd")) {
                program.cd(cmd);
            } else if (cmd[0].equals("length")) {
                program.length(cmd);
            } else if (cmd[0].equals("grep")) {
                program.grep(cmd);
            } else if (cmd[0].equals("tail")) {
                program.tail(cmd);
			} else if (cmd[0].equals("sorszuro")) {
				program.sorszuro(cmd);
			} else if (cmd[0].equals("filter")) {
				program.filter(cmd);
			} else if (cmd[0].equals("gzip")) {
				program.gzip(cmd);
            } else {
                System.out.println("Ismeretlen parancs: " + cmd[0]);
            }
        }
    }


    // 1. feladat
    protected void exit(String[] cmd) {
        System.out.println("A program leáll.");
        System.exit(0);
    }

    // 2. feladat
    protected void hello(String[] cmd) {
        System.out.println("Hello world!");
    }


    // 3. feladat
    protected void pwd(String[] cmd) {
        System.out.println("Aktuális könyvtár: " + wd.getAbsolutePath());
    }


    // 4. feladat
    protected void ls(String[] cmd) {
        File[] files = wd.listFiles();
    
        if (files != null) {
            boolean detailed = cmd.length > 1 && cmd[1].equals("-l");
    
            for (File file : files) {
                if (detailed) {
                    String type = file.isDirectory() ? "d" : "f";
                    long size = file.length();
                    System.out.printf("%s %10d %s%n", type, size, file.getName());
                } else {
                    System.out.println(file.getName());
                }
            }
        } else {
            System.out.println("Nincs fájl ebben a könyvtárban.");
        }
    }


    // 5. feladat
    protected void cd(String[] cmd) {
        if (cmd.length < 2) {
            System.out.println("Hiányzó argumentum: cd <dir>");
            return;
        }
    
        if (cmd[1].equals("..")) {
            wd = wd.getParentFile();
        } else {
            File newDir = new File(wd, cmd[1]);
            if (newDir.exists() && newDir.isDirectory()) {
                wd = newDir;
            } else {
                System.out.println("Nem létező könyvtár: " + cmd[1]);
            }
        }
    }


    // (6/A)
    protected void length(String[] cmd) {
        if (cmd.length < 2) {
            System.out.println("Hiányzó argumentum: length <file>");
            return;
        }
        File file = new File(wd, cmd[1]);
        if (file.exists() && file.isFile()) {
            System.out.println("A fájl mérete: " + file.length() + " bájt.");
        } else {
            System.out.println("A fájl nem található: " + cmd[1]);
        }
    }


    // (6/B)
    // kiírja a <file> nevű fájl tartalmából a <pattern>-re illeszkedő sorokat
    protected void grep(String[] cmd) {
        if (cmd.length < 3) {
            System.out.println("Használat: grep <minta> <file>");
            return;
        }
        String pattern = cmd[1];
        File file = new File(wd, cmd[2]);

        if (file.exists() && file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.matches(".*" + pattern + ".*")) {
                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                System.out.println("Hiba történt a fájl olvasása közben: " + e.getMessage());
            }
        } else {
            System.out.println("A fájl nem található: " + cmd[2]);
        }
    }


    // (6/C)
    // kiírja a <file> nevű fájl utolsó <n> sorát
    protected void tail(String[] cmd) {
        if (cmd.length < 2) {
            System.out.println("Használat: tail [-n sorok száma] <file>");
            return;
        }
    
        int n = 10; // alapértelmezett érték
        String fileName;
    
        if (cmd.length >= 4 && cmd[1].equals("-n")) {
            try {
                n = Integer.parseInt(cmd[2]);
            } catch (NumberFormatException e) {
                System.out.println("Érvénytelen sorok száma: " + cmd[2]);
                return;
            }
            fileName = cmd[3];
        } else if (cmd.length == 3 && cmd[1].equals("-n")) {
            try {
                n = Integer.parseInt(cmd[2]);
            } catch (NumberFormatException e) {
                System.out.println("Érvénytelen sorok száma: " + cmd[2]);
                return;
            }
            fileName = cmd[1];
        } else {
            fileName = cmd[1];
        }
    
        File file = new File(wd, fileName);
    
        if (file.exists() && file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                LinkedList<String> lines = new LinkedList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    if (lines.size() == n) {
                        lines.removeFirst();
                    }
                    lines.addLast(line);
                }
                for (String tailLine : lines) {
                    System.out.println(tailLine);
                }
            } catch (IOException e) {
                System.out.println("Hiba történt a fájl olvasása közben: " + e.getMessage());
            }
        } else {
            System.out.println("A fájl nem található: " + fileName);
        }
    }


    // 7. feladat
    // Az alkalmazás a standard bementről olvas sorokat
    // A standard kimenetre kiírja azokat, amelyek egy adott szövegmintának megfelelnek
    protected void sorszuro(String[] cmd) {
        if (cmd.length < 2) {
            System.out.println("Használat: sorszuro <minta>");
            return;
        }
        String pattern = cmd[1];

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.matches(".*" + pattern + ".*")) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Hiba történt az olvasás közben: " + e.getMessage());
        }
    }


    // 8. feladat
    protected void filter(String[] args) {
        String input = null;
        String output = null;
        String pattern = "";

        for (int i = 0; i < args.length; i++) {
            if ((i + 1 < args.length) && args[i].equals("-i")) {
                i++;
                input = args[i];
            } else if ((i + 1 < args.length) && args[i].equals("-o")) {
                i++;
                output = args[i];
            } else if ((i + 1 < args.length) && args[i].equals("-p")) {
                i++;
                pattern = args[i];
            }
        }

        // Szűrés bemeneti és kimeneti fájl alapján
        try {
            BufferedReader reader;
            if (input != null) {
                reader = new BufferedReader(new FileReader(new File(wd, input)));
            } else {
                reader = new BufferedReader(new InputStreamReader(System.in));
            }

            BufferedWriter writer;
            if (output != null) {
                writer = new BufferedWriter(new FileWriter(new File(wd, output)));
            } else {
                writer = new BufferedWriter(new OutputStreamWriter(System.out));
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.matches(".*" + pattern + ".*")) {
                    writer.write(line + "\n");
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("Hiba történt a szűrés közben: " + e.getMessage());
        }
    }


    // 9. feladat
    protected void gzip(String[] cmd) {
        if (cmd.length < 4) {
            System.out.println("Használat: gzip -c|-x <inputFile> <outputFile>");
            return;
        }
        String option = cmd[1];
        File inputFile = new File(wd, cmd[2]);
        File outputFile = new File(wd, cmd[3]);

        if (!inputFile.exists()) {
            System.out.println("A bemeneti fájl nem található: " + inputFile.getName());
            return;
        }

        if (option.equals("-c")) {
            try (FileInputStream fis = new FileInputStream(inputFile);
                 FileOutputStream fos = new FileOutputStream(outputFile);
                 GZIPOutputStream gzipOut = new GZIPOutputStream(fos)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    gzipOut.write(buffer, 0, len);
                }
                System.out.println("A fájl tömörítése sikeres: " + outputFile.getName());
            } catch (IOException e) {
                System.out.println("Hiba történt a tömörítés közben: " + e.getMessage());
            }
        } else if (option.equals("-x")) {
            try (FileInputStream fis = new FileInputStream(inputFile);
                 GZIPInputStream gzipIn = new GZIPInputStream(fis);
                 FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = gzipIn.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                System.out.println("A fájl kicsomagolása sikeres: " + outputFile.getName());
            } catch (IOException e) {
                System.out.println("Hiba történt a kicsomagolás közben: " + e.getMessage());
            }
        } else {
            System.out.println("Ismeretlen opció: " + option);
        }
    }


    // uj fuggveny hozzaadasa
    // egyik van kivalasztva branchkent
    protected void ujFuggveny(String[] cmd) {
        // ...
    }


    // masik branchhez adas
    // uj fuggveny
    protected void ujFuggveny2() {
        System.out.println("Új funkció");
    }


    // uj fuggveny a master branchhez
    protected void ujFuggveny3() {
        System.out.println("Új funkció a master branchhez");
    }

}
