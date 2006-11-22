package com.idega.block.formadmin.presentation.beans;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;

import com.idega.block.formadmin.presentation.FormViewerBlock;
import com.idega.block.formadmin.presentation.actions.GetAvailableFormsAction;
import com.idega.block.formadmin.presentation.actions.GetSubmittedDataListAction;
import com.idega.block.formadmin.presentation.actions.IPhaseValueProvider;
import com.idega.block.formadmin.presentation.actions.ViewAllSubmittedDataAction;
import com.idega.block.formadmin.presentation.actions.ViewAvailableFormsAction;
import com.idega.block.formadmin.presentation.actions.ViewSubmittedDataListAction;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class PhaseManagedBean {
	
	private ActionListener view_available_forms_action;
	private ActionListener get_available_forms_action;
	private ActionListener get_submitted_data_list_action;
	private ActionListener view_submitted_data_list_action;
	private ActionListener view_all_submitted_data_action;
	
//	private Map session_map;
	
	private static final String PHASE = FormViewerBlock.PHASE;
	private static final String PHASE1 = FormViewerBlock.PHASE1;
	private static final String PHASE2 = FormViewerBlock.PHASE2;
	
	protected Map getSessionMap() {
		
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}
	
	public ActionListener getButton1ActionListener() {
		
		if(view_available_forms_action == null)
			view_available_forms_action = new ViewAvailableFormsAction();
				
		return view_available_forms_action;
	}
	
	public String getButton1Value() {
		
		return ((IPhaseValueProvider)getButton1ActionListener()).getButtonValue();
	}
	
	public String getButton2Value() {
		
		return ((IPhaseValueProvider)getButton2ActionListener()).getButtonValue();
	}

	public String getButton3Value() {
		
		return ((IPhaseValueProvider)getButton3ActionListener()).getButtonValue();
	}
	
	public ActionListener getButton2ActionListener() {
		
		Map session_map = getSessionMap();
		String phase = (String)session_map.get(PHASE);
		
		if(phase == null || phase.equals(PHASE1)) {
			
			if(get_available_forms_action == null)
				get_available_forms_action = new GetAvailableFormsAction();
				
			return get_available_forms_action;
			
		} else if(phase.equals(PHASE2)) {
			
			if(get_submitted_data_list_action == null)
				get_submitted_data_list_action = new GetSubmittedDataListAction();
				
			return get_submitted_data_list_action;
		}
		
		return null;
	}
	
	public ActionListener getButton3ActionListener() {
		
		Map session_map = getSessionMap();
		String phase = (String)session_map.get(PHASE);
		
		if(phase == null || phase.equals(PHASE1)) {
			
			if(view_submitted_data_list_action == null)
				view_submitted_data_list_action = new ViewSubmittedDataListAction();
				
			return view_submitted_data_list_action;
			
		} else if(phase.equals(PHASE2)) {
			
			if(view_all_submitted_data_action == null)
				view_all_submitted_data_action = new ViewAllSubmittedDataAction();
				
			return view_all_submitted_data_action;
		}
		
		return null;
	}
	
	public IPhaseValueProvider getGridTableValuesProvider() {
		
		Map session_map = getSessionMap();
		String phase = (String)session_map.get(PHASE);
		
		if(phase == null || phase.equals(PHASE1)) {
			
			if(get_available_forms_action == null)
				get_available_forms_action = new GetAvailableFormsAction();
			
			return (IPhaseValueProvider)get_available_forms_action;
			
		} else if(phase.equals(PHASE2)) {
			
			if(view_all_submitted_data_action == null)
				view_all_submitted_data_action = new ViewAllSubmittedDataAction();
			
			return (IPhaseValueProvider)view_all_submitted_data_action;
		}
		
		return null;
	}
	
	public String getLabelValue() {
		
		Map session_map = getSessionMap();
		String phase = (String)session_map.get(PHASE);
		
		if(phase == null || phase.equals(PHASE1)) {
			
			return "Available forms";
			
		} else if(phase.equals(PHASE2)) {
			
			return "View all submitted data";
		}
	
		return null;
	}
	
	public static void initiateTableColumns() {
		
		Map session_map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		String phase = (String)session_map.get(PHASE);
		
		if(phase == null || phase.equals(PHASE1)) {
			
			GetAvailableFormsAction.initiateTableColumnsProperties(session_map);
			
		} else if(phase.equals(PHASE2)) {
			
			ViewAllSubmittedDataAction.initiateTableColumnsProperties(session_map);
		}
	}
	
	public boolean isFormReaderRendered() {
		
		Map session_map = getSessionMap();
		String phase = (String)session_map.get(PHASE);
		
		if(phase != null && phase.equals(PHASE2))
			
			return true;
		
		return false;
	}
	
	public String getSelectedFormIdentifier() {
		
		return (String)getSessionMap().get(FormViewerBlock.CURRENTLY_VIEWED_FORMID);
	}
	
	public String getSelectedSubmittedDataIdentifier() {
		
		return (String)getSessionMap().get(FormViewerBlock.CURRENTLY_VIEWED_SUBMITTED_DATA_IDENTIFIER);
	}
}