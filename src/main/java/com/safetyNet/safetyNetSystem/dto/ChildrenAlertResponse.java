package com.safetyNet.safetyNetSystem.dto;

import java.util.List;

/**
 * Réponse de l'alerte des enfants, contenant des listes d'enfants et d'adultes.
 * Cette classe encapsule les informations relatives aux enfants et aux adultes
 * en réponse à une alerte, permettant de séparer les deux groupes dans la réponse.
 */
public class ChildrenAlertResponse {
    private List<ChildInfo> children; // Liste des enfants concernés par l'alerte
    private List<PersonInfo> adults;  // Liste des adultes concernés par l'alerte

    /**
     * Constructeur pour initialiser un objet ChildrenAlertResponse avec des listes d'enfants et d'adultes.
     *
     * @param children Liste des enfants concernés par l'alerte.
     * @param adults Liste des adultes concernés par l'alerte.
     */
    public ChildrenAlertResponse(List<ChildInfo> children, List<PersonInfo> adults) {
        this.children = children;
        this.adults = adults;
    }

    /**
     * Récupère la liste des enfants dans la réponse de l'alerte.
     *
     * @return Liste des enfants.
     */
    public List<ChildInfo> getChildren() {
        return children;
    }

    /**
     * Modifie la liste des enfants dans la réponse de l'alerte.
     *
     * @param children Liste des enfants à définir.
     */
    public void setChildren(List<ChildInfo> children) {
        this.children = children;
    }

    /**
     * Récupère la liste des adultes dans la réponse de l'alerte.
     *
     * @return Liste des adultes.
     */
    public List<PersonInfo> getAdults() {
        return adults;
    }

    /**
     * Modifie la liste des adultes dans la réponse de l'alerte.
     *
     * @param adults Liste des adultes à définir.
     */
    public void setAdults(List<PersonInfo> adults) {
        this.adults = adults;
    }
}
