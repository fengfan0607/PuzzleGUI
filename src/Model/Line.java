package Model;

import java.util.Arrays;

public class Line {
	private int[] line;
	private String lineName;

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public int[] getLine() {
		return line;
	}

	public void setLine(int[] line) {
		this.line = line;
	}

	public void setLineShiftAtPosition(int shift, int driver) {
		line[shift] = driver;
	}

	public int getLineShiftAtPosition(int shift) {
		return line[shift];
	}

	public Line(int[] line, String name) {
		this.line = line;
		this.lineName = name;
	}

	@Override
	public String toString() {
		return "Line " + lineName + " [line=" + Arrays.toString(getLine()) + "]";
	}
}
