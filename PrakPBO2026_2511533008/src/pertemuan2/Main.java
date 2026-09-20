package pertemuan2;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Rekening> daftarRekening = new ArrayList<>();
		Rekening akunAktif = null;
		boolean isRunning = true;
		
		System.out.println("=== SISTEM PEMBAYARAN MINI ===");
		
		while (isRunning) {
			System.out.println("\nMenu Utama:");
			System.out.println("1. Buka Rekening Baru");
			System.out.println("2. Setor Tunai");
			System.out.println("3. Tarik Tunai");
			System.out.println("4. Cek Informasi Rekening");
			System.out.println("5. Ganti Akun");
			System.out.println("6. Cetak Mutasi (Riwayat)");
			System.out.println("0. Keluar");
			System.out.print("Pilih Menu: ");
			
			int pilihan = input.nextInt();
			input.nextLine();
			
			switch (pilihan) {
			case 1:
				System.out.print("Masukkan No Rekening: ");
				String no = input.nextLine();
				System.out.print("Masukkan Nama pemilik: ");
				String nama = input.nextLine();
				System.out.print("Masukkan Saldo Awal: ");
				double saldo = input.nextDouble();
				
				akunAktif = new Rekening(no, nama, saldo);
				daftarRekening.add(akunAktif);
				break;
				
			case 2:
				if (akunAktif == null) {
					System.out.println("Error: Mohon Maaf, Anda belum memiliki nomor rekening!");
				} else {
					System.out.print("Masukkan nominal setor: ");
					double setor = input.nextDouble();
					akunAktif.setorTunai(setor);
				}
				break;
				
			case 3:
				System.out.println("Fitur ini akan berjalan sebagai tugas mandiri");
				if (akunAktif == null) {
					System.out.println("Error: Mohon Maaf, Anda belum memiliki nomor rekening!");
				} else {
					System.out.print("Masukkan nominal tarik: ");
					double tarik = input.nextDouble();
					akunAktif.tarikTunai(tarik);
				}
				break;
				
			case 4:
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening");
				} else {
					akunAktif.cekInformasi();
				}
				break;
				
			case 5:
				if (daftarRekening.isEmpty()) {
				    System.out.println("Belum ada rekening yang terdaftar di sistem.");
				} else {
				    System.out.print("Masukkan Nomor Rekening yang ingin diaktifkan: ");
				    String cariNoRek = input.nextLine();
				    boolean ditemukan = false;

				    for (Rekening r : daftarRekening) {
				        if (r.nomorRekening.equalsIgnoreCase(cariNoRek)) {
				            akunAktif = r;
				            ditemukan = true;
				            System.out.println("Berhasil mengganti ke akun milik: " + r.namaPemilik);
				            break;
				        }
				    }

				    if (!ditemukan) {
				        System.out.println("Error: Nomor rekening tidak ditemukan!");
				    }
				}
				break;

			case 6:
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening");
				} else {
					akunAktif.cetakMutasi();
				}
				
				break;
			
			case 0:
				isRunning = false;
				System.out.println("Sistem ditutup. Terima Kasih!");
				break;
				
			default:
				System.out.println("Pilihan tidak valid!");
				
			}
					
		}

	}

}
