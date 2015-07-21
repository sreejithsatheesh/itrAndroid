package androidhive.info.materialdesign.model;

/**
 * Created by VNK-LAP on 5/29/2015.
 */
public class TransportationModel {

    private int Id, Region_Id, Cost1, KM_Limit, Price_Per_KM, Max_Person, Transportation_Id, Cost;

    private String Title, Image;

    public TransportationModel(int id, int region_Id, int cost1, int km_Limit, int price_Per_KM,
                               int max_Person, int popular, int transportation_Id, int cost, String title, String image) {

        Id = id;
        Region_Id = region_Id;
        Cost1 = cost1;
        KM_Limit = km_Limit;
        Price_Per_KM = price_Per_KM;
        Max_Person = max_Person;
        Title = title;
        Image = image;
        Transportation_Id = transportation_Id;
        Cost = cost;

    }

    public TransportationModel() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getRegion_Id() {
        return Region_Id;
    }

    public void setRegion_Id(int region_Id) {
        Region_Id = region_Id;
    }

    public int getCost1() {
        return Cost1;
    }

    public int getTransportation_Id() {

        return Transportation_Id;
    }

    public int getCost() {
       return Cost;
    }

    public void setCost1(int cost1) {
        Cost1 = cost1;
    }

    public int getKM_Limit() {
        return KM_Limit;
    }

    public void setKM_Limit(int KM_Limit) {
        this.KM_Limit = KM_Limit;
    }

    public int getPrice_Per_KM() {
        return Price_Per_KM;
    }

    public void setPrice_Per_KM(int price_Per_KM) {
        Price_Per_KM = price_Per_KM;
    }

    public int getMax_Person() {
        return Max_Person;
    }

    public void setMax_Person(int max_Person) {
        Max_Person = max_Person;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setTransportation_Id(int transportation_Id) {
        Transportation_Id = transportation_Id;
    }

    public void setCost(int cost) {
        Cost = cost;
    }
}
