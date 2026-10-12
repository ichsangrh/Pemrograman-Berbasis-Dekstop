public class Minuman extends MenuItem {
    private String jenisMinuman; // misal "Panas", "Dingin", "Segar"

    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "Minuman");
        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() { return jenisMinuman; }
    public void setJenisMinuman(String jenisMinuman) { this.jenisMinuman = jenisMinuman; }

    @Override
    public void tampilMenu() {
        System.out.println("Minuman: " + getNama() + " | Jenis: " + jenisMinuman + " | Harga: Rp" + String.format("%.0f", getHarga()));
    }
}