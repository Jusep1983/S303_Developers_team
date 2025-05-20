package entities;

import lombok.Getter;
import lombok.Setter;
import observer.Observable;
import observer.Observer;
import entities.Player;

import java.util.ArrayList;
import java.util.List;

public class Newsletter implements Observable {
    List<Observer> observers = new ArrayList<>();
    @Setter@Getter
    String newsletter;
    List<Player> allPlayers;  //EscapeRoom.getInstance.getAllPlayers();

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
    public void notifyObserver(Observer observer) {
            observer.update(newsletter);
    }

    public void notifyAllPlayers(String newsletter){

        for (Player p :allPlayers){
            p.update(newsletter);
        }
    }

    public void notifySubscribed(String newsletter){
        for(Player p : allPlayers ){
            if (p.isSubscribed()){
                p.update(newsletter);
            }
        }
    }


}
