package com.buss.demo.controller;

import com.buss.demo.domain.Contact;
import com.buss.demo.domain.TelephoneTypeEnum;
import com.buss.demo.domain.User;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerIT {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void whenCreateUserThenReturn() {

        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", "Forrest");
        userMap.put("lastName", "Gump");
        userMap.put("age", String.valueOf(20));

        given()
                .header("Content-Type", "application/json")
                .body(userMap)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("name", equalTo("Forrest"))
                .body("lastName", equalTo("Gump"))
                .body("age", equalTo(20))
                .body("contacts", empty());
    }

    @Test
    public void whenFindUserThenReturn() {
        User userCreated = createUser();
        given()
                .when()
                .get("/users/".concat(userCreated.getId().toString()))
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(userCreated.getId().intValue()))
                .body("name", equalTo("Joseph"))
                .body("lastName", equalTo("Cooper Murphy"))
                .body("age", equalTo(50))
                .body("contacts[0].id", notNullValue())
                .body("contacts[0].type", equalTo("HOUSE"))
                .body("contacts[0].phone", equalTo("1234-5678"));
    }


    private User mountObjectToCreate(String name, String lastName, int age, TelephoneTypeEnum type, String phone) {
        User user = new User(name, lastName, age);
        Contact contact = new Contact();
        contact.setType(type);
        contact.setPhone(phone);
        contact.setUser(user);
        user.getContacts().add(contact);
        return user;
    }

    private User createUser() {
        User user = mountObjectToCreate("Joseph", "Cooper Murphy", 50, TelephoneTypeEnum.HOUSE, "1234-5678");
        return given()
                .header("Content-Type", "application/json")
                .body(user) //a biblioteca faz a "conversão" para JSON de forma automática.
                .when()
                .post("/users")
                .body()
                .as(User.class);
    }
}
