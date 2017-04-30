/**
 * Created by Izzan Fakhril Islam (1606875806)
 * Kelas    : DDP 2 - A
 * Project  : TugasPemrograman2v2
 * Date     : 30/04/2017
 * Time     : 21:19 PM
 *
 * Class yang digunakan untuk membentuk Layanan Owjek Regular (diturunkan dari class OwjekService)
 */

public class OwjekRegular extends OwjekService {
    private static final String SERVICE_TYPE = "Regular";
    private static final double COST_PER_KM = 1000;
    private static final double FIRST2_KM_COST = 3000;
    private static final double PROMO_RATE = 0.4;
    private String startCoordinate, finishCoordinate;
    private Map peta;
    private double promoCost;
    private int distance;

    /**
     * Main Constructor dari kelas OwjekRegular
     * Fungsi: Untuk membentuk sebuah layanan Owjek Regular dengan Koordinat perjalanan dan Peta yang sudah ditentukan
     * @param startCoordinate Koordinat awal perjalanan
     * @param finishCoordinate Koordinat akhir perjalanan
     * @param peta objek Peta yang akan dihitung
     */

    public OwjekRegular(String startCoordinate, String finishCoordinate, Map peta){
        super();
        super.setServiceType(SERVICE_TYPE);
        super.setCostPerKm(COST_PER_KM);
        super.setPromoRate(PROMO_RATE);
        super.setMinYearAllowed(2012);
        this.startCoordinate = startCoordinate;
        this.finishCoordinate = finishCoordinate;
        this.peta = peta;
    }

    /**
     * Menghitung total biaya perjalanan per KM dengan biaya Rp1.000 per KM
     * @param from Titik awal keberangkatan
     * @param to Titik akhir keberangkatan
     * @return Harga perjalanan
     */

    @Override
    public double calculateTotalCostPerKm(int from, int to){
        return COST_PER_KM*(to-from);
    }

    /**
     * Menghitung total Promo yang diberikan dalam sebuah perjalanan
     * @return Harga Promo dari perjalanan
     */

    @Override
    public double calculatePromoCost(){
        distance = OwjekService.shortestPath(this.startCoordinate, this.finishCoordinate, this.peta);

        //2 KM Pertama (Tarif flat)
        if (distance <= 20){
            promoCost = (1-PROMO_RATE)*FIRST2_KM_COST;
            return promoCost;
        }

        //6 KM Pertama (Tarif flat + potongan promo)
        if (distance <= 60){
            promoCost = (1-PROMO_RATE)*(FIRST2_KM_COST+calculateTotalCostPerKm(20, distance));
            return promoCost;
        }

        //Lebih dari 6 KM
        else {
            promoCost = (1-PROMO_RATE)*calculateTotalCostPerKm(20,60);
            return promoCost;
        }
    }

    /**
     * Menghitung Total Biaya Perjalanan (setelah dipotong Promo)
     * @return Total ongkos perjalanan
     */

    @Override
    public double calculateTotalCost(){
        return FIRST2_KM_COST + (calculateTotalCostPerKm(20,distance)*0.1) - (calculatePromoCost()*0.1);
    }

    /**
     * Mencetak Ringkasan (Summary) dari perjalanan yang telah dilakukan
     */

    @Override
    public void printTripSummary(){
        peta.print();
        System.out.println("Terimakasih telah melakukan perjalanan dengan OW-JEK.");
        System.out.println("[Jarak] "+distance*0.1+" KM");
        System.out.println("[TipeO] "+SERVICE_TYPE);
        System.out.println("[2KMPe] Rp "+FIRST2_KM_COST+" (+)");
        System.out.println("[KMSel] Rp "+calculateTotalCostPerKm(20, distance)*0.1+" (+)");
        System.out.println("[Promo] Rp "+calculatePromoCost()*0.1+" (-)");
        System.out.println("[TOTAL] Rp "+calculateTotalCost());
        System.out.println();
        peta.resetMap();
    }
}