package udel.GardenProject.garden;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.junit.Test;

import udel.GardenProject.enums.Windows;
import udel.GardenProject.plants.Plant;
import udel.GardenProject.windows.AllPlants;
import udel.GardenProject.windows.Welcome;
import udel.GardenProject.windows.Window;

public class ModelTest {

	@Test
	public void testObject() throws NoSuchFieldException, SecurityException, 
		IllegalArgumentException, IllegalAccessException {
		Controller c = new Controller();
		Model m = new Model(c, 0, 1);
		assertTrue(m instanceof Model);
		
		Field privateWidth = Model.class.getDeclaredField("width");
		privateWidth.setAccessible(true);
		assertTrue(privateWidth.getInt(m) == 0);
		
		Field privateHeight = Model.class.getDeclaredField("height");
		privateHeight.setAccessible(true);
		assertTrue(privateHeight.getInt(m) == 1);
	}
	
	@Test
	public void testUpdate() {
		//Controller c = new Controller();
		//Model m = new Model(c, 0, 1);
		// TODO: Extend as update becomes defined
		// update() no longer existing as animation timer was removed
	}
	
	@Test
	public void testGetWindow() {
		Controller c = new Controller();
		Model m = new Model(c, 0, 1);
		Window w = m.getWindow();
		assertTrue(w instanceof Welcome);
	}
	
	@Test
	public void testSetWindow() {
		Controller c = new Controller();
		Model m = new Model(c, 0, 1);
		m.setWindow(Windows.AllPlants);
		assertTrue(m.getWindow() instanceof AllPlants);
	}
	
	@Test
	public void testSearchPlantsEmpty() {
		Controller c = new Controller();
		Model m = new Model(c, 0, 1);
		HashMap<String, Plant> p1 = m.searchPlants("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		HashMap<String, Plant> p2 = m.searchPlants("bingo bongo nonsense hippity hoppity");
		HashMap<String, Plant> p3 = m.searchPlants("null****************");
		HashMap<String, Plant> p4 = m.searchPlants("n/a");
		
		assertTrue(p1.size() == 0);
		assertTrue(p2.size() == 0);
		assertTrue(p3.size() == 0);
		assertTrue(p4.size() == 0);
	}
	
	@Test
	public void testSearchPlantsHasResults() {
		Controller c = new Controller();
		Model m = new Model(c, 0, 1);
		HashMap<String, Plant> p1 = m.searchPlants("pine");
		HashMap<String, Plant> p2 = m.searchPlants("");
		HashMap<String, Plant> p3 = m.searchPlants("dogwood");
		HashMap<String, Plant> p4 = m.searchPlants("Acer negundo");
		
		// could be multiples of these plants
		assertTrue(p1.size() > 0);
		assertTrue(p2.size() > 0);
		assertTrue(p3.size() > 0);
		
		// should only be one of these plants
		assertTrue(p4.size() == 1);
	}

}
