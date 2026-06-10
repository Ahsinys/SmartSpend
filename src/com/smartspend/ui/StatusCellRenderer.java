package com.smartspend.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StatusCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column
    ) {

        Component c = super.getTableCellRendererComponent(
                table,
                value,
                isSelected,
                hasFocus,
                row,
                column
        );

        if (value != null) {

            String status = value.toString();

            if (status.equals("OK")) {

                c.setBackground(Color.GREEN);

            } else if (status.equals("WARNING")) {

                c.setBackground(Color.ORANGE);

            } else if (status.equals("OVER BUDGET")) {

                c.setBackground(Color.RED);

            } else {

                c.setBackground(Color.WHITE);
            }
        }

        return c;
    }
}