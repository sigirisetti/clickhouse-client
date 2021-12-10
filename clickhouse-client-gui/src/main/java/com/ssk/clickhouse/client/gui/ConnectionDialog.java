package com.ssk.clickhouse.client.gui;

import com.ssk.clickhouse.db.model.ClientConnectionConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ConnectionDialog extends JDialog implements ActionListener, PropertyChangeListener {

    private JOptionPane optionPane;

    private String connectButton = "Connect";
    private String addConnButton = "Add Connection";
    private String saveButton = "Save";
    private String cancelButton = "Cancel";

    private Application app;
    private ConnectionTable connectionTable;

    /**
     * Creates the reusable dialog.
     */
    public ConnectionDialog(Application app, Frame parent, String aWord) {
        super(parent, true);

        this.app = app;

        setTitle("Clickhouse Connection Dialog");

        connectionTable = new ConnectionTable(app);

        //Create an array of the text and components to be displayed.
        Object[] array = {connectionTable};

        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {connectButton, addConnButton, saveButton, cancelButton};

        //Create the JOptionPane.
        optionPane = new JOptionPane(array,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);

        //Make this dialog display it.
        setContentPane(optionPane);

        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                optionPane.setValue(JOptionPane.CLOSED_OPTION);
            }
        });

        //Ensure the text field always gets the first focus.
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
            }
        });

        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
        setLocationRelativeTo(null);

        pack();
    }

    /**
     * This method handles events for the text field.
     */
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(addConnButton);
    }

    /**
     * This method reacts to state changes in the option pane.
     */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible()
                && (e.getSource() == optionPane)
                && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
                JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                return;
            }

            //Reset the JOptionPane's value. If you don't do this, then if the user
            //presses the same button next time, no property change event will be fired.
            optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

            if (connectButton.equals(value)) {
                ClientConnectionConfig clientConnectionConfig = connectionTable.getSelectedRow();
                if (clientConnectionConfig != null) {
                    app.connect(clientConnectionConfig);
                }
            } else if (addConnButton.equals(value)) {
                connectionTable.addNewConnection();
            } else if (saveButton.equals(value)) {
                app.saveConnConfig(connectionTable.getAllConnConfigs());
            } else {
                setVisible(false);
            }
        }
    }
}

