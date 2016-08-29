package cluster.plots;

import cluster.clusterer.data.Clusters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by ksv on 8/28/16.
 */
public class ClusterMap {
    private List clusters;
    private List components;
    private List keys;
    public ClusterMap(ArrayList<Clusters> clusters){
        this.clusters = clusters;
    }
}
