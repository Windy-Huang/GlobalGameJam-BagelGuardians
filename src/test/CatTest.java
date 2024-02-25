package test;

import model.Cat;
import model.MouseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GamePanel;

import static org.junit.jupiter.api.Assertions.*;

public class CatTest {

    Cat c;
    MouseHandler m;
    GamePanel gp;
    Thread th;

    @BeforeEach
    public void setUp() {
        gp = new GamePanel();
        m = new MouseHandler(gp);
        c = new Cat(gp, m);
        th = new Thread(gp);
        th.start();
    }

    @Test
    public void testCheckValidOutsideBound() {
        assertEquals(3, c.checkValid(100,200));
        assertEquals(3, c.checkValid(200,400));
        assertEquals(3, c.checkValid(229,460));
        assertEquals(3, c.checkValid(230,200));
        assertEquals(3, c.checkValid(400,150));
        assertEquals(3, c.checkValid(450,300));
        assertEquals(3, c.checkValid(600,344));
        assertEquals(3, c.checkValid(580,350));
        assertEquals(3, c.checkValid(570,500));
        assertEquals(3, c.checkValid(460,460));
        assertEquals(3, c.checkValid(350,470));
        assertEquals(3, c.checkValid(240,475));
        assertEquals(3, c.checkValid(350,360));
    }

    @Test
    public void testCheckValidLeftBound() {
        assertEquals(1, c.checkValid(230,347));
        assertEquals(1, c.checkValid(335,347));
        assertEquals(1, c.checkValid(300,380));
        assertEquals(1, c.checkValid(250,453));

    }

    @Test
    public void testCheckValidRightBound() {
        assertEquals(2, c.checkValid(444,347));
        assertEquals(2, c.checkValid(444,453));
        assertEquals(2, c.checkValid(550,453));
        assertEquals(2, c.checkValid(500,400));
    }

    @Test
    public void testUpdateCatReactionNoReaction() {
        c.updateCatReaction();
        assertFalse(c.getNoReactionTime() == 0);
    }

    @Test
    public void testUpdateCatReactionNoReactionLong() {
        c.updateCatReaction();
        try {
            Thread.sleep((long) c.NO_REACTION + 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        c.updateCatReaction();
        assertEquals(c.getSmirky(), c.getImg());
    }

    @Test
    public void testUpdateCatReactionLeft() {
        m.setLeftDirection(true);
        c.updateCatReaction();
        assertTrue(c.getNoReactionTime() == 0);
        assertFalse(c.getStartReactionTime() == 0);
        assertEquals(1, c.getHit());
        assertFalse(m.getLeftDirection());
    }

    @Test
    public void testUpdateCatReactionLeftMultiple() {
        m.setLeftDirection(true);
        c.updateCatReaction();
        try {
            Thread.sleep((long) c.WAIT + 100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        c.updateCatReaction();
        m.setLeftDirection(true);
        c.updateCatReaction();
        assertTrue(c.getNoReactionTime() == 0);
        assertFalse(c.getStartReactionTime() == 0);
        assertEquals(2, c.getHit());
        assertFalse(m.getLeftDirection());
    }

    @Test
    public void testUpdateCatReactionRight() {
        m.setRightDirection(true);
        c.updateCatReaction();
        assertTrue(c.getNoReactionTime() == 0);
        assertFalse(c.getStartReactionTime() == 0);
        assertEquals(1, c.getHit());
        assertFalse(m.getRightDirection());
    }

    @Test
    public void testUpdateCatReactionRightMultiple() {
        m.setRightDirection(true);
        c.updateCatReaction();
        try {
            Thread.sleep((long) c.WAIT + 100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        c.updateCatReaction();
        m.setRightDirection(true);
        c.updateCatReaction();
        assertTrue(c.getNoReactionTime() == 0);
        assertFalse(c.getStartReactionTime() == 0);
        assertEquals(2, c.getHit());
        assertFalse(m.getRightDirection());
    }

    @Test
    public void testUpdateCatReactionInBetween() {
        m.setRightDirection(true);
        c.updateCatReaction();
        c.updateCatReaction();
        assertFalse(c.getStartReactionTime() == 0);
    }

    @Test
    public void testRestart() {
        m.setRightDirection(true);
        c.updateCatReaction();
        c.restart();
        assertEquals(0, c.getNoReactionTime());
        assertEquals(0, c.getStartReactionTime());
        assertEquals(0, c.getHit());
    }

    @Test
    public void testSuccess() {
        for(int i = 0; i < c.TARGET; i++) {
            m.setRightDirection(true);
            c.updateCatReaction();
            try {
                Thread.sleep((long) c.WAIT + 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            c.updateCatReaction();
        }
        m.setRightDirection(true);
        c.updateCatReaction();
        assertEquals(c.getCry(), c.getImg());
    }

}
