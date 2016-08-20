package cluster.plots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ksv on 8/23/16.
 */
public class CountMap {
    private List dataset;
    private Map count;

    public CountMap(ArrayList<String> set) {
        this.dataset = new ArrayList<> ( set );
        count = new HashMap<String, Integer> ( );
        setCount ( );
    }

    private void setCount() {
        ArrayList<String> list = new ArrayList<> ( );
        ArrayList<ArrayList<String>> items = new ArrayList<> ( );
        for (Object s : dataset) {
            list.clear ( );
            String str = s.toString ( ).split ( "," )[6];
            int count = 0;
            for (Object s1 : dataset) {
                String inst = s1.toString ( ).split ( "," )[6];
                if (inst.equals ( str )) {
                    count++;
                }
            }
            System.out.print ( str + "\t" + count + "\n" );
        }
    }
}
