package driver;

import java.util.ArrayList;
import java.util.Scanner;

// ===== ENUM untuk Status =====
enum StatusLaundry {
    DITERIMA, DIPROSES, SELESAI, DIAMBIL
}

// ===== CLASS PELANGGAN =====
class Pelanggan {
    private String id;
    private String nama;
    private String noHp;
    private String alamat;

    public Pelanggan(String id, String nama, String noHp, String alamat) {
        this.id = id;
        this.nama = nama;
        this.noHp = noHp;
        this.alamat = alamat;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public String getNoHp() { return noHp; }
    public String getAlamat() { return alamat; }

    public void tampilInfo() {
        System.out.println(id + " | " + nama + " | " + noHp + " | " + alamat);
    }
}

// ===== CLASS LAYANAN =====
class Layanan {
    private String kode;
    private String namaLayanan;
    private int hargaPerKg;

    public Layanan(String kode, String namaLayanan, int hargaPerKg) {
        this.kode = kode;
        this.namaLayanan = namaLayanan;
        this.hargaPerKg = hargaPerKg;
    }

    public String getKode() { return kode; }
    public String getNamaLayanan() { return namaLayanan; }
    public int getHargaPerKg() { return hargaPerKg; }

    public int hitungBiaya(int beratKg) {
        return beratKg * hargaPerKg;
    }

    public void tampilInfo() {
        System.out.println(kode + " | " + namaLayanan + " | Rp " + hargaPerKg + "/kg");
    }
}

// ===== CLASS TRANSAKSI =====
class Transaksi {
    private String idTransaksi;
    private Pelanggan pelanggan;
    private Layanan layanan;
    private int beratKg;
    private StatusLaundry status;

    public Transaksi(String idTransaksi, Pelanggan pelanggan, Layanan layanan, int beratKg) {
        this.idTransaksi = idTransaksi;
        this.pelanggan = pelanggan;
        this.layanan = layanan;
        this.beratKg = beratKg;
        this.status = StatusLaundry.DITERIMA;
    }

    public String getIdTransaksi() { return idTransaksi; }
    public StatusLaundry getStatus() { return status; }

    public int totalBayar() {
        return layanan.hitungBiaya(beratKg);
    }

    public void ubahStatus(StatusLaundry statusBaru) {
        this.status = statusBaru;
    }

    public void cetakNota() {
        System.out.println("\n===== NOTA LAUNDRY DEL =====");
        System.out.println("ID Transaksi   : " + idTransaksi);
        System.out.println("Pelanggan      : " + pelanggan.getNama() + " (" + pelanggan.getNoHp() + ")");
        System.out.println("Layanan        : " + layanan.getNamaLayanan());
        System.out.println("Berat          : " + beratKg + " kg");
        System.out.println("Total Bayar    : Rp " + totalBayar());
        System.out.println("Status         : " + status);
        System.out.println("============================\n");
    }
}

// ===== MAIN APP (PUBLIC CLASS) =====
public class Driver3 {

    static ArrayList<Pelanggan> pelangganList = new ArrayList<>();
    static ArrayList<Layanan> layananList = new ArrayList<>();
    static ArrayList<Transaksi> transaksiList = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    static int counterPelanggan = 1;
    static int counterTransaksi = 1;

    public static void main(String[] args) {

        layananList.add(new Layanan("L1", "Cuci Kering", 5000));
        layananList.add(new Layanan("L2", "Cuci Setrika", 7000));
        layananList.add(new Layanan("L3", "Setrika Saja", 4000));

        int pilih;
        do {
            System.out.println("=== SISTEM LAUNDRY DEL ===");
            System.out.println("1. Tambah Pelanggan");
            System.out.println("2. Lihat Pelanggan");
            System.out.println("3. Buat Transaksi");
            System.out.println("4. Lihat Transaksi");
            System.out.println("5. Update Status Transaksi");
            System.out.println("6. Laporan Pendapatan (Total)");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            pilih = inputAngka();

            switch (pilih) {
                case 1 -> tambahPelanggan();
                case 2 -> lihatPelanggan();
                case 3 -> buatTransaksi();
                case 4 -> lihatTransaksi();
                case 5 -> updateStatus();
                case 6 -> laporanPendapatan();
                case 0 -> System.out.println("Terima kasih sudah memakai Laundry Del!");
                default -> System.out.println("Menu tidak valid.\n");
            }
        } while (pilih != 0);
    }

    static int inputAngka() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Input harus angka. Coba lagi: ");
            }
        }
    }

    static void tambahPelanggan() {
        String id = String.format("P%03d", counterPelanggan++);
        System.out.println("\n--- TAMBAH PELANGGAN ---");
        System.out.println("ID otomatis: " + id);

        System.out.print("Nama   : ");
        String nama = sc.nextLine();

        System.out.print("No HP  : ");
        String noHp = sc.nextLine();

        System.out.print("Alamat : ");
        String alamat = sc.nextLine();

        pelangganList.add(new Pelanggan(id, nama, noHp, alamat));
        System.out.println("Pelanggan berhasil ditambahkan.\n");
    }

    static void lihatPelanggan() {
        System.out.println("\n--- DAFTAR PELANGGAN ---");
        if (pelangganList.isEmpty()) {
            System.out.println("Belum ada pelanggan.\n");
            return;
        }
        for (Pelanggan p : pelangganList) {
            p.tampilInfo();
        }
        System.out.println();
    }

    static Pelanggan cariPelanggan(String id) {
        for (Pelanggan p : pelangganList) {
            if (p.getId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    static Layanan cariLayanan(String kode) {
        for (Layanan l : layananList) {
            if (l.getKode().equalsIgnoreCase(kode)) return l;
        }
        return null;
    }

    static void buatTransaksi() {
        if (pelangganList.isEmpty()) {
            System.out.println("\nTambahkan pelanggan dulu sebelum transaksi.\n");
            return;
        }

        String idTransaksi = String.format("T%04d", counterTransaksi++);
        System.out.println("\n--- BUAT TRANSAKSI ---");
        System.out.println("ID transaksi otomatis: " + idTransaksi);

        System.out.print("Masukkan ID Pelanggan (contoh P001): ");
        String idP = sc.nextLine();
        Pelanggan pelanggan = cariPelanggan(idP);

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.\n");
            return;
        }

        System.out.println("\n--- PILIH LAYANAN ---");
        for (Layanan l : layananList) l.tampilInfo();

        System.out.print("Kode layanan (L1/L2/L3): ");
        String kode = sc.nextLine();
        Layanan layanan = cariLayanan(kode);

        if (layanan == null) {
            System.out.println("Layanan tidak ditemukan.\n");
            return;
        }

        System.out.print("Berat (kg): ");
        int berat = inputAngka();
        if (berat <= 0) {
            System.out.println("Berat harus lebih dari 0.\n");
            return;
        }

        Transaksi t = new Transaksi(idTransaksi, pelanggan, layanan, berat);
        transaksiList.add(t);

        System.out.println("Transaksi berhasil dibuat.");
        t.cetakNota();
    }

    static void lihatTransaksi() {
        System.out.println("\n--- DAFTAR TRANSAKSI ---");
        if (transaksiList.isEmpty()) {
            System.out.println("Belum ada transaksi.\n");
            return;
        }
        for (Transaksi t : transaksiList) t.cetakNota();
    }

    static Transaksi cariTransaksi(String idTransaksi) {
        for (Transaksi t : transaksiList) {
            if (t.getIdTransaksi().equalsIgnoreCase(idTransaksi)) return t;
        }
        return null;
    }

    static void updateStatus() {
        if (transaksiList.isEmpty()) {
            System.out.println("\nBelum ada transaksi.\n");
            return;
        }

        System.out.print("\nMasukkan ID Transaksi (contoh T0001): ");
        String idT = sc.nextLine();
        Transaksi t = cariTransaksi(idT);

        if (t == null) {
            System.out.println("Transaksi tidak ditemukan.\n");
            return;
        }

        System.out.println("Status sekarang: " + t.getStatus());
        System.out.println("1. DITERIMA");
        System.out.println("2. DIPROSES");
        System.out.println("3. SELESAI");
        System.out.println("4. DIAMBIL");
        System.out.print("Pilih: ");
        int s = inputAngka();

        StatusLaundry statusBaru = switch (s) {
            case 1 -> StatusLaundry.DITERIMA;
            case 2 -> StatusLaundry.DIPROSES;
            case 3 -> StatusLaundry.SELESAI;
            case 4 -> StatusLaundry.DIAMBIL;
            default -> null;
        };

        if (statusBaru == null) {
            System.out.println("Pilihan status tidak valid.\n");
            return;
        }

        t.ubahStatus(statusBaru);
        System.out.println("✅ Status berhasil diupdate.");
        t.cetakNota();
    }

    static void laporanPendapatan() {
        int total = 0;
        for (Transaksi t : transaksiList) total += t.totalBayar();

        System.out.println("\n--- LAPORAN PENDAPATAN ---");
        System.out.println("Jumlah transaksi : " + transaksiList.size());
        System.out.println("Total pemasukan  : Rp " + total);
        System.out.println("--------------------------\n");
    }
}