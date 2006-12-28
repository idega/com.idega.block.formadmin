package com.idega.block.formadmin.presentation.actions;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;
import com.idega.block.form.business.FormsService;
import com.idega.block.formadmin.presentation.components.ISelectedRowProvider;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWMainApplication;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class GetAvailableFormsAction implements ActionListener, ISelectedRowProvider {
	
	private static final Logger log = Logger.getLogger(GetAvailableFormsAction.class.getName());
	
	private String selected_row;
	List<String> col_props;
	
	public void processAction(ActionEvent ae) { }
	
	public static final String COLUMNID_FORM_NAME = "label";
	
	public List getAvailableForms() {	
		List<SelectItem> forms = null;
		try {
			forms = getFormsService().listForms();
		}
		catch (RemoteException e) {
			log.warning("Error getting available forms");
		}
		
		return forms == null ? new ArrayList<SelectItem>() : forms;
	}
	
	public List<String> getTableColumnsProperties() {
		
		if(col_props == null) {
			
			col_props = new ArrayList<String>();
			col_props.add(COLUMNID_FORM_NAME);
		}
		return col_props;
	}
	
	private FormsService getFormsService() {
		FormsService service = null;
		try {
			IWApplicationContext iwc = IWMainApplication.getDefaultIWApplicationContext();
			service = (FormsService) IBOLookup.getServiceInstance(iwc, FormsService.class);
		}
		catch (IBOLookupException e) {
			log.severe("Could not find FormsService");
		}
		return service;
	}
	
	public ISelectedRowProvider getSelectedRowProvider() {
		return this;
	}
	
	public void setSelectedRow(String selected_row) {
		this.selected_row = selected_row;
	}
	
	public String getSelectedRow() {
		return selected_row;
	}
}
