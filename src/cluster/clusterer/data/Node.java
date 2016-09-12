package cluster.clusterer.data;

/**
 * Created by ksv on 8/7/16.
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
        sensor = new double[5];
        product = null;
        String[] value = instance.split(",");
        for (int i = 0; i < 5; i++) {
            try {
                this.sensor[i] = Double.parseDouble(value[i]);
            } catch (NumberFormatException e) {
                this.sensor[i] = 0;
            }
        }
        if (value.length > 5) {
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
