public abstract class MenuItem {
    private String nama;
    private double harga;
    private String kategori; // misal "Makanan", "Minuman", "Diskon"

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // Encapsulation: akses melalui getter/setter
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    // Polymorphism: tiap subclass implement tampilMenu()
    public abstract void tampilMenu();

    // Representasi singkat
    @Override
    public String toString() {
        return nama + " (" + kategori + ") - Rp" + String.format("%.0f", harga);
    }
}