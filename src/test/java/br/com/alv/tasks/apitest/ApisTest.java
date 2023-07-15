package br.com.alv.tasks.apitest;

import org.junit.Test;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApisTest {
    
    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas(){
        RestAssured.given()
        .when()
            .get("/todo")
        .then()
            .statusCode(200);
    }

    @Test
    public void deveAdicionarTarefaComSucesso(){
        RestAssured.given()
            .body("{ \"task\": \"Teste via API\",\"dueDate\": \"2033-12-30\" }")
            .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            .statusCode(201);
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida(){
        RestAssured.given()
            .body("{ \"task\": \"Teste via API\",\"dueDate\": \"2010-12-30\" }")
            .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            .statusCode(400)
            .body("message",CoreMatchers.is("Due date must not be in past"));
    }

    @Test
    public void deveRemoverTarefaComSucesso(){
        Integer id = RestAssured.given()
            .body("{ \"task\": \"Teste teste\",\"dueDate\": \"2033-12-30\" }")
            .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            //.log().all()
            .statusCode(201)
            .extract().path("id");
        System.out.println(id);

        RestAssured.given()
        .when()
            .delete("/todo/"+id)
        .then()
            .statusCode(204);
    }



}

