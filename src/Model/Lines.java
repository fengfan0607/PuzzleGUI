package Model;

import java.util.Arrays;
import java.util.Observable;

import View.lineView;

public class Lines extends Observable {
	private Line[] AllLines;

	public String toStringLine() {
		return "lines " + ": " + Arrays.toString(AllLines);
	}

	public Line[] getAllLines() {
		return AllLines;
	}

	public void setLine(int i, Line line) {
		AllLines[i] = line;
	}

	public Line getLine(int i) {
		return AllLines[i];
	}

	public Lines(Line[] allLines) {
		super();
		AllLines = allLines;
	}

	public void setAllLines(Line[] allLines) {
		AllLines = allLines;
	}

	public Lines() {
		super();
		// TODO Auto-generated constructor stub
	}

}
