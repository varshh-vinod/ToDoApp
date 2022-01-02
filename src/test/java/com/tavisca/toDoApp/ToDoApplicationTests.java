package com.tavisca.toDoApp;
import com.tavisca.toDoApp.Services.ServicesToDo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ToDoApplication.class)
public class ToDoApplicationTests {
	@Test
	public void testToStart()
	{
		ToDoApplication.main(new String[]{
				"--spring.main.web-environment=false",
		});
	}
    @Test
    public void getFromToDo() {
        ServicesToDo todoService = new ServicesToDo();
        todoService.addTodo("{ \"todoname\" : \"firstTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"secondTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"thirdTodo\" }");
        ResponseEntity responseEntity = todoService.getTodos();
        Assert.assertTrue(responseEntity.getBody().toString().contains("[\"firstTodo\",\"secondTodo\",\"thirdTodo\"]"));
    }
    @Test
    public void testSingleAddTodo() {
        ServicesToDo todoService = new ServicesToDo();
        ResponseEntity responseEntity =  todoService.addTodo("{ \"todoname\" : \"firstTodo\" }");
        Assert.assertTrue(responseEntity.getBody().toString().contains("[\"firstTodo\"]"));
    }
    @Test
    public void testMultipleAddTodo() {
        ServicesToDo todoService = new ServicesToDo();
        todoService.addTodo("{ \"todoname\" : \"firstTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"secondTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"thirdTodo\" }");
        ResponseEntity responseEntity = todoService.addTodo("{ \"todoname\" : \"fourthTodo\" }");
        Assert.assertTrue(responseEntity.getBody().toString().contains("[\"firstTodo\",\"secondTodo\",\"thirdTodo\",\"fourthTodo\"]"));
    }
    @Test
    public void testUpdateTodo() {
        ServicesToDo todoService = new ServicesToDo();
        todoService.addTodo("{ \"todoname\" : \"firstTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"secondTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"thirdTodo\" }");
        ResponseEntity responseEntity = todoService.updateTodo("secondTodo", "updatedToDo");
        Assert.assertTrue(responseEntity.getBody().toString().contains("firstTodo"));
        Assert.assertTrue(responseEntity.getBody().toString().contains("updatedToDo"));
        Assert.assertTrue(responseEntity.getBody().toString().contains("thirdTodo"));
    }
    @Test
    public void testToSingleDelete()
    {
        ServicesToDo todoService = new ServicesToDo();
        todoService.addTodo("{ \"todoname\" : \"firstTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"secondTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"thirdTodo\" }");
        ResponseEntity responseEntity = todoService.deleteTodo("secondTodo");
        Assert.assertTrue(responseEntity.getBody().toString().contains("Todo-secondTodo Deleted"));
    }
    @Test
    public void testToMultipleDelete()
    {
        ServicesToDo todoService = new ServicesToDo();
        todoService.addTodo("{ \"todoname\" : \"firstTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"secondTodo\" }");
        todoService.addTodo("{ \"todoname\" : \"thirdTodo\" }");
        ResponseEntity responseEntity = todoService.deleteTodo("secondTodo");
        Assert.assertTrue(responseEntity.getBody().toString().contains("Todo-secondTodo Deleted"));
        responseEntity = todoService.deleteTodo("firstTodo");
        Assert.assertTrue(responseEntity.getBody().toString().contains("Todo-firstTodo Deleted"));
        responseEntity = todoService.deleteTodo("thirdTodo");
        Assert.assertTrue(responseEntity.getBody().toString().contains("Todo-thirdTodo Deleted"));
    }
    @Test
    public void testEmptyToDoGet()
    {
        ServicesToDo todoService = new ServicesToDo();
        ResponseEntity responseEntity = todoService.getTodos();
        Assert.assertTrue(responseEntity.getBody().toString().contains("[]"));
    }
}