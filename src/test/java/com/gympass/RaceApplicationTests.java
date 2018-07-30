package com.gympass;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.gympass.model.Piloto;
import com.gympass.model.Volta;

public class RaceApplicationTests {
	List<String> list = new ArrayList<>();

	@Before
    public void setUp() {
		
		try (BufferedReader br = new BufferedReader(new FileReader("race.txt"))) {
			list = br.lines().collect(Collectors.toList());
			list = list.subList(1, list.size());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void shouldLoadLaps() {
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void shouldLoadVolta(){
		Volta volta = RaceApplication.formataLinha(list.get(0));
		assertTrue(volta != null);
	}
	
	@Test
	public void shouldLoadPiloto(){
		Volta volta = RaceApplication.formataLinha(list.get(0));
		Piloto piloto = volta.getPiloto();
		assertTrue(piloto != null);
	}
	
	@Test
	public void shouldLoadSpeed(){
		Volta volta = RaceApplication.formataLinha(list.get(0));
		Piloto piloto = volta.getPiloto();
		Double speed = piloto.getVelocidadeTotal();
		assertTrue(speed > 0);
	}

}
