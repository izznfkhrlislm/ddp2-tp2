/**
 * Created by Izzan Fakhril Islam (1606875806)
 * Kelas    : DDP 2 - A
 * Project  : TugasPemrograman2v2
 * Date     : 30/04/2017
 * Time     : 20:58 PM
 *
 * Class yang digunakan untuk membentuk layanan Owjek (mengimplementasi Interface Payable)
 */

import java.util.LinkedList;

public class OwjekService implements Payable {

    private int minYearAllowed;
    private int minTopSpeed;
    private int minCc;
    private double costPerKm;
    private double promoRate;
    private double fixedCost;
    private String serviceType;

    /**
     * Constructor Kelas OwjekService
     * Fungsi: Membentuk kelas OwjekService dengan nilai-nilai default 0
     */

    public OwjekService(){
        this.minYearAllowed = 0;
        this.minTopSpeed = 0;
        this.minCc = 0;
        this.costPerKm = 0;
        this.promoRate = 0;
        this.fixedCost = 0;
    }

    /**
     * Shortest Path (Pengukur jarak terpendek antara 2 koordinat pada Map)
     * Menggunakan dasar algoritma BFS (Breadth-First Search)
     * Referensi: http://stackoverflow.com/questions/10099221/breadth-first-search-on-an-8x8-grid-in-java dengan sedikit perubahan
     * @param startCoordinate koordinat awal (Start) dari perjalanan
     * @param finishCoordinate koordinat akhir (Finish) dari perjalanan
     * @param map objek Peta yang ingin dihitung jarak nya
     * @return Integer sebuah nilai jarak terdekat
     */

    public static int shortestPath(String startCoordinate, String finishCoordinate, Map map){
        int distance = 0;
        LinkedList<int[]> queue = new LinkedList<>();

        //Mengubah string Koordinat menjadi index pada Array Map
        int startI = ((((int) startCoordinate.charAt(0))-65)*10)+(Integer.parseInt(startCoordinate.substring(1,2)));
        int startJ = ((((int) startCoordinate.charAt(2))-81)*10)+(Integer.parseInt(startCoordinate.substring(3)));
        int finishI = ((((int) finishCoordinate.charAt(0))-65)*10)+(Integer.parseInt(finishCoordinate.substring(1,2)));
        int finishJ = ((((int) finishCoordinate.charAt(2))-81)*10)+(Integer.parseInt(finishCoordinate.substring(3)));

        int[] start = {startI, startJ, 0};
        queue.add(start);
        map.set('S', startI, startJ);

        //Traceback: Untuk menelusuri kembali alur perjalanan dari Finish ke Start
        int[][][] traceback = new int[Map.HEIGHT][Map.WIDTH][2];

        //visitHistory: Untuk menandai tempat-tempat yang sudah dilewati agar tidak dilewati kembali
        boolean[][] visitHistory = new boolean[Map.HEIGHT][Map.WIDTH];

        while (queue.peek() != null){
            int[] arr = queue.remove();

            //Pengecekan arah Utara
            if (arr[0]-1 >= 0 && map.getMap()[arr[0]-1][arr[1]] != '#' && visitHistory[arr[0]-1][arr[1]] != true){
                if (map.getMap()[arr[0]-1][arr[1]] == 'F'){
                    traceback[arr[0]-1][arr[1]][0] = arr[0];
                    traceback[arr[0]-1][arr[1]][1] = arr[1];
                    arr[2]++;
                    break;
                }
                else {
                    visitHistory[arr[0]-1][arr[1]] = true;
                    traceback[arr[0]-1][arr[1]][0] = arr[0];
                    traceback[arr[0]-1][arr[1]][1] = arr[1];
                    int[] temp = {arr[0]-1, arr[1], arr[2]+1};
                    queue.add(temp);
                }
            }

            //Pengecekan arah Barat
            if (arr[1]-1 >= 0 && map.getMap()[arr[0]][arr[1]-1] != '#' && visitHistory[arr[0]][arr[1]-1] != true){
                if (map.getMap()[arr[0]][arr[1]-1] == 'F'){
                    traceback[arr[0]][arr[1]-1][0] = arr[0];
                    traceback[arr[0]][arr[1]-1][1] = arr[1];
                    arr[2]++;
                    break;
                }
                else {
                    visitHistory[arr[0]][arr[1]-1] = true;
                    traceback[arr[0]][arr[1]-1][0] = arr[0];
                    traceback[arr[0]][arr[1]-1][1] = arr[1];
                    int[] temp = {arr[0], arr[1]-1, arr[2]+1};
                    queue.add(temp);
                }
            }

            //Pengecekan arah Timur
            if (arr[1]+1 <= Map.WIDTH && map.getMap()[arr[0]][arr[1]+1] != '#' && visitHistory[arr[0]][arr[1]+1] != true){
                if (map.getMap()[arr[0]][arr[1]+1] == 'F'){
                    traceback[arr[0]][arr[1]+1][0] = arr[0];
                    traceback[arr[0]][arr[1]+1][1] = arr[1];
                    arr[2]++;
                    break;
                }
                else{
                    visitHistory[arr[0]][arr[1]+1] = true;
                    traceback[arr[0]][arr[1]+1][0] = arr[0];
                    traceback[arr[0]][arr[1]+1][1] = arr[1];
                    int[] temp = {arr[0], arr[1]+1, arr[2]+1};
                    queue.add(temp);
                }
            }

            //Pengecekan arah Selatan
            if (arr[0]+1 <= Map.HEIGHT && map.getMap()[arr[0]+1][arr[1]] != '#' && visitHistory[arr[0]+1][arr[1]] != true){
                if (map.getMap()[arr[0]+1][arr[1]] == 'F'){
                    traceback[arr[0]+1][arr[1]][0] = arr[0];
                    traceback[arr[0]+1][arr[1]][1] = arr[1];
                    arr[2]++;
                    break;
                }
                else {
                    visitHistory[arr[0]+1][arr[1]] = true;
                    traceback[arr[0]+1][arr[1]][0] = arr[0];
                    traceback[arr[0]+1][arr[1]][1] = arr[1];
                    int[] temp = {arr[0]+1, arr[1], arr[2]+1};
                    queue.add(temp);
                }
            }
            distance = arr[2];
        }
        int iNow = finishI;
        int jNow = finishJ;
        map.set('S', startI, startJ);

        //Pengecekan kembali dari Finish ke Start (Traceback)
        while (!(iNow == startI && jNow == startJ)){
            map.getMap()[iNow][jNow] = '.';
            int iSekarang = iNow;
            int jSekarang = jNow;
            iNow = traceback[iSekarang][jSekarang][0];
            jNow = traceback[iSekarang][jSekarang][1];
            if (iNow == 0 && jNow == 0){
                break;
            }
        }
        map.set('F', finishI, finishJ);
        return distance+2;
    }

    /**
     * Overriden Methods dari Interface Payable untuk class OwjekService
     * Akan di-Override lagi menjadi lebih spesifik oleh masing-masing class layanan Owjek
     */

    @Override
    public double calculateTotalCostPerKm(int from, int to) {
        return 0;
    }

    @Override
    public double calculateTotalCost() {
        return 0;
    }

    @Override
    public double calculatePromoCost() {
        return 0;
    }

    @Override
    public double calculateProtectionCost(){
        return 0;
    }

    @Override
    public void printTripSummary() {
        System.out.println("TERIMA KASIH TELAH MENGGUNAKAN OW-JEK.");
    }

    /**
     * Attribute getter untuk class OwjekService
     */

    public void setMinYearAllowed(int minYearAllowed) {
        this.minYearAllowed = minYearAllowed;
    }


    public void setMinTopSpeed(int minTopSpeed) {
        this.minTopSpeed = minTopSpeed;
    }


    public void setMinCc(int minCc) {
        this.minCc = minCc;
    }


    public void setCostPerKm(double costPerKm) {
        this.costPerKm = costPerKm;
    }


    public void setPromoRate(double promoRate) {
        this.promoRate = promoRate;
    }


    public void setFixedCost(double fixedCost) {
        this.fixedCost = fixedCost;
    }


    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
