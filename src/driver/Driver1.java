import java.util.*;

public class Driver1 {
    static class MenuItem {
        String kode, nama;
        long harga;
        MenuItem(String kode, String nama, long harga) {
            this.kode = kode; this.nama = nama; this.harga = harga;
        }
    }

    static class OrderLine {
        MenuItem item;
        int porsiButet, porsiUcok, porsiTotal;
        long subtotal;

        OrderLine(MenuItem item, int porsiButet) {
            this.item = item;
            this.porsiButet = porsiButet;
            this.porsiUcok = 2 * porsiButet;
            this.porsiTotal = porsiButet + porsiUcok;
            this.subtotal = item.harga * this.porsiTotal;
        }
    }

    static class Coupon {
        String nama;
        int persen;
        Coupon(String nama, int persen) { this.nama = nama; this.persen = persen; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Map<String, MenuItem> menu = buildMenu();
        List<OrderLine> orders = new ArrayList<>();

        while (true) {
            if (!sc.hasNext()) break;
            String kode = sc.next().trim();
            if (kode.equalsIgnoreCase("END")) break;

            int porsiButet = sc.nextInt();
            if (porsiButet <= 0) continue;

            MenuItem item = menu.get(kode);
            if (item == null) continue;

            orders.add(new OrderLine(item, porsiButet));
        }

        long total = 0;
        for (OrderLine o : orders) total += o.subtotal;

        Coupon coupon = chooseCoupon(total);
        long diskon = total * coupon.persen / 100;
        long totalBayar = total - diskon;

        printReceipt(orders, total, coupon, diskon, totalBayar);
    }

    static Map<String, MenuItem> buildMenu() {
        Map<String, MenuItem> m = new HashMap<>();
        m.put("NGS", new MenuItem("NGS", "Nasi Goreng Spesial", 15000));
        m.put("AP",  new MenuItem("AP",  "Ayam Penyet", 20000));
        m.put("SA",  new MenuItem("SA",  "Sate Ayam", 25000));
        m.put("BU",  new MenuItem("BU",  "Bakso Urat", 18000));
        m.put("MAP", new MenuItem("MAP", "Mie Ayam Pangsit", 15000));
        m.put("GG",  new MenuItem("GG",  "Gado-Gado", 15000));
        m.put("SAM", new MenuItem("SAM", "Soto Ayam", 17000));
        m.put("RD",  new MenuItem("RD",  "Rendang Daging", 25000));
        m.put("IB",  new MenuItem("IB",  "Ikan Bakar", 35000));
        m.put("NUK", new MenuItem("NUK", "Nasi Uduk Komplit", 20000));
        return m;
    }

    static Coupon chooseCoupon(long total) {
        if (total >= 500_000) return new Coupon("Kupon Hitam", 25);
        if (total >= 400_000) return new Coupon("Kupon Hijau", 20);
        if (total >= 300_000) return new Coupon("Kupon Merah", 15);
        if (total >= 200_000) return new Coupon("Kupon Kuning", 10);
        if (total >= 100_000) return new Coupon("Kupon Biru", 5);
        return new Coupon("Tanpa Kupon", 0);
    }

    static void printReceipt(List<OrderLine> orders, long total, Coupon coupon, long diskon, long totalBayar) {
        System.out.printf("%-18s %5s %8s %10s%n", "Menu", "Porsi", "Harga", "Total");
        System.out.println("====================================================");

        for (OrderLine o : orders) {
            String nama = o.item.nama;
            if (nama.length() > 18) nama = nama.substring(0, 18);

            System.out.printf("%-18s %5d %8d %10d%n",
                    nama, o.porsiTotal, o.item.harga, o.subtotal);
        }

        System.out.println("----------------------------------------------------");
        System.out.printf("%-30s %18d%n", "Total Pembayaran", total);

        // kalau tidak diminta tampil diskon/kupon, boleh hapus blok ini
        if (coupon.persen > 0) {
            System.out.printf("%-30s %18s%n", "Kupon", coupon.nama + " (" + coupon.persen + "%)");
            System.out.printf("%-30s %18d%n", "Diskon", diskon);
            System.out.printf("%-30s %18d%n", "Total Setelah Diskon", totalBayar);
        }
    }
}