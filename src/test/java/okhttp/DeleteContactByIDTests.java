package okhttp;

import dto.ContactsDTO;
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

import java.io.IOException;

public class DeleteContactByIDTests implements BaseAPI {

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
                System.out.println("Login Failed" + response.code());
            }
        } catch (IOException e) {
            System.out.println("Created Exception login()");
            throw new RuntimeException(e);
        }
    }

    // Status Code: 200

    @Test
    public void deleteContactByID() {

        String firstContactID = "";

        Request requestGET = new Request.Builder()
                .url(BASE_URL + CONTACT)
                .addHeader("Authorization", tokenDTO.getToken())
                .get()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(requestGET).execute()) {
            if (response.isSuccessful()) {
                ContactsDTO contactsDTO = GSON.fromJson(response.body().string(), ContactsDTO.class);
                firstContactID = contactsDTO.getContacts()[0].getId();
                System.out.println("First Contact ID -> " + firstContactID.toString());
            } else {
                System.out.println("No Contacts " + response.code());
            }
        } catch (IOException e) {
            System.out.println("Created Exception -> deleteContactByID()");
            throw new RuntimeException(e);
        }

        Request requestDELETE = new Request.Builder()
                .url(BASE_URL + CONTACT + "/" + firstContactID)
                .addHeader("Authorization", tokenDTO.getToken())
                .delete()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(requestDELETE).execute()) {
            if (response.isSuccessful()) {
                Assert.assertEquals(response.code(), 200);
            } else {
                Assert.fail("DELETE Failed " + response.code());
            }
        } catch (IOException e) {
            Assert.fail("Created Exception -> deleteContactByID()");
            throw new RuntimeException(e);
        }

    }

    // Status Code: 400

    @Test
    public void deleteContactByID_400_invalidURL() {

        Request requestDELETE = new Request.Builder()
                .url(BASE_URL + CONTACT + "/invalidID")
                .addHeader("Authorization", tokenDTO.getToken())
                .delete()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(requestDELETE).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Delete Failed");
                Assert.assertEquals(response.code(), 400);
            } else {
                Assert.fail("Status Code -> " + response.code());
            }
        } catch (IOException e) {
            Assert.fail("Created Exception -> deleteContactByID_400_invalidURL()");
            throw new RuntimeException(e);
        }

    }

    // Status Code: 401

    @Test
    public void deleteContactByID_401_unauthorized() {

        String firstContactID = "";

        Request requestGET = new Request.Builder()
                .url(BASE_URL + CONTACT)
                .addHeader("Authorization", tokenDTO.getToken())
                .get()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(requestGET).execute()) {
            if (response.isSuccessful()) {
                ContactsDTO contactsDTO = GSON.fromJson(response.body().string(), ContactsDTO.class);
                firstContactID = contactsDTO.getContacts()[0].getId();
                System.out.println("First Contact ID -> " + firstContactID.toString());
            } else {
                System.out.println("No Contacts " + response.code());
            }
        } catch (IOException e) {
            System.out.println("Created Exception -> deleteContactByID()");
            throw new RuntimeException(e);
        }

        Request requestDELETE = new Request.Builder()
                .url(BASE_URL + CONTACT + "/" + firstContactID)
                .addHeader("Authorization", "invalidToken")
                .delete()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(requestDELETE).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Delete Failed");
                Assert.assertEquals(response.code(), 401);
            } else {
                Assert.fail("Status Code -> " + response.code());
            }
        } catch (IOException e) {
            Assert.fail("Created Exception -> deleteContactByID_401_unauthorized()");
            throw new RuntimeException(e);
        }

    }

    // Status Code: 500

    @Test
    public void deleteContactByID_500_invalidURL() {

        Request requestDELETE = new Request.Builder()
                .url(BASE_URL + CONTACT + "/")
                .addHeader("Authorization", tokenDTO.getToken())
                .delete()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(requestDELETE).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Delete Failed");
                Assert.assertEquals(response.code(), 500);
            } else {
                Assert.fail("Status Code -> " + response.code());
            }
        } catch (IOException e) {
            Assert.fail("Created Exception -> deleteContactByID_500_invalidURL()");
            throw new RuntimeException(e);
        }

    }

}
