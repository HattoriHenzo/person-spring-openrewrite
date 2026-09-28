package com.practice.application.spring.itest;

import com.practice.application.spring.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

import static com.practice.application.spring.utils.TestData.createPerson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class PersonControllerITest {

    private static final int PERSON_ID = 1;
    private static final String URI_TEMPLATE = "http://localhost:%s";
    private String uri;
    @LocalServerPort
    private int localServerPort;
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        uri = String.format(URI_TEMPLATE, localServerPort);
    }

    @Test
    void integrationTest_For_FindingPersons() {
        webTestClient.get()
                .uri(uri + "/persons")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void integrationTest_For_FindingPersonById() {
        webTestClient.get()
                .uri(uri + "/persons/{id}", PERSON_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Person.class)
                .consumeWith(result -> {
                    var person = result.getResponseBody();
                    assertAll(
                            () -> assumingThat(Objects.nonNull(person), () -> {
                                assertThat(person.getFirstName()).isNotEmpty();
                                assertThat(person.getLastName()).isNotEmpty();
                            })
                    );
                });
    }

    @Test
    void integrationTest_For_CreatingPerson() {
        var newPerson = createPerson();

        webTestClient.post()
                .uri(uri + "/persons")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newPerson)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void integrationTest_For_UpdatingPerson() {
        var updatePerson = createPerson();

        webTestClient.put()
                .uri(uri + "/persons")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(updatePerson)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void integrationTest_For_DeletingPerson() {
        webTestClient.delete()
                .uri(uri + "/persons/{id}", PERSON_ID)
                .exchange()
                .expectStatus().isOk();
    }
}
