package tripDemo.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class Trip {
    private Long id;
    private Long companyId;
    private String plane;
    private String townFrom;
    private String townTo;
    private String timeOut;
    private String timeIn;
    private List<Passenger> passengerList = new ArrayList<>();

    /*public void setPassengerList(List<Passenger> passengerList) {

    }*/
    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }
}
