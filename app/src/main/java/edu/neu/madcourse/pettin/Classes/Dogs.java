package edu.neu.madcourse.pettin.Classes;

import java.util.ArrayList;

public class Dogs {

    private String name;
    private String gender;
    private boolean spayed;
    private int age;
    private Breed breed;
    private ArrayList<PlayStyles> playStyles;
    private Double weight;
    private int energyLevel;
    private String img;
    private String location;


    public Dogs(String name, String gender, boolean spayed, int age, Breed breed, ArrayList<PlayStyles> playStyles, Double weight, int energyLevel, String img, String location) {
        this.name = name;
        this.gender = gender;
        this.spayed = spayed;
        this.age = age;
        this.breed = breed;
        this.playStyles = playStyles;
        this.weight = weight;
        this.energyLevel = energyLevel;
        this.img = img;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isSpayed() {
        return spayed;
    }

    public void setSpayed(boolean spayed) {
        this.spayed = spayed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public ArrayList<PlayStyles> getPlayStyles() {
        return playStyles;
    }

    public void setPlayStyles(ArrayList<PlayStyles> playStyles) {
        this.playStyles = playStyles;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
