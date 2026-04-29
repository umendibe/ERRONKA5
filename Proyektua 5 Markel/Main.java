
import java.util.Scanner;

public class Main {

    private static Scanner sc;
    private static DBKudeatzailea dbKudeatzailea;

    public static void main(String[] args) {
        boolean login = true;
        boolean menu = true;
        int aukera;

        dbKudeatzailea = new DBKudeatzailea();
        dbKudeatzailea.konektatu();

        sc = new Scanner(System.in);

        while (login) {
            System.out.print("\nSartu erabiltzaile izena: ");
            String izenaSartuta = sc.nextLine();

            System.out.print("Sartu pasahitza: ");
            String pasahitzaSartuta = sc.nextLine();

            String rol = dbKudeatzailea.loginEgin(izenaSartuta, pasahitzaSartuta);

            if (rol != null) {
                System.out.println("\nKredentzial zuzenak! Ongi etorri, " + izenaSartuta + ".");
                System.out.println("Zure rola: " + rol + "\n");
                login = false;

                System.out.println("Zein biltegi kudeatu nahi duzu orain?");
                dbKudeatzailea.biltegiGuztiakErakutsi();
                int biltegiAukera = irakurriZenbakia("Aukeratu (zenbakia): ");

                if ("Admin".equals(rol)) {
                    while (menu) {
                        System.out.println("\n---- Menu Administratzailea ----");
                        System.out.println("1. Donazio bat erregistratu");
                        System.out.println("2. Irteera bat erregistratu");
                        System.out.println("3. Produktu berri bat gehitu (edo stock-a eguneratu)");
                        System.out.println("4. Produktu baten informazioa aldatu");
                        System.out.println("5. Produktu guztiak erakutsi");
                        System.out.println("6. Produktuaren stock-a ezabatu biltegi honetan");
                        System.out.println("7. Irten");
                        aukera = irakurriZenbakia("Aukeratu bat: ");

                        switch (aukera) {
                            case 1:
                                donazioaErregistratu();
                                break;
                            case 2:
                                irteeraErregistratu();
                                break;
                            case 3:
                                produktuBerriBat(biltegiAukera);
                                break;
                            case 4:
                                produktuaAldatu();
                                break;
                            case 5:
                                dbKudeatzailea.produktuGuztiakErakutsi(biltegiAukera);
                                break;
                            case 6:
                                produktuaEzabatu(biltegiAukera);
                                break;
                            case 7:
                                menu = false;
                                System.out.println("Sistematik irteten... Agur!");
                                break;
                            default:
                                System.out.println("ERROREA: Agertzen diren aukeretatik bat aukeratu.");
                                break;
                        }
                    }
                } else if ("Sarrera".equals(rol)) {
                    while (menu) {
                        System.out.println("\n---- Menu Sarrera/Irteera kudeatzailea ----");
                        System.out.println("1. Donazio bat erregistratu");
                        System.out.println("2. Irteera bat erregistratu");
                        System.out.println("3. Irten");
                        aukera = irakurriZenbakia("Aukeratu bat: ");

                        switch (aukera) {
                            case 1:
                                donazioaErregistratu();
                                break;
                            case 2:
                                irteeraErregistratu();
                                break;
                            case 3:
                                menu = false;
                                System.out.println("Sistematik irteten... Agur!");
                                break;
                            default:
                                System.out.println("ERROREA: Agertzen diren aukeretatik bat aukeratu.");
                                break;
                        }
                    }
                } else if ("Produktua".equals(rol)) {
                    while (menu) {
                        System.out.println("\n---- Menu Produktu kudeatzailea ----");
                        System.out.println("1. Produktu berri bat gehitu (edo stock-a eguneratu)");
                        System.out.println("2. Produktu baten informazioa aldatu");
                        System.out.println("3. Produktu guztiak erakutsi");
                        System.out.println("4. Produktuaren stock-a ezabatu biltegi honetan");
                        System.out.println("5. Irten");
                        aukera = irakurriZenbakia("Aukeratu bat: ");

                        switch (aukera) {
                            case 1:
                                produktuBerriBat(biltegiAukera);
                                break;
                            case 2:
                                produktuaAldatu();
                                break;
                            case 3:
                                dbKudeatzailea.produktuGuztiakErakutsi(biltegiAukera);
                                break;
                            case 4:
                                produktuaEzabatu(biltegiAukera);
                                break;
                            case 5:
                                menu = false;
                                System.out.println("Sistematik irteten... Agur!");
                                break;
                            default:
                                System.out.println("ERROREA: Agertzen diren aukeretatik bat aukeratu.");
                                break;
                        }
                    }
                }
            } else {
                System.out.println("\nERROREA: Erabiltzaile izena edo pasahitza ez dira zuzenak. Saiatu berriro.");
            }
        }

        sc.close();
        dbKudeatzailea.deskonektatu();
    }

    public static int irakurriZenbakia(String mezua) {
        int zenbaki = -1;
        boolean ondo = false;
        while (!ondo) {
            System.out.print(mezua);
            String sarrera = sc.nextLine();
            try {
                zenbaki = Integer.parseInt(sarrera);
                ondo = true;
            } catch (NumberFormatException e) {
                System.out.println("Errorea: Zenbaki oso bat sartu behar duzu.");
            }
        }
        return zenbaki;
    }

    public static double irakurriDezimala(String mezua) {
        double zenbaki = -1.0;
        boolean ondo = false;
        while (!ondo) {
            System.out.print(mezua);
            String sarrera = sc.nextLine();
            try {
                zenbaki = Double.parseDouble(sarrera);
                ondo = true;
            } catch (NumberFormatException e) {
                System.out.println("Errorea: Zenbaki bat (dezimala izan daiteke) sartu behar duzu.");
            }
        }
        return zenbaki;
    }

    public static boolean irakurriBoolearra(String mezua) {
        while (true) {
            System.out.print(mezua + " (BAI/EZ): ");
            String sarrera = sc.nextLine().trim().toUpperCase();
            if (sarrera.equals("BAI") || sarrera.equals("B") || sarrera.equals("TRUE")) {
                return true;
            } else if (sarrera.equals("EZ") || sarrera.equals("E") || sarrera.equals("FALSE")) {
                return false;
            } else {
                System.out.println("Errorea: 'BAI' edo 'EZ' erantzun behar duzu.");
            }
        }
    }

    public static String irakurriNan() {
        String nan;
        do {
            System.out.print("Sartu donatzailearen NAN-a (adibidez: 12345678A): ");
            nan = sc.nextLine().toUpperCase().trim();
            if (!nan.matches("^\\d{8}[A-Z]$")) {
                System.out.println("Errorea: NAN formatua ez da zuzena. 8 zenbaki eta letra bat izan behar ditu.");
            }
        } while (!nan.matches("^\\d{8}[A-Z]$"));
        return nan;
    }

    public static String irakurriEdozeinErreferentzia() {
        String erref;
        do {
            System.out.print("Sartu erreferentzia (IR, ER edo EZ + 5 digitu): ");
            erref = sc.nextLine().toUpperCase().trim();
            if (!erref.matches("^(IR|ER|EZ)\\d{5}$")) {
                System.out.println("Errorea: Erreferentziak IR, ER edo EZ aurrizkia eta zehazki 5 zenbaki izan behar ditu.");
            }
        } while (!erref.matches("^(IR|ER|EZ)\\d{5}$"));
        return erref;
    }

    public static java.sql.Date irakurriData(String mezua, boolean hutsikUtziDaiteke) {
        java.sql.Date data = null;
        boolean ondo = false;
        do {
            System.out.print(mezua);
            String sarrera = sc.nextLine().trim();
            if (hutsikUtziDaiteke && sarrera.isEmpty()) {
                ondo = true;
            } else {
                try {
                    data = java.sql.Date.valueOf(sarrera);
                    ondo = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Errorea: Data formatua ez da zuzena. Formatu onartua YYYY-MM-DD da.");
                }
            }
        } while (!ondo);
        return data;
    }

    public static void donazioaErregistratu() {
        System.out.println("\n--- DONAZIOA ERREGISTRATU ---");
        String erref = irakurriEdozeinErreferentzia();
        String nan = irakurriNan();

        boolean donatzaileaExistitzenDa = dbKudeatzailea.egiaztatuDonatzaileaDB(nan);

        if (!donatzaileaExistitzenDa) {
            System.out.println("Donatzaile hori (NAN: " + nan + ") ez dago datu-basean. Mesedez, erregistratu.");
            System.out.print("Sartu donatzailearen izena: ");
            String izena = sc.nextLine();
            System.out.print("Sartu donatzailearen abizena: ");
            String abizena = sc.nextLine();

            dbKudeatzailea.donatzaileaGehituDB(nan, izena, abizena);
        }

        int kantitatea = irakurriZenbakia("Sartu donatutako kantitatea: ");

        java.sql.Date gaurkoData = new java.sql.Date(System.currentTimeMillis());
        dbKudeatzailea.donazioaErregistratuDB(erref, nan, gaurkoData, kantitatea);
    }

    public static void irteeraErregistratu() {
        System.out.println("\n--- IRTEERA ERREGISTRATU ---");
        String erref = irakurriEdozeinErreferentzia();

        System.out.print("Sartu irteeraren helmuga: ");
        String helmuga = sc.nextLine();

        int kantitatea = irakurriZenbakia("Sartu aterako den kantitatea: ");

        java.sql.Date gaurkoData = new java.sql.Date(System.currentTimeMillis());
        dbKudeatzailea.irteeraErregistratuDB(erref, helmuga, gaurkoData, kantitatea);
    }

    public static void produktuaAldatu() {
        System.out.println("\n--- PRODUKTUAREN INFORMAZIOA ALDATU ---");
        int prodId = irakurriZenbakia("Sartu aldatu nahi duzun produktuaren ID-a: ");

        System.out.print("Sartu izen berria: ");
        String izena = sc.nextLine();

        String erref = irakurriEdozeinErreferentzia();

        System.out.print("Sartu fabrikatzaile berria: ");
        String fab = sc.nextLine();

        dbKudeatzailea.produktuaAldatuDB(prodId, izena, erref, fab);
    }

    public static void produktuBerriBat(int biltegiAukera) {
        System.out.println("\n--- PRODUKTUA GEHITU / EGUNERATU ---");
        System.out.print("Izena: ");
        String izena = sc.nextLine();

        int kokapen = irakurriZenbakia("Kokapen kodea (Zenbakia): ");
        int pasilo = irakurriZenbakia("Pasilo zenbakia: ");

        int motaAukera;
        do {
            motaAukera = irakurriZenbakia("Mota (1: Iragankorra, 2: Erdi-Iragankorra, 3: Ez-Iragankorra): ");
            if (motaAukera < 1 || motaAukera > 3) {
                System.out.println("Errorea: Aukeratu mota egoki bat (1-3 arteko zenbakia)");
            }
        } while (motaAukera < 1 || motaAukera > 3);

        String motaString = "";
        String errefRegex = "";
        if (motaAukera == 1) {
            motaString = "Iragankorra";
            errefRegex = "^IR\\d{5}$";
        } else if (motaAukera == 2) {
            motaString = "Erdi-Iragankorra";
            errefRegex = "^ER\\d{5}$";
        } else if (motaAukera == 3) {
            motaString = "Ez-Iragankorra";
            errefRegex = "^EZ\\d{5}$";
        }

        String erref;
        do {
            System.out.print("Erreferentzia (" + errefRegex.substring(1, 3) + " eta 5 digitu, adib. " + errefRegex.substring(1, 3) + "12345): ");
            erref = sc.nextLine().toUpperCase().trim();
            if (!erref.matches(errefRegex)) {
                System.out.println("Errorea: Mota honetako produktu batek " + errefRegex.substring(1, 3) + " hizkiak eta 5 zenbaki izan behar ditu nahitaez.");
            }
        } while (!erref.matches(errefRegex));

        System.out.print("Fabrikatzailea: ");
        String fab = sc.nextLine();

        boolean hoztea = false;
        double hezetasun = 0.0;
        boolean kontserba = false;
        java.sql.Date data = null;

        if (motaAukera == 1) {
            hoztea = irakurriBoolearra("Hoztea behar du?");
            data = irakurriData("Iraungitze data (YYYY-MM-DD) edo utzi hutsik: ", true);
        } else if (motaAukera == 2) {
            hoztea = irakurriBoolearra("Hoztea behar du?");
            hezetasun = irakurriDezimala("Hezetasun maximoa: ");
            data = irakurriData("Iraungitze data (YYYY-MM-DD) edo utzi hutsik: ", true);
        } else if (motaAukera == 3) {
            kontserba = irakurriBoolearra("Kontserba da?");
        }

        int stock = irakurriZenbakia("Hasierako stock-a (ez badago, 0 sartu): ");

        dbKudeatzailea.produktuGehituDB(biltegiAukera, izena, kokapen, pasilo, erref, motaString, fab, hoztea, hezetasun, kontserba, stock, data);
    }

    public static void produktuaEzabatu(int biltegiAukera) {
        System.out.println("\n--- PRODUKTUAREN STOCK-A EZABATU (" + biltegiAukera + ". BILTEGIAN) ---");
        String erref = irakurriEdozeinErreferentzia();

        int unekoStock = dbKudeatzailea.produktuStockBiltegiLortuDB(erref, biltegiAukera);
        boolean aurreraEgin = true;

        if (unekoStock == -1) {
            aurreraEgin = false;
            System.out.println("KONTUZ: Ez da erreferentzia hori duen produktua aurkitu biltegi honetan.");
        } else if (unekoStock > 0) {
            boolean baieztapena = irakurriBoolearra("KONTUZ! Produktu honek " + unekoStock + " unitateko stock TOTALA du biltegi HONETAN. Ziur zaude ezabatu nahi duzula?");
            if (!baieztapena) {
                aurreraEgin = false;
                System.out.println("Ezabatze prozesua bertan behera utzi da.");
            }
        } else {
            boolean baieztapena = irakurriBoolearra("Produktuak ez du stock-ik geratzen biltegi honetan. Ziur erregistroa ezabatu nahi duzula biltegi honetatik?");
            if (!baieztapena) {
                aurreraEgin = false;
            }
        }

        if (aurreraEgin) {
            dbKudeatzailea.produktuaEzabatuDB(erref, biltegiAukera);
        }
    }
}
