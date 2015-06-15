package androidhive.info.materialdesign.model;

/**
 * Created by VNK on 6/10/2015.
 */
public class RegionPlaceModel {

    private int Arrival_Port_Id, Departure_Port_Id, Itinerary_Id, Duration_Day, Price, Discount;

    private String Title, Link, Image, Destination;

    public RegionPlaceModel(){

    }

    public RegionPlaceModel(int arrival_Port_Id, int departure_Port_Id, int itinerary_Id, int duration_Day, int price, int discount, String title, String link, String image, String destination) {
        Arrival_Port_Id = arrival_Port_Id;
        Departure_Port_Id = departure_Port_Id;
        Itinerary_Id = itinerary_Id;
        Duration_Day = duration_Day;
        Price = price;
        Discount = discount;
        Title = title;
        Link = link;
        Image = image;
        Destination = destination;
    }

    public int getArrival_Port_Id() {
        return Arrival_Port_Id;
    }

    public void setArrival_Port_Id(int arrival_Port_Id) {
        Arrival_Port_Id = arrival_Port_Id;
    }

    public int getDeparture_Port_Id() {
        return Departure_Port_Id;
    }

    public void setDeparture_Port_Id(int departure_Port_Id) {
        Departure_Port_Id = departure_Port_Id;
    }

    public int getItinerary_Id() {
        return Itinerary_Id;
    }

    public void setItinerary_Id(int itinerary_Id) {
        Itinerary_Id = itinerary_Id;
    }

    public int getDuration_Day() {
        return Duration_Day;
    }

    public void setDuration_Day(int duration_Day) {
        Duration_Day = duration_Day;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }
}