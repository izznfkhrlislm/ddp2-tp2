/**
 * Interface Payable (Interface utama untuk program Owjek)
 * Fungsi: Menampung fungsi-fungsi penghitungan pembayaran
 * Created by: Izzan Fakhril Islam (1606875806)
 */

interface Payable {

    double calculateTotalCostPerKm(int from, int to);
    double calculateTotalCost();
    double calculatePromoCost();
    double calculateProtectionCost();
    void printTripSummary();

}
