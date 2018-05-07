import java.util.ArrayList;
import java.text.DecimalFormat;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        Simulation sim = new Simulation();

        /*
         * CREATE AND LOAD ROCKET FLEETS
         */
        ArrayList<Item> itemList1 = sim.loadItems("Phase-1.txt");
        ArrayList<Item> itemList2 = sim.loadItems("Phase-2.txt");

        sim.StorageSort(itemList1);
        sim.StorageSort(itemList2);

        ArrayList rocketFleetU1_ph1 = sim.loadU1((ArrayList)itemList1.clone());
        ArrayList rocketFleetU2_ph1 = sim.loadU2((ArrayList)itemList1.clone());
        ArrayList rocketFleetU1_ph2 = sim.loadU1((ArrayList)itemList2.clone());
        ArrayList rocketFleetU2_ph2 = sim.loadU2((ArrayList)itemList2.clone());

        //Optional showing loading of rockets
        if (true){
            System.out.println("=== Loading rockets ===");
            sim.ShowFleetDetails(rocketFleetU1_ph1);
            sim.ShowFleetDetails(rocketFleetU2_ph1);
            sim.ShowFleetDetails(rocketFleetU1_ph2);
            sim.ShowFleetDetails(rocketFleetU2_ph2);
        }



        /*
         * SIMULATE EXPEDITION
         */
        long budgetU1_ph1 = 0;
        long budgetU2_ph1 = 0;
        long budgetU1_ph2 = 0;
        long budgetU2_ph2 = 0;

        int countSimMax = 1; //10000000;
        boolean verboseRocket = true;
        if(countSimMax>3)
            verboseRocket = false;

        for(int countSim=1; countSim<=countSimMax; countSim++) {
            budgetU1_ph1 += sim.runSimulation(rocketFleetU1_ph1, verboseRocket);
            budgetU2_ph1 += sim.runSimulation(rocketFleetU2_ph1, verboseRocket);
            budgetU1_ph2 += sim.runSimulation(rocketFleetU1_ph2, verboseRocket);
            budgetU2_ph2 += sim.runSimulation(rocketFleetU2_ph2, verboseRocket);
        }

        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.HALF_UP);
        double budgetU1_ph1_av = Double.valueOf(df.format((double)budgetU1_ph1/countSimMax));
        double budgetU1_ph2_av = Double.valueOf(df.format((double)budgetU1_ph2/countSimMax));
        double budgetU2_ph1_av = Double.valueOf(df.format((double)budgetU2_ph1/countSimMax));
        double budgetU2_ph2_av = Double.valueOf(df.format((double)budgetU2_ph2/countSimMax));
        double budgetU1_total =  Double.valueOf(df.format((double)(budgetU1_ph1+budgetU1_ph2)/countSimMax));
        double budgetU2_total =  Double.valueOf(df.format((double)(budgetU2_ph1+budgetU2_ph2)/countSimMax));

        System.out.println("\n\n\n   === Phase 1 ===");
        System.out.println("Total budget for U1 phase 1 is " + budgetU1_ph1_av + " millions dollars.");
        System.out.println("Total budget for U2 phase 1 is " + budgetU2_ph1_av + " millions dollars.");

        System.out.println("\n   === Phase 2 ===");
        System.out.println("Total budget for U1 phase 1 is " + budgetU1_ph2_av + " millions dollars.");
        System.out.println("Total budget for U2 phase 1 is " + budgetU2_ph2_av + " millions dollars.");

        System.out.println("\n   === Total ===");
        System.out.println("Total budget for U1 is " + budgetU1_total + " millions dollars.");
        System.out.println("Total budget for U2 is " + budgetU2_total + " millions dollars.");
    }
}