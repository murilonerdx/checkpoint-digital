package com.fiap.DesafioDigital.util;

import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.datatable.export.DataTableExporter;
import org.primefaces.component.export.ExportConfiguration;
import org.primefaces.util.EscapeUtils;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

public class TextExporter extends DataTableExporter {

    private OutputStreamWriter osw;
    private PrintWriter writer;

    @Override
    protected void preExport(FacesContext context, ExportConfiguration exportConfiguration) throws IOException {

        osw = new OutputStreamWriter(getOutputStream(), exportConfiguration.getEncodingType());
        writer = new PrintWriter(osw);

        if (exportConfiguration.getPreProcessor() != null) {
            exportConfiguration.getPreProcessor().invoke(context.getELContext(), new Object[]{writer});
        }
    }

    @Override
    protected void doExport(FacesContext context, DataTable table, ExportConfiguration exportConfiguration, int index) throws IOException {

        writer.append("" + table.getId() + "\n");

        if (exportConfiguration.isPageOnly()) {
            exportPageOnly(context, table, writer);
        } else if (exportConfiguration.isSelectionOnly()) {
            exportSelectionOnly(context, table, writer);
        } else {
            exportAll(context, table, writer);
        }

        writer.append("" + table.getId() + "");

        table.setRowIndex(-1);
    }

    @Override
    protected void postExport(FacesContext context, ExportConfiguration exportConfiguration) throws IOException {

        if (exportConfiguration.getPostProcessor() != null) {
            exportConfiguration.getPostProcessor().invoke(context.getELContext(), new Object[]{writer});
        }

        writer.flush();
        writer.close();
        writer = null;

        osw.close();
        osw = null;
    }

    @Override
    protected void preRowExport(DataTable table, Object document) {
        ((PrintWriter) document).append("\t" + table.getVar() + "\n");
    }

    @Override
    protected void postRowExport(DataTable table, Object document) {
        ((PrintWriter) document).append("\t" + table.getVar() + "\n");
    }

    @Override
    protected void exportCells(DataTable table, Object document) {
        PrintWriter writer = (PrintWriter) document;
        for (UIColumn col : table.getColumns()) {
            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }

            if (col.isRendered() && col.isExportable()) {
                String columnTag = getColumnTag(col);
                addColumnValue(writer, col.getChildren(), columnTag, col);
            }
        }
    }

    protected String getColumnTag(UIColumn column) {
        String headerText = (column.getExportHeaderValue() != null) ? column.getExportHeaderValue() : column.getHeaderText();
        UIComponent facet = column.getFacet("header");
        String columnTag;

        if (headerText != null) {
            columnTag = headerText.toLowerCase();
        }
        else if (facet != null) {
            columnTag = exportValue(FacesContext.getCurrentInstance(), facet).toLowerCase();
        }
        else {
            throw new FacesException("No suitable xml tag found for " + column);
        }

        return EscapeUtils.forXmlTag(columnTag);
    }

    protected void addColumnValue(PrintWriter writer, List<UIComponent> components, String tag, UIColumn column) {
        FacesContext context = FacesContext.getCurrentInstance();

        writer.append("\t\t" + tag + "");

        if (column.getExportFunction() != null) {
            writer.append(EscapeUtils.forXml(exportColumnByFunction(context, column)));
        }
        else {
            for (UIComponent component : components) {
                if (component.isRendered()) {
                    String value = exportValue(context, component);
                    if (value != null) {
                        writer.append(EscapeUtils.forXml(value));
                    }
                }
            }
        }

        writer.append("" + tag + "\n");
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }

    @Override
    public String getFileExtension() {
        return ".txt";
    }

}