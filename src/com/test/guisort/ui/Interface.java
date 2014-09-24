/*
 * Interface.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Main Swing frame container class, the Interface class maintains the JFrame,
 * JPanel, and JMenuBar that make up the user interface for the application.
 * @author Nicholas DiPasquale
 */
public class Interface {

	/* Constant for the JFrame title string */
	private static final String FRAME_TITLE = "Example GUI Sorter";
	/* Constant for the default JPanel size */
	private static final Dimension DEFAULT_SIZE = new Dimension (600, 400);
	
	/* The main frame of the UI */
	private JFrame _frame;
	/* The main panel on the frame */
	private JPanel _panel;
	/* The menu bar on the frame */
	private JMenuBar _menu;
	
	/**
	 * Create a new instance of the Interface class that is not visible
	 */
	public Interface () {
		this (false);
	}
	/**
	 * Create a new instance of the Interface class with the provided visiblity
	 * flag
	 * @param visible The visiblity flag for the Interface frame
	 */
	public Interface (boolean visible) {
		_frame = new JFrame ();
		_panel = new DisplayPanel ();
		_menu = new JMenuBar ();
		
		JMenu options = new JMenu ("Options");
		options.setMnemonic ('o');
		
		JMenuItem exit = new JMenuItem ("Exit");
		exit.setMnemonic ('x');
		exit.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		exit.setToolTipText ("Quit this application");
		exit.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				exit_ActionListener_actionPerformed (event);
			}
		});
		
		options.add (exit);
		_menu.add (options);
		
		_frame.addWindowListener (new WindowAdapter () {
			public void windowClosing (WindowEvent event) {
				WindowAdapter_windowClosing (event);
			}
		});
		
		_frame.getContentPane ().setLayout (new BorderLayout ());
		_frame.getContentPane ().add (_panel, BorderLayout.CENTER);
		_panel.setPreferredSize (DEFAULT_SIZE);
		_frame.setJMenuBar (_menu);
		
		_frame.setResizable (false);
		_frame.pack ();
		
		Dimension screen = _frame.getToolkit ().getScreenSize ();
		Dimension size = _frame.getSize ();
		_frame.setLocation ((screen.width - size.width) / 2, (screen.height - size.height) / 2);
		_frame.setTitle (FRAME_TITLE);
		_frame.setVisible (visible);
	}
	
	/**
	 * Sets the visibility flag on the JFrame
	 * @param visible The visibility flag
	 */
	public void setVisible (boolean visible) {
		_frame.setVisible (visible);
	}
	
	/**
	 * Gets the Interfaces JFrame instance
	 * @return The JFrame maintained by this Interface instance
	 */
	protected JFrame getFrame () {
		return _frame;
	}
	
	/**
	 * Gets the Interfaces JPanel instance
	 * @return The JPanel maintained by this Interface instance
	 */
	protected JPanel getPanel () {
		return _panel;
	}
	
	/*
	 * WindowAdapter implementation for the Interface JFrame
	 */
	private void WindowAdapter_windowClosing (WindowEvent event) {
		if (_frame != null) {
			_frame.dispose ();
			_frame = null;
		}
		System.exit (0);
	}
	
	/*
	 * ActionListener implementation for the exit menu option
	 */
	private void exit_ActionListener_actionPerformed (ActionEvent event) {
		if (_frame != null) {
			_frame.dispose ();
			_frame = null;
		}
		System.exit (0);
	}
	
}
