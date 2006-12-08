package com.idega.block.formadmin.presentation.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import com.idega.block.form.business.FormsService;
import com.idega.block.formadmin.presentation.FormViewerBlock;
import com.idega.block.formadmin.presentation.components.PhaseManagedGridHtmlDataTable;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.presentation.IWContext;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class ViewAllSubmittedDataAction implements ActionListener, IPhaseValueProvider {
	
	public void processAction(ActionEvent ae) {
		
		Map session_map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		session_map.put(FormViewerBlock.CURRENTLY_VIEWED_SUBMITTED_DATA_IDENTIFIER, session_map.get(FormViewerBlock.SELECTED_ROWID));
	}
	
	public static final String COLUMNID_LABEL1 = "label1";
	public static final String COLUMNID_LABEL2 = "label2";
	public static final String SUBMITTED_DATA = "com.idega.block.formadmin.presentation.actions.ViewAllSubmittedDataAction.SUBMITTED_DATA";
	public static final String SUBMITTED_DATA_COLUMNS_PROPERTIES = "com.idega.block.formadmin.presentation.actions.ViewAllSubmittedDataAction.SUBMITTED_DATA_COLUMNS_PROPERTIES";
	
	public List getAllSubmittedData() {		
		FacesContext ctx = FacesContext.getCurrentInstance();
		Map session_map = ctx.getExternalContext().getSessionMap();
		String current_formid = (String)session_map.get(FormViewerBlock.CURRENTLY_VIEWED_FORMID);
		
		List submitted_data_names = new ArrayList();
		
		if(current_formid != null) {
			try {
				submitted_data_names = getFormsService(ctx).listSubmittedData(current_formid);
			} catch (Exception e) {
				
			}
		}
		
		return submitted_data_names;
	}
	
	public static void initiateTableColumnsProperties(Map session_map) {
		
		List<String> col_props = new ArrayList<String>();
		col_props.add(COLUMNID_LABEL1);
		col_props.add(COLUMNID_LABEL2);
		
		session_map.put(PhaseManagedGridHtmlDataTable.COLUMN_PROPERTIES, col_props);
	}
	
	public List getGridTableValues() {
		
		return getAllSubmittedData();
	}
	
	public String getButtonValue() {
		
		return "View all submitted data";
	}
	
	private FormsService getFormsService(FacesContext context) {
		FormsService service = null;
		try {
			IWApplicationContext iwc = IWContext.getIWContext(context);
			service = (FormsService) IBOLookup.getServiceInstance(iwc, FormsService.class);
		}
		catch (IBOLookupException e) {
			//logger.error("Could not find FormsService");
		}
		return service;
	}
	
}
