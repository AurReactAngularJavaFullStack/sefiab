package fr.soprasteria.agircarcco.sefiab.repository;

import fr.soprasteria.agircarcco.sefiab.model.Entreprise;

import java.util.List;

public interface CustomItemWriteListenerInterface {
    void beforeWrite(List<? extends Entreprise> items);
    void afterWrite(List<? extends Entreprise> items);
    void onWriteError(Exception exception, List<? extends Entreprise> items);
}
