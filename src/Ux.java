import java.util.ArrayList; //?

abstract class Ux implements Ux_interface {
    public int weightRocket;   //in kg
    public int weightLoading;
    public int weightMax;
    public double ChanceLaunchExplosion = 1;
    public double ChanceLandingCrash = 1;
    public int cost;           //in millions dollars
    public ArrayList<Item> rocketItems = new ArrayList();

    
    public abstract boolean launch();

    
    public abstract boolean land();

    
    final public boolean canCarry(Item itm){
        return this.weightMax - this.weightRocket - this.weightLoading >= itm.weight;
    }

    
    final public void carry(Item itm){
        int j;
        for(j=0; j<this.rocketItems.size(); j++)
            if (this.rocketItems.get(j).weight<=itm.weight)
                break;
        this.rocketItems.add(j, itm);
        this.weightLoading += itm.weight;
    }

    
    final public void unload(Item itm){
        boolean isDone = false;
        int j;
        for(j=0; j<this.rocketItems.size(); j++)
            if(this.rocketItems.get(j).name==itm.name && this.rocketItems.get(j).weight==itm.weight){
                this.rocketItems.remove(j);
                isDone = true;
                break;
            }

        if(isDone)
            this.weightLoading -= itm.weight;
         else
            System.out.println("! Error in Rocket.unload. Item " + itm.name + "-" + itm.weight + "kg is not in the rocket.");
    }

    
    final public int freeSpace(){
        return this.weightMax - this.weightRocket - this.weightLoading;
    }

    
    final public void reloading(ExchangeVariant exchange, ArrayList<Item> storage){
        //unload items from rocket and add it to storage
        for(int i=0; i<exchange.removedItems.size(); i++){
            Item workItem = exchange.removedItems.get(i);
            int j;

            this.unload(workItem);

            for(j=0; j<storage.size(); j++)
                if (storage.get(j).weight<=workItem.weight)
                    break;
            storage.add(j, workItem);
        }

        //take items from storage and load them in the rocket
        for(int i=0; i<exchange.addedItems.size(); i++){
            Item workItem = exchange.addedItems.get(i);
            int j;

            for(j=0; j<storage.size(); j++)
                if (storage.get(j)==workItem) {
                    storage.remove(j);
                    break;
                }

            this.carry(workItem);
        }
    }
}
