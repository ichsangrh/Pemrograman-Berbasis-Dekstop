import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Menu menu = new Menu();
        Pesanan pesanan = new Pesanan();

        menu.muatMenuDariFile();

        int pilihan = 0;

        while (pilihan != 6) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Tambah Item Menu");
            System.out.println("2. Tampilkan Menu");
            System.out.println("3. Buat Pesanan");
            System.out.println("4. Tampilkan Struk");
            System.out.println("5. Simpan Menu & Struk");
            System.out.println("6. Keluar");
            System.out.print("Pilih: ");
            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {

            case 1:
                System.out.println("Tambah (1) Makanan, (2) Minuman, (3) Diskon: ");
                int jenis = input.nextInt();
                input.nextLine();

                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Harga: ");
                double harga = input.nextDouble();

                if (jenis == 1) {
                    menu.tambahItem(new Makanan(nama, harga, "Umum"));
                } else if (jenis == 2) {
                    menu.tambahItem(new Minuman(nama, harga, "Umum"));
                } else if (jenis == 3) {
                    menu.tambahItem(new Diskon(nama, harga));
                }
                break;

            case 2:
                menu.tampilMenu();
                break;

            case 3:
                System.out.print("Masukkan nama item: ");
                String cari = input.nextLine();

                try {
                    pesanan.tambahPesanan(menu.cariItem(cari));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 4:
                pesanan.tampilStruk();
                break;

            case 5:
                menu.simpanMenuKeFile();
                pesanan.simpanStrukKeFile();
                break;

            case 6:
                System.out.println("Keluar...");
                break;

            default:
                System.out.println("Pilihan tidak valid!");
            }
        }
    }
}