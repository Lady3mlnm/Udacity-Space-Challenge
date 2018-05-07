public class U2 extends Rocket {
    U2(){
        weightRocket = 18000;
        weightLoading = 0;
        weightMax = 29000;
        ChanceLaunchExplosion = 0.04;
        ChanceLandingCrash = 0.08;
        cost = 120;               //in millions dollars
    }

    public boolean launch(){
        return Math.random() > ChanceLaunchExplosion * ((double)(weightRocket + weightLoading) / weightMax);
    }

    public boolean land(){
        return Math.random() > ChanceLandingCrash * ((double)(weightRocket + weightLoading) / weightMax);
    }
}