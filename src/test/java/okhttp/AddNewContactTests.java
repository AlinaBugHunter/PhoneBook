package okhttp;

import dto.ResponseMessageDTO;
import dto.TokenDTO;
import dto.UserContactDTO;
import dto.UserDTOLombok;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.BaseAPI;

import static utils.RandomUtils.*;

import java.io.IOException;

public class AddNewContactTests implements BaseAPI {

    TokenDTO tokenDTO;

    @BeforeClass
    public void login() {
        UserDTOLombok userDTOLombok = UserDTOLombok.builder()
                .username("testemail@example.com")
                .password("Password123!")
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(userDTOLombok), JSON);
        Request request = new Request.Builder().url(BASE_URL + LOGIN).post(requestBody).build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                tokenDTO = GSON.fromJson(response.body().string(), TokenDTO.class);
            } else {
                System.out.println("Login failed" + response.code());
            }
        } catch (IOException e) {
            System.out.println("Created Exception login()");
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addNewContact() {

        UserContactDTO userContactDTO = UserContactDTO.builder()
                .name("Peter")
                .lastName("Johnson")
                .phoneNumber(generatePhone(11))
                .email(generateEmail(5))
                .address("Tel Aviv")
                .description(generateString(15))
                .build();

        RequestBody requestBody = RequestBody.create(GSON.toJson(userContactDTO), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + CONTACT)
                .addHeader("Authorization", tokenDTO.getToken())
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseMessageDTO responseMessageDTO = GSON.fromJson(response.body().string(), ResponseMessageDTO.class);
                System.out.println(responseMessageDTO.getMessage());
                Assert.assertTrue(responseMessageDTO.getMessage().contains("Contact was added!"));
            } else {
                Assert.fail("Contact wasn't added" + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
