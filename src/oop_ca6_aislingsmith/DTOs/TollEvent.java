package oop_ca6_aislingsmith.DTOs;

/**                                                     Feb 2019 
 * 
 * This POJO (Plain Old Java Object) is called the Data Transfer Object (DTO).
 * (or, alternatively, the Model Object or the Value Object).
 * It is used to transfer data between the DAO and the Business Objects.
 * Here, it represents a row of data from the User database table.
 * The DAO fills a User object (DTO) with data retrieved from the resultSet 
 * and passes the User object to the Business Layer. 
 * Collections of DTOs( e.g. ArrayList<TollEvents> ) may also be passed 
 * between the Data Access Layer (DAOs) and the Business Layer objects.
 */


/**
 *
 * @author aisli
 */
public class TollEvent {
    
    private String registration;
    private long imageId;
    private long timestamp;

    public TollEvent(String registration, long imageId, String timestamp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public TollEvent(String registration, long imageId, long timestamp) {
        this.registration = registration;
        this.imageId = imageId;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TollEvent{" + "registration=" + registration + ", imageId=" + imageId + ", timestamp=" + timestamp + '}';
    }

}
