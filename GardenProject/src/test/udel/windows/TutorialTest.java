package test.udel.windows;

import static org.junit.Assert.*;

import org.junit.Test;

import main.udel.windows.Tutorial;

public class TutorialTest {

	@Test
	public void testTutorial() {
		Tutorial t = new Tutorial();
		assertTrue(t.getTitle().equals("Tutorial Window"));
	}

}
