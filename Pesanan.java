import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.*;

public class Pesanan {
    private ArrayList<MenuItem> dipesan;
    private String namaPelanggan;

    public Pesanan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
        dipesan = new ArrayList<>();
    }

    public void tambah(MenuItem item) {
        dipesan.add(item);
    }

    public boolean isEmpty() {
        return dipesan.isEmpty();
    }

    // Hitung total dengan mempertimbangkan Diskon di dalam pesanan
    public double hitungTotal() {
        // Hitung subtotal per kategori
        Map<String, Double> subtotalPerKategori = new HashMap<>();
        double subtotalAll = 0.0;
        ArrayList<Diskon> diskons = new ArrayList<>();

        for (MenuItem it : dipesan) {
            if (it instanceof Diskon) {
                diskons.add((Diskon) it);
            } else {
                double price = it.getHarga();
                subtotalAll += price;
                String cat = it.getKategori();
                subtotalPerKategori.put(cat, subtotalPerKategori.getOrDefault(cat, 0.0) + price);
            }
        }

        if (subtotalAll == 0) return 0.0;

        // Cari diskon tertinggi untuk kategori tertentu dan juga untuk "All"
        double maxAll = 0.0;
        Map<String, Double> maxPerCat = new HashMap<>();
        for (Diskon d : diskons) {
            String t = d.getTargetKategori();
            if (t.equalsIgnoreCase("All")) {
                if (d.getDiskonPercent() > maxAll) maxAll = d.getDiskonPercent();
            } else {
                double prev = maxPerCat.getOrDefault(t, 0.0);
                if (d.getDiskonPercent() > prev) maxPerCat.put(t, d.getDiskonPercent());
            }
        }

        // Terapkan diskon: untuk tiap kategori gunakan diskon = max(maxAll, maxPerCat.get(cat))
        double total = 0.0;
        for (Map.Entry<String, Double> entry : subtotalPerKategori.entrySet()) {
            String cat = entry.getKey();
            double sub = entry.getValue();
            double catDisc = maxPerCat.getOrDefault(cat, 0.0);
            double usedDisc = Math.max(maxAll, catDisc);
            total += sub * (1.0 - usedDisc / 100.0);
        }

        return total;
    }

    public String generateStruk() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== STRUK PESANAN =====\n");
        sb.append("Nama Pelanggan: ").append(namaPelanggan).append("\n");
        sb.append("Waktu: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n");
        for (MenuItem it : dipesan) {
            if (it instanceof Diskon) {
                Diskon d = (Diskon) it;
                sb.append("[Diskon] ").append(d.getNama()).append(" - ").append(String.format("%.0f", d.getDiskonPercent())).append("% untuk ").append(d.getTargetKategori()).append("\n");
            } else {
                sb.append(it.getNama()).append(" (").append(it.getKategori()).append(") - Rp").append(String.format("%.0f", it.getHarga())).append("\n");
            }
        }
        sb.append("\nTotal bayar: Rp").append(String.format("%.0f", hitungTotal())).append("\n");
        sb.append("=========================\n");
        return sb.toString();
    }

    // Simpan struk ke file
    public String simpanStrukKeFile(String folderPath) throws IOException {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String filename = "order_" + ts + ".txt";
        File dir = new File(folderPath);
        if (!dir.exists()) dir.mkdirs();
        File out = new File(dir, filename);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(out))) {
            bw.write(generateStruk());
        }
        return out.getAbsolutePath();
    }
}