import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Simulation {
	
    public ArrayList loadItems(String fileName){
        ArrayList<Item> storage = new ArrayList();
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                int delimiter = line.indexOf("=");
                Item itm = new Item();
                itm.name = line.substring(0, delimiter);
                itm.weight = Integer.valueOf(line.substring(delimiter+1));
                storage.add(itm);
            }

            sc.close();

        }catch (FileNotFoundException e){
            System.out.println("\n! Caught FileNotFoundException !\n" + e + "\n");
        }catch (Exception e){
            System.out.println("\n! Caught common Exception !\n" + e + "\n");
        }

        return storage;
    }

    
    
    public void storageSort(ArrayList<Item> storage){
        //sort from the end of the list
        for(int i=storage.size()-2; i>=0; i--){
            int j = i+1;
            if(storage.get(i).weight<storage.get(j).weight){
                Item itm = storage.get(i);
                storage.remove(i);
                j--;
                do{
                    j++;
                } while (j<storage.size() && itm.weight<storage.get(j).weight);
                storage.add(j, itm);
            }
        }

        /* DON'T DELETE FOR A WHILE: variant with sorting from the beginning of the list
        for(int i=1; i<storage.size(); i++) {
            int j = i - 1;
            if (storage.get(i).weight > storage.get(j).weight) {
                Item itm = storage.get(i);
                storage.remove(i);
                do {
                    j--;
                } while (j >= 0 && itm.weight > storage.get(j).weight);
                storage.add(j + 1, itm);
            }
        }*/
    }



    /*
     * Create rocket fleet in place items in its rocket.
     * The type of rocket is transfered via rocketClass.
     * Not ideal solution but work, better than 2 separate method.
     */
    public ArrayList loadUx(ArrayList<Item> storage, Class rocketClass){
        ArrayList<Ux> rocketFleet = new ArrayList();

        do {
        	Ux rct;
        	if (rocketClass.equals(U1.class))
        		rct = new U1();
        	else if (rocketClass.equals(U2.class))
        		rct = new U2();
        	else {
        		System.out.println("! ERROR: not correct type in method Simulation.loadUx");
        		return null;
        	}
        	
            int itemCount = 0;

            do {
                if(storage.isEmpty() )
                    break;
                if (itemCount>storage.size()-1){
                    if(rct.freeSpace()>0)
                        optimizeRocketLoading(rct, storage);

                    rocketFleet.add(rct);
                    break;
                }
                if(rct.canCarry(storage.get(itemCount)) ) {
                    rct.carry(storage.get(itemCount));
                    storage.remove(itemCount);
                    if (rct.freeSpace()<=0 || storage.isEmpty()) {       //shortcut for reducing number of cycles
                        rocketFleet.add(rct);
                        break;
                    }
                } else {
                    itemCount++;
                }
            } while (true);
        } while (!storage.isEmpty());

        return rocketFleet;
    }



    private void optimizeRocketLoading(Ux rct, ArrayList<Item> storage){
        ArrayList<ExchangeVariant> variantList = new ArrayList();
        ExchangeVariant exchange;

        int minValue = rct.freeSpace();
        int minIndex = -1;

        for(int i=rct.rocketItems.size()-1; i>=0; i--){
            exchange = new ExchangeVariant();
            exchange.removedItems.add(rct.rocketItems.get(i));

            int itemWeight = rct.rocketItems.get(i).weight;
            int totalFree = rct.freeSpace() + itemWeight;
            for(int j=0; j<storage.size(); j++){
                if(storage.get(j).weight<itemWeight && storage.get(j).weight<=totalFree){
                    exchange.addedItems.add(storage.get(j));
                    totalFree -= storage.get(j).weight;
                    if(totalFree==0)
                        break;
                }
            }
            exchange.freeSpace = totalFree;
            variantList.add(exchange);

            if(totalFree<minValue) {
                minValue = totalFree;
                minIndex = variantList.size()-1;
            }
        }

        if(minIndex>-1)
            rct.reloading(variantList.get(minIndex), storage);
    }



    public ResultExpedition runSimulation(ArrayList<Ux> rocketFleet, boolean verbose){
        if (verbose)
            System.out.println("\n\n=== Simulation starts ===");
        int budget = 0;
        int numberLaunches = 0;
        for (int i=0; i<rocketFleet.size(); i++){
            String result;
            numberLaunches++;
            budget += rocketFleet.get(i).cost;

            if (!rocketFleet.get(i).launch() ) {
                result = "! Rocket #" + (i+1) + " exploded at start.";
                i--;
            } else if (!rocketFleet.get(i).land() ) {
                result = "! Rocket #" + (i+1) + " crashed during a landing.";
                i--;
            } else {
                result = "Rocket #" + (i+1) + " succeeded";
            }

            if (verbose)
                System.out.println(result);
        }

        return new ResultExpedition(budget, numberLaunches);
    }
}
