import java.util.ArrayList;
import java.util.List;

public class CI {
    public static Double[] calc95CI(ArrayList<Double> dps){
        double mean = 0;
        double var_ = 0;
        for (double dp: dps){
            mean += dp;
        }
        for (double dp: dps){
            var_ += Math.pow(dp - mean, 2.0);
        }

        double ME = Math.sqrt(var_/dps.size());

        Double[] CI = new Double[2];
        CI[0] = mean - 1.96*ME;
        CI[1] = mean + 1.96*ME;
        return CI;
    }


    public static boolean noOverlappingCI(ArrayList< ArrayList<Double> > measurements){
        List<Double[]> CIs = new ArrayList<>();
        for(ArrayList<Double> measure: measurements){
            CIs.add(calc95CI(measure));
        }

        for (Double[] CI: CIs){
            for (Double[] OtherCI: CIs){
                if (!OtherCI.equals(CI)){
                    if( !( CI[1] <= OtherCI[0] || OtherCI[1] <= CI[0]) ){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
