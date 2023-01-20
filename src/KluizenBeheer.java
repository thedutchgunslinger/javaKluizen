import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class KluizenBeheer {
//    een array met alle kluizen
    private static ArrayList<Kluis> kluizen;

//    vul de array met de aantal opgegeven kluizen
    public static void maakKluizen(int aantal) {
        kluizen = new ArrayList<Kluis>();
        // loop door de aantal kluizen
        for (int i = 1; i <= aantal; i++) {
//            geef iedere kluis een id en een code van null
            kluizen.add(new Kluis(i, null));
        }
        // lees bestand met kluizen
        try {
            FileReader fileReader = new FileReader("kluizen.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
//            blijf het bestand lezen totdat de er geen gevulde regel meer is
            while ((line = bufferedReader.readLine()) != null) {
//              split de regel op de ;
                String[] parts = line.split(";");
//                check of de juiste info erin zit en format de string
                if (parts.length == 2 && parts[0].matches("\\d+") && !parts[1].isEmpty()) {
//                    parse het nummer naar een int
                    int nummer = Integer.parseInt(parts[0]);
//                    sla de code op
                    String code = parts[1];
//                    loop door alle kluizen heen
                    for (Kluis kluis : kluizen) {
//                        zet de code van de kluis op de code van het bestand
                        if (kluis.getNummer() == nummer) {
                            kluis.setCode(code);
                        }
                    }
                }
            }
//           sluit de reader
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



// vraag de code op voor kluis aan de gebruiker
    public static String vraagCode() {
        Scanner scanner = new Scanner(System.in);
        String code = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.print("Voer een code in van minimaal 4 tekens, geen ';': ");
            code = scanner.nextLine();
//            check of de code minimaal 4 characters lang is en geen ; bevat
            if (code.length() >= 4 && !code.contains(";")) {
                isValid = true;
            } else {
                System.out.println("Ongeldige code, probeer het opnieuw.");
            }
        }
            return code;
        }


// wijs een kluis toe
    public static Kluis toewijzenKluis() {
//      loop door de kluizen om te kijken of er nog een vrij is.
        for (Kluis kluis : kluizen) {
            if (kluis.getCode() == null) {
                return kluis;
            }
        }
        return null;
    }

//    sla de kluis nummer op en de code in een bestand
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

// open een kluis
    public static boolean openKluis(int nummer) {
        try {
            FileReader fileReader = new FileReader("kluizen.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
//            parse de nummers en code vanuit het bestand
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(";");
                if(parts.length == 2){
//                    check of het nummer van de kluis overeenkomt met het nummer dat de gebruiker opgevraagd heeft
                    if(Integer.parseInt(parts[0]) == nummer ) {
//                        vraag de gebruiker om de code
                        System.out.println("Vul je kluis code in:");
                        Scanner scan = new Scanner(System.in);
                        String kluisCode = scan.nextLine();
//                        check of de code klopt
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




// sluit een kluis
//    hier heeft chatgpt geholpen met het voorstellen om een tempfile te gebruiken. ik had chatgpt alleen gevraagd
//    hoe ik zo iets moet aanpakken en om geen code te geven zodat ik dat zelf moest doen.
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
//                    zet alle andere kluizen in de tempfile
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
//            rename de temp file naar kluizen
            tempFile.renameTo(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

//check hoeveel vrije kluizen beschikbaar zijn
    public static int aantalBeschikbareKluizen() {
        int aantal = 0;
//      tel alle kluizen op die geen code hebben
        for (Kluis kluis : kluizen) {
            if (kluis.getCode() == null) {
                aantal++;
            }
        }
        return aantal;
    }

}
