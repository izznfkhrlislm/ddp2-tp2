/**
 * Created by Izzan Fakhril Islam (1606875806)
 * Kelas    : DDP 2 - A
 * Project  : TugasPemrograman2v2
 * Date     : 30/04/2017
 * Time     : 21:29 PM
 *
 * Class yang digunakan untuk membentuk Layanan Owjek Sporty (diturunkan dari class OwjekService)
 */

public class OwjekSporty extends OwjekService {
    private static final String SERVICE_TYPE = "Sporty";
    private static final double COST_PER_KM = 3000;
    private static final double FIRST5_KM_COST = 20000;
    private static final double PROMO_RATE = 0.6;
    private static final double PROTECTION_RATE = 0.1;
    private String startCoordinate, finishCoordinate;
    private Map peta;
    private double promoCost, protectionCost;
    private int distance;

    /**
     * Main Constructor dari kelas OwjekSporty
     * Fungsi: Untuk membentuk sebuah layanan Owjek Sporty dengan Koordinat perjalanan dan Peta yang sudah ditentukan
     * @param startCoordinate Koordinat awal perjalanan
     * @param finishCoordinate Koordinat akhir perjalanan
     * @param peta objek Peta yang akan dihitung
     */

    public OwjekSporty(String startCoordinate, String finishCoordinate, Map peta){
        super();
        super.setServiceType(SERVICE_TYPE);
        super.setCostPerKm(COST_PER_KM);
        super.setPromoRate(PROMO_RATE);
        super.setMinYearAllowed(2015);
        super.setMinTopSpeed(140);
        this.startCoordinate = startCoordinate;
        this.finishCoordinate = finishCoordinate;
        this.peta = peta;
    }

    /**
     * Menghitung total biaya perjalanan per KM dengan biaya Rp3.000 per KM
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

        //5 KM Pertama (Tarif flat)
        if (distance <= 50){
            promoCost = (1-PROMO_RATE)*FIRST5_KM_COST;
            protectionCost = PROTECTION_RATE*(promoCost*0.1);
            return promoCost;
        }

        //8 KM Pertama (Tarif flat + promo)
        if (distance <= 80){
            promoCost = (1-PROMO_RATE)*(FIRST5_KM_COST+calculateTotalCostPerKm(50, distance));
            protectionCost = PROTECTION_RATE*(promoCost*0.1);
            return promoCost;
        }

        //Lebih dari 8 KM
        else {
            promoCost = (1-PROMO_RATE)*calculateTotalCostPerKm(50,80);
            return promoCost;
        }
    }

    /**
     * Menghitung total biaya Proteksi yang diberikan dalam sebuah perjalanan
     * @return biaya Proteksi dari perjalanan
     */

    @Override
    public double calculateProtectionCost(){
        if (distance <= 50){
            protectionCost = PROTECTION_RATE*(promoCost*0.1);
            return protectionCost;
        }
        if (distance <= 80){
            protectionCost = PROTECTION_RATE*(promoCost*0.1);
            return protectionCost;
        }
        else {
            protectionCost = PROTECTION_RATE*(FIRST5_KM_COST+(calculateTotalCostPerKm(50, distance)*0.1)-(calculatePromoCost()*0.1));
            return protectionCost;
        }
    }

    /**
     * Menghitung Total Biaya Perjalanan (setelah dipotong Promo dan ditambah Protection Cost)
     * @return Total ongkos perjalanan
     */

    @Override
    public double calculateTotalCost(){
        return FIRST5_KM_COST + (calculateTotalCostPerKm(50,distance)*0.1) + calculateProtectionCost() - (calculatePromoCost()*0.1);
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
        System.out.println("[5KMPe] Rp "+FIRST5_KM_COST+" (+)");
        System.out.println("[KMSel] Rp "+calculateTotalCostPerKm(50, distance)*0.1+" (+)");
        System.out.println("[Promo] Rp "+calculatePromoCost()*0.1+" (-)");
        System.out.println("[Prtks] Rp "+calculateProtectionCost()+" (+)");
        System.out.println("[TOTAL] Rp "+calculateTotalCost());
        System.out.println();
        peta.resetMap();
    }
}