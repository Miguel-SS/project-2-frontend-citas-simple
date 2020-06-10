package cr.una.frontend.model;

import com.google.gson.annotations.SerializedName;

public class DoctorType {

    @SerializedName("_id")
    private IdType id;
    @SerializedName("tipo")
    private String type;
    @SerializedName("salario")
    private double salary;
    @SerializedName("moneda")
    private String coin;
    @SerializedName("codigo-medico")
    private String code;

    /**
     * Default constructor
     */
    public DoctorType(){
    }

    /**
     *
     * @param id id
     * @param type type
     * @param salary salary
     * @param coin coin
     * @param code code
     */
    public DoctorType(IdType id, String type, double salary, String coin, String code){
        this.id = id;
        this.type = type;
        this.salary = salary;
        this.coin = coin;
        this.code = code;
    }


    /**
     * Getter
     * @return id
     */
    public IdType getId() {
        return id;
    }

    /**
     * Setter
     * @param id id
     */
    public void setId(IdType id) {
        this.id = id;
    }

    /**
     * Getter
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter
     * @param type type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter
     * @return salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Setter
     * @param salary salary
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Getter
     * @return coin
     */
    public String getCoin() {
        return coin;
    }

    /**
     * Setter
     * @param coin coin
     */
    public void setCoin(String coin) {
        this.coin = coin;
    }

    /**
     * Getter
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter
     * @param code code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * toString
     * @return attributes of the class
     */
    @Override
    public String toString() {
        return "DoctorType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", salary=" + salary +
                ", coin='" + coin + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
