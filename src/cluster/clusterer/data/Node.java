package cluster.clusterer.data;

/**
 * Created by ksv on 8/7/16.
 */
public class Node {
    /**
     * Instance properties
     */
    public double[] sensor;
    public String product;
    /**
     * boolean flags to describe status of the instance
     */
    private boolean visited;
    private boolean clustured;
    private int index;


    public Node(String instance) {
        sensor = new double[6];
        product = null;
        String[] value = instance.split(",");
        for (int i = 0; i < 6; i++) {
            try {
                this.sensor[i] = Double.parseDouble(value[i]);
            } catch (NumberFormatException e) {
                this.sensor[i] = 0;
            }
        }
        if (value.length > 6) {
            this.product = new String(value[6]);
        }

        this.visited = false;
        this.clustured = false;
    }
    public void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }

    public void setVisted(){
        this.visited = true;
    }

    public void setClustured(){
        this.clustured = true;
    }

    public boolean isClustured(){
        return clustured;
    }

    public boolean isVisited(){
        return visited;
    }


    public String getProduct(){
        return this.product;
    }

    public void print(){
        for (double d: sensor) {
            System.out.print(d+"\t");
        }
        if(product!=null)
            System.out.print(product+"\n");
        else
            System.out.print(product+"\n");
    }
}
