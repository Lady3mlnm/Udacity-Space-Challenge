/*
 * Class contains function for displaying different information
 */

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class InfoOut {
	
	//Show info about items in received list
    public static void infoAboutItems(String msg, ArrayList<Item> itemList) {
    	int countItems = 0;
    	int countWeight = 0;
    	for( Item i : itemList) {
    		countItems++;
    		countWeight += i.weight;
    	}
    	
    	if (!msg.isEmpty())
    		System.out.println(msg);
    	System.out.println("Number of items: " + countItems);
    	System.out.println("Weight of items: " + countWeight);
    }
    
    
    
    /*
    //Show detailed info about items in received list
    public static void infoAboutItemsDetailed(ArrayList<Item> storage){
        System.out.println();
        for(int i=0; i<storage.size(); i++){
            System.out.println(storage.get(i).name + ": " + storage.get(i).weight);
        }
        System.out.println();
    }
    */
    
    
    
    //Show total cursory info about received fleet of rockets
    public static void totalInfoAboutFleet(String msg, ArrayList<Ux> rocketFleet) {
    	int countItems = 0;
    	int countWeight = 0;
    	for( Ux rct : rocketFleet) {
        	for( Item i : rct.rocketItems) {
        		countItems++;
        		countWeight += i.weight;
        	}
    	}
    	
    	if (!msg.isEmpty())
    		System.out.println(msg);
    	System.out.println("Number of items: " + countItems);
    	System.out.println("Weight of items: " + countWeight);
    }
    
    
    
    //Show detail info about loading of rockets
    public static void showLoadingDetails(ArrayList<Ux> rocketFleet){
        System.out.println();

        for(int i=0; i<rocketFleet.size(); i++){
            Ux rct = rocketFleet.get(i);
            System.out.println("\nRocket #" + (i+1) + ":");

            for(int j=0; j<rct.rocketItems.size(); j++)
                System.out.println("   " + rct.rocketItems.get(j).name + " - " + rct.rocketItems.get(j).weight);

            System.out.println("                     unused loading - " + rct.freeSpace());
        }
    }
    
    
    
    //Show final result of simulation
    public static void resultSimulation(String msg, long budget, long numberLaunches, int numberSims, int numberRocketsIdeally) {
    	DecimalFormat dfBudget = new DecimalFormat("#.#");
    	dfBudget.setRoundingMode(RoundingMode.HALF_UP);
    	DecimalFormat dfLaunches = new DecimalFormat("#.##");
    	dfLaunches.setRoundingMode(RoundingMode.HALF_UP);
    	DecimalFormat dfPercent = new DecimalFormat("#.##");
    	dfPercent.setRoundingMode(RoundingMode.HALF_UP);
    	
        double budgetAv = Double.valueOf(dfBudget.format((double)budget/numberSims));
        double launchesAv = Double.valueOf(dfLaunches.format((double)numberLaunches/numberSims));
        double percentSuccess = Double.valueOf(dfPercent.format(numberRocketsIdeally*100/launchesAv));
        
        //Received parameters:
		//System.out.println("budget: " + budget);
		//System.out.println("numberLaunches: " + numberLaunches);
		//System.out.println("numberSims: " + numberSims);
		//System.out.println("numberRocketsIdeally: " + numberRocketsIdeally);
		//System.out.println();
        
        System.out.println();
    	if (!msg.isEmpty())
    		System.out.println(msg);
        System.out.println("Average budget: $ " + budgetAv + " million");
        System.out.println("Average number of launches: " + launchesAv);
        System.out.println("Number of launches ideally: " + numberRocketsIdeally);
        System.out.println("Percent of successful launches: " + percentSuccess + "%");
    }
}