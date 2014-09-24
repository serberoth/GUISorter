/*
 * DisplayPanel.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.ui;

import java.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * Main display panel for this project, contains the controls, buttons, and
 * SortingPanel that displays the sort in action
 * @author Nicholas DiPasquale
 */
public class DisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/* Minimum size for the item count spinner control */
	private static final int MIN_SIZE = 10;
	/* Maximum size for the item count spinner control */
	private static final int MAX_SIZE = 100;
	/* Default size for the item count spinner control */
	private static final int DEFAULT_SIZE = 60;
	
	/**
	 * Implements the Runnable interface to run a sort action while allowing the
	 * display to update and recieve events
	 * @author Nicholas DiPasquale
	 */
	private static class SortingRunner implements Runnable {
		
		/* The SortingPanel to updte while running the sort */
		private final SortingPanel _sortingPanel;
		/* The size of the dataset to generate */
		private final int _size;
		/* A button to disable while running the sort */
		private JComponent _button;
		/* A label that can contains the sort time upon completion */
		private JLabel _timeLabel;
		
		/**
		 * Creates a new SortingRunner with the given SortingPanel size of data
		 * set to generate
		 * @param sortingPanel SortingPanel to update while sorting
		 * @param size Size of the dataset to generate
		 */
		public SortingRunner (SortingPanel sortingPanel, int size) {
			this (sortingPanel, size, null, null);
		}
		/**
		 * Creates a new SortingRunner with the given SortingPanel size of data
		 * set to generate and component to disable while running
		 * @param sortingPanel SortingPanel to update while sorting
		 * @param size Size of the dataset to generate
		 * @param button Component to disable while running
		 * @param timeLabel Label to display the algorithm completion time
		 */
		public SortingRunner (SortingPanel sortingPanel, int size, JComponent button, JLabel timeLabel) {
			_sortingPanel = sortingPanel;
			_size = size;
			_button = button;
			_timeLabel = timeLabel;
		}
		
		/**
		 * @see java.lang.Runnable#run ()
		 */
		public void run () {
			if (_button != null) {
				_button.setEnabled (false);
			}
			
			Integer[] data = new Integer[_size];
			for (int i = 0; i < data.length; ++i) {
				data[i] = new Integer ((int) (Math.random () * (double) (_sortingPanel.getSize ().height - 3)) + 2);
			}

			_timeLabel.setText ("Working...");
			
			try {
				long startTime = System.currentTimeMillis ();
				
				_sortingPanel.algorighm ().execute (_sortingPanel, data);
				
				long sortTime = System.currentTimeMillis () - startTime;
				_timeLabel.setText (sortTime + " ms");
			} catch (Throwable t) {
				_timeLabel.setText ("Error");
				Utils.showException (_sortingPanel, t);
			}
			
			_sortingPanel.repaint ();
			
			if (_button != null) {
				_button.setEnabled (true);
			}
		}
		
	} /* END: class SortingRunner */

	/* Combo control for the selected algorithm */
	private JComboBox _algorithmCombo;
	/* Spinner control for the dataset size */
	private JSpinner _sizeSpinner;
	/* Flag for displaying the arrows on the SortingPanel */
	private JCheckBox _arrowCheckbox;
	/* Flag for displaying the columns on the SortingPanel */
	private JCheckBox _columnCheckbox;
	/* An instance of a SortingPanel to visually display the sort */
	private SortingPanel _sortingPanel;
	/* The sort button; onclick starts a SortingRunner */
	private JButton _sortButton;
	/* Text area that contains information about the selected sorting algorithm*/
	private JTextArea _algorithmInfoArea;
	/* Label field that contains the last completed sort time */
	private JLabel _sortingTimeLabel;
	
	/**
	 * Create a new instance of the DisplayPanel class
	 */
	public DisplayPanel () {
		SpringLayout layout = new SpringLayout ();
		setLayout (layout);
		
		SpinnerModel model = new SpinnerNumberModel (DEFAULT_SIZE, MIN_SIZE, MAX_SIZE, 1);
		_sortingPanel = new SortingPanel ();
		JLabel algorithmLabel = new JLabel ("Algorithm: ");
		_algorithmCombo = new JComboBox (SortingPanel.Algorithm.values ());
		_algorithmCombo.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				_algorithmCombo_ActionListener_actionPerformed (event);
			}
		});
		JLabel sizeLabel = new JLabel ("Item Count: ");
		_sizeSpinner = new JSpinner (model);
		_arrowCheckbox = new JCheckBox ("Show Arrows", _sortingPanel.displayArrows ());
		_arrowCheckbox.addChangeListener (new ChangeListener () {
			public void stateChanged (ChangeEvent event) {
				_arrowCheckbox_ChangeListener_stateChanged (event);
			}
		});
		_columnCheckbox = new JCheckBox ("Show Columns", _sortingPanel.displayColumns ());
		_columnCheckbox.addChangeListener (new ChangeListener () {
			public void stateChanged (ChangeEvent event) {
				_columnCheckbox_ChangeListener_stateChanged (event);
			}
		});
		_sortButton = new JButton ("Sort");
		_sortButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				_sortButton_ActionListener_actionPerformed (event);
			}
		});
		JLabel algorithmInfoLabel = new JLabel ("Algorithm Info: ");
		_algorithmInfoArea = new JTextArea (7, 18);
		_algorithmInfoArea.setEditable (false);
		_algorithmInfoArea.setBackground (new Color (0x000000FF, true));
		_algorithmInfoArea.setBorder (new LineBorder (Color.DARK_GRAY));
		JLabel sortTimeLabel = new JLabel ("Sort Time: ");
		_sortingTimeLabel = new JLabel ("N/A");
		
		_algorithmCombo_ActionListener_actionPerformed (null);
		
		layout.putConstraint (SpringLayout.WEST, algorithmLabel, 5, SpringLayout.WEST, this);
		layout.putConstraint (SpringLayout.NORTH, algorithmLabel, 5, SpringLayout.NORTH, this);
		
		layout.putConstraint (SpringLayout.WEST, _algorithmCombo, 5,  SpringLayout.EAST, algorithmLabel);
		layout.putConstraint (SpringLayout.NORTH, _algorithmCombo, 5, SpringLayout.NORTH, this);
		
		layout.putConstraint (SpringLayout.WEST, sizeLabel, 5, SpringLayout.WEST, this);
		layout.putConstraint (SpringLayout.NORTH, sizeLabel, 5, SpringLayout.SOUTH, _algorithmCombo);
		
		layout.putConstraint (SpringLayout.WEST, _sizeSpinner, 5,  SpringLayout.EAST, sizeLabel);
		layout.putConstraint (SpringLayout.NORTH, _sizeSpinner, 5, SpringLayout.SOUTH, _algorithmCombo);
		
		layout.putConstraint (SpringLayout.WEST, _arrowCheckbox, 5, SpringLayout.WEST, this);
		layout.putConstraint (SpringLayout.NORTH, _arrowCheckbox, 5, SpringLayout.SOUTH, _sizeSpinner);
		
		layout.putConstraint (SpringLayout.WEST, _columnCheckbox, 5, SpringLayout.WEST, this);
		layout.putConstraint (SpringLayout.NORTH, _columnCheckbox, 5, SpringLayout.SOUTH, _arrowCheckbox);
		
		layout.putConstraint (SpringLayout.WEST, algorithmInfoLabel, 5, SpringLayout.WEST, this);
		layout.putConstraint (SpringLayout.NORTH, algorithmInfoLabel, 5, SpringLayout.SOUTH, _columnCheckbox);
		
		layout.putConstraint (SpringLayout.WEST, _algorithmInfoArea, 5, SpringLayout.WEST, this);
		layout.putConstraint (SpringLayout.NORTH, _algorithmInfoArea, 5, SpringLayout.SOUTH, algorithmInfoLabel);
		
		layout.putConstraint (SpringLayout.WEST, sortTimeLabel, 5, SpringLayout.WEST, this);
		layout.putConstraint (SpringLayout.NORTH, sortTimeLabel, 5, SpringLayout.SOUTH, _algorithmInfoArea);
		
		layout.putConstraint (SpringLayout.WEST, _sortingTimeLabel, 5, SpringLayout.EAST, sortTimeLabel);
		layout.putConstraint (SpringLayout.NORTH, _sortingTimeLabel, 5, SpringLayout.SOUTH, _algorithmInfoArea);
		
		layout.putConstraint (SpringLayout.EAST, _algorithmCombo, -5, SpringLayout.WEST, _sortingPanel);
		layout.putConstraint (SpringLayout.WEST, _sortingPanel, 5, SpringLayout.EAST, _sizeSpinner);
		layout.putConstraint (SpringLayout.NORTH, _sortingPanel, 5, SpringLayout.NORTH, this);
		
		layout.putConstraint (SpringLayout.EAST, this, 5, SpringLayout.EAST, _sortingPanel);
		layout.putConstraint (SpringLayout.NORTH, _sortButton, 5, SpringLayout.SOUTH, _sortingPanel);
		
		layout.putConstraint (SpringLayout.EAST, _sortButton, -20, SpringLayout.EAST, _sortingPanel);
		layout.putConstraint (SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, _sortButton);

		
		add (algorithmLabel);
		add (_algorithmCombo);
		add (sizeLabel);
		add (_sizeSpinner);
		add (_arrowCheckbox);
		add (_columnCheckbox);
		add (algorithmInfoLabel);
		add (_algorithmInfoArea);
		add (sortTimeLabel);
		add (_sortingTimeLabel);
		add (_sortingPanel);
		add (_sortButton);
	}
	
	/*
	 * Method that updates the sizeSpinner control internal value based on the
	 * Editor part of the spinner control
	 */
	private void _sizeSpinner_update () {
		Object oldValue = _sizeSpinner.getValue ();
		try {
			_sizeSpinner.commitEdit ();
		} catch (ParseException pe) {
			JComponent editor = _sizeSpinner.getEditor ();
			if (editor instanceof JSpinner.DefaultEditor) {
				((JSpinner.DefaultEditor) editor).getTextField ().setValue (_sizeSpinner.getValue ());
			}
			_sizeSpinner.setValue (oldValue);
		}
	}
	
	/*
	 * ActionListener implementation that effects the change of the combobox on
	 * the SortingPanel in real time
	 * @param event The event that occurred
	 */
	private void _algorithmCombo_ActionListener_actionPerformed (ActionEvent event) {
		SortingPanel.Algorithm algorithm = (SortingPanel.Algorithm) _algorithmCombo.getSelectedItem ();
		_sortingPanel.algorithm (algorithm);
		_algorithmInfoArea.setText (algorithm.getInfo ());
	}
	/*
	 * ChangeListener implementation that effects the change of the checkbox on
	 * the SortingPanel in real time
	 * @param event The event that occurred
	 */
	private void _arrowCheckbox_ChangeListener_stateChanged (ChangeEvent event) {
		_sortingPanel.displayArrows (_arrowCheckbox.isSelected ());
		_sortingPanel.repaint ();
	}
	
	/*
	 * ChangeListener implementation that effects the change of the checkbox on
	 * the SortingPanel in real time
	 * @param event The event that occurred
	 */
	private void _columnCheckbox_ChangeListener_stateChanged (ChangeEvent event) {
		_sortingPanel.displayColumns (_columnCheckbox.isSelected ());
		_sortingPanel.repaint ();
	}
	
	/*
	 * ActionListener implementation that starts a SortingRunner thread to
	 * execute a Sort and disabled the _sortButton on the panel while the
	 * sort is running
	 * @param event The event that occurred
	 */
	private void _sortButton_ActionListener_actionPerformed (ActionEvent event) {
		_sortButton.setEnabled (false);
		
		int size = DEFAULT_SIZE;
		_sizeSpinner_update ();
		SpinnerModel model = _sizeSpinner.getModel ();
		if (model instanceof SpinnerNumberModel) {
			SpinnerNumberModel numberModel = (SpinnerNumberModel) model;
			size = numberModel.getNumber ().intValue ();
		}

		_sortingPanel.displayArrows (_arrowCheckbox.isSelected ());
		_sortingPanel.displayColumns (_columnCheckbox.isSelected ());
		Thread thread = new Thread (new SortingRunner (_sortingPanel, size, _sortButton, _sortingTimeLabel));
		thread.start ();
	}
	
}
