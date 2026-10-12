import java.util.*;
import java.io.*;

public class Menu {
    private ArrayList<MenuItem> items;

    public Menu() {
        items = new ArrayList<>();
    }

    public void tambahItem(MenuItem item) {
        items.add(item);
    }

    public MenuItem getItem(int index) throws IndexOutOfBoundsException {
        return items.get(index);
    }

    public int size() { return items.size(); }

    public void tampilkanSemua() {
        if (items.isEmpty()) {
            System.out.println("Menu kosong.");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.print((i+1) + ". ");
            items.get(i).tampilMenu();
        }
    }

    // I/O: simpan menu ke file (CSV sederhana)
    public void simpanKeFile(String filepath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            for (MenuItem it : items) {
                String line = "";
                if (it instanceof Makanan) {
                    Makanan m = (Makanan) it;
                    line = "Makanan|" + escape(m.getNama()) + "|" + m.getHarga() + "|" + m.getJenisMakanan();
                } else if (it instanceof Minuman) {
                    Minuman m = (Minuman) it;
                    line = "Minuman|" + escape(m.getNama()) + "|" + m.getHarga() + "|" + m.getJenisMinuman();
                } else if (it instanceof Diskon) {
                    Diskon d = (Diskon) it;
                    line = "Diskon|" + escape(d.getNama()) + "|" + d.getDiskonPercent() + "|" + d.getTargetKategori();
                }
                bw.write(line);
                bw.newLine();
            }
        }
    }

    // I/O: muat menu dari file (menghapus menu yang ada lalu menambah dari file)
    public void muatDariFile(String filepath) throws IOException {
        items.clear();
        File f = new File(filepath);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length < 4) continue;
                String type = parts[0];
                String nama = unescape(parts[1]);
                if (type.equals("Makanan")) {
                    double harga = Double.parseDouble(parts[2]);
                    String jenis = parts[3];
                    tambahItem(new Makanan(nama, harga, jenis));
                } else if (type.equals("Minuman")) {
                    double harga = Double.parseDouble(parts[2]);
                    String jenis = parts[3];
                    tambahItem(new Minuman(nama, harga, jenis));
                } else if (type.equals("Diskon")) {
                    double pct = Double.parseDouble(parts[2]);
                    String target = parts[3];
                    tambahItem(new Diskon(nama, pct, target));
                }
            }
        }
    }

    // utility
    private String escape(String s) {
        return s.replace("|", "/PIPE/");
    }
    private String unescape(String s) {
        return s.replace("/PIPE/", "|");
    }
}