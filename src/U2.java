public class U2 extends Ux {
    U2(){
        weightRocket = 18000;
        weightLoading = 0;
        weightMax = 29000;
        ChanceLaunchExplosion = 0.04;
        ChanceLandingCrash = 0.08;
        cost = 120;               //in millions dollars
    }

    
    public boolean launch(){
    	return Math.random() > ChanceLaunchExplosion * ((double)(weightLoading) / (weightMax-weightRocket));
    }

    
    public boolean land(){
    	return Math.random() > ChanceLandingCrash * ((double)(weightLoading) / (weightMax-weightRocket));
    }
}