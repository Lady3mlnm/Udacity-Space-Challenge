import java.util.ArrayList;
import java.text.DecimalFormat;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        Simulation sim = new Simulation();

        /*
         * CREATE AND LOAD ROCKET FLEETS
         */
        //Read lists of items from the file
        ArrayList<Item> itemList1 = sim.loadItems("Phase-1.txt");
        ArrayList<Item> itemList2 = sim.loadItems("Phase-2.txt");

        //Sort items by weight
        sim.storageSort(itemList1);
        sim.storageSort(itemList2);

        //Load items in rockets, create rocket fleets
        ArrayList<Ux> rocketFleetU1_ph1 = sim.loadUx((ArrayList)itemList1.clone(), U1.class);
        ArrayList<Ux> rocketFleetU2_ph1 = sim.loadUx((ArrayList)itemList1.clone(), U2.class);
        ArrayList<Ux> rocketFleetU1_ph2 = sim.loadUx((ArrayList)itemList2.clone(), U1.class);
        ArrayList<Ux> rocketFleetU2_ph2 = sim.loadUx((ArrayList)itemList2.clone(), U2.class);

        //Optional showing info about initial items and fleets
        if (true){
            InfoOut.infoAboutItems("   === Items for phase-1 ===", itemList1);
            InfoOut.infoAboutItems("\n   === Items for phase-2 ===", itemList2);
            InfoOut.totalInfoAboutFleet("\n   === Phase 1, U1 fleet cargo ===", rocketFleetU1_ph1);
            InfoOut.totalInfoAboutFleet("\n   === Phase 1, U2 fleet cargo ===", rocketFleetU2_ph1);
            InfoOut.totalInfoAboutFleet("\n   === Phase 2, U1 fleet cargo ===", rocketFleetU1_ph2);
            InfoOut.totalInfoAboutFleet("\n   === Phase 2, U2 fleet cargo ===", rocketFleetU2_ph2);
            System.out.println("\n");
        }
        
        //Optional showing loading of rockets
        if (true){
            System.out.println("=== Loading rockets ===");
            InfoOut.showLoadingDetails(rocketFleetU1_ph1);
            InfoOut.showLoadingDetails(rocketFleetU2_ph1);
            InfoOut.showLoadingDetails(rocketFleetU1_ph2);
            InfoOut.showLoadingDetails(rocketFleetU2_ph2);
            System.out.println("\n");
        }


        
        /*
         * SIMULATE EXPEDITION
         */
        long budgetU1_ph1 = 0;
        long numberLaunchesU1_ph1 = 0;
        long budgetU2_ph1 = 0;
        long numberLaunchesU2_ph1 = 0;
        long budgetU1_ph2 = 0;
        long numberLaunchesU1_ph2 = 0;
        long budgetU2_ph2 = 0;
        long numberLaunchesU2_ph2 = 0;

        int numberSims = 10_000_000; //10_000_000;  //number of simulations
        System.out.println("Number of test simulations: " + numberSims);
        
        boolean verboseRocket = true;               //show detailed info about launches
        if(numberSims>3)
            verboseRocket = false;

        //Loop with simulations
        ResultExpedition res;
        for(int countSim=1; countSim<=numberSims; countSim++) {
        	res = sim.runSimulation(rocketFleetU1_ph1, verboseRocket);
            budgetU1_ph1 += res.budget;
            numberLaunchesU1_ph1 += res.numberLaunches;
            
            res = sim.runSimulation(rocketFleetU2_ph1, verboseRocket);
            budgetU2_ph1 += res.budget;
            numberLaunchesU2_ph1 += res.numberLaunches;
            
            res = sim.runSimulation(rocketFleetU1_ph2, verboseRocket);
            budgetU1_ph2 += res.budget;
            numberLaunchesU1_ph2 += res.numberLaunches;
            
            res = sim.runSimulation(rocketFleetU2_ph2, verboseRocket);
            budgetU2_ph2 += res.budget;
            numberLaunchesU2_ph2 += res.numberLaunches;
        }

        //Output info about results
        InfoOut.resultSimulation("   === phase1, U1 ===", budgetU1_ph1, numberLaunchesU1_ph1, numberSims, rocketFleetU1_ph1.size());
        InfoOut.resultSimulation("   === phase1, U2 ===", budgetU2_ph1, numberLaunchesU2_ph1, numberSims, rocketFleetU2_ph1.size());
        
        InfoOut.resultSimulation("   === phase2, U1 ===", budgetU1_ph2, numberLaunchesU1_ph2, numberSims, rocketFleetU1_ph2.size());
        InfoOut.resultSimulation("   === phase2, U2 ===", budgetU2_ph2, numberLaunchesU2_ph2, numberSims, rocketFleetU2_ph2.size());
        
        System.out.println();
        InfoOut.resultSimulation("   === total, U1 ===", budgetU1_ph1+budgetU1_ph2, numberLaunchesU1_ph1+numberLaunchesU1_ph2, numberSims, rocketFleetU1_ph1.size()+rocketFleetU1_ph2.size());
        InfoOut.resultSimulation("   === total, U2 ===", budgetU2_ph1+budgetU2_ph2, numberLaunchesU2_ph1+numberLaunchesU2_ph2, numberSims, rocketFleetU2_ph1.size()+rocketFleetU2_ph2.size());
    }
    

}