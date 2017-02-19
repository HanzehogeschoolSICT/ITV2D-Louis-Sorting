import controllers.DataSetController;
import data.Settings;
import displays.MainDisplay;
import models.DataSetModel;

class Bootstrap {
    /**
     * Start the application.
     *
     * @param args Any arguments.
     */
    public static void main(String[] args) {
        DataSetController dataSetController = new DataSetController();

        DataSetModel dataSet = new DataSetModel(Settings.DATA_SET_SIZE);
        dataSetController.changeDataSet(dataSet);

        new MainDisplay(dataSetController);
    }
}
