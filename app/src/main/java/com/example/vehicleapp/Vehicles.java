package com.example.vehicleapp;

public class Vehicles {
    //values required for describing vehicle
    private int vehicleId;
    private String model;
    private int year;
    private String make;
    private int price;
    private String licenseNumber;
    private String colour;
    private int numberDoors;
    private String transmission;
    private int mileage;
    private String fuelType;
    private int engineSize;
    private String bodyStyle;
    private String condition;
    private String notes;
    private int posNum;


    public Vehicles(String model, int year, String make, int price, String licenseNumber, String colour, int numberDoors, String transmission, int mileage, String fuelType, int engineSize, String bodyStyle, String condition, String notes,int posNum) {

        this.model = model;
        this.year = year;
        this.make = make;
        this.price = price;
        this.licenseNumber = licenseNumber;
        this.colour = colour;
        this.numberDoors = numberDoors;
        this.transmission = transmission;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.engineSize = engineSize;
        this.bodyStyle = bodyStyle;
        this.condition = condition;
        this.notes = notes;
        this.posNum = posNum;
    }



    public String toString() {
        String vehicleString =  "Vehicle ID " +this.vehicleId +"\n" +
                "Model "  +this.model +"\n"+
                "Year " + this.year + "\n" +
                "Make "  + this.make + "\n" +
                "Price " + this.price + "\n"+
                "License Number " + this.licenseNumber + "\n" +
                "Colour " + this.colour + "\n" +
                "Number Doors " + this.numberDoors + "\n" +
                "Transmission " + this.transmission + "\n" +
                "Mileage " + this.mileage + "\n" +
                "Fuel type " + this.fuelType+ "\n" +
                "Engine size " + this.engineSize + "\n" +
                "Body style " + this.bodyStyle + "\n" +
                "Condition " + this.condition + "\n" +
                "Notes " + this.notes + "\n";
        return vehicleString;
    }


    //getters and setters for instance variables
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getNumberDoors() {
        return numberDoors;
    }

    public void setNumberDoors(int numberDoors) {
        this.numberDoors = numberDoors;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(int engineSize) {
        this.engineSize = engineSize;
    }

    public String getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(String bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getPosNum() {
        return posNum;
    }

    public void setPosNum(int posNum) {
        this.posNum = posNum;
    }
}
