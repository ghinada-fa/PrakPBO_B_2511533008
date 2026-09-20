package pertemuan2;
import java.util.ArrayList;

public class Rekening {
	
		String nomorRekening;
		String namaPemilik;
		double saldo;
		
		// arraylist
		ArrayList<Transaksi> riwayatTransaksi;
		
		public Rekening(String nomor, String nama, double saldoAwal) {
			this.nomorRekening = nomor;
			this.namaPemilik = nama;
			this.saldo = saldoAwal;
			this.riwayatTransaksi = new ArrayList<>();
			
			System.out.println("Rekening atas nama " + " berhasil dibuat dengan saldo Rp" + saldo);
		}
		
		public void setorTunai(double nominal) {
			if (nominal > 0) {
				saldo += nominal;
				String idTrx = "TRX-S-" + System.currentTimeMillis();
				Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
				riwayatTransaksi.add(trxBaru);
				
				System.out.println("Setor tunai RP" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
			} else {
				System.out.println("Gagal: Nominal setor harus lebih dari 0!");
			}
		}
		
		public void tarikTunai(double nominal) {
			if (nominal > 10000 && nominal < saldo) {
				saldo -= nominal;
				String idTrx = "TRX-T-" + System.currentTimeMillis();
				Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
				riwayatTransaksi.add(trxBaru);
				System.out.println("Tarik tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
			} else if (nominal <= 10000){
				System.out.println("Gagal: Nominal tarik tunai harus lebih dari 10.000 !");
			} else {
				System.out.println("Gagal: saldo anda tidak cukup!");
			}
		}
		
		public void cekInformasi() {
			System.out.println("--- INFO REKENING ---");
			System.out.println("No. Rekening: " + nomorRekening);
			System.out.println("Nama Pemilik: " + namaPemilik);
			System.out.println("Saldo Akhir : Rp" + saldo );
			System.out.println("---------------------");
		}
		
		public void cetakMutasi() {
			if (riwayatTransaksi.isEmpty()) {
				System.out.println("Belum ada transasksi pada rekening ini");
			return; }
			
			System.out.println("--- Mutasi Rekening ---");
			
			for (Transaksi transaksi : riwayatTransaksi) {
				transaksi.cetakDetail();
			}
			
		}

}
