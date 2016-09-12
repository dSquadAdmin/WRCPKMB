package cluster.plots;

import cluster.clusterer.data.Clusters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ksv on 8/23/16.
 *
 * Copyright (c) All right reserved Keshav Bist.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * @Author Keshav Bist <squad.reconn@gmail.com>
 * @URI http://keshavbist.com.np
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
