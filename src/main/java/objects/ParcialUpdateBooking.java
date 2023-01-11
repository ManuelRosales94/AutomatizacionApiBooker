package objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParcialUpdateBooking {
    @JsonProperty(value = "firstname") String firstname;
    @JsonProperty(value = "lastname") String lastname;

    public ParcialUpdateBooking(String firstname, String lastname) {

        this.firstname = firstname;
        this.lastname = lastname;
    }
}
