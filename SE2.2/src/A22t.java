import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class A22t {

	@Test
	//Untersucht richtige Reihenfolgen
	void reihenFolgeRichtig()  {
		String[][] reihenfolge = new String[][]{{ "A" , "C" }, { "C", "D" }, { "B", "C" }};
		A22 r01 = new A22(reihenfolge);
		String []  s01 = new String[]{ "A" , "B", "C", "D" };
		String []  s02 = new String[]{ "C" , "D" };
		String []  s03 = new String[]{ "B" , "A", "C", "D" };
		assertEquals(true,r01.isWellSorted(s01));
		assertEquals(true,r01.isWellSorted(s02));
		assertEquals(true,r01.isWellSorted(s03));
	}
	
	@Test
	//Untersucht falsche Reihenfolgen
	void reihenFolgeFalsch() {
		String[][] reihenfolge = new String[][]{{ "A" , "C" }, { "C", "D" }, { "B", "C" }};
		A22 r01 = new A22(reihenfolge);
		String []  s01 = new String[]{ "D" , "C", "B", "A" };
		String []  s02 = new String[]{ "D" , "D", "D", "D" };
		String []  s03 = new String[]{ "A" , "B", "C", "D", "A" };
		assertEquals(false,r01.isWellSorted(s01));
		assertEquals(false,r01.isWellSorted(s02));
		assertEquals(false,r01.isWellSorted(s03));
	}
	
	@Test
	void transitivität() {
		String[][] reihenfolge = new String[][]{{ "A" , "B" }, { "B", "C" }};
		A22 r01 = new A22(reihenfolge);
		String []  s01 = new String[]{ "A","B","C" };
		String []  s02 = new String[]{ "B","A","C" };
		assertEquals(true,r01.isWellSorted(s01));
		assertEquals(false,r01.isWellSorted(s02));
	}
	
	@Test
	void fehlerhafteEingabe() {
		String[][] reihenfolge = new String[][]{{ "A" , "C" }, { "C", "D" }, { "B", "C" }};
		A22 r01 = new A22(reihenfolge);
		String []  s01 = new String[]{ "A","C","E" };
		assertEquals(false,r01.isWellSorted(s01));
}
}
