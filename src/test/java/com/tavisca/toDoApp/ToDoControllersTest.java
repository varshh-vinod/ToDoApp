package com.tavisca.toDoApp;

import com.tavisca.toDoApp.Services.ServicesToDo;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ToDoControllersTest {
    @Autowired  private MockMvc mockMvc;
    @MockBean   private ServicesToDo todoService;
    @Test
    public void getAllTodosTest() throws Exception {
        List<String> mockTodoList = new ArrayList<String>() {{add("firsttodo");}};
        JSONObject mockJsonResponse = new JSONObject();
        mockJsonResponse.put("todos", mockTodoList);
        mockJsonResponse.put("status", "All Todos returned");
        mockJsonResponse.put("timestamp", Instant.now().toString());
        ResponseEntity responseEntity = new ResponseEntity<>(mockJsonResponse.toString(), HttpStatus.OK);
       given(todoService.getTodos()).willReturn(responseEntity);
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseEntity.getBody().toString()));
    }


    @Test
    public void addTodoTest() throws Exception {
        List<String> mockTodoList = new ArrayList<String>() {{add("addedTodo"); }};
        JSONObject mockJsonResponse = new JSONObject();
        mockJsonResponse.put("todos", mockTodoList);
        mockJsonResponse.put("status", "All todos retrieved");
        mockJsonResponse.put("timestamp", Instant.now().toString());
        ResponseEntity responseEntity = new ResponseEntity<>(mockJsonResponse.toString(), HttpStatus.OK);

        given(todoService.addTodo("{ \"todoname\" : \"addedTodo\" }")).willReturn(responseEntity);
        String requestBody = "{ \"todoname\" : \"addedTodo\" }";
        mockMvc.perform(post("/todos")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(responseEntity.getBody().toString()));
    }



    @Test
    public void deleteTodoTest() throws Exception {
        List<String> mockTodoList = new ArrayList<String>() {{ add("first");add("second");add("third");}};
        String todoid = "third";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("todoname", mockTodoList.get(2));
        jsonObject.put("status", "Todo-third Deleted");
        jsonObject.put("timestamp", Instant.now().toString());
        ResponseEntity responseEntity = new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

        given(todoService.deleteTodo(todoid)).willReturn(responseEntity);
        this.mockMvc.perform(delete("/todos/third"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseEntity.getBody().toString()));
    }

    @Test
    public void updateTodoTest() throws Exception {
        List<String> mockTodoList = new ArrayList<String>() {{add("updatedTodo");}};
        JSONObject mockJsonResponse = new JSONObject();
        mockJsonResponse.put("todos", mockTodoList);
        mockJsonResponse.put("status", "All todos retrieved");
        mockJsonResponse.put("timestamp", Instant.now().toString());
        ResponseEntity responseEntity = new ResponseEntity<>(mockJsonResponse.toString(), HttpStatus.OK);

        given(todoService.updateTodo("updatedTodo", "secondTodo")).willReturn(responseEntity);
        String requestBody = "{ \"todoname\" : \"updatedTodo\" }";
        mockMvc.perform(put("/todos/secondTodo/updatedTodo"))
                .andExpect(status().isOk());
    }
}
