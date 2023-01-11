package objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class bookingDates {
    @JsonProperty(value = "checkin") private String checkin;
    @JsonProperty(value = "checkout") private String checkout;

    public bookingDates(String checkin, String checkout){
        this.checkin = checkin;
        this.checkout= checkout;

    }
}


