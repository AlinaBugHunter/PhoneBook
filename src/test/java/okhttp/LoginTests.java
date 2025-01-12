package okhttp;

import dto.ErrorMessageDTO;
import dto.TokenDTO;
import dto.UserDTOLombok;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseAPI;

import java.io.IOException;

import static utils.RandomUtils.generateEmail;

public class LoginTests implements BaseAPI {

    SoftAssert softAssert = new SoftAssert();
    UserDTOLombok userDTOLombok;

    @BeforeClass
    public void registration() {
        userDTOLombok = UserDTOLombok.builder()
                .username(generateEmail(10))
                .password("Password123!")
                .build();
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(userDTOLombok), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION)
                .post(requestBody).build();
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful())
                System.out.println("Registration is successful" + userDTOLombok.toString());
            else System.out.println("Registration failed" + response.code());
        } catch (IOException e) {
            System.out.println("Created Exception registration()");
            throw new RuntimeException(e);
        }
    }

    @Test
    public void login() {
        RequestBody requestBody = RequestBody.create(GSON.toJson(userDTOLombok), JSON);
        Request request = new Request.Builder().url(BASE_URL + LOGIN).post(requestBody).build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Login is successful" + userDTOLombok.toString());
                TokenDTO tokenDTO = GSON.fromJson(response.body().string(), TokenDTO.class);
                System.out.println(tokenDTO.getToken());
                Assert.assertFalse(tokenDTO.getToken().isBlank());
            } else {
                Assert.fail("Login failed" + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void login_invalidPassword() {
        userDTOLombok.setPassword("Password123");
        RequestBody requestBody = RequestBody.create(GSON.toJson(userDTOLombok), JSON);
        Request request = new Request.Builder().url(BASE_URL + LOGIN).post(requestBody).build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {

                ErrorMessageDTO errorMessageDTO = GSON.fromJson(response.body().string(), ErrorMessageDTO.class);

                System.out.println(response.toString()); // Registration is successfulUserDTOLombok(username=k3cghjcgwk@yandex.ru, password=Password123!)
                                                         // Response{protocol=http/1.1, code=401, message=, url=https://contactapp-telran-backend.herokuapp
                softAssert.assertEquals(errorMessageDTO.getStatus(), 401);

                System.out.println(errorMessageDTO.getMessage().toString()); // Login or Password incorrect
                softAssert.assertEquals(errorMessageDTO.getMessage(), "Login or Password incorrect");

                System.out.println(errorMessageDTO.getError()); // Unauthorized
                softAssert.assertEquals(errorMessageDTO.getError(), "Unauthorized");

                softAssert.assertAll();

            } else {
                Assert.fail("Login is successful" + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
