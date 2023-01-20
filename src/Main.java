//variabele aantal kluizen:
// een kluis heeft een ID en een Wachtwoord van minimaal 4 teken, geen ;
// de wachtwoorden moeten worden opgeslagen in een bestand. fa_kluizen.txt

// als je het programma opstart moet je de volgende opties zien:
// 01. hoeveel kluizen zijn nog vrij /INT
// 02. ik wil een nieuwe kluis /INT
// 03. open mijn kluis. / BOOL
// 04. geef kluis terug. / BOOL
// 05. sluit het programma.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        KluizenBeheer.maakKluizen(12);
        optieMenu();

    }

    static void optieMenu() {
        System.out.println("1: Ik wil weten hoeveel kluizen nog vrij zijn");
        System.out.println("2: Ik wil een nieuwe kluis");
        System.out.println("3: Ik wil even iets uit mijn kluis halen");
        System.out.println("4: Ik geef mijn kluis terug");
        System.out.println("5: Programma afsluiten");
        Scanner option = new Scanner(System.in);
        switch (option.nextInt()) {
            case 1 -> {
                int aantalVrijeKluizen = KluizenBeheer.aantalBeschikbareKluizen();
                System.out.println("Er zijn nog " + aantalVrijeKluizen + " kluizen beschikbaar.");
                optieMenu();
            }
            case 2 -> {
                Kluis nieuweKluis = KluizenBeheer.toewijzenKluis();
                String code = KluizenBeheer.vraagCode();
                assert nieuweKluis != null;
                nieuweKluis.setCode(code);
                KluizenBeheer.saveKluis(nieuweKluis);
                System.out.println("Je hebt kluis nummer " + nieuweKluis.getNummer() + "gekregen.");
                optieMenu();
            }
            case 3 -> {
                int kluisNummer = getKluisNummer();
                KluizenBeheer.openKluis(kluisNummer);
                optieMenu();
            }
            case 4 -> {
                int kluisNumber = getKluisNummer();
                KluizenBeheer.sluitKluis(kluisNumber);
                optieMenu();
            }
            default -> System.exit(0);
        }
    }
    static int getKluisNummer(){
        System.out.println("voer kluis nummer in:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}

