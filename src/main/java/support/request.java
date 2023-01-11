package support;


import io.restassured.response.Response;
import objects.bookingDates;
import objects.authUserPayload;
import objects.CreateBooking;
import objects.ParcialUpdateBooking;
import objects.TotalUpdateBooking;

public class request {

    apiHelper api;
    public static Response restfulBookerResponse;

    authUserPayload authUser;
    CreateBooking createBooking;
    bookingDates bookingDates;
    ParcialUpdateBooking partialBookingPayload;
    TotalUpdateBooking totalUpdateBooking;

    public request(){
        api = new apiHelper();
    }

    public void getBookingIds(){
        String url = "https://restful-booker.herokuapp.com/booking";
        restfulBookerResponse = api.get(url);
    }

    public void getBookingIdsByName(String firstName, String lastName){
        String url = "https://restful-booker.herokuapp.com/booking";
        restfulBookerResponse = api.getFilteredByName(url, firstName, lastName);
    }

    public void getBookingIdsByCheckinCheckout(String checkin, String checkout){
        String url = "https://restful-booker.herokuapp.com/booking";
        restfulBookerResponse = api.getFilteredByCheckinCheckout(url, checkin, checkout);
    }

    public void authCreateToken(String username, String password){
        String url = "https://restful-booker.herokuapp.com/auth";
        authUser = new authUserPayload(username, password);
        restfulBookerResponse = api.post(url, authUser);
    }


    public void createBooking(String firstName, String lastName, String totalPrice, String depositPaid, String checkin, String checkout, String additionalNeeds){
        String url= "https://restful-booker.herokuapp.com/booking";
        bookingDates = new bookingDates(checkin, checkout);
        createBooking = new CreateBooking(firstName,lastName, Integer.parseInt(totalPrice), Boolean.parseBoolean(depositPaid), bookingDates, additionalNeeds);
        restfulBookerResponse = api.post(url, createBooking);
    }

    public void updateBooking(String id, String firstName, String lastName, String totalPrice, String depositPaid, String checkin, String checkout, String additionalNeeds,String token){
        String url ="https://restful-booker.herokuapp.com/booking/" + id;
        bookingDates = new bookingDates(checkin, checkout);
        totalUpdateBooking = new TotalUpdateBooking(firstName,lastName, Integer.parseInt(totalPrice), Boolean.parseBoolean(depositPaid), bookingDates, additionalNeeds);
        restfulBookerResponse = api.put(url, totalUpdateBooking, token);
    }

    public void partialUpdateBooking(String id, String firstName, String lastName, String token){
        String url = "https://restful-booker.herokuapp.com/booking/" + id;
        partialBookingPayload = new ParcialUpdateBooking(firstName, lastName);
        restfulBookerResponse = api.patch(url, partialBookingPayload, token);
    }

    public void deleteBooking(String id, String token){
        String url = "https://restful-booker.herokuapp.com/booking/" + id;
        restfulBookerResponse = api.delete(url, token);
    }

    public void healthCheck(){
        String url = "https://restful-booker.herokuapp.com/ping";
        restfulBookerResponse = api.get(url);
    }
}

