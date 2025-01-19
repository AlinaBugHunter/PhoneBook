package okhttp;

import dto.ContactsDTO;
import dto.TokenDTO;
import dto.UserContactDTO;
import dto.UserDTOLombok;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.BaseAPI;

import java.io.IOException;

public class GetAllUserContactsTests implements BaseAPI {

    TokenDTO tokenDTO;

    @BeforeClass(alwaysRun = true)
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

    @Test(groups = "smoke")
    public void getAllUserContacts() {

        Request request = new Request.Builder()
                .url(BASE_URL + CONTACT)
                .addHeader("Authorization", tokenDTO.getToken())
                .get()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ContactsDTO contactsDTO = GSON.fromJson(response.body().string(), ContactsDTO.class);
                for (UserContactDTO contactDTO : contactsDTO.getContacts()) {
                    System.out.println(contactDTO.toString());
                }
            } else {
                System.out.println("No Contacts " + response.code());
            }
        } catch (IOException e) {
            System.out.println("Created Exception -> getAllUserContacts()");
            throw new RuntimeException(e);
        }

    }

}
