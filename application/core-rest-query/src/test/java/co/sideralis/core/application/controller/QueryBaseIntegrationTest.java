package co.sideralis.core.application.controller;

import co.com.sofka.domain.generic.DomainEvent;
import co.sideralis.core.application.Application;
import com.mongodb.MongoClient;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class QueryBaseIntegrationTest {

    private RequestSpecification documentationSpec;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @BeforeAll
    static void cleanAll() {
        new MongoClient().getDatabase("queries").drop();
    }

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.documentationSpec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();

    }

    @LocalServerPort
    private void initRestAssured(final int localPort) {
        RestAssured.port = localPort;
        RestAssured.baseURI = "http://localhost";
    }

    protected void fireEvent(DomainEvent event, String aggregateName, String aggregateId) {
        event.setAggregateName(aggregateName);
        event.setAggregateRootId(aggregateId);
        applicationEventPublisher.publishEvent(event);
    }

    protected void executor(String path, ResponseFieldsSnippet responseFieldsSnippet) {
        given(documentationSpec)
                .filter(document(
                        path.substring(0, path.indexOf('?') > 0 ? path.indexOf('?') : path.length()),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFieldsSnippet
                ))
                .contentType(ContentType.JSON)
                .when()
                .get(path)
                .then()
                .assertThat().statusCode(is(200));
    }
}
