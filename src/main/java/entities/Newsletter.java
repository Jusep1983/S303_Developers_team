package entities;

import daos.PlayerDAOImpl;
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
    List<Player> allPlayers = new ArrayList<>();
    PlayerDAOImpl playerDAOImpl = new PlayerDAOImpl();
    private String lastNews;



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

    public void notifyObservers(String lastNews) {
        for(Observer observer : observers){
            observer.update(lastNews);
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
