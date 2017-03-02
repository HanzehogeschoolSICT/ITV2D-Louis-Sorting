package displays;

import data.DataManager;
import data.Settings;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import models.DataSetModel;

public class TopControlsDisplay {
    @FXML
    private Spinner<Integer> dataSetSpinner;

    /**
     * Initialize the top controls display.
     */
    @FXML
    public void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory spinnerValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(Settings.DATA_SET_MINIMUM_SIZE,
                        Settings.DATA_SET_MAXIMUM_SIZE, Settings.DATA_SET_SIZE, Settings.DATA_SET_STEP_SIZE);

        dataSetSpinner.setValueFactory(spinnerValueFactory);
    }

    /**
     * Handle a new data set button action.
     *
     * @param actionEvent Event for the action.
     */
    @FXML
    private void onNewDataSetButtonAction(ActionEvent actionEvent) {
        int items = dataSetSpinner.getValue();

        DataSetModel dataSet = new DataSetModel(items);
        DataManager.setDataSet(dataSet);
    }
}
