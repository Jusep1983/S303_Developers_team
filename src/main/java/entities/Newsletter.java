package entities;

import daos.PlayerDAOImpl;
import lombok.Getter;
import lombok.Setter;
import observer.Observable;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Newsletter implements Observable {
    List<Observer> observers = new ArrayList<>();
    List<Player> allPlayers;
    PlayerDAOImpl playerDAOImpl = new PlayerDAOImpl();
    private String lastNews;

    public Newsletter(List<Player> allPlayers) {
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
        for (Observer observer : observers) {
            observer.update(this.lastNews);
        }
    }

}
