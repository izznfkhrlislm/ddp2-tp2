/**
 * Created by Izzan Fakhril Islam (1606875806)
 * Kelas    : DDP 2 - A
 * Project  : TugasPemrograman2v2
 * Date     : 30/04/2017
 * Time     : 21:32 PM
 *
 * Kelas Utama (Main Class)
 */

import java.util.Scanner;

public class Trip {

    /**
     * Main Method dari project TugasPemrograman2v2
     */

    public static void main(String[] inputs){
        //Flag hasFinished: penanda apakah program telah selesai berjalan atau belum
        boolean hasFinished = false;

        Map peta = new Map();
        Scanner scan = new Scanner(System.in);

        while (!hasFinished){
            try{
                System.out.println("Selamat Datang di OW-JEK! Ketik 'help' untuk petunjuk penggunaan.");
                System.out.println("Masukkan Perintah Anda >> ");
                String input = scan.nextLine();
                String[] input1 = input.split(" ");

                //Perintah untuk menunjukkan map (show map)
                if (input1[0].equalsIgnoreCase("SHOW") && input1[1].equalsIgnoreCase("MAP")){
                    peta.resetMap();
                    peta.print();
                }

                //Perintah inti (menginisiasi perjalanan dengan OW-JEK)
                else if (input1[0].equalsIgnoreCase("GO")){
                    OwjekService ojek;
                    String startCoordinate = input1[2];
                    String finishCoordinate = input1[4];

                    //Mengubah string Koordinat menjadi Index pada Array Map
                    int startI = ((((int) startCoordinate.charAt(0))-65)*10)+(Integer.parseInt(startCoordinate.substring(1,2)));
                    int startJ = ((((int) startCoordinate.charAt(2))-81)*10)+(Integer.parseInt(startCoordinate.substring(3)));
                    int finishI = ((((int) finishCoordinate.charAt(0))-65)*10)+(Integer.parseInt(finishCoordinate.substring(1,2)));
                    int finishJ = ((((int) finishCoordinate.charAt(2))-81)*10)+(Integer.parseInt(finishCoordinate.substring(3)));

                    //Error handling: Jika koordinat yang dimasukkan adalah bukan jalan '#'
                    if (peta.get(startI, startJ) == '#' || peta.get(finishI, finishJ) == '#'){
                        if (peta.get(startI, startJ) == '#'){
                            System.out.println("ERROR: Daerah "+startCoordinate+" bukan jalan!");
                            System.out.println();
                        }
                        else if (peta.get(finishI,finishJ) == '#'){
                            System.out.println("ERROR: Daerah "+finishCoordinate+" bukan jalan!");
                            System.out.println();
                        }
                        else {
                            System.out.println("ERROR: Daerah "+startCoordinate+" dan "+finishCoordinate+" bukan jalan!");
                            System.out.println();
                        }
                    }

                    //Error Handling: Jika koordinat awal sama dengan koordinat akhir (perjalanan 0 KM)
                    else if (startCoordinate.equalsIgnoreCase(finishCoordinate)){
                        System.out.println("ERROR: Anda tidak dapat melakukan perjalanan dengan jarak 0 KM!");
                        System.out.println();
                    }

                    else {
                        //Menandai koordinat awal dan akhir
                        peta.set('S', startI, startJ);
                        peta.set('F', finishI, finishJ);

                        //Layanan Paket Regular
                        if (input1[7].equalsIgnoreCase("REGULAR")){
                            int distance = OwjekService.shortestPath(startCoordinate, finishCoordinate, peta);
                            ojek = new OwjekRegular(startCoordinate, finishCoordinate, peta);
                            ojek.calculateTotalCostPerKm(0, distance);
                            ojek.calculatePromoCost();
                            ojek.calculateTotalCost();
                            ojek.printTripSummary();
                        }

                        //Layanan Paket Sporty
                        else if (input1[7].equalsIgnoreCase("SPORTY")){
                            int distance = OwjekService.shortestPath(startCoordinate, finishCoordinate, peta);
                            ojek = new OwjekSporty(startCoordinate, finishCoordinate, peta);
                            ojek.calculateTotalCostPerKm(0, distance);
                            ojek.calculatePromoCost();
                            ojek.calculateTotalCost();
                            ojek.calculateProtectionCost();
                            ojek.printTripSummary();
                        }

                        //Layanan Paket Regular
                        else if (input1[7].equalsIgnoreCase("EXCLUSIVE")){
                            int distance = OwjekService.shortestPath(startCoordinate, finishCoordinate, peta);
                            ojek = new OwjekExclusive(startCoordinate, finishCoordinate, peta);
                            ojek.calculateTotalCostPerKm(0, distance);
                            ojek.calculatePromoCost();
                            ojek.calculateProtectionCost();
                            ojek.calculateProtectionCost();
                            ojek.printTripSummary();
                        }
                    }
                }

                //Menampilkan bantuan penggunaan aplikasi (help)
                else if (input1[0].equalsIgnoreCase("HELP")){
                    System.out.println("show map --> Menampilkan Peta Perjalanan");
                    System.out.println("go from [START] to [FINISH] with OW-JEK [TYPE] --> Inisiasi Keberangkatan dengan Tipe tertentu");
                    System.out.println("exit --> keluar dari aplikasi");
                    System.out.println();
                }

                //Keluar dari program
                else if (input1[0].equalsIgnoreCase("EXIT")){
                    hasFinished = true;
                }

                else {
                    System.out.println("ERROR: Perintah tidak dikenali!");
                    System.out.println();
                }
            }

            catch (Exception e){
                System.out.println("ERROR: Perintah tidak dikenali!");
                System.out.println();
            }
        }
    }
}