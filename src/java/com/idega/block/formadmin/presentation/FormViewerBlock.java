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
import com.idega.block.formreader.presentation.FormReaderBlock;
import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;


/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class FormViewerBlock extends WFBlock {

	public FormViewerBlock() {
		super("Form Admin");
	}
	
	private static final String FORM_ADMIN_STYLE_CLASS_ID = "_formadmin";
	private static final String form_name = "FormViewer";
	private static final String button_style_class = "button"+FORM_ADMIN_STYLE_CLASS_ID;
	private static final String label_style_class = "label"+FORM_ADMIN_STYLE_CLASS_ID;
	private static final String table_style_class = "sp_table"+FORM_ADMIN_STYLE_CLASS_ID;
	private static final String button1_id = "button1_id";
	private static final String button2_id = "button2_id";
	private static final String button3_id = "button3_id";
	private static final String table_id = "table_id";
	private static final String value_att = "value";
	private static final String rendered_att = "rendered";

	@Override
	public void initializeComponent(FacesContext ctx) {
		
		ctx.getExternalContext().getSessionMap().put(PHASE, PHASE1);
		
		Application app = ctx.getApplication();
		
		HtmlForm form = new HtmlForm();
		form.setId(form_name);
		
		HtmlCommandButton button1 = new HtmlCommandButton();
		button1.setId(button1_id);
		button1.setRendered(true);
		button1.setStyleClass(button_style_class);
		button1.setActionListener(app.createMethodBinding("#{phaseManagedBean.button1ActionListener.processAction}", new Class[]{ActionEvent.class}));
		button1.setValueBinding(value_att, app.createValueBinding("#{phaseManagedBean.button1Value}"));
		
		HtmlCommandButton button2 = new HtmlCommandButton();
		button2.setId(button2_id);
		button2.setRendered(true);
		button2.setStyleClass(button_style_class);
		button2.setActionListener(app.createMethodBinding("#{phaseManagedBean.button2ActionListener.processAction}", new Class[]{ActionEvent.class}));
		button2.setValueBinding(value_att, app.createValueBinding("#{phaseManagedBean.button2Value}"));
		
		HtmlOutputText label = new HtmlOutputText();
		label.setRendered(true);
		label.setStyleClass(label_style_class);
		label.setValueBinding(value_att, app.createValueBinding("#{phaseManagedBean.labelValue}"));
		
		HtmlCommandButton button3 = new HtmlCommandButton();
		button3.setId(button3_id);
		button3.setRendered(true);
		button3.setStyleClass(button_style_class);
		button3.setActionListener(app.createMethodBinding("#{phaseManagedBean.button3ActionListener.processAction}", new Class[]{ActionEvent.class}));
		button3.setValueBinding(value_att, app.createValueBinding("#{phaseManagedBean.button3Value}"));
		
		PhaseManagedGridHtmlDataTable grid_table = new PhaseManagedGridHtmlDataTable();
		grid_table.setRendered(true);
		grid_table.setStyleClass(table_style_class);
		grid_table.setRowChangeColor("#72C0FE");
		grid_table.setId(table_id);
		grid_table.setValueBinding(value_att, app.createValueBinding("#{phaseManagedBean.gridTableValuesProvider.gridTableValues}"));
		PhaseManagedBean.initiateTableColumns();
		
		FormReaderBlock form_reader = new FormReaderBlock();
		form_reader.setValueBinding(rendered_att, app.createValueBinding("#{phaseManagedBean.formReaderRendered}"));
		form_reader.setValueBinding(FormReaderBlock.form_identifier, app.createValueBinding("#{phaseManagedBean.selectedFormIdentifier}"));
		form_reader.setValueBinding(FormReaderBlock.submitted_data_identifier, app.createValueBinding("#{phaseManagedBean.selectedSubmittedDataIdentifier}"));
		
		List<UIComponent> form_children = form.getChildren();
		form_children.add(button1);
		form_children.add(button2);
		form_children.add(label);
		form_children.add(grid_table);
		form_children.add(button3);
		
		WFContainer form_container = new WFContainer();
		form_container.getChildren().add(form);
		
		WFContainer form_renderer_container = new WFContainer();
		form_renderer_container.getChildren().add(form_reader);
		
		getFacets().put(FORM_CONTAINER, form_container);
		getFacets().put(FORM_RENDERER_CONTAINER, form_renderer_container);
	}
	
	public static final String SELECTED_ROWID = "com.idega.block.formadmin.presentation.FormViewerBlock.SELECTED_ROWID";
	public static final String CURRENTLY_VIEWED_FORMID = "com.idega.block.formadmin.presentation.FormViewerBlock.CURRENTLY_VIEWED_FORMID";
	public static final String CURRENTLY_VIEWED_SUBMITTED_DATA_IDENTIFIER = "com.idega.block.formadmin.presentation.FormViewerBlock.CURRENTLY_VIEWED_SUBMITTED_DATA_IDENTIFIER";
	private static final int GRID_TABLE = 3;
	
	public static final String PHASE = "com.idega.block.formadmin.presentation.FormViewerBlock.phase";
	public static final String PHASE1 = "com.idega.block.formadmin.presentation.FormViewerBlock.phase1";
	public static final String PHASE2 = "com.idega.block.formadmin.presentation.FormViewerBlock.phase2";
	
	private static final String FORM_CONTAINER = "FORM_CONTAINER";
	private static final String FORM_RENDERER_CONTAINER = "FORM_RENDERER_CONTAINER";
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException {
		
		PhaseManagedBean.initiateTableColumns();
		
		super.encodeBegin(ctx);
	}
	
	@Override
	public void decode(FacesContext ctx) {
		
		super.decode(ctx);
		
		Map session_map = ctx.getExternalContext().getSessionMap();
		List form_children = ((HtmlForm)getFacet(FORM_CONTAINER).getChildren().get(0)).getChildren();
		
		GridHtmlDataTable grid_table = (GridHtmlDataTable)form_children.get(GRID_TABLE);
		String selected_row_id = grid_table.getSelectedRowId(ctx);
		
		if(selected_row_id != null)
			session_map.put(SELECTED_ROWID, selected_row_id);
	}
	
	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		
		super.encodeChildren(context);
		
		UIComponent form_container = getFacet(FORM_CONTAINER);
		
		if(form_container != null) {
			
			form_container.setRendered(true);
			renderChild(context, form_container);
		}
		
		UIComponent form_renderer_container = getFacet(FORM_RENDERER_CONTAINER);
		
		if(form_renderer_container != null) {
			
			form_renderer_container.setRendered(true);
			renderChild(context, form_renderer_container);
		}
	}
}