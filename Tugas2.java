import java.util.Scanner;

public class Tugas2 {
    static Menu[] menuList = new Menu[20];
    static int jumlahMenu = 0;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        inisialisasiMenu();
        menuUtama();
    }

    // ====== Inisialisasi Data Awal ======
    public static void inisialisasiMenu() {
        menuList[jumlahMenu++] = new Menu("Nasi Goreng", 25000, "Makanan");
        menuList[jumlahMenu++] = new Menu("Mie Ayam", 20000, "Makanan");
        menuList[jumlahMenu++] = new Menu("Ayam Geprek", 22000, "Makanan");
        menuList[jumlahMenu++] = new Menu("Sate Ayam", 30000, "Makanan");
        menuList[jumlahMenu++] = new Menu("Es Teh", 8000, "Minuman");
        menuList[jumlahMenu++] = new Menu("Jus Jeruk", 12000, "Minuman");
        menuList[jumlahMenu++] = new Menu("Kopi Hitam", 10000, "Minuman");
        menuList[jumlahMenu++] = new Menu("Susu Cokelat", 15000, "Minuman");
    }

    // ====== Menu Utama ======
    public static void menuUtama() {
        while (true) {
            System.out.println("\n=== SELAMAT DATANG DI RESTORAN SEDERHANA ===");
            System.out.println("1. Menu Pelanggan");
            System.out.println("2. Menu Pengelola Restoran");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");
            int pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1 -> menuPelanggan();
                case 2 -> menuPengelola();
                case 3 -> {
                    System.out.println("Terima kasih telah berkunjung!");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    // ====== Menu Pelanggan ======
    public static void menuPelanggan() {
        String[] pesanan = new String[50];
        int[] jumlahPesanan = new int[50];
        int jumlahItem = 0;

        System.out.println("\n=== MENU RESTORAN ===");
        tampilkanMenu();

        while (true) {
            System.out.print("\nMasukkan nama menu yang ingin dipesan (atau ketik 'selesai'): ");
            String namaPesanan = input.nextLine();

            if (namaPesanan.equalsIgnoreCase("selesai")) break;

            int indexMenu = cariMenu(namaPesanan);
            if (indexMenu == -1) {
                System.out.println("Menu tidak ditemukan, coba lagi!");
                continue;
            }

            System.out.print("Masukkan jumlah: ");
            int jumlah = input.nextInt();
            input.nextLine();

            pesanan[jumlahItem] = menuList[indexMenu].nama;
            jumlahPesanan[jumlahItem] = jumlah;
            jumlahItem++;
        }

        cetakStruk(pesanan, jumlahPesanan, jumlahItem);
    }

    // ====== Menampilkan Menu ======
    public static void tampilkanMenu() {
        System.out.println("\n--- MAKANAN ---");
        for (int i = 0; i < jumlahMenu; i++) {
            if (menuList[i].kategori.equalsIgnoreCase("Makanan")) {
                System.out.println((i + 1) + ". " + menuList[i]);
            }
        }

        System.out.println("\n--- MINUMAN ---");
        for (int i = 0; i < jumlahMenu; i++) {
            if (menuList[i].kategori.equalsIgnoreCase("Minuman")) {
                System.out.println((i + 1) + ". " + menuList[i]);
            }
        }
    }

    // ====== Cari Menu Berdasarkan Nama ======
    public static int cariMenu(String nama) {
        for (int i = 0; i < jumlahMenu; i++) {
            if (menuList[i].nama.equalsIgnoreCase(nama)) {
                return i;
            }
        }
        return -1;
    }

    // ====== Cetak Struk ======
    public static void cetakStruk(String[] pesanan, int[] jumlah, int jumlahItem) {
        double total = 0;
        boolean beliMinuman = false;

        System.out.println("\n=== STRUK PEMESANAN ===");
        for (int i = 0; i < jumlahItem; i++) {
            int idx = cariMenu(pesanan[i]);
            double hargaItem = menuList[idx].harga * jumlah[i];
            System.out.println(pesanan[i] + " x" + jumlah[i] + " = Rp" + hargaItem);
            total += hargaItem;
            if (menuList[idx].kategori.equalsIgnoreCase("Minuman")) beliMinuman = true;
        }

        // Diskon & Promo
        double diskon = 0;
        boolean promoMinuman = false;
        if (total > 100000) {
            diskon = total * 0.10;
            System.out.println("Diskon 10%: -Rp" + diskon);
        } else if (total > 50000 && beliMinuman) {
            promoMinuman = true;
            System.out.println("Promo: Beli 1 Gratis 1 untuk kategori minuman!");
        }

        double pajak = total * 0.10;
        double service = 20000;
        double totalBayar = total - diskon + pajak + service;

        System.out.println("Pajak (10%): Rp" + pajak);
        System.out.println("Biaya Pelayanan: Rp" + service);
        System.out.println("---------------------------------");
        System.out.println("TOTAL BAYAR: Rp" + totalBayar);
    }

    // ====== Menu Pengelola ======
    public static void menuPengelola() {
        while (true) {
            System.out.println("\n=== MENU PENGELOLA RESTORAN ===");
            System.out.println("1. Tampilkan Menu");
            System.out.println("2. Tambah Menu");
            System.out.println("3. Ubah Harga");
            System.out.println("4. Hapus Menu");
            System.out.println("5. Kembali");
            System.out.print("Pilih: ");
            int pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1 -> tampilkanMenu();
                case 2 -> tambahMenu();
                case 3 -> ubahHarga();
                case 4 -> hapusMenu();
                case 5 -> { return; }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    // ====== Tambah Menu ======
    public static void tambahMenu() {
        System.out.print("Masukkan nama menu baru: ");
        String nama = input.nextLine();
        System.out.print("Masukkan harga: ");
        double harga = input.nextDouble();
        input.nextLine();
        System.out.print("Masukkan kategori (Makanan/Minuman): ");
        String kategori = input.nextLine();

        menuList[jumlahMenu++] = new Menu(nama, harga, kategori);
        System.out.println("Menu berhasil ditambahkan!");
    }

    // ====== Ubah Harga Menu ======
    public static void ubahHarga() {
        tampilkanMenu();
        System.out.print("\nMasukkan nama menu yang akan diubah: ");
        String nama = input.nextLine();
        int index = cariMenu(nama);
        if (index == -1) {
            System.out.println("Menu tidak ditemukan!");
            return;
        }

        System.out.print("Harga baru: ");
        double hargaBaru = input.nextDouble();
        input.nextLine();
        System.out.print("Yakin ubah harga? (Ya/Tidak): ");
        String konfirmasi = input.nextLine();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            menuList[index].harga = hargaBaru;
            System.out.println("Harga berhasil diubah!");
        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }

    // ====== Hapus Menu ======
    public static void hapusMenu() {
        tampilkanMenu();
        System.out.print("\nMasukkan nama menu yang akan dihapus: ");
        String nama = input.nextLine();
        int index = cariMenu(nama);
        if (index == -1) {
            System.out.println("Menu tidak ditemukan!");
            return;
        }

        System.out.print("Yakin hapus menu? (Ya/Tidak): ");
        String konfirmasi = input.nextLine();
        if (konfirmasi.equalsIgnoreCase("Ya")) {
            for (int i = index; i < jumlahMenu - 1; i++) {
                menuList[i] = menuList[i + 1];
            }
            jumlahMenu--;
            System.out.println("Menu berhasil dihapus!");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }
}
    