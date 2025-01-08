package okhttp;

import dto.UserDTOLombok;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseAPI;

import java.io.IOException;

import static utils.RandomUtils.*;

public class RegistrationTests implements BaseAPI {

    @Test
    public void registration() {

        UserDTOLombok userDTOLombok = UserDTOLombok.builder().username(generateEmail(5)).password("Password1!").build();

        RequestBody requestBody = RequestBody.create(GSON.toJson(userDTOLombok), JSON);
        Request request = new Request.Builder().url(BASE_URL + REGISTRATION).post(requestBody).build();
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            Assert.assertTrue(response.isSuccessful());
        } catch (IOException e) {
            Assert.fail("Created Exception -> registration()");
            throw new RuntimeException(e);
        }

    }

    @Test
    public void registration_400_emptyUsername() {

        UserDTOLombok userDTOLombok = UserDTOLombok.builder().username("").password("Password1!").build();

        RequestBody requestBody = RequestBody.create(GSON.toJson(userDTOLombok), JSON);
        Request request = new Request.Builder().url(BASE_URL + REGISTRATION).post(requestBody).build();
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            System.out.println(response.toString());
            Assert.assertEquals(response.code(), 400);
        } catch (IOException e) {
            Assert.fail("Created Exception -> registration()");
            throw new RuntimeException(e);
        }

    }

    @Test
    public void registration_409_duplicateUser() {

        UserDTOLombok userDTOLombok = UserDTOLombok.builder().username("testemail@example.com").password("Password1!").build();

        RequestBody requestBody = RequestBody.create(GSON.toJson(userDTOLombok), JSON);
        Request request = new Request.Builder().url(BASE_URL + REGISTRATION).post(requestBody).build();
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                Response response1 = OK_HTTP_CLIENT.newCall(request).execute();
            }
            System.out.println(response.toString());
            Assert.assertEquals(response.code(), 409);
        } catch (IOException e) {
            Assert.fail("Created Exception -> registration()");
            throw new RuntimeException(e);
        }

    }

}
