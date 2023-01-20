import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class KluizenBeheer {
    private static ArrayList<Kluis> kluizen;

    public static void maakKluizen(int aantal) {
        kluizen = new ArrayList<Kluis>();
        // initialiseer een aantal kluizen
        for (int i = 1; i <= aantal; i++) {
            kluizen.add(new Kluis(i, null));
        }
        // lees bestand met kluizen
        try {
            FileReader fileReader = new FileReader("kluizen.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && parts[0].matches("\\d+") && !parts[1].isEmpty()) {
                    int nummer = Integer.parseInt(parts[0]);
                    String code = parts[1];
                    for (Kluis kluis : kluizen) {
                        if (kluis.getNummer() == nummer) {
                            kluis.setCode(code);
                        }
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




                    public static String vraagCode() {
            Scanner scanner = new Scanner(System.in);
            String code = "";
            boolean isValid = false;
            while (!isValid) {
                System.out.print("Voer een code in van minimaal 4 tekens, geen ';': ");
                code = scanner.nextLine();
                if (code.length() >= 4 && !code.contains(";")) {
                    isValid = true;
                } else {
                    System.out.println("Ongeldige code, probeer het opnieuw.");
                }
            }
            return code;
        }



    public static Kluis toewijzenKluis() {
        for (Kluis kluis : kluizen) {
            if (kluis.getCode() == null) {
                return kluis;
            }
        }
        return null;
    }

    public static void saveKluis(Kluis kluis) {
        try {
            FileWriter fileWriter = new FileWriter("kluizen.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(kluis.getNummer() + ";" + kluis.getCode());
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean openKluis(int nummer) {
        try {
            FileReader fileReader = new FileReader("kluizen.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(";");
                if(parts.length == 2){
                    if(Integer.parseInt(parts[0]) == nummer ) {
                        System.out.println("Vul je kluis code in:");
                        Scanner scan = new Scanner(System.in);
                        String kluisCode = scan.nextLine();
                        if(parts[1].equals(kluisCode)){
                            System.out.println("Kluis " + nummer + " is geopend.");
                            bufferedReader.close();
                            return true;
                        }
                    }
                }
            }
            bufferedReader.close();
            System.out.println("Foutieve code of kluisnummer.");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }





    public static boolean sluitKluis(int nummer) {
        try {
            File file = new File("kluizen.txt");
            File tempFile = new File("temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            boolean kluisGevonden = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (Integer.parseInt(parts[0]) == nummer) {
                    System.out.println("Vul je kluis code in:");
                    Scanner scan = new Scanner(System.in);
                    String kluisCode = scan.nextLine();
                    if(parts[1].equals(kluisCode)) {
                        kluisGevonden = true;
                        System.out.println("Kluis " + nummer + " is gesloten.");
                    } } else {
                    writer.write(line + System.getProperty("line.separator"));
                }
            }
            writer.close();
            reader.close();
            if (!kluisGevonden) {
                System.out.println("Foutieve code of kluisnummer.");
                tempFile.delete();
                return false;
            }
            file.delete();
            tempFile.renameTo(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static int aantalBeschikbareKluizen() {
        int aantal = 0;
        for (Kluis kluis : kluizen) {
            if (kluis.getCode() == null) {
                aantal++;
            }
        }
        return aantal;
    }

}
