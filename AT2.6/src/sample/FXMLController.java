package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.annotation.processing.FilerException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class FXMLController implements Initializable {

    @FXML
    private TableView<CSVRow> tableView;

    @FXML
    private AnchorPane root;
    private FileChooser fileChooser;
    private CSVFormat csvFormat;
    private Integer numberColumns = 0;
    private File file;
    private boolean saved = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        file = new File("");
        csvFormat = CSVFormat.DEFAULT.withIgnoreEmptyLines(false);

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setAutoHide(true);
        MenuItem insertRow = new MenuItem("Insert Row");
        insertRow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                addNewRow();
                NotSaved();
            }
        });

        contextMenu.getItems().add(insertRow);
        MenuItem removeRow = new MenuItem("Remove Row");
        removeRow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                deleteRow();
                NotSaved();
            }
        });
        contextMenu.getItems().add(removeRow);

        contextMenu.getItems().add(new SeparatorMenuItem());

        MenuItem insertColumn = new MenuItem("Insert column");
        insertColumn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addNewColumn();
                NotSaved();
            }
        });
        contextMenu.getItems().add(insertColumn);

        MenuItem removeColumn = new MenuItem("Remove column");
        removeColumn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteColumn();
                NotSaved();
            }
        });
        contextMenu.getItems().add(removeColumn);

        tableView.setContextMenu(contextMenu);
    }

    @FXML
    private void onSaveActionEvent(ActionEvent event) {
        try (PrintWriter pw = new PrintWriter(file); CSVPrinter print = csvFormat.print(pw))
        {
            for (CSVRow row : tableView.getItems())
            {
                if (row.isEmpty())
                {
                    print.println();
                }
                else
                    {
                    for (SimpleStringProperty column : row.getColumns())
                    {
                        print.print(column.getValue());
                    }
                    print.println();
                }
            }
            print.flush();
            Saved();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Alert d = new Alert(Alert.AlertType.ERROR);
            d.setHeaderText("The file could not be saved. Please try again" + (file != null ? file.getName() : "."));
            d.setContentText(ex.getMessage());
            d.setTitle("Error");
            d.initOwner(root.getScene().getWindow());
            d.show();
        }
    }

    @FXML
    private void onOpenActionEvent(ActionEvent event)
    {
        File csvFile = null;
        try
        {
            if (!saved)
            {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                a.setHeaderText("Do you want to discard changes?");
                a.initOwner(root.getScene().getWindow());
                Optional<ButtonType> result = a.showAndWait();
                if (result.get() != ButtonType.YES)
                {
                    return;
                }
            }
            csvFile = openFile();
            if (csvFile == null || !csvFile.exists())
            {
                throw new FileNotFoundException("The selected file does not exist!");
            }
            ObservableList<CSVRow> rows = readFile(csvFile);
            if (rows == null || rows.isEmpty())
            {
                throw new FilerException("The selected file is empty!");
            }
            updateTable(rows);
            this.file = csvFile;
            Saved();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Alert d = new Alert(Alert.AlertType.ERROR);
            d.setHeaderText("The file could not be opened " + (csvFile != null ? csvFile.getName() : "."));
            d.setContentText(ex.getMessage());
            d.setTitle("Error");
            d.initOwner(root.getScene().getWindow());
            d.show();
        }
    }

    private void addNewRow()
    {
        Integer current = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().add(current, new CSVRow());
        tableView.getSelectionModel().select(current);
    }

    private void deleteRow() {
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
    }

    private void addNewColumn()
    {
        List<TablePosition> cells = tableView.getSelectionModel().getSelectedCells();
        int columnIndex = cells.get(0).getColumn();
        for (CSVRow row : tableView.getItems())
        {
            row.addColumn(columnIndex);
        }
        numberColumns++;
        tableView.getColumns().add(createColumn(numberColumns - 1));
        tableView.refresh();
    }

    private void deleteColumn()
    {
        List<TablePosition> cells = tableView.getSelectionModel().getSelectedCells();
        int columnIndex = cells.get(0).getColumn();
        for (CSVRow row : tableView.getItems()) {
            row.removeColumn(columnIndex);
        }
        numberColumns--;
        tableView.getColumns().remove(tableView.getColumns().size() - 1);
        tableView.refresh();
    }

    private File openFile()
    {
        if (fileChooser == null) {
            fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
            );
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV", "*.csv")
            );
        }
        return fileChooser.showOpenDialog(root.getScene().getWindow());
    }

    private ObservableList<CSVRow> readFile(File csvFile) throws IOException
    {
        ObservableList<CSVRow> rows = FXCollections.observableArrayList();
        Integer maxColumns = 0;
        try (Reader in = new InputStreamReader(new FileInputStream(csvFile));)
        {
            CSVParser parse = csvFormat.parse(in);
            for (CSVRecord record : parse.getRecords())
            {
                if (maxColumns < record.size())
                {
                    maxColumns = record.size();
                }
                CSVRow row = new CSVRow();
                for (int i = 0; i < record.size(); i++)
                {
                    row.getColumns().add(new SimpleStringProperty(record.get(i)));
                }
                rows.add(row);
            }
            this.numberColumns = maxColumns;
        }
        return rows;
    }

    private void updateTable(ObservableList<CSVRow> rows)
    {
        tableView.getColumns().clear();
        for (int i = 0; i < numberColumns; i++) {
            TableColumn<CSVRow, String> col = createColumn(i);
            tableView.getColumns().add(col);
        }
        tableView.setItems(rows);
        tableView.setEditable(true);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
    }

    private void NotSaved() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setTitle(file.getName() + " (Not Saved) - " + Main.TITLE);
        saved = false;
    }

    private void Saved() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setTitle(file.getName() + " - " + Main.TITLE);
        saved = true;
    }

    @FXML
    private void onCloseActionEvent(ActionEvent event)
    {
        if (saved) {
            Close();
        } else {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
            a.setHeaderText("Exit without saving?");
            a.initOwner(root.getScene().getWindow());
            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.YES) {
                Close();
            }
        }
    }

    private void Close() {
        System.exit(0);
    }

    private TableColumn<CSVRow, String> createColumn(int index) {
        TableColumn<CSVRow, String> col = new TableColumn<>((index + 1) + "");
        col.setSortable(false);
        col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CSVRow, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CSVRow, String> param) {
                adjustColumns(param.getValue().getColumns());
                return param.getValue().getColumns().get(index);
            }
        });
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setOnEditCommit(new EventHandler<CellEditEvent<CSVRow, String>>() {
            @Override
            public void handle(CellEditEvent<CSVRow, String> event) {
                adjustColumns(event.getRowValue().getColumns());
                event.getRowValue().getColumns().get(index).set(event.getNewValue());
                NotSaved();
            }
        });
        col.setEditable(true);
        return col;
    }

    private void adjustColumns(List<SimpleStringProperty> columns) {
        int dif = numberColumns - columns.size();
        for (int i = 0; i < dif; i++) {
            columns.add(new SimpleStringProperty());
        }
    }
}