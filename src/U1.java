public class U1 extends Ux {
    U1(){
        weightRocket = 10000;
        weightLoading = 0;
        weightMax = 18000;
        ChanceLaunchExplosion = 0.05;
        ChanceLandingCrash = 0.01;
        cost = 100;               //in millions dollars
    }

    
    public boolean launch(){
    	return Math.random() > ChanceLaunchExplosion * ((double)(weightLoading) / (weightMax-weightRocket));
    }

    
    public boolean land(){
    	return Math.random() > ChanceLandingCrash * ((double)(weightLoading) / (weightMax-weightRocket));
    }
}
