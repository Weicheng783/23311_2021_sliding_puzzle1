package uk.ac.man.cs.puzzle.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import uk.ac.man.cs.puzzle.logic.Model;

public class GraphicsPanel extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = -6961345367517564513L;
	private static int ROWS;
    private static int COLS;
    
    private Model puzzleModel;
    
    private static final int CELL_SIZE = 80; // Pixels
    private Font biggerFont;
    
    public GraphicsPanel(Model puzzleModel, int rows, int cols) {
        biggerFont = new Font("SansSerif", Font.BOLD, CELL_SIZE/2);
        
        ROWS = rows;
        COLS = cols;
        this.setPreferredSize(
               new Dimension(CELL_SIZE * COLS, CELL_SIZE * ROWS));
        
        this.setBackground(Color.black);
        this.addMouseListener(this);
        this.puzzleModel = puzzleModel;

        puzzleModel.shuffle();
    }

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int x = c * CELL_SIZE; //X represents columns
                int y = r * CELL_SIZE; //Y represents rows
                String text = puzzleModel.getFace(r, c);
                if (text != null) {
                    g.setColor(Color.gray);
                    g.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                    g.setColor(Color.black);
                    g.setFont(biggerFont);
                    g.drawString(text, x + 20, y + (3 * CELL_SIZE) / 4);
                }
            }
        }
    }
    //BUG ASSUMED...
    public int[] getTileXYFromCoordinates(MouseEvent e) {
    	int[] tile = { e.getX() / CELL_SIZE, e.getY() / CELL_SIZE };
    	return tile;
    }
    
    public void mousePressed(MouseEvent e) {
        // Map x, y coordinates into a row and column.
        int[] tile = getTileXYFromCoordinates(e);
        int col = tile[0];
        int row = tile[1];
        
        if (!puzzleModel.moveTile(row, col)) {
            // moveTile moves tile if legal, else returns false.
            Toolkit.getDefaultToolkit().beep();
        }
        
        this.repaint();  // Show any updates to model.
        if (puzzleModel.gameOver()) {
        	this.setBackground(Color.green);
        }
    }
    
    // Ignore these events
    public void mouseClicked (MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered (MouseEvent e) {}
    public void mouseExited  (MouseEvent e) {}

}
