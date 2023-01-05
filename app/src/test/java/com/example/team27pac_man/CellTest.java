package com.example.team27pac_man;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CellTest {
    @Test
public void checkvisited(){
    Cell cell=new Cell(3,4);
    assertFalse(cell.visited);
}
@Test
public void checkleftWall(){
    Cell cell=new Cell(3,4);
    assertFalse(cell.leftWall);
}

@Test
    public void checkrightWall(){
        Cell cell=new Cell(3,4);
        assertFalse(cell.rightWall);
    }

    @Test
    public void checktopWall(){
        Cell cell=new Cell(3,4);
        assertFalse(cell.topWall);
    }

    @Test
    public void checkbottomWall(){
        Cell cell=new Cell(3,4);
        assertFalse(cell.bottomWall);
    }
    @Test
    public void checkPellet(){
        Cell cell=new Cell(3,4);
        assertTrue(cell.pellet);
    }
    //edge case
    @Test
    public void checkbottomWall2(){
        Cell cell=new Cell(0,0);
        assertFalse(cell.bottomWall);
    }
    @Test
    public void checkvisited2(){
        Cell cell=new Cell(0,0);
        assertFalse(cell.visited);
    }
    @Test
    public void checkleftWall2(){
        Cell cell=new Cell(0,0);
        assertFalse(cell.leftWall);
    }

    @Test
    public void checkrightWall2(){
        Cell cell=new Cell(0,0);
        assertFalse(cell.rightWall);
    }

    @Test
    public void checktopWall2(){
        Cell cell=new Cell(0,0);
        assertFalse(cell.topWall);
    }
    @Test
    public void checkPellet2(){
        Cell cell=new Cell(0,0);
        assertTrue(cell.pellet);
    }
    //edge 2, other corner
    @Test
    public void checkbottomWall3(){
        Cell cell=new Cell(16,16);
        assertFalse(cell.bottomWall);
    }
    @Test
    public void checkvisited3(){
        Cell cell=new Cell(16,16);
        assertFalse(cell.visited);
    }
    @Test
    public void checkleftWall3(){
        Cell cell=new Cell(16,16);
        assertFalse(cell.leftWall);
    }

    @Test
    public void checkrightWall3(){
        Cell cell=new Cell(16,16);
        assertFalse(cell.rightWall);
    }

    @Test
    public void checktopWall3(){
        Cell cell=new Cell(16,16);
        assertFalse(cell.topWall);
    }
    @Test
    public void checkPellet3(){
        Cell cell=new Cell(16,16);
        assertTrue(cell.pellet);
    }
}
