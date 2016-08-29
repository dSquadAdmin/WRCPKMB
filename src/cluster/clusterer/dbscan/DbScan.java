package cluster.clusterer.dbscan;

import cluster.clusterer.data.Clusters;
import cluster.clusterer.data.Node;

import java.util.ArrayList;

/**
 * Created by ksv on 5/23/16.
 * @author ksv
 *
 */
public class DbScan {
    private double minRadius;
    private int minPts;
    public ArrayList <Clusters> clusters;
    public ArrayList<Node> dataSet;
    //private int cid = 0;

    /**
     * @param data
     * @param minRadius
     * @param minPts
     */
    public DbScan(ArrayList<String> data, double minRadius, int minPts) {
        this.minPts = minPts;
        this.minRadius = minRadius;
        dataSet = new ArrayList<Node>();
        clusters = new ArrayList<>();
        int i = 0;
        if(!data.isEmpty()){
            for (String instance: data) {
                Node n1 = new Node(instance);
                n1.setIndex(i);
                dataSet.add( i, n1 );
                i++;
            }
        }
        beginClusturing();
    }


    /**
     * @param minPts
     */

    /**
     * DBSCAN Clusturing begins
     * @author ksv
     * keshavbist.com.np
     */
    private void beginClusturing(){
        System.out.print("Clusturing Begins ............\n");
        int cid = 0;

        for (Node n: dataSet) {
            if(!(n.isVisited())){
                n.setVisted();
                dataSet.set(n.getIndex(), n);
                ArrayList<Node> neigh = new ArrayList<>(regionQuery(n));

                if(neigh.size() <minPts){
                    Clusters cluster = new Clusters(cid);
                    n.setClustured();
                    cluster.addNode(n);
                    cluster.setNoise();
                    dataSet.set(n.getIndex(), n);
                    clusters.add(cluster);
                }
                else
                {
                    n.setClustured ();
                    expandCluster(n, neigh, cid);
                }
                cid = cid+1;
            }
        }
        clusturePrint();
        System.out.print("Clusturing Ends ............\n");
    }

    /**
     *
     * @param n
     * @param neighpts
     */
    private void expandCluster(Node n, ArrayList<Node> neighpts, int clusterid){
        Clusters cluster = new Clusters(clusterid);
        n.setClustured ();
        n.setVisted ();
        dataSet.set ( n.getIndex (), n );
        cluster.addNode(n);
        int size = neighpts.size();
        for (int i = 0; i<size; i++) {
            Node n1 = neighpts.get(i);
            if(n1.getIndex () != n.getIndex ()) {
                if (!(n1.isVisited ( ))) {
                    n1.setVisted ( );
                    n1.setClustured ();
                    dataSet.set ( n1.getIndex ( ), n1 );
                    ArrayList<Node> nbr = regionQuery ( n1 );
                    if (nbr.size ( ) >= minPts) {
                        neighpts.addAll ( nbr );
                        size = neighpts.size ( );
                    }
                    cluster.addNode ( n1 );
                }
            }
        }

        clusters.add(cluster);
    }
    /**
     *
     * @param n1 - Node to be analyzed
     * @return neighbours - returns the neighbours of the point
     */

    private ArrayList<Node> regionQuery(Node n1){
        ArrayList<Node> neighbour = new ArrayList<>();
        for (Node n: dataSet){
            double eps1 = distance(n1, n);
            if (eps1 <= minRadius){
                if((!n.isVisited ())){
                    neighbour.add(n);
                }
            }
        }
        return neighbour;
    }

    private double distance(Node n1, Node n2){
        double sum = 0;
        for(int i =0; i<5; i++){
            double diff = Math.abs(n1.sensor[i]-n2.sensor[i]);
            sum+=diff*diff;
        }
        return sum;
    }


    public void clusturePrint(){
        for (Clusters c: clusters) {
            c.print();
        }
    }

    public ArrayList<Clusters> getClusters(){
        return clusters;
    }

}
