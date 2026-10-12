public class Diskon extends MenuItem {
    private double diskonPercent; // persentase, misal 10.0 untuk 10%
    private String targetKategori; // "Makanan", "Minuman", atau "All"

    public Diskon(String nama, double diskonPercent, String targetKategori) {
        // Harga untuk item Diskon sendiri bisa 0 (atau menjaga sebagai fee), di sini 0
        super(nama, 0.0, "Diskon");
        this.diskonPercent = diskonPercent;
        this.targetKategori = targetKategori;
    }

    public double getDiskonPercent() { return diskonPercent; }
    public void setDiskonPercent(double diskonPercent) { this.diskonPercent = diskonPercent; }

    public String getTargetKategori() { return targetKategori; }
    public void setTargetKategori(String targetKategori) { this.targetKategori = targetKategori; }

    @Override
    public void tampilMenu() {
        System.out.println("Diskon: " + getNama() + " | " + String.format("%.0f", diskonPercent) + "% untuk: " + targetKategori);
    }

    @Override
    public String toString() {
        return getNama() + " (Diskon " + String.format("%.0f", diskonPercent) + "% untuk " + targetKategori + ")";
    }
}