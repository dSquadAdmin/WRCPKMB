package cluster.clusterer.data;

import java.util.ArrayList;

/**
 * Created by ksv on 8/7/16.
 *
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