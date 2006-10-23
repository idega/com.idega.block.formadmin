package com.idega.block.formadmin.presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import org.apache.myfaces.component.html.ext.HtmlCommandButton;

import com.idega.block.formadmin.presentation.actions.CreateNewFormAction;
import com.idega.block.formadmin.presentation.actions.GetAvailableFormsAction;
import com.idega.block.formadmin.presentation.actions.GetSubmittedDataListAction;
import com.idega.block.formadmin.presentation.actions.ViewAllSubmittedDataAction;
import com.idega.block.formadmin.presentation.components.GridHtmlDataTable;
import com.idega.webface.WFBlock;


/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class FormViewerBlock extends WFBlock {
	
	public FormViewerBlock() {
		super("Form Admin");
	}
	
	private static final String FORM_ADMIN_STYLE_CLASS_ID = "_formadmin";

	@Override
	public void initializeComponent(FacesContext ctx) {
		
		HtmlForm form = new HtmlForm();
		form.setId("FormViewer");
		
		initPhase1(ctx, form);
		
		getFacets().put("form", form);
		
		ctx.getExternalContext().getSessionMap().put(PHASE, PHASE1);
	}
	
	protected void initPhase1(FacesContext ctx, HtmlForm form) {
		
		Map session_map = ctx.getExternalContext().getSessionMap();
		
		List<UIComponent> component_list = (List<UIComponent>)session_map.get(PHASE1_COMPONENTS); 
		
		if(component_list == null) {
			
			String button_style_class = "button"+FORM_ADMIN_STYLE_CLASS_ID;
			
			HtmlCommandButton button1 = new HtmlCommandButton();
			button1.setId("button1_id");
			button1.setValue("Create new form");
			button1.setRendered(true);
			button1.setStyleClass(button_style_class);
			
			button1.addActionListener(new CreateNewFormAction());
			
			HtmlCommandButton button2 = new HtmlCommandButton();
			button2.setId("button2_id");
			button2.setValue("Get available forms");
			button2.setRendered(true);
			button2.setStyleClass(button_style_class);
			
			button2.addActionListener(new GetAvailableFormsAction());
			
			HtmlOutputText label = new HtmlOutputText();
			label.setValue("Available forms");
			label.setRendered(true);
			label.setStyleClass("label"+FORM_ADMIN_STYLE_CLASS_ID);
			
			HtmlCommandButton button3 = new HtmlCommandButton();
			button3.setId("button3_id");
			button3.setValue("View submitted data list");
			button3.setRendered(true);
			button3.setStyleClass(button_style_class);
			
			GridHtmlDataTable grid_table = new GridHtmlDataTable();
			grid_table.setRendered(true);
			grid_table.setStyleClass("sp_table"+FORM_ADMIN_STYLE_CLASS_ID);
			grid_table.setRowChangeColor("#72C0FE");
			grid_table.setId("table_id");
			
			component_list = new ArrayList<UIComponent>();
			
			component_list.add(button1);
			component_list.add(button2);
			component_list.add(label);
			component_list.add(grid_table);
			component_list.add(button3);
			
			fillTableWithAvailableFormList(grid_table);
			
			session_map.put(PHASE1_COMPONENTS, component_list);
			
		} else {
			
			GridHtmlDataTable grid_table = new GridHtmlDataTable();
			grid_table.setRendered(true);
			grid_table.setStyleClass("sp_table"+FORM_ADMIN_STYLE_CLASS_ID);
			grid_table.setRowChangeColor("#72C0FE");
			grid_table.setId("table_id");
			
			component_list.set(GRID_TABLE, grid_table);
			
			fillTableWithAvailableFormList(grid_table);
		}
		
		List<UIComponent> form_children = form.getChildren();
		
		form_children.clear();
		form_children.addAll(component_list);
	}
	
	protected void initPhase2(FacesContext ctx, HtmlForm form) {
		
		Map session_map = ctx.getExternalContext().getSessionMap();
		
		List<UIComponent> component_list = (List<UIComponent>)session_map.get(PHASE2_COMPONENTS); 
		
		if(component_list == null) {
			
			String button_style_class = "button"+FORM_ADMIN_STYLE_CLASS_ID;
			
			HtmlCommandButton button1 = new HtmlCommandButton();
			button1.setId("button1_id");
			button1.setValue("View available forms");
			button1.setRendered(true);
			button1.setStyleClass(button_style_class);
			
			HtmlCommandButton button2 = new HtmlCommandButton();
			button2.setId("button2_id");
			button2.setValue("Get submitted data list");
			button2.setRendered(true);
			button2.setStyleClass(button_style_class);
			
			button2.addActionListener(new GetSubmittedDataListAction());
			
			HtmlOutputText label = new HtmlOutputText();
			label.setValue("Your form submitted data list");
			label.setRendered(true);
			label.setStyleClass("label"+FORM_ADMIN_STYLE_CLASS_ID);
			
			HtmlCommandButton button3 = new HtmlCommandButton();
			button3.setId("button3_id");
			button3.setValue("View all submitted data");
			button3.setRendered(true);
			button3.setStyleClass(button_style_class);
			
			button3.addActionListener(new ViewAllSubmittedDataAction());
			
			GridHtmlDataTable grid_table = new GridHtmlDataTable();
			grid_table.setRendered(true);
			grid_table.setStyleClass("sp_table"+FORM_ADMIN_STYLE_CLASS_ID);
			grid_table.setRowChangeColor("#72C0FE");
			grid_table.setId("table_id");
			
			component_list = new ArrayList<UIComponent>();
			
			component_list.add(button1);
			component_list.add(button2);
			component_list.add(label);
			component_list.add(grid_table);
			component_list.add(button3);
			
			fillTableWithSubmittedDataList(grid_table);
			
			session_map.put(PHASE2_COMPONENTS, component_list);
			
		} else {
			
			GridHtmlDataTable grid_table = new GridHtmlDataTable();
			grid_table.setRendered(true);
			grid_table.setStyleClass("sp_table"+FORM_ADMIN_STYLE_CLASS_ID);
			grid_table.setRowChangeColor("#72C0FE");
			grid_table.setId("table_id");
			
			component_list.set(GRID_TABLE, grid_table);
			
			fillTableWithSubmittedDataList(grid_table);
		}
		
		List<UIComponent> form_children = form.getChildren();
		
		form_children.clear();
		form_children.addAll(component_list);
	}
	
	protected void fillTableWithAvailableFormList(GridHtmlDataTable grid_table) {
		
		List<String> props = new ArrayList<String>();
		props.add("form_name");
		
		grid_table.setColumnsPropertiesNames(props);
		
		List<Map> columns_values = new ArrayList<Map>();
		
//		generate fake values array to iterate
		for (int i = 0; i < 3; i++) {
			
			Map<String, String> row_values = new HashMap<String, String>();
			row_values.put("form_name", "formaa "+i);
			row_values.put("id", String.valueOf(i));
			columns_values.add(row_values);
		}
		
		grid_table.setValue(columns_values);
	}
	
	protected void fillTableWithSubmittedDataList(GridHtmlDataTable grid_table) {
		
		List<String> props = new ArrayList<String>();
		props.add("label1");
		props.add("label2");
		
		grid_table.setColumnsPropertiesNames(props);
		
		List<Map> columns_values = new ArrayList<Map>();
		
//		generate fake values array to iterate
		for (int i = 0; i < 5; i++) {
			
			Map<String, String> row_values = new HashMap<String, String>();
			row_values.put("label1", "value"+i);
			row_values.put("label2", "zzzz"+i);
			row_values.put("id", String.valueOf(i));
			columns_values.add(row_values);
		}
		
		grid_table.setValue(columns_values);
	}
	
	public static final String SELECTED_ROWID = "com.idega.block.formadmin.presentation.FormViewerBlock.SELECTED_ROWID";
	public static final String CURRENTLY_VIEWED_FORMID = "com.idega.block.formadmin.presentation.FormViewerBlock.CURRENTLY_VIEWED_FORMID";
	private static final int BUTTON1 = 0;
	private static final int BUTTON2 = 1;
	private static final int LABEL = 2;
	private static final int GRID_TABLE = 3;
	private static final int BUTTON3 = 4;
	
	private static final String PHASE = "com.idega.block.formadmin.presentation.FormViewerBlock.phase";
	private static final String PHASE1 = "com.idega.block.formadmin.presentation.FormViewerBlock.phase1";
	private static final String PHASE2 = "com.idega.block.formadmin.presentation.FormViewerBlock.phase2";
	
	private static final String PHASE1_COMPONENTS = "com.idega.block.formadmin.presentation.FormViewerBlock.PHASE1_COMPONENTS";
	private static final String PHASE2_COMPONENTS = "com.idega.block.formadmin.presentation.FormViewerBlock.PHASE2_COMPONENTS";
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException {
		
		super.encodeBegin(ctx);
	}
	
	@Override
	public void decode(FacesContext ctx) {
		
		super.decode(ctx);
		
		Map session_map = ctx.getExternalContext().getSessionMap();
		Map param_map = ctx.getExternalContext().getRequestParameterMap();
		List form_children = ((HtmlForm)getFacets().get("form")).getChildren();
		
		String phase = (String)session_map.get(PHASE);
		
		if(phase == null || phase.equals(PHASE1)) {
			
			GridHtmlDataTable grid_table = (GridHtmlDataTable)form_children.get(GRID_TABLE);
			
			String selected_row_id = grid_table.getSelectedRowId(ctx);
			
			if(selected_row_id != null)
				session_map.put(SELECTED_ROWID, selected_row_id);
			
			if(isButtonSelected(BUTTON3, form_children, param_map, ctx)) {
				
				initPhase2(ctx, (HtmlForm)getFacets().get("form"));
				session_map.put(PHASE, PHASE2);
				session_map.remove(SELECTED_ROWID);
				
				session_map.put(CURRENTLY_VIEWED_FORMID, session_map.get(SELECTED_ROWID));
			}
			
		} else if(phase.equals(PHASE2)) {
			
			if(isButtonSelected(BUTTON1, form_children, param_map, ctx)) {
				
				initPhase1(ctx, (HtmlForm)getFacets().get("form"));
				session_map.put(PHASE, PHASE1);
				session_map.remove(SELECTED_ROWID);
				
			} else {
				GridHtmlDataTable grid_table = (GridHtmlDataTable)form_children.get(GRID_TABLE);
				
				String selected_row_id = grid_table.getSelectedRowId(ctx);
				
				if(selected_row_id != null)
					session_map.put(SELECTED_ROWID, selected_row_id);
			}
		}
	}
	
	protected boolean isButtonSelected(int button_identifier, List form_children, Map request_param_map, FacesContext ctx) {
		
		String client_id = ((UIComponent)form_children.get(button_identifier)).getClientId(ctx);
		
		if(request_param_map.get(client_id) != null)
			return true;
		
		return false;
	}
	
	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		
		super.encodeChildren(context);
		
		UIComponent form = getFacet("form");
		
		if(form != null) {
			
			form.setRendered(true);
			renderChild(context, form);
		}
	}
}