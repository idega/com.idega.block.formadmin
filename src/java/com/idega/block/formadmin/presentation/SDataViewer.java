package com.idega.block.formadmin.presentation;

import java.io.IOException;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import com.idega.block.formadmin.presentation.components.GridHtmlDataTable;
import com.idega.block.formreader.presentation.SDataPreview;
import com.idega.webface.WFBlock;
import com.idega.webface.WFContainer;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version 1.0
 */
public class SDataViewer extends WFBlock {
	
	private static final String FORM_CONTAINER = "FORM_CONTAINER";
	private static final String FORM_RENDERER_CONTAINER = "FORM_RENDERER_CONTAINER";

	public SDataViewer() {
		super("SDataViewer");
	}
	
	private static final String FORM_ADMIN_STYLE_CLASS_ID = "_formadmin";
	private static final String form_name = "FormViewer";
	private static final String table_style_class = "sp_table"+FORM_ADMIN_STYLE_CLASS_ID;
	private static final String table2_style_class = "sp_table2"+FORM_ADMIN_STYLE_CLASS_ID;
	private static final String available_forms_table_id = "available_forms_table_id";
	private static final String submitted_data_table_id = "submitted_data_table_id";
	private static final String value_att = "value";
	private static final String rendered_att = "rendered";

	@Override
	public void initializeComponent(FacesContext ctx) {
		
		setMainAreaStyleClass(null);
		setStyleClass(getStyleClass()+(" form_viewer_block"+FORM_ADMIN_STYLE_CLASS_ID));
		
		Application app = ctx.getApplication();
		
		HtmlForm form = new HtmlForm();
		form.setId(form_name);
		
		GridHtmlDataTable available_forms_grid_table = new GridHtmlDataTable();
		available_forms_grid_table.setRendered(true);
		available_forms_grid_table.setStyleClass(table_style_class);
		available_forms_grid_table.setRowChangeColor("#F3F7F6");
		available_forms_grid_table.setRowSelectedColor("#F3F7F6");
		available_forms_grid_table.setId(available_forms_table_id);
		available_forms_grid_table.setValueBinding(value_att, app.createValueBinding("#{availableFormsAction.availableForms}"));
		available_forms_grid_table.setValueBinding(GridHtmlDataTable.COLS_PROPS_NAMES, app.createValueBinding("#{availableFormsAction.tableColumnsProperties}"));
		available_forms_grid_table.setValueBinding(GridHtmlDataTable.SELECTED_ROW, app.createValueBinding("#{availableFormsAction.selectedRowProvider}"));
		HtmlOutputText header = new HtmlOutputText();
		header.setValue("All forms");
		available_forms_grid_table.setHeader(header);
		
		GridHtmlDataTable submitted_data_grid_table = new GridHtmlDataTable();
		submitted_data_grid_table.setRendered(true);
		submitted_data_grid_table.setStyleClass(table2_style_class);
		submitted_data_grid_table.setRowChangeColor("#F3F7F6");
		submitted_data_grid_table.setRowSelectedColor("#F3F7F6");
		submitted_data_grid_table.setId(submitted_data_table_id);
		submitted_data_grid_table.setValueBinding(value_att, app.createValueBinding("#{allSubmittedDataAction.allSubmittedData}"));
		submitted_data_grid_table.setValueBinding(GridHtmlDataTable.COLS_PROPS_NAMES, app.createValueBinding("#{allSubmittedDataAction.tableColumnsProperties}"));
		submitted_data_grid_table.setValueBinding(GridHtmlDataTable.SELECTED_ROW, app.createValueBinding("#{allSubmittedDataAction.selectedRowProvider}"));
		header = new HtmlOutputText();
		header.setValue("Selected form submitted data list");
		submitted_data_grid_table.setHeader(header);
		
		WFContainer table1_container = new WFContainer();
		table1_container.getChildren().add(available_forms_grid_table);
		table1_container.setStyleClass("table1_container"+FORM_ADMIN_STYLE_CLASS_ID);
		
		WFContainer table2_container = new WFContainer();
		table2_container.getChildren().add(submitted_data_grid_table);
		table2_container.setStyleClass("table2_container"+FORM_ADMIN_STYLE_CLASS_ID);
		
		WFContainer tables_container = new WFContainer();
		tables_container.setStyleClass("tables_container"+FORM_ADMIN_STYLE_CLASS_ID);
		tables_container.getChildren().add(table1_container);
		tables_container.getChildren().add(table2_container);
		
		SDataPreview form_reader = new SDataPreview();
		form_reader.setValueBinding(rendered_att, app.createValueBinding("#{allSubmittedDataAction.formReaderRendered}"));
		form_reader.setValueBinding(SDataPreview.form_identifier, app.createValueBinding("#{availableFormsAction.selectedRow}"));
		form_reader.setValueBinding(SDataPreview.submitted_data_identifier, app.createValueBinding("#{allSubmittedDataAction.selectedRow}"));
		
		List<UIComponent> form_children = form.getChildren();
		form_children.add(tables_container);
		
		WFContainer form_container = new WFContainer();
		form_container.getChildren().add(form);
		
		WFContainer form_renderer_container = new WFContainer();
		form_renderer_container.getChildren().add(form_reader);
		
		getFacets().put(FORM_CONTAINER, form_container);
		getFacets().put(FORM_RENDERER_CONTAINER, form_renderer_container);
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