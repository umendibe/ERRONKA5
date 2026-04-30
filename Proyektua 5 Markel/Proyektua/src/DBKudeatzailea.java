
import java.sql.*;

public class DBKudeatzailea {

    private Connection konexioa;

    public void konektatu() {
        try {
            konexioa = DriverManager.getConnection("jdbc:mysql://localhost:3306/erronkamarkel", "root", "Mendikp_2007");
        } catch (SQLException e) {
            System.out.println("Errorea datu-basera konektatzean: " + e.getMessage());
        }
    }

    public void deskonektatu() {
        try {
            if (konexioa != null && !konexioa.isClosed()) {
                konexioa.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String loginEgin(String izena, String pasahitza) {
        String rol = null;
        String call = "{CALL sp_login_egiaztatu(?, ?, ?)}";
        try (CallableStatement stmt = konexioa.prepareCall(call)) {
            stmt.setString(1, izena);
            stmt.setString(2, pasahitza);
            stmt.registerOutParameter(3, Types.VARCHAR);
            stmt.execute();
            rol = stmt.getString(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rol;
    }

    public void produktuGehituDB(int biltegiKod, String izena, int kokapen, int pasilo, String erref, String mota, String fab, boolean hoztea, double heze, boolean kontserba, int stock, java.sql.Date iraungitze) {
        String call = "{CALL sp_produktu_gehitu(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = konexioa.prepareCall(call)) {
            stmt.setInt(1, biltegiKod);
            stmt.setString(2, izena);
            stmt.setInt(3, kokapen);
            stmt.setInt(4, pasilo);
            stmt.setString(5, erref);
            stmt.setString(6, mota);
            stmt.setString(7, fab);
            stmt.setBoolean(8, hoztea);
            stmt.setDouble(9, heze);
            stmt.setBoolean(10, kontserba);

            if (stock > 0) {
                stmt.setInt(11, stock);
            } else {
                stmt.setNull(11, Types.INTEGER);
            }
            stmt.setDate(12, iraungitze);

            stmt.execute();
            System.out.println("\nProduktua (edo bere stock-a) arrakastaz gehitu/eguneratu da " + biltegiKod + " biltegian!");
        } catch (SQLException e) {
            System.out.println("Errorea produktua gehitzean: " + e.getMessage());
        }
    }

    public int produktuStockBiltegiLortuDB(String erref, int biltegiKod) {
        int stock = -1;
        String call = "{CALL sp_produktu_stock_biltegi_lortu(?, ?, ?)}";
        try (CallableStatement stmt = konexioa.prepareCall(call)) {
            stmt.setString(1, erref);
            stmt.setInt(2, biltegiKod);
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.execute();
            stock = stmt.getInt(3);
        } catch (SQLException e) {
            System.out.println("Errorea biltegiko stock-a lortzean: " + e.getMessage());
        }
        return stock;
    }

    public void produktuaEzabatuDB(String erref, int biltegiKod) {
        String call = "{CALL sp_produktu_ezabatu(?, ?)}";
        try (CallableStatement stmt = konexioa.prepareCall(call)) {
            stmt.setString(1, erref);
            stmt.setInt(2, biltegiKod);
            stmt.execute();
            System.out.println("Produktuaren stock guztiak arrakastaz ezabatu dira " + biltegiKod + " biltegitik!");
        } catch (SQLException e) {
            System.out.println("Errorea produktua ezabatzean: " + e.getMessage());
        }
    }

    public boolean egiaztatuDonatzaileaDB(String nan) {
        boolean existitzenDa = false;
        String call = "{CALL sp_egiaztatu_donatzailea(?, ?)}";
        try (CallableStatement stmt = konexioa.prepareCall(call)) {
            stmt.setString(1, nan);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.execute();
            if (stmt.getInt(2) > 0) {
                existitzenDa = true;
            }
        } catch (SQLException e) {
            System.out.println("Errorea donatzailea egiaztatzean: " + e.getMessage());
        }
        return existitzenDa;
    }

    public void donatzaileaGehituDB(String nan, String izena, String abizena) {
        String call = "{CALL sp_donatzailea_gehitu(?, ?, ?)}";
        try (CallableStatement stmt = konexioa.prepareCall(call)) {
            stmt.setString(1, nan);
            stmt.setString(2, izena);
            stmt.setString(3, abizena);
            stmt.execute();
            System.out.println("Donatzaile berria arrakastaz erregistratu da!");
        } catch (SQLException e) {
            System.out.println("Errorea donatzailea gehitzean: " + e.getMessage());
        }
    }

    public void donazioaErregistratuDB(String erref, String nan, java.sql.Date data, int kantitatea) {
        String call = "{CALL sp_donazioa_erregistratu(?, ?, ?, ?)}";
        try (CallableStatement stmt = konexioa.prepareCall(call)) {
            stmt.setString(1, erref);
            stmt.setString(2, nan);
            stmt.setDate(3, data);
            stmt.setInt(4, kantitatea);
            stmt.execute();
            System.out.println("Donazioa arrakastaz erregistratu da eta stock-a eguneratu da!");
        } catch (SQLException e) {
            if (e.getSQLState().equals("45000")) {
                System.out.println(e.getMessage());
            } else {
                System.out.println("Errorea donazioa erregistratzean: " + e.getMessage());
            }
        }
    }

    public void irteeraErregistratuDB(String erref, String helmuga, java.sql.Date data, int kantitatea) {
        String call = "{CALL sp_irteera_erregistratu(?, ?, ?, ?)}";
        try (CallableStatement stmt = konexioa.prepareCall(call)) {
            stmt.setString(1, erref);
            stmt.setString(2, helmuga);
            stmt.setDate(3, data);
            stmt.setInt(4, kantitatea);
            stmt.execute();
            System.out.println("Irteera arrakastaz erregistratu da eta stock-a eguneratu da!");
        } catch (SQLException e) {
            if (e.getSQLState().equals("45000")) {
                System.out.println(e.getMessage());
            } else {
                System.out.println("Errorea irteera erregistratzean: " + e.getMessage());
            }
        }
    }

    public void produktuaAldatuDB(int id, String izena, String erref, String fab) {
        String call = "{CALL sp_produktu_aldatu(?, ?, ?, ?)}";
        try (CallableStatement stmt = konexioa.prepareCall(call)) {
            stmt.setInt(1, id);
            stmt.setString(2, izena);
            stmt.setString(3, erref);
            stmt.setString(4, fab);
            stmt.execute();
            System.out.println("Produktuaren informazioa arrakastaz eguneratu da!");
        } catch (SQLException e) {
            System.out.println("Errorea produktua aldatzean: " + e.getMessage());
        }
    }

    public void produktuGuztiakErakutsi(int biltegiKod) {
        String sql = "SELECT * FROM bista_produktu_guztiak WHERE biltegi_kod = ?";

        try (PreparedStatement stmt = konexioa.prepareStatement(sql)) {
            stmt.setInt(1, biltegiKod);

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n--- " + biltegiKod + " BILTEGIKO PRODUKTU GUZTIAK ---");
                System.out.printf("%-5s | %-15s | %-15s | %-17s | %-15s | %-12s | %-7s | %-12s\n",
                        "ID", "Izena", "Erreferentzia", "Mota", "Fabrikatzailea", "Kokapena", "Stock", "Iraungitzea");
                System.out.println("------------------------------------------------------------------------------------------------------------------");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String izena = rs.getString("izena");
                    String erreferentzia = rs.getString("erreferentzia");
                    String mota = rs.getString("mota");
                    String fabrikatzailea = rs.getString("fabrikatzailea");
                    int pasilo = rs.getInt("pasilo_zenb");
                    int kokapen = rs.getInt("kokapen_kod");
                    int stock = rs.getInt("stock_totala");
                    java.sql.Date iraungitze = rs.getDate("iraungitze_data");

                    String kokapenaStr = "P:" + pasilo + " / K:" + kokapen;
                    String iraungitzeStr = "-";

                    if (iraungitze != null && !iraungitze.toString().equals("2099-12-31")) {
                        iraungitzeStr = iraungitze.toString();
                    }

                    System.out.printf("%-5d | %-15s | %-15s | %-17s | %-15s | %-12s | %-7d | %-12s\n",
                            id, izena, erreferentzia, mota, fabrikatzailea, kokapenaStr, stock, iraungitzeStr);
                }
                System.out.println("------------------------------------------------------------------------------------------------------------------\n");
            }
        } catch (SQLException e) {
            System.out.println("Errorea produktuak kargatzean: " + e.getMessage());
        }
    }

    public void biltegiGuztiakErakutsi() {
        String sql = "SELECT * FROM bista_biltegiak";
        try (PreparedStatement stmt = konexioa.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("biltegi_kod");
                String izena = rs.getString("izena");
                String kokapena = rs.getString("kokapena");
                System.out.printf(id + ". " + izena + ", " + kokapena + "\n");
            }
        } catch (SQLException e) {
            System.out.println("Errorea biltegiak kargatzean: " + e.getMessage());
        }
    }
}
