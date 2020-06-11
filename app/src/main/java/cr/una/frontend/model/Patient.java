package cr.una.frontend.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Patient {

    // Attributes
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("lastName")
    private String lastName;

    @Expose
    @SerializedName("birthYear")
    private Date birthYear;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("address")
    private String address;

    @Expose
    @SerializedName("disease")
    private String disease;

    @Expose
    @SerializedName("observation")
    private String observation;

    /**
     * Default constructor
     */
    public Patient() {}

    /**
     * @param id id
     * @param name name
     * @param lastName lastName
     * @param birthYear birthYear
     * @param password password
     * @param phone phone
     * @param address address
     * @param disease disease
     * @param observation observation
     */
    public Patient(int id, String name, String lastName, Date birthYear, String phone, String address,
                   String disease, String observation, String password) {
        this.name = name;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.disease = disease;
        this.observation = observation;
    }

    /**
     * @param id id
     * @param name name
     * @param lastName lastName
     * @param birthYear birthYear
     * @param password password
     * @param phone phone
     * @param address address
     */
    public Patient(int id, String name, String lastName, Date birthYear, String phone, String address, String password) {
        this.name = name;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.disease = "INDEF";
        this.observation = "INDEF";
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Date birthYear) {
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthYear=" + birthYear +
                ", id=" + id +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", disease='" + disease + '\'' +
                ", observation='" + observation + '\'' +
                '}';
    }
}
