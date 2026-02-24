package driver;

import java.util.Scanner;

public class Driver2 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Membaca jumlah data
        int jumlahData = input.nextInt();

        // Menyimpan nilai siswa
        int[] nilai = new int[jumlahData];

        for (int i = 0; i < jumlahData; i++) {
            nilai[i] = input.nextInt();
        }

        // Membaca kode kelompok (1 - 4)
        int kodeKelompok = input.nextInt();

        int total = 0;

        // Pola pembagian 4 kelompok (sesuai gambar)
        for (int i = 0; i < jumlahData; i++) {
            if (i % 4 == kodeKelompok - 1) {
                total += nilai[i];
            }
        }

        // Menampilkan hasil
        System.out.println(total);

        input.close();
    }
}