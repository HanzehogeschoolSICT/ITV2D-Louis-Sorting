package displays;

import data.Settings;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.TextAlignment;
import models.DataSetModel;
import models.DrawBarDataModel;

public class DataSetDisplay {
    @FXML
    private Canvas dataSetCanvas;

    private DataSetModel dataSet;

    private GraphicsContext graphics;

    @FXML
    public void initialize() {
        graphics = dataSetCanvas.getGraphicsContext2D();
    }

    private void updateDataSet(DataSetModel dataSet) {
        this.dataSet = dataSet;

        drawDataSet();
    }

    private void drawDataSet() {
        clear();

        double width = dataSetCanvas.getWidth();
        double height = dataSetCanvas.getHeight();

        double heightPerNumber = height / dataSet.getHighestNumber();
        double widthPerBar = width / dataSet.getHighestNumber();

        DrawBarDataModel drawBarData = new DrawBarDataModel(heightPerNumber, widthPerBar);
        for (Integer number : dataSet)
            drawBar(number, drawBarData, getBarColor(dataSet, number));
    }

    private void clear() {
        double width = dataSetCanvas.getWidth();
        double height = dataSetCanvas.getHeight();

        graphics.setFill(Settings.BACKGROUND_COLOR);
        graphics.fillRect(0, 0, width, height);
    }

    private void drawBar(int number, DrawBarDataModel drawBarData, Color color) {
        double barHeight = drawBarData.getHeightForBar(number);
        double x = drawBarData.getCurrentX();
        double y = dataSetCanvas.getHeight() - barHeight;

        Rectangle2D barRectangle = new Rectangle2D(x, y, drawBarData.getWidthPerBar(), barHeight);

        graphics.setFill(color);
        graphics.fillRect(barRectangle.getMinX(), barRectangle.getMinY(),
                barRectangle.getWidth(), barRectangle.getHeight());

        graphics.setFill(Color.WHITE);
        drawNumberInBar(number, barRectangle);
        drawBarData.increaseCurrentX();
    }

    /**
     * Get the color to use to draw the specified number.
     *
     * @param number Number to get the color for.
     * @return Color to use to draw the specified number.
     */
    private Color getBarColor(DataSetModel dataSet, int number) {
        if (dataSet.getIsSorted())
            return Settings.BAR_SORTED_COLOR;

        if (dataSet.isNumberCompared(number))
            return Settings.BAR_COMPARED_COLOR;

        return Settings.BAR_COLOR;
    }

    private void drawNumberInBar(Integer number, Rectangle2D barRectangle) {
        graphics.setFontSmoothingType(FontSmoothingType.LCD);

        graphics.setTextAlign(TextAlignment.CENTER);
        graphics.setTextBaseline(VPos.TOP);

        double centerX = barRectangle.getMinX() + barRectangle.getWidth() / 2;
        double topY = barRectangle.getMinY();

        String numberText = number.toString();
        graphics.fillText(numberText, centerX, topY);
    }
}
