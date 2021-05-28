import org.junit.*;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class tritypTest {
    trityp tri=new trityp();

    //    Triang = 1 if triangle is scalene
    //    Triang = 2 if triangle is isosceles
    //    Triang = 3 if triangle is equilaterala
    //    Triang = 4 if not a triangle

    @Test
    public void testTriang_4(){
        assertEquals(4,tri.Triang(1,2,7));
        assertEquals(4,tri.Triang(2,1,7));
        assertEquals(4,tri.Triang(1,7,2));
        assertEquals(4,tri.Triang(2,7,1));
        assertEquals(4,tri.Triang(7,2,1));
        assertEquals(4,tri.Triang(7,1,2));
        assertEquals(4,tri.Triang(2,2,7));
        assertEquals(4,tri.Triang(2,7,2));
        assertEquals(4,tri.Triang(7,2,2));
    }

    @Test
    public void test3NegaSide(){
        assertEquals(4,tri.Triang(-1,-1,-1));
    }

    @Test
    public void test2NegaSide(){
        assertEquals(4,tri.Triang(-1,-1,1));
        assertEquals(4,tri.Triang(-1,1,-1));
        assertEquals(4,tri.Triang(1,-1,-1));
    }

    @Test
    public void test1NegaSide(){
        assertEquals(4,tri.Triang(-1,1,1));
        assertEquals(4,tri.Triang(1,-1,1));
        assertEquals(4,tri.Triang(1,1,-1));
    }

    @Test
    public void testTriang_3(){
        assertEquals(3,tri.Triang(6,6,6));
    }

    @Test
    public void testTriang_2(){
        assertEquals(2,tri.Triang(5,5,3));
        assertEquals(2,tri.Triang(5,5,7));
        assertEquals(2,tri.Triang(3,5,5));
        assertEquals(2,tri.Triang(7,5,5));
        assertEquals(2,tri.Triang(5,3,5));
        assertEquals(2,tri.Triang(5,7,5));
    }

    @Test
    public void testTriang_1(){
        assertEquals(1,tri.Triang(2,3,4));
        assertEquals(1,tri.Triang(3,2,4));
        assertEquals(1,tri.Triang(2,4,3));
        assertEquals(1,tri.Triang(3,4,2));
        assertEquals(1,tri.Triang(4,3,2));
        assertEquals(1,tri.Triang(4,2,3));
    }
}