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



    public void ShowStorage(ArrayList<Item> storage){
        System.out.println();
        for(int i=0; i<storage.size(); i++){
            System.out.println(storage.get(i).name + ": " + storage.get(i).weight);
        }
        System.out.println();
    }



    public void ShowFleetDetails(ArrayList<Rocket> rocketFleet){
        System.out.println();

        for(int i=0; i<rocketFleet.size(); i++){
            Rocket rct = rocketFleet.get(i);
            System.out.println("Rocket #" + (i+1) + ":");

            for(int j=0; j<rct.rocketItems.size(); j++)
                System.out.println("   " + rct.rocketItems.get(j).name + " - " + rct.rocketItems.get(j).weight);

            System.out.println("                     unused loading - " + rct.freeSpace() + "\n");
        }

        System.out.println();
    }



    public void StorageSort(ArrayList<Item> storage){
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



    public ArrayList loadU1(ArrayList<Item> storage){
        //ArrayList<Item> tmpList = items;
        ArrayList<U1> rocketFleet = new ArrayList();

        do {
            U1 rct = new U1();
            int itemCount = 0;

            do {
                if(storage.isEmpty() )
                    break;
                if (itemCount>storage.size()-1){
                    if(rct.freeSpace()>0)
                        OptimizeRocketLoading(rct, storage);

                    rocketFleet.add(rct);
                    break;
                }
                if(rct.canCarry(storage.get(itemCount)) ) {
                    rct.carry(storage.get(itemCount));
                    storage.remove(itemCount);
                    if (rct.freeSpace()<=0) {       //shortcut for reducing number of cycles
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



    private void OptimizeRocketLoading(Rocket rct, ArrayList<Item> storage){
        ArrayList<ExchangeVariant> variantList = new ArrayList();
        ExchangeVariant exchange;

//        exchange.freeSpace = rct.freeSpace();
//        variantList.add(exchange);
//        System.out.println("Done. rct.freeSpace(): " + exchange.freeSpace);

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









    public ArrayList loadU2(ArrayList<Item> storage){
        //ArrayList<Item> tmpList = items;
        ArrayList<U2> rocketFleet = new ArrayList();

        do {
            U2 rct = new U2();
            int itemCount = 0;

            do {
                if(storage.isEmpty() )
                    break;
                if (itemCount>storage.size()-1){
                    if(rct.freeSpace()>0)
                        OptimizeRocketLoading(rct, storage);

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


    public int runSimulation(ArrayList<Rocket> rocketFleet, boolean verbose){
        if (verbose)
            System.out.println("\n\n=== Simulation starts ===");
        int budget = 0;
        for (int i=0; i<rocketFleet.size(); i++){
            String result;
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

        return budget;
    }
}
