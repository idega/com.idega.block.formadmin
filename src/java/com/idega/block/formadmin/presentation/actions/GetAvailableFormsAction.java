package com.idega.block.formadmin.presentation.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.block.formadmin.business.beans.AvailableFormBean;
import com.idega.block.formadmin.business.beans.AvailableFormsLister;
import com.idega.block.formadmin.presentation.components.PhaseManagedGridHtmlDataTable;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class GetAvailableFormsAction implements ActionListener, IPhaseValueProvider {
	
	public void processAction(ActionEvent ae) {
		
	}
	
	public static final String COLUMNID_FORM_NAME = "formTitle";
	
	public List getAvailableForms() {
	
		List<AvailableFormBean> available_forms = AvailableFormsLister.getInstance().getAvailableForms();
		
		return available_forms;
	}
	
	public static void initiateTableColumnsProperties(Map session_map) {
		
		List<String> col_props = new ArrayList<String>();
		col_props.add(COLUMNID_FORM_NAME);
		
		session_map.put(PhaseManagedGridHtmlDataTable.COLUMN_PROPERTIES, col_props);
	}
	
	public List getGridTableValues() {

		return getAvailableForms();
	}
	
	public String getButtonValue() {
		
		return "Get available forms";
	}
}
