import java.util.*;
import java.io.*;

public class RestaurantApp {
    private static Menu menu = new Menu();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // coba muat menu dari file default
        String menuFile = "menu.txt";
        try {
            menu.muatDariFile(menuFile);
            System.out.println("Menu dimuat dari " + menuFile);
        } catch (IOException e) {
            System.out.println("Gagal memuat menu: " + e.getMessage());
        }

        boolean running = true;
        while (running) {
            System.out.println("\n=== APLIKASI MANAJEMEN RESTORAN ===");
            System.out.println("1. Tambah item ke menu");
            System.out.println("2. Tampilkan menu restoran");
            System.out.println("3. Terima pesanan pelanggan");
            System.out.println("4. Tampilkan struk pesanan (setelah order)");
            System.out.println("5. Simpan / Muat menu ke/dari file");
            System.out.println("6. Simpan contoh menu ke file");
            System.out.println("7. Keluar");
            System.out.print("Pilih (1-7): ");
            String pilih = sc.nextLine().trim();
            switch (pilih) {
                case "1": tambahItemKeMenu(); break;
                case "2": menu.tampilkanSemua(); break;
                case "3": terimaPesanan(); break;
                case "4": System.out.println("Gunakan menu 3 untuk membuat pesanan lalu simpan struk."); break;
                case "5": simpanMuatMenu(); break;
                case "6": simpanContohMenu(); break;
                case "7": running = false; System.out.println("Keluar. Terima kasih."); break;
                default: System.out.println("Pilihan tidak valid."); break;
            }
        }

        // sebelum keluar coba simpan menu ke menu.txt
        try {
            menu.simpanKeFile(menuFile);
            System.out.println("Menu disimpan ke " + menuFile);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan menu: " + e.getMessage());
        }
    }

    private static void tambahItemKeMenu() {
        System.out.println("\nTambah item: 1) Makanan 2) Minuman 3) Diskon");
        System.out.print("Pilih jenis: ");
        String j = sc.nextLine().trim();
        try {
            if (j.equals("1")) {
                System.out.print("Nama makanan: "); String nama = sc.nextLine();
                System.out.print("Harga: "); double harga = Double.parseDouble(sc.nextLine());
                System.out.print("Jenis makanan (Pembuka/Utama/Penutup): "); String jenis = sc.nextLine();
                menu.tambahItem(new Makanan(nama, harga, jenis));
                System.out.println("Makanan ditambahkan.");
            } else if (j.equals("2")) {
                System.out.print("Nama minuman: "); String nama = sc.nextLine();
                System.out.print("Harga: "); double harga = Double.parseDouble(sc.nextLine());
                System.out.print("Jenis minuman (Panas/Dingin/Segar): "); String jenis = sc.nextLine();
                menu.tambahItem(new Minuman(nama, harga, jenis));
                System.out.println("Minuman ditambahkan.");
            } else if (j.equals("3")) {
                System.out.print("Nama diskon (misal 'Promo Siang'): "); String nama = sc.nextLine();
                System.out.print("Persen diskon (misal 10): "); double pct = Double.parseDouble(sc.nextLine());
                System.out.print("Target (Makanan/Minuman/All): "); String target = sc.nextLine();
                menu.tambahItem(new Diskon(nama, pct, target));
                System.out.println("Diskon ditambahkan.");
            } else {
                System.out.println("Jenis tidak dikenali.");
            }
        } catch (NumberFormatException ne) {
            System.out.println("Input angka salah: " + ne.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    private static void terimaPesanan() {
        System.out.print("Nama pelanggan: ");
        String nama = sc.nextLine();
        Pesanan p = new Pesanan(nama);
        boolean taking = true;
        while (taking) {
            System.out.println("\nMenu restoran:");
            menu.tampilkanSemua();
            System.out.println("0 = selesai pesan");
            System.out.print("Masukkan nomor item (1..n) untuk ditambah ke pesanan: ");
            String s = sc.nextLine().trim();
            if (s.equals("0")) {
                taking = false;
                break;
            }
            try {
                int idx = Integer.parseInt(s) - 1;
                MenuItem it = menu.getItem(idx); // dapat lempar IndexOutOfBoundsException
                p.tambah(it);
                System.out.println(it.getNama() + " ditambahkan ke pesanan.");
            } catch (NumberFormatException nf) {
                System.out.println("Masukkan angka valid.");
            } catch (IndexOutOfBoundsException ib) {
                System.out.println("Item tidak ada (index diluar jangkauan).");
            } catch (Exception e) {
                System.out.println("Kesalahan: " + e.getMessage());
            }
        }

        if (p.isEmpty()) {
            System.out.println("Pesanan kosong. Batal.");
            return;
        }

        System.out.println("\n--- Struk ---");
        System.out.println(p.generateStruk());
        try {
            String path = p.simpanStrukKeFile("orders");
            System.out.println("Struk disimpan di: " + path);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan struk: " + e.getMessage());
        }
    }

    private static void simpanMuatMenu() {
        System.out.println("1. Simpan menu ke file");
        System.out.println("2. Muat menu dari file");
        System.out.print("Pilih: ");
        String p = sc.nextLine().trim();
        try {
            if (p.equals("1")) {
                System.out.print("Nama file (misal menu.txt): "); String fn = sc.nextLine();
                menu.simpanKeFile(fn);
                System.out.println("Menu disimpan ke " + fn);
            } else if (p.equals("2")) {
                System.out.print("Nama file untuk dimuat: "); String fn = sc.nextLine();
                menu.muatDariFile(fn);
                System.out.println("Menu dimuat dari " + fn);
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }

    private static void simpanContohMenu() {
        // menambahkan contoh item
        menu.tambahItem(new Makanan("Nasi Goreng", 25000, "Utama"));
        menu.tambahItem(new Makanan("Sate Ayam", 30000, "Utama"));
        menu.tambahItem(new Minuman("Teh Manis", 5000, "Dingin"));
        menu.tambahItem(new Minuman("Kopi Hitam", 10000, "Panas"));
        menu.tambahItem(new Diskon("Promo Siang", 10.0, "All"));
        System.out.println("Contoh menu ditambahkan (ingat untuk menyimpan ke file jika ingin disimpan).");
    }
}