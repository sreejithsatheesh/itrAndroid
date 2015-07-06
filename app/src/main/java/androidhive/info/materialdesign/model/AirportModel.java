package androidhive.info.materialdesign.model;

/**
 * Created by VNK on 6/17/2015.
 */
public class AirportModel {

    String value, key;

    public AirportModel() {
    }

    public AirportModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
