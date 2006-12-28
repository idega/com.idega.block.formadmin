package com.idega.block.formadmin.presentation.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import com.idega.block.form.business.FormsService;
import com.idega.block.formadmin.presentation.components.ISelectedRowProvider;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.presentation.IWContext;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class ViewAllSubmittedDataAction implements ActionListener, ISelectedRowProvider {
	
	private static final Logger log = Logger.getLogger(ViewAllSubmittedDataAction.class.getName());

	public static final String COLUMNID_LABEL1 = "label1";
	public static final String COLUMNID_LABEL2 = "label2";
	
	private String selected_row;
	private String selected_forms_row;
	List<String> col_props;
	
	private static final String AV_FORMS_ACTION = "availableFormsAction";
	
	public void processAction(ActionEvent ae) { }
	
	public List getAllSubmittedData() {		
		FacesContext ctx = FacesContext.getCurrentInstance();
		Map session_map = ctx.getExternalContext().getSessionMap();
		
		GetAvailableFormsAction available_forms_action = 
			(GetAvailableFormsAction)session_map.get(AV_FORMS_ACTION);
		
		List submitted_data_names = null;
		
		if(available_forms_action != null && available_forms_action.getSelectedRow() != null) {
			
			try {
				submitted_data_names = getFormsService(ctx).listSubmittedData(available_forms_action.getSelectedRow());
			} catch (Exception e) {
				log.warning("Error getting submitted data list");
				e.printStackTrace();
			}
		}
		
//		List<Map<String, String>> fakpak = new ArrayList<Map<String, String>>();
//		
//		for (int i = 0; i < 5; i++) {
//			
//			Map<String, String> xx = new HashMap<String, String>();
//			fakpak.add(xx);
//			xx.put("id", (i+"xx"));
//			xx.put("label1", (i+" label1"));
//			xx.put("label2", (i+" label2"));
//		}
		
		return submitted_data_names == null ? new ArrayList() : submitted_data_names;
//		return submitted_data_names == null ? fakpak : submitted_data_names;
	}
	
	public List<String> getTableColumnsProperties() {
		
		if(col_props == null) {
			
			col_props = new ArrayList<String>();
			
			col_props.add(COLUMNID_LABEL1);
			col_props.add(COLUMNID_LABEL2);
		}
		return col_props;
	}
	
	private FormsService getFormsService(FacesContext context) {
		FormsService service = null;
		try {
			IWApplicationContext iwc = IWContext.getIWContext(context);
			service = (FormsService) IBOLookup.getServiceInstance(iwc, FormsService.class);
		}
		catch (IBOLookupException e) {
			log.warning("Could not find FormsService");
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
		
		Map session_map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		GetAvailableFormsAction available_forms_action = 
			(GetAvailableFormsAction)session_map.get(AV_FORMS_ACTION);
		
		if(available_forms_action == null)
			return null;
		
		System.out.println("getSelectedRow()______________");
		if(selected_forms_row != null) {
			
			System.out.println("selected_forms_row: "+selected_forms_row);
			
			String selected_row = available_forms_action.getSelectedRow();
			
			System.out.println("selected_row: "+selected_row);
			
			if(!selected_forms_row.equals(selected_row)) {
				selected_forms_row = available_forms_action.getSelectedRow();
				this.selected_row = null;
			}
				
		} else {
			selected_row = null;
			selected_forms_row = available_forms_action.getSelectedRow();
			System.out.println("else: setting selected_forms_row: "+selected_forms_row);
		}
		
		System.out.println("returning: "+selected_row);
		
		System.out.println("getSelectedRow()_______end_______");
		return selected_row;
	}
	
	public boolean isFormReaderRendered() {
		
		System.out.println("is reader rendered: ");
		Map session_map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		GetAvailableFormsAction available_forms_action = 
			(GetAvailableFormsAction)session_map.get(AV_FORMS_ACTION);
		
		System.out.println("returning: "+(available_forms_action != null && 
				available_forms_action.getSelectedRow() != null &&
				getSelectedRow() != null));
		
		return available_forms_action != null && 
			available_forms_action.getSelectedRow() != null &&
			getSelectedRow() != null;
	}
}
