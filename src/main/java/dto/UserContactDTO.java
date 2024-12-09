package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder

public class UserContactDTO {

    private String id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private String description;

}
