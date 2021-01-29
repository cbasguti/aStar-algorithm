package com.cbasguti.astaralgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * A Star - Heuristic Search Algorithm for finding the shortest path
 * Worst Case => O(|E|)
 * @author Sebastian Gutierrez Jaramillo
 * @author Esteban Salas Florez
 */
public class AStar {

    private PriorityQueue<Node> openList;
    private ArrayList<Node> closedList;
    HashMap<Node, Double> gVals;
    HashMap<Node, Double> fVals;
    private int initialCapacity = 100;
    private static double [][] valueMatrix = {
        {0,6,1.4,2.5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {6,0,2,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {1.4,2,0,0,0,9.9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {2.5,0,0,0,0,8.2,20,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,8,0,0,0,7,0,0,0,0,16,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,9.9,8.2,7,0,0,0,14,16.9,18,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,20,0,0,0,14,18,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,14,0,16,0,0,0,0,0,0,0, 0,0,8.5,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,14,18,16,0,9,0,0,0,0,0,0,0,10,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,16.9,0,0,9,0,16,0,0,0,0,6.5,0,8,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,16,18,0,0,0,16,0,20,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,20,0,8,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,4,8,0,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,4,0,0,3.5,0,0,0,0,0,0,0,0,0,0,0,0,10},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,4.3,0,0,0,0,0,0,0,0,3.5,0,0,0},
        {0,0,0,0,0,0,0,0,0,6.5,0,0,5,0,4.3,0,9,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0,11,0,12,0,0,0,0,2,0,0,0},
        {0,0,0,0,0,0,0,0,10,8,0,0,0,0,0,0,11,0,12,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,8.5,0,0,0,0,0,0,0,0,0,12,0,4.3,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,12,0,4.3,0,7,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,1.7,0,0,15.2,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1.7,0,1.5,15.3,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1.5,0,0,0,0,3,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15.3,0,0,0,2.8,5,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,3.5,0,2,0,0,0,15.2,0,0,0,0,1,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2.8,1,0,0,2},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,5,0,0,0,4},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,0,0,0,0,0,0,0,0,0,0,2,4,0},
    };
    
    public AStar() {
        gVals = new HashMap<>();
        fVals = new HashMap<>();
        openList = new PriorityQueue<>(initialCapacity, new fCompare());
        closedList = new ArrayList<>();
    }

    public static void main(String[] args) {
        Node[] n = new Node[28];
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZAB";
        for (int i = 0; i < n.length; i++) {
            n[i] = new Node();
            switch(i){
                case 26:
                    n[i].setData("AA");
                    break;
                case 27:
                    n[i].setData("AB");
                    break;
                default:
                    n[i].setData(letters.substring(i, i + 1));
                    break;
            }
        }
        /*
         * A => Start
         * W => Goal
         *
         */

        n[0].setXY(0, 140);
        n[1].setXY(50, 160);
        n[2].setXY(40, 125);
        n[3].setXY(25, 85);
        n[4].setXY(90, 175);
        n[5].setXY(90, 105);
        n[6].setXY(75, 45);
        n[7].setXY(135, 25);
        n[8].setXY(135, 60);
        n[9].setXY(165, 110);
        n[10].setXY(160, 185);
        n[11].setXY(255, 180);
        n[12].setXY(210, 155);
        n[13].setXY(300, 150);
        n[14].setXY(265, 125);
        n[15].setXY(220, 100);
        n[16].setXY(235, 50);
        n[17].setXY(180, 55);
        n[18].setXY(195, 10);
        n[19].setXY(245, 10);
        n[20].setXY(270, 0);
        n[21].setXY(310, 0);
        n[22].setXY(335, 30);
        n[23].setXY(300, 50);
        n[24].setXY(270, 95);
        n[25].setXY(300, 85);
        n[26].setXY(330, 70);
        n[27].setXY(320, 110);
        
        setNeighborhood(n);
        new AStar().traverse(n, n[0], n[22]);
    }
    
    public static double distanceBetweenNodes(Node[] n, Node a, Node b){
        int x, z;
        x = 0;
        z = 0;
        for(int i = 0; i < n.length; i++){
            if(n[i].equals(a)){
                x = i;
            }
            else if(n[i].equals(b)){
                z = i;
            }
        }
        return valueMatrix[x][z];
    }

    public void traverse(Node[] n, Node start, Node end) {
        openList.clear();
        closedList.clear();

        gVals.put(start, 0.0);
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.element();
            if (current.equals(end)) {
                System.out.println("Goal Reached");
                printPath(current);
                return;
            }
            closedList.add(openList.poll());
            ArrayList<Node> neighbors = current.getNeighbors();

            for (Node neighbor : neighbors) {
                double gScore = gVals.get(current) + distanceBetweenNodes(n, current, neighbor);
                double fScore = gScore + h(neighbor, end);

                if (closedList.contains(neighbor)) {

                    if (gVals.get(neighbor) == null) {
                        gVals.put(neighbor, gScore);
                    }
                    if (fVals.get(neighbor) == null) {
                        fVals.put(neighbor, fScore);
                    }

                    if (fScore >= fVals.get(neighbor)) {
                        continue;
                    }
                }
                if (!openList.contains(neighbor) || fScore < fVals.get(neighbor)) {
                    neighbor.setParent(current);
                    gVals.put(neighbor, gScore);
                    fVals.put(neighbor, fScore);
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);

                    }
                }
            }
        }
        System.out.println("FAIL");
    }
    
    public static void setNeighborhood(Node[] nodes){
        for(int i = 0; i < nodes.length; i++){
            for(int j = 0; j < valueMatrix.length; j++){
                if(valueMatrix[i][j] != 0){
                    nodes[i].addNeighbor(nodes[j]);
                }
            }
        }
    }
    
    private double h(Node node, Node goal) {
        int x = node.getX() - goal.getX();
        int y = node.getY() - goal.getY();
        var result = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));// Heuristica de la Distancia Euclidiana
        //var result = (Math.abs(x) + Math.abs(y));// Heuristica de la Distancia Manhattan
        return result;
    }

    private void printPath(Node node) {
        System.out.println(node.getData());

        while (node.getParent() != null) {
            node = node.getParent();
            System.out.println(node.getData());
        }
    }

    class fCompare implements Comparator<Node> {

        public int compare(Node o1, Node o2) {
            if (fVals.get(o1) < fVals.get(o2)) {
                return -1;
            } else if (fVals.get(o1) > fVals.get(o2)) {
                return 1;
            } else {
                return 1;
            }
        }
    }
}

class Node {

    private Node parent;
    private ArrayList<Node> neighbors;
    private int x;
    private int y;
    private Object data;

    public Node() {
        neighbors = new ArrayList<Node>();
        data = new Object();
    }

    public Node(int x, int y) {
        this();
        this.x = x;
        this.y = y;
    }

    public Node(Node parent) {
        this();
        this.parent = parent;
    }

    public Node(Node parent, int x, int y) {
        this();
        this.parent = parent;
        this.x = x;
        this.y = y;
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public void addNeighbor(Node node) {
        this.neighbors.add(node);
    }

    public void addNeighbors(Node... node) {
        this.neighbors.addAll(Arrays.asList(node));
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    @Override
    public String toString(){
        return "[" + data + "]";
    }

    public boolean equals(Node n) {
        return this.x == n.x && this.y == n.y;
    }
}
