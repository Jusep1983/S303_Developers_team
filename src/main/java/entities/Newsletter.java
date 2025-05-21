package entities;

import lombok.Getter;
import lombok.Setter;
import observer.Observable;
import observer.Observer;
import entities.Player;

import java.util.ArrayList;
import java.util.List;
@Getter@Setter
public class Newsletter implements Observable {
    List<Observer> observers = new ArrayList<>();

    String lastNews;
    List<Player> allPlayers;  //EscapeRoom.getInstance.getAllPlayers();

    public Newsletter(){}
    public Newsletter(List<Player> allPlayers){
        this.allPlayers = allPlayers;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update(this.lastNews);
        }
    }

    public void notifyAllPlayers(){

        for (Player player :allPlayers){
            player.update(this.lastNews);
        }
    }

    public void notifySubscribed(){
        for(Player player : allPlayers ){
            if (player.isSubscribed()){
                player.update(this.lastNews);
            }
        }
    }


}
