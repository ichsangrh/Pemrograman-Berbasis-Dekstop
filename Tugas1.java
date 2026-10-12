import java.util.Scanner;

public class Tugas1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // ===== 1. Data Menu Restoran =====
        String[] namaMenu = {"Nasi Goreng", "Ayam Bakar", "Soto Ayam", "Es Teh", "Jus Jeruk", "Kopi"};
        int[] hargaMenu = {25000, 30000, 20000, 5000, 10000, 8000};
        String[] kategoriMenu = {"Makanan", "Makanan", "Makanan", "Minuman", "Minuman", "Minuman"};

        // ===== 2. Tampilkan Daftar Menu =====
        System.out.println("==== SELAMAT DATANG DI RESTORAN ICHSAN ====");
        System.out.println("Daftar Menu:");
        for (int i = 0; i < namaMenu.length; i++) {
            System.out.printf("%d. %-12s | Kategori: %-8s | Harga: Rp %,d%n",
                    (i + 1), namaMenu[i], kategoriMenu[i], hargaMenu[i]);
        }

        // ===== 3. Input Pesanan =====
        System.out.println("\nSilakan masukkan jumlah menu yang ingin dipesan (maksimal 4): ");
        int jumlahPesanan = input.nextInt();
        input.nextLine(); // membersihkan buffer

        String[] pesananNama = new String[jumlahPesanan];
        int[] pesananJumlah = new int[jumlahPesanan];
        int[] pesananTotal = new int[jumlahPesanan];
        String[] pesananKategori = new String[jumlahPesanan];

        for (int i = 0; i < jumlahPesanan; i++) {
            System.out.print("\nMasukkan nama menu ke-" + (i + 1) + ": ");
            String nama = input.nextLine();
            boolean ditemukan = false;

            for (int j = 0; j < namaMenu.length; j++) {
                if (nama.equalsIgnoreCase(namaMenu[j])) {
                    pesananNama[i] = namaMenu[j];
                    pesananKategori[i] = kategoriMenu[j];

                    System.out.print("Masukkan jumlah pesanan untuk " + namaMenu[j] + ": ");
                    pesananJumlah[i] = input.nextInt();
                    input.nextLine();

                    pesananTotal[i] = hargaMenu[j] * pesananJumlah[i];
                    ditemukan = true;
                    break;
                }
            }
            if (!ditemukan) {
                System.out.println("Menu tidak ditemukan! Otomatis dilewati.");
                pesananNama[i] = "-";
                pesananJumlah[i] = 0;
                pesananTotal[i] = 0;
                pesananKategori[i] = "-";
            }
        }

        // ===== 4. Hitung Total Biaya =====
        int subtotal = 0;
        for (int total : pesananTotal) {
            subtotal += total;
        }

        double pajak = 0.1 * subtotal;
        int biayaPelayanan = 20000;
        double totalSetelahPajak = subtotal + pajak + biayaPelayanan;

        // ===== 5. Diskon & Promo =====
        double diskon = 0;
        boolean promoMinuman = false;

        if (subtotal > 100000) {
            diskon = 0.1 * subtotal;
        } else if (subtotal > 50000) {
            promoMinuman = true;
        }

        // ===== 6. Jika promo minuman, terapkan beli 1 gratis 1 =====
        if (promoMinuman) {
            for (int i = 0; i < jumlahPesanan; i++) {
                if (pesananKategori[i].equalsIgnoreCase("Minuman") && pesananJumlah[i] >= 2) {
                    System.out.println("\nPROMO! Beli 1 Gratis 1 untuk minuman " + pesananNama[i]);
                    subtotal -= hargaMenu[i]; // potong harga satu minuman gratis
                    break;
                }
            }
        }

        double totalBayar = subtotal - diskon + pajak + biayaPelayanan;

        // ===== 7. Cetak Struk =====
        System.out.println("\n================ STRUK PEMESANAN ================");
        for (int i = 0; i < jumlahPesanan; i++) {
            if (!pesananNama[i].equals("-")) {
                System.out.printf("%-15s x%d  = Rp %,d%n",
                        pesananNama[i], pesananJumlah[i], pesananTotal[i]);
            }
        }
        System.out.println("------------------------------------------------");
        System.out.printf("Subtotal\t: Rp %, .0f%n", (double) subtotal);
        System.out.printf("Pajak (10%%)\t: Rp %, .0f%n", pajak);
        System.out.printf("Pelayanan\t: Rp %,d%n", biayaPelayanan);

        if (diskon > 0) {
            System.out.printf("Diskon 10%%\t: -Rp %, .0f%n", diskon);
        }
        if (promoMinuman) {
            System.out.println("Promo\t\t: Beli 1 Gratis 1 Minuman");
        }

        System.out.println("================================================");
        System.out.printf("TOTAL BAYAR\t: Rp %, .0f%n", totalBayar);
        System.out.println("================================================");
        System.out.println("Terima kasih telah berkunjung di Restoran ICHSAN!");
        System.out.println("================================================");

        input.close();
    }
}
