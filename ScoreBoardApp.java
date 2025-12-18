package TreeSet;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;

import MySql.Mysql;

class Player implements Comparable<Player> {
    String name;
    int score;
    int jevseyNumber;

    Player(String name, int score , int jerseyNumber){
        this.name = name;
        this.score = score;
        this.jevseyNumber = jerseyNumber;
      }


    @Override
    public int compareTo(Player p) {
        int cmp = Integer.compare(p.score, this.score);
        if(cmp != 0){
            return cmp;
        }
        if(this.name != p.name){
            return this.name.compareTo(p.name);
        }
        return Integer.compare(this.jevseyNumber, p.jevseyNumber);
    }

    @Override
    public String toString() {
        return "\nPlayer Name: " + name +
               "\nScore: " + score+
               "\nJersey Number: " + jevseyNumber + "\n";
    }


}



class ScoreBoard {
    MyTreeSet<Player> players = new MyTreeSet<>();
    Mysql sql;
   ScoreBoard(){
        try {
            sql = new Mysql();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql.retreiveData(players);
    }

    void addPlayer(String name, int score, int jerseyNumber){
        players.add(new Player(name, score,jerseyNumber));
        sql.storeData(name, score);

    }

    Player getTopPlayer(){
        return players.last();
    }
    Player getLowestPlayer(){
        return players.first();
    }
    void removePlayer(String name, int score, int jerseyNumber){
        players.remove(new Player(name, score,  jerseyNumber));
        sql.deleteData(name, score);
    }

    void topPlayers(int n){
        if(players.isEmpty()){
            System.out.println("No players in the scoreboard.");
            return;
        }
        int count = 0;
        Iterator<Node<Player>> it = players.iterator();
        System.out.println("\n-----------Top "+n+" Players:--------------\n");
        while(it.hasNext() && count < n){
            System.out.println("\nRank: " +(count+1)+it.next().data);
            count++;
        }
    }

    void printAllPlayers(){
        if(players.isEmpty()){
            System.out.println("No players in the scoreboard.");
            return;
        }
        topPlayers(players.size());
    }

    void clear(){
        players.clear();
    }

    void showTree(){
        players.print();
    }

    String options(){
        return "\n1. Add Player" +
               "\n2. Remove Player" +
               "\n3. Show Top Player" +
               "\n4. Show Lowest Player" +
               "\n5. Show Top N Players" +
               "\n6. Show All Players" +
               "\n7. Clear ScoreBoard" +
               "\n9. Exit";
    }



} 



public class ScoreBoardApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);



        ScoreBoard sb = new ScoreBoard();

        sb.addPlayer("Alice", 1500, 10);
        sb.addPlayer("Bob", 1200, 9);
        sb.addPlayer("Charlie", 1800, 11);




        while (true) {
            System.out.println(sb.options());
            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            if (choice == 9) {
                System.out.println("Exiting...");
                break;
            }
            switch (choice) {
                case 1:
                    System.out.print("Enter player name: ");
                    String name = sc.next();
                    System.out.print("Enter player score: ");
                    int score = sc.nextInt();
                    System.out.print("Enter player jersey number: ");
                    int jerseyNumber = sc.nextInt();
                    sb.addPlayer(name, score, jerseyNumber);
                    break;
                case 2:
                    System.out.print("Enter player name to remove: ");
                    String rname = sc.next();
                    System.out.print("Enter player score to remove: ");
                    int rscore = sc.nextInt();
                    System.out.print("Enter player jersey number: ");
                     jerseyNumber = sc.nextInt();
                    sb.removePlayer(rname, rscore,jerseyNumber);
                    break;
                case 3:
                    System.out.println("Top Player: " + sb.getTopPlayer());
                    break;
                case 4:
                    System.out.println("Lowest Player: " + sb.getLowestPlayer());
                    break;
                case 5:
                    System.out.print("Enter N: ");
                    int n = sc.nextInt();
                    sb.topPlayers(n);
                    break;
                case 6:
                    System.out.println("\n-----------All Players:--------------\n");
                    sb.printAllPlayers();
                    break;
                case 7:
                    sb.clear();
                    System.out.println("\nScoreBoard cleared\n");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again\n");
            }


        }

        sc.close();


    }
}