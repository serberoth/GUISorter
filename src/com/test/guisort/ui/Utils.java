/*
 * Utils.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.ui;

import java.io.*;
import java.awt.*;
import javax.swing.*;

/**
 * Utilities class that contains utility methods that help with displaying
 * message panels for errors and messages
 * @author Nicholas DiPasquale
 */
public class Utils {

	/**
	 * Utility method to display a popup with the provided message and title
	 * @param c The parent component of the message popup
	 * @param message The message to display on the popup
	 * @param title The title to display on the popup
	 */
	public static void showMessage (Component c, String message, String title) {
		JOptionPane.showMessageDialog (c, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Utility method to display a popup with the provided Throwable
	 * @param c The parent component of the message popup
	 * @param t The exception to display on the popup
	 */
	public static void showException (Component c, Throwable t) {
		showException (c, t.getMessage (), t);
	}
	/**
	 * Utility method to display a popup with the provided message ant Throwable
	 * @param c The parent component of the message popup
	 * @param message The message to display on the popup
	 * @param t The exception to display on the popup
	 */
	public static void showException (Component c, String message, Throwable t) {
		if (t == null) {
			t = new Exception ("Unknown Exception");
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		t.printStackTrace (new PrintStream (baos));
		if (message == null) {
			message = t.getClass ().getName ();
		}
		String stackTrace = baos.toString ();
		message += System.getProperty ("line.separator") + stackTrace;
		
		JOptionPane.showMessageDialog (c, message, "Error Message", JOptionPane.ERROR_MESSAGE);
	}
	
}
