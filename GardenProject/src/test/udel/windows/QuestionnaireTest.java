package test.udel.windows;

import static org.junit.Assert.*;

import org.junit.Test;

import main.udel.windows.Questionnaire;

public class QuestionnaireTest {
	
	@Test
	public void testQuestionnaire() {
		Questionnaire q = new Questionnaire();
		assertTrue(q.getTitle().equals("Questions About Your Garden..."));
	}
}