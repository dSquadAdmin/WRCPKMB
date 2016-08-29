package cluster.plots;

import cluster.clusterer.data.Clusters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ksv on 8/23/16.
 */
public class CountMap {
    private List dataset;
    private Map countmaps;
    private List keys;


    public CountMap(ArrayList<String> set) {
        this.dataset = new ArrayList<> ( set );
        countmaps = new HashMap<String, Integer> ( );
        keys = new ArrayList<> ( computeStrCount ( ));
    }

    public ArrayList<String> getKeys(){
        return (ArrayList<String>) keys;
    }

    public Map getCountMaps(){
        return countmaps;
    }


    private ArrayList<String> computeStrCount() {
        ArrayList<String> list = new ArrayList<> ( );
        String temp = dataset.get ( 1 ).toString ().split ( "," )[6];
        list.add (0, temp );
        for (Object s : dataset) {
            String str = s.toString ( ).split ( "," )[6];
            if(!str.equals ( list.get ( 0 ) )){
                list.add ( 0, str );
            }
            int cnt = 0;
            for (Object s1 : dataset) {
                String inst = s1.toString ( ).split ( "," )[6];
                if (inst.equals ( str )) {
                    cnt++;
                }
            }
            if(!countmaps.containsKey ( str )){
                countmaps.put ( str, cnt );
            }
        }

        System.out.print ( list );
        return list;
    }
}
