package cr.una.frontend.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Appointment {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("patient")
    private Patient patient;

    @Expose
    @SerializedName("hospital")
    private Hospital hospital;

    @Expose
    @SerializedName("doctor")
    private Doctor doctor;

    @Expose
    @SerializedName("date")
    private Date date;

    @Expose
    @SerializedName("hour")
    private String hour;

    @Expose
    @SerializedName("typeOfService_id")
    private String typeOfService_id;

    @Expose
    @SerializedName("totalCost")
    private double totalCost;

    @Expose
    @SerializedName("active")
    private boolean active;



    /**
     * Default constructor
     */
    public Appointment() {}


    /**
     *
     * @param id id
     * @param patient patient
     * @param hospital hospital
     * @param doctor doctor
     * @param date date
     * @param hour hour
     * @param typeOfService_id typeOfService id
     * @param totalCost total cost
     * @param active  active
     */
    public Appointment(int id, Patient patient, Hospital hospital, Doctor doctor,
                       Date date, String hour, String typeOfService_id, double totalCost, boolean active) {
        this.id = id;
        this.patient = patient;
        this.hospital = hospital;
        this.doctor = doctor;
        this.date = date;
        this.hour = hour;
        this.typeOfService_id = typeOfService_id;
        this.totalCost = totalCost;
        this.active = active;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * setter
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter
     * @return patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * setter
     * @param patient patient
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * getter
     * @return hospital
     */
    public Hospital getHospital() {
        return hospital;
    }

    /**
     * setter
     * @param hospital hospital
     */
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    /**
     * getter
     * @return doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * setter
     * @param doctor doctor
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * getter
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * setter
     * @param date date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * getter
     * @return hour
     */
    public String getHour() {
        return hour;
    }

    /**
     * setter
     * @param hour hour
     */
    public void setHour(String hour) {
        this.hour = hour;
    }

    /**
     * getter
     * @return  id type of RetroClient
     */
    public String getTypeOfService_id() {
        return typeOfService_id;
    }

    /**
     * setter
     * @param typeOfService_id od type of service
     */
    public void setTypeOfService_id(String typeOfService_id) {
        this.typeOfService_id = typeOfService_id;
    }

    /**
     *
     * @return total cost
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     *
     * @param totalCost total cost
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * getter
     * @return active
     */
    public boolean isActive() { return active; }

    /**
     * setter
     * @param active active
     */
    public void setActive(boolean active) { this.active = active; }

    /**
     *
     * @return all information
     */
    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patient=" + patient +
                ", hospital=" + hospital +
                ", doctor=" + doctor +
                ", date=" + date +
                ", hour='" + hour + '\'' +
                ", typeOfService_id='" + typeOfService_id + '\'' +
                ", totalCost=" + totalCost +
                ", active=" + active +
                '}';
    }
}
