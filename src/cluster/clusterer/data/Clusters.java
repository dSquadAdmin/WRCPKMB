package cluster.clusterer.data;

import java.util.ArrayList;

/**
 * Created by ksv on 8/7/16.
 */
public class Clusters {
    private ArrayList<Node> clusture;
    private boolean noise;
    private int id;


    public Clusters(int id){
        this.id = id;
        this.clusture = new ArrayList<Node>();
        noise = false;
    }

    public void addNode(Node node){
        this.clusture.add(node);
    }


    public ArrayList<Node> getClusture(){
        return this.clusture;
    }

    public void setNoise(){
        this.noise = true;
    }
    public boolean isNoise(){
        return noise;
    }

    public int getId(){
        return id;
    }

    public int getSize(){
        return clusture.size ();
    }

    public void print(){
        if(noise) {
            System.out.print("Clusture ID: " + id + "(Noise)\n");
        }else{
            System.out.print("Clusture ID: " + id + "\n");
        }
        for (Node n : clusture) {
            n.print();
        }
        System.out.print("\n");
    }

}