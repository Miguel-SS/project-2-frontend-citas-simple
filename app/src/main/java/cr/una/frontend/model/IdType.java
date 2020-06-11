package cr.una.frontend.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdType {

    @Expose
    @SerializedName("$oid")
    private String $oid;

    /**
     * Default constructor
     */
    public IdType() {
    }

    /**
     * Setter
     * @param $oid $oid
     */
    public IdType(String $oid) {
        this.$oid = $oid;
    }

    /**
     * Getter
     * @return $oid
     */
    public String get$oid() {
        return $oid;
    }

    /**
     * Setter
     * @param $oid $oid
     */
    public void set$oid(String $oid) {
        this.$oid = $oid;
    }

    /**
     * toString
     * @return attributes of IdType
     */
    @Override
    public String toString() {
        return "IdType{" + "$oid=" + $oid + '}';
    }
}
