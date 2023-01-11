package definitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.it.Ma;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import support.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;



public class restFullBookerDefinition {

    request restfulBooker;

    private static String token;
    private static String bookingid;

    public restFullBookerDefinition() {
        restfulBooker = new request();
    }

    @Given("El usuario esta registrado en el api")
    public void usuarioRegistradoApi() {
    }

    @When("El usuario ingresa las credenciales para generar el token")
    public void credencialesGeneranToken(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> dt : data) {
            restfulBooker.authCreateToken(dt.get("username"), dt.get("password"));
        }
    }

    @Then("Verificamos que el servicio responsa con status code {string}")
    public void verificamosStatusCode(String statusCode) {
        System.out.println("Código de respuesta: " + request.restfulBookerResponse.getStatusCode());
        Assert.assertEquals(Integer.parseInt(statusCode), request.restfulBookerResponse.getStatusCode());
    }

    @And("Verificamos la respuesta tenga el toke")
    public void verificamosTokenExiste() {
        ResponseBody<Response> body = request.restfulBookerResponse;
        System.out.println(body.asString());
        Assert.assertTrue(body.asString().contains("\"token\":"));
    }

    @And("Verificamos que el token debe de tener {string} caracteres")
    public void verificamosTokenCaracteres(String charactersLength) {
        ResponseBody<Response> body = request.restfulBookerResponse;
        JsonPath json = new JsonPath(body.asString());
        token = json.getString("token");
        Assert.assertEquals(Integer.parseInt(charactersLength), token.length());
        System.out.println("El token si tiene 15 caracteres los cuales son :" + token);
    }

    @And("Verificamos que el token contenga solo caracteres alfanumericos")
    public void verificamosTokenAlfanumerico() {
        Assert.assertFalse(token.contains("^[a-zA-Z0-9]*$"));
        System.out.println("El token si contiene caracteres alfanumericos ");
    }

    @Given("El usuario accede al api getBookings")
    public void userAccedeGetBookings() {
        restfulBooker.getBookingIds();
    }

    @When("Solicita toda la información de los bookingid")
    public void accesoBookingidsInformación() {
        ResponseBody<Response> body = request.restfulBookerResponse;
        System.out.println(body.asString());
    }

    @And("Se muestra toda la información que obtiene el servicio")
    public void muestraTodaInformación() {
        System.out.println("Se muestra toda la información obtenida de la consulta realizada");
        ResponseBody<Response> body = request.restfulBookerResponse;
        System.out.println(body.asString());
    }

    @When("Se soilicita toda la información filtrando por los siguientes nombres")
    public void accesoFiltrandoNombres(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < data.size(); i++) {
            restfulBooker.getBookingIdsByName(data.get(i).get("firstname"), data.get(i).get("lastname"));
        }
    }

    @When("Se solicita toda la información filtrando con la fecha de checkin y checkout")
    public void accesoFiltrandoFechas(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < data.size(); i++) {
            restfulBooker.getBookingIdsByCheckinCheckout(data.get(i).get("checkin"), data.get(i).get("checkout"));
        }
    }

    @Given("El booking no esta creado")
    public void userNoRegistrado() {
    }

    @When("Accede al api para crear un nuevo booking")
    public void accesoCreacionNewBoking(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < data.size(); i++) {
            restfulBooker.createBooking(data.get(i).get("firstname"),
                    data.get(i).get("lastname"),
                    data.get(i).get("totalprice"),
                    data.get(i).get("depositpaid"),
                    data.get(i).get("checkin"),
                    data.get(i).get("checkout"),
                    data.get(i).get("additionalneeds"));
        }
    }

    @And("Verificamos que se obtenga en la respuesta el bookingid")
    public void verifyTheResponseBodyContainsTheBookingid() {
        ResponseBody<Response> body = request.restfulBookerResponse;
        System.out.println(body.asString());
        JsonPath json = new JsonPath(body.asString());
        bookingid = json.getString("bookingid");
        Assert.assertTrue(body.asString().contains("bookingid"));
        System.out.println("Se realizo la creación correcta del usuario");
    }

    @Given("Realizo actualizacion de booking")
    public void bookingCreatedOnTheApi() {

        System.out.println("El id del booking es: " + bookingid);
    }
    @When("Accede para realizar una actualización total de los datos")
    public void iAccesTheApiRequestEndpointToUpdateABooking(DataTable dataTable) {
        ResponseBody<Response> body = request.restfulBookerResponse;
        JsonPath json = new JsonPath(body.asString());
        token = json.getString("token");
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < data.size(); i++) {
            restfulBooker.updateBooking(bookingid,
                    data.get(i).get("firstname"),
                    data.get(i).get("lastname"),
                    data.get(i).get("totalprice"),
                    data.get(i).get("depositpaid"),
                    data.get(i).get("checkin"),
                    data.get(i).get("checkout"),
                    data.get(i).get("additionalneeds"),
                    token);

        }
    }

    @When("Accede para realizar una actualización parcial de los datos")
    public void iAccesTheApiRequestEndpointToPartialUpdateABooking(DataTable dataTable) {
        ResponseBody<Response> body = request.restfulBookerResponse;
        JsonPath json = new JsonPath(body.asString());
        token = json.getString("token");
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < data.size(); i++) {
            restfulBooker.partialUpdateBooking(bookingid,
                    data.get(i).get("firstname"),
                    data.get(i).get("lastname"),
                    token);
        }

    }

    @And("Verificamos que se realizo la actualización")
    public void verifyTheResponseBodyContainsBookingid() {
        System.out.println("Se realizo la actualicación la cual es la siguiente : ");
        ResponseBody<Response> body = request.restfulBookerResponse;
        System.out.println(body.asString());
        JsonPath json = new JsonPath(body.asString());
        bookingid = json.getString("bookingid");
    }

    @When("Accedemos al api para realizar la eliminación")
    public void accesoDeleteBooking() {
        ResponseBody<Response> body = request.restfulBookerResponse;
        JsonPath json = new JsonPath(body.asString());
        token = json.getString("token");
        System.out.println("El id del booking borrado es : " + bookingid);
        restfulBooker.deleteBooking(bookingid, token);
    }
    @Given("El usuario ejecuta el servicio")
    public void servicioEjecutado() {
        System.out.println("El id del booking es: " + bookingid);
    }

    @When("Accede al servicio HealthCheck")
    public void accesHealthCheck() {
        restfulBooker.healthCheck();
    }

    @And("Verificamos que el servicio este con status code {string}")
    public void healService(String code) {
        System.out.println("Código de respuesta: " + request.restfulBookerResponse.getStatusCode());
        Assert.assertEquals(Integer.parseInt(code), request.restfulBookerResponse.getStatusCode());
    }
}
