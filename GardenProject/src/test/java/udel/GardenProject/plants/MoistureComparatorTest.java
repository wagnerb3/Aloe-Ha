package udel.GardenProject.plants;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

import udel.GardenProject.enums.Canopy;
import udel.GardenProject.enums.Moisture;
import udel.GardenProject.enums.Seasons;
import udel.GardenProject.plants.MoistureComparator;
import udel.GardenProject.plants.Plant;

public class MoistureComparatorTest {

	@Test
	public void defaultConstructorTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		MoistureComparator c = new MoistureComparator();
		Field privateDescending = MoistureComparator.class.getDeclaredField("descending");
		privateDescending.setAccessible(true);
		assertTrue(privateDescending.getBoolean(c) == true);
	}
	
	@Test
	public void booleanConstructorTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		MoistureComparator c = new MoistureComparator(true);
		Field privateDescending = MoistureComparator.class.getDeclaredField("descending");
		privateDescending.setAccessible(true);
		assertTrue(privateDescending.getBoolean(c) == true);
	}

	@Test
	public void sameMoistureTest() {
		MoistureComparator c = new MoistureComparator(true);
		Plant pine = new Plant(null, null, null, null, 0, Moisture.DRY, null, Canopy.FLOOR, false, false, null, null);
		Plant flower = new Plant(null, null, null, null, 0, Moisture.DRY, null, Canopy.FLOOR, false, false, null, null);
		assertTrue(c.compare(pine, flower) == 0);
	}
	
	@Test
	public void ascendMoistureTest() {
		MoistureComparator c = new MoistureComparator(false);
		Plant pine = new Plant(null, null, null, null, 0, Moisture.DRY, null, Canopy.FLOOR, false, false, null, null);
		Plant flower = new Plant(null, null, null, null, 0, Moisture.MOIST, null, Canopy.FLOOR, false, false, null, null);
		assertTrue(c.compare(pine, flower) < 0);
	}
	
	@Test
	public void descendMoistureTest() {
		MoistureComparator c = new MoistureComparator(true);
		Plant pine = new Plant(null, null, null, null, 0, Moisture.DRY, null, Canopy.FLOOR, false, false, null, null);
		Plant flower = new Plant(null, null, null, null, 0, Moisture.MOIST, null, Canopy.FLOOR, false, false, null, null);
		assertTrue(c.compare(pine, flower) > 0);
	}
	
}
