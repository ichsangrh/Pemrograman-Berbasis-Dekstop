public class Makanan extends MenuItem {
    private String jenisMakanan; // misal "Pembuka", "Utama", "Penutup"

    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "Makanan");
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() { return jenisMakanan; }
    public void setJenisMakanan(String jenisMakanan) { this.jenisMakanan = jenisMakanan; }

    @Override
    public void tampilMenu() {
        System.out.println("Makanan: " + getNama() + " | Jenis: " + jenisMakanan + " | Harga: Rp" + String.format("%.0f", getHarga()));
    }
}