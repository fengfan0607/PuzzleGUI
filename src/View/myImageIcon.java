package View;

import java.awt.Image;

import javax.swing.ImageIcon;

public class myImageIcon extends ImageIcon {

	private int row;
	private int column;
	private Image image;

	public myImageIcon() {

	}

	public myImageIcon(int r, int c, Image image) {
		// TODO Auto-generated constructor stub
		super(image);
		this.row = r;
		this.column = c;
		this.image = image;
	}

	public int getRow() {
		return row;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

}
