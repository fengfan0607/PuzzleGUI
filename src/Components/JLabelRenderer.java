package Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class JLabelRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (hasFocus) {
			setBorder(new LineBorder(Color.RED));
		}
		if (value instanceof JLabel) {
			JLabel label = (JLabel) value;
			label.setOpaque(true);
			return label;
		}

		else
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

}