package com.idega.block.formadmin.presentation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.component.html.ext.HtmlCommandButton;

import com.idega.block.formadmin.presentation.beans.PhaseManagedBean;
import com.idega.block.formadmin.presentation.components.GridHtmlDataTable;
import com.idega.block.formadmin.presentation.components.PhaseManagedGridHtmlDataTable;
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
		
		Application app = ctx.getApplication();
		
		HtmlForm form = new HtmlForm();
		form.setId("FormViewer");
		
		String button_style_class = "button"+FORM_ADMIN_STYLE_CLASS_ID;
		
		HtmlCommandButton button1 = new HtmlCommandButton();
		button1.setId("button1_id");
		button1.setRendered(true);
		button1.setStyleClass(button_style_class);
		button1.setActionListener(app.createMethodBinding("#{phaseManagedBean.button1ActionListener.processAction}", new Class[]{ActionEvent.class}));
		button1.setValueBinding("value", app.createValueBinding("#{phaseManagedBean.button1Value}"));
		
		HtmlCommandButton button2 = new HtmlCommandButton();
		button2.setId("button2_id");
		button2.setRendered(true);
		button2.setStyleClass(button_style_class);
		button2.setActionListener(app.createMethodBinding("#{phaseManagedBean.button2ActionListener.processAction}", new Class[]{ActionEvent.class}));
		button2.setValueBinding("value", app.createValueBinding("#{phaseManagedBean.button2Value}"));
		
		HtmlOutputText label = new HtmlOutputText();
		label.setRendered(true);
		label.setStyleClass("label"+FORM_ADMIN_STYLE_CLASS_ID);
		label.setValueBinding("value", app.createValueBinding("#{phaseManagedBean.labelValue}"));
		
		HtmlCommandButton button3 = new HtmlCommandButton();
		button3.setId("button3_id");
		button3.setRendered(true);
		button3.setStyleClass(button_style_class);
		button3.setActionListener(app.createMethodBinding("#{phaseManagedBean.button3ActionListener.processAction}", new Class[]{ActionEvent.class}));
		button3.setValueBinding("value", app.createValueBinding("#{phaseManagedBean.button3Value}"));
		
		PhaseManagedGridHtmlDataTable grid_table = new PhaseManagedGridHtmlDataTable();
		grid_table.setRendered(true);
		grid_table.setStyleClass("sp_table"+FORM_ADMIN_STYLE_CLASS_ID);
		grid_table.setRowChangeColor("#72C0FE");
		grid_table.setId("table_id");
		grid_table.setValueBinding("value", app.createValueBinding("#{phaseManagedBean.gridTableValuesProvider.gridTableValues}"));
		
		List<UIComponent> form_children = form.getChildren();
		form_children.add(button1);
		form_children.add(button2);
		form_children.add(label);
		form_children.add(grid_table);
		form_children.add(button3);
		
		getFacets().put("form", form);
		
		ctx.getExternalContext().getSessionMap().put(PHASE, PHASE1);
	}
	
	public static final String SELECTED_ROWID = "com.idega.block.formadmin.presentation.FormViewerBlock.SELECTED_ROWID";
	public static final String CURRENTLY_VIEWED_FORMID = "com.idega.block.formadmin.presentation.FormViewerBlock.CURRENTLY_VIEWED_FORMID";
	private static final int GRID_TABLE = 3;
	
	public static final String PHASE = "com.idega.block.formadmin.presentation.FormViewerBlock.phase";
	public static final String PHASE1 = "com.idega.block.formadmin.presentation.FormViewerBlock.phase1";
	public static final String PHASE2 = "com.idega.block.formadmin.presentation.FormViewerBlock.phase2";
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException {
		
		PhaseManagedBean.initiateTableColumns();
		
		super.encodeBegin(ctx);
	}
	
	@Override
	public void decode(FacesContext ctx) {
		
		super.decode(ctx);
		
		Map session_map = ctx.getExternalContext().getSessionMap();
		List form_children = ((HtmlForm)getFacets().get("form")).getChildren();
		
		GridHtmlDataTable grid_table = (GridHtmlDataTable)form_children.get(GRID_TABLE);
		String selected_row_id = grid_table.getSelectedRowId(ctx);
		
		if(selected_row_id != null)
			session_map.put(SELECTED_ROWID, selected_row_id);
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