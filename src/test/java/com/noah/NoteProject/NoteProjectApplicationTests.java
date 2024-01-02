package com.noah.NoteProject;

import com.noah.NoteProject.controller.TaskController;
import com.noah.NoteProject.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class NoteProjectApplicationTests {
	@Autowired
	private TaskController testController;

	@BeforeEach
	public void setup(){
		testController.createTask("FirstTask", "First");
		testController.createTask("SecondTask", "Second");
	}

	@Test
	public void newTask(){
		Task task = new Task("Test", "Test");
		testController.createTask("ForTest", "Test");
	}
}
