package data_provider;

import dto.UserContactDTO;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

public class DPContact {

    @DataProvider
    public UserContactDTO[] newContactDP() {

        UserContactDTO user1 = UserContactDTO.builder()
                .name(generateString(7))
                .lastName(generateString(11))
                .phoneNumber(generatePhone(15))
                .email(generateEmail(5))
                .address(generateString(10))
                .build();

        UserContactDTO user2 = UserContactDTO.builder()
                .name(generateString(7))
                .lastName(generateString(11))
                .phoneNumber(generatePhone(15))
                .email(generateEmail(5))
                .address(generateString(10))
                .build();

        UserContactDTO user3 = UserContactDTO.builder()
                .name(generateString(7))
                .lastName(generateString(11))
                .phoneNumber(generatePhone(15))
                .email(generateEmail(5))
                .address(generateString(10))
                .build();

        return new UserContactDTO[] { user1, user2, user3 };

    }

    @DataProvider
    public Iterator<UserContactDTO> newContactDPFile() {

        List<UserContactDTO> contactList = new ArrayList<>();
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/resources/data_provider/table_contact.csv"));
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] splitArray = line.split(",");
                contactList.add(UserContactDTO.builder()
                        .name(splitArray[0])
                        .lastName(splitArray[1])
                        .phoneNumber(splitArray[2])
                        .email(splitArray[3])
                        .address(splitArray[4])
                        .description(splitArray[5])
                        .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return contactList.listIterator();

    }

}
