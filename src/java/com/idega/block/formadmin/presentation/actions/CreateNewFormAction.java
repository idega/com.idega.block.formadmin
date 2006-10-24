package com.idega.block.formadmin.presentation.actions;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class CreateNewFormAction implements ActionListener, IPhaseValueProvider {
	public void processAction(ActionEvent ae) {
		
	}
	
	public List getGridTableValues() { 
		throw new NullPointerException("__ Not implemented __");
	}
	
	public String getButtonValue() {
		
		return "Create new form";
	}
}
