//Manager
// Contains a list of ServerThread(s).
// Has a method to add a ServerThread to the the list.
// Broadcast msg to all ServerThread in the list by calling the send method of each ServerThread.
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import javax.swing.JFrame;
public class Manager {
    private MyArrayList<ServerThread> serverThreads;
    private MyArrayList<Color> color_grid= new MyArrayList<Color>();
    private MyArrayList<ServerSocket> serverSockets = new MyArrayList<ServerSocket>();
    private boolean waiting;
    private ServerSocket serverSocket;
    private int round;
    private int num_row;
    private int clientNumber;
    private int num_col;
    private int num_tiles;
    private int points;
    private int correct_x;
    private int correct_y;
    private int correct_color;
    private int ct;
    private MyArrayList<Integer> point;
    private int numPlayers;
    Manager(){
        clientNumber=0;
        ct=0;
        numPlayers=0;
        point=new MyArrayList<Integer>();
        //intialize point with null values
        for(int i=0;i<10;i++){
            point.add(-99999999);
        }
        waiting = true;
        points = 0;
        serverThreads = new MyArrayList<ServerThread>();
    }
    public void broadcast(String d) {
        for(int i = 0; i < d.length(); i++){
            if(d.charAt(i) == ':'){
                clientNumber = Integer.parseInt(d.charAt(i-1)+"");
                int ls=d.length()-1;
                while(d.charAt(ls)!=':'){
                    ls--;
                }
                String points=d.substring(ls+1);
                point.set(clientNumber, Integer.parseInt(points));
                ct++;
                break;
            }
        }
        //clear null values
        System.out.println("ct: "+ct);
        System.out.println("numPlayers: "+numPlayers);
        if (ct == numPlayers) {
            System.out.println("All points received");
            //remove null values
            for(int i=0;i<point.size();i++){
                if(point.get(i)==-99999999){
                    point.remove(i);
                    System.out.println("removed null value");
                }
            }
            System.out.println("point size: "+point.size());
            for(int i=0;i<point.size();i++){
                if(point.get(i)==-99999999){
                    point.remove(i);
                    System.out.println("removed null value");
                }
            }
            int max = point.max();
            // Print out all players' points
            for (int i = 0; i < numPlayers; i++) {
                System.out.println("Player " + i + " points: " + point.get(i));
            }
            //check for tie
            int tie=0;
            for(int i=0;i<point.size();i++){
                if(point.get(i)==max){
                    tie++;
                }
            }
            if(tie>1){
                for(int i=0;i<point.size();i++){
                    if(point.get(i)==max){
                        serverThreads.get(i).send("tie");
                        System.out.println("Player " + i + " ties");
                    }
                    if(point.get(i)!=max){
                        serverThreads.get(i).send("lose");
                        System.out.println("Player " + i + " loses");
                    }
                }
            }else{
            for (int i = 0; i < numPlayers; i++) {
                if (point.get(i) == max) {
                    serverThreads.get(i).send("win");
                    System.out.println("Player " + i + " wins");
                } else if (point.get(i) < max) {
                    serverThreads.get(i).send("lose");
                    System.out.println("Player " + i + " loses");
                }
            }
            }
            point.clear();
        }
    }
    
            // Clear the list of points for the next round
            
        
    
    

//method to receive data from client
    public void addServerThread(ServerThread serverThread){
        serverThreads.add(serverThread);
        numPlayers++;        
    }

}

