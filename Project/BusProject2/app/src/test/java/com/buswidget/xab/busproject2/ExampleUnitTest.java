package com.buswidget.xab.busproject2;

import org.junit.Test;
import com.buswidget.xab.busproject2.TimeFunction;

import junit.framework.TestFailure;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    TimeFunction timeFunction = new TimeFunction();

    @Test
    public void testConvertTimeToInt() throws Exception {
        String[] testingCorrectValues = {"00:00", "05:45", "10:00", "15:30", "17:59", "23:59"};
        Integer[] outputCorrectValues = {0, 345, 600, 930, 1079, 1439};
        for(int i = 0; i < testingCorrectValues.length; i++){
            assertEquals(outputCorrectValues[i], timeFunction.ConvertTimeToInt(
                    testingCorrectValues[i]
            ));
        }

        String[] testingUncorrectValues = {"0000", "05", "25:40", "-10:00", "07:65", "10:-15"};
        Integer[] outputUncorrectValues = {-1, -1, -2, -2, -2, -2};
        for(int i = 0; i < testingUncorrectValues.length; i++){
            assertEquals(outputUncorrectValues[i], timeFunction.ConvertTimeToInt(
                    testingUncorrectValues[i]
            ));
        }
    }

    @Test
    public void testConvertIntToTime() throws Exception {
        Integer[] testingCorrectValues = {0, 345, 600, 930, 1079, 1439, -15, -1439};
        String[] outputCorrectValues = {
                "0m", "5h 45m", "10h 0m", "15h 30m", "17h 59m", "23h 59m", "-15m", "-23h 59m"
        };
        for(int i = 0; i < testingCorrectValues.length; i++){
            assertEquals(outputCorrectValues[i], timeFunction.ConvertIntToTime(
                    testingCorrectValues[i]
            ));
        }

        Integer[] testingUncorrectValues = {-2000, -1440, 1440, 3000};
        String[] outputUncorrectValues = {"uncorrect", "uncorrect", "uncorrect", "uncorrect"};
        for(int i = 0; i < testingUncorrectValues.length; i++){
            assertEquals(outputUncorrectValues[i], timeFunction.ConvertIntToTime(
                    testingUncorrectValues[i]
            ));
        }
    }

    @Test
    public void testCalcDiffOfTime() throws Exception {
        Integer[] testingCorrectFirstParametr = {0, 100, 1400};
        Integer[] testingCorrectSecondParametr = {100, 0, 1400};
        Integer[] outputCorrectValues = {1340, 100, 0};
        for(int i = 0; i < testingCorrectFirstParametr.length; i++){
            assertEquals(outputCorrectValues[i], timeFunction.CalcDiffOfTime(
                    testingCorrectFirstParametr[i], testingCorrectSecondParametr[i]
            ));
        }

        Integer[] testingUncorrectFirstParametr = {-1440, 1440, 0, 0, -2000, 2000, 0,0};
        Integer[] testingUncorrectSecondParametr = {0, 0, -1440, 1440, 0, 0, -2000, 2000};
        Integer[] outputUncorrectValues = {-1, -1, -2, -2, -1, -1, -2, -2};
        for(int i = 0; i < testingUncorrectFirstParametr.length; i++){
            assertEquals(outputUncorrectValues[i], timeFunction.CalcDiffOfTime(
                    testingUncorrectFirstParametr[i], testingUncorrectSecondParametr[i]
            ));
        }
    }

    @Test
    public void testFoundBusTime() throws Exception {
        ArrayList<Integer> testingCorrectFirstParametr = new ArrayList<>();
        //{"0m", "5h 45m", "10h 0m", "15h 30m", "17h 59m", "23h 59m"}
        testingCorrectFirstParametr.add(0);
        testingCorrectFirstParametr.add(345);
        testingCorrectFirstParametr.add(600);
        testingCorrectFirstParametr.add(930);
        testingCorrectFirstParametr.add(1079);
        testingCorrectFirstParametr.add(1439);
        Integer[] testingCorrectSecondParametr = {40, 700, 1200};
        Integer[] testingCorrectThirdParametr = {
                timeFunction.LAST, timeFunction.NEXT, timeFunction.AFTER_NEXT
        };
        Integer[] outputCorrectValues = {0, 1, 2, 2, 3, 4, 4, 5, 0};
        for(int i = 0; i < testingCorrectSecondParametr.length; i++){
            for(int j = 0; j < testingCorrectThirdParametr.length; j++) {
                assertEquals(outputCorrectValues[(i * 3) + j], timeFunction.FoundBusTime(
                        testingCorrectFirstParametr,
                        testingCorrectSecondParametr[i],
                        testingCorrectThirdParametr[j]
                ));
            }
        }

        ArrayList<Integer> testingCorrectFirstParametr2 = new ArrayList<>();
        //{"15h 30m", "17h 59m", "23h 59m"}
        testingCorrectFirstParametr2.add(930);
        testingCorrectFirstParametr2.add(1079);
        testingCorrectFirstParametr2.add(1439);
        Integer[] testingCorrectSecondParametr2 = {40, 350, 700};
        Integer[] outputCorrectValues2 = {2, 0, 1, 2, 0, 1, 2, 0, 1};
        for(int i = 0; i < testingCorrectSecondParametr.length; i++){
            for(int j = 0; j < testingCorrectThirdParametr.length; j++) {
                assertEquals(outputCorrectValues2[(i * 3) + j], timeFunction.FoundBusTime(
                        testingCorrectFirstParametr2,
                        testingCorrectSecondParametr2[i],
                        testingCorrectThirdParametr[j]
                ));
            }
        }

        Integer[] testingUncorrectSecondParametr = {-1, 1440, 2000};
        Integer[] outputUncorrectValues1 = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
        for(int i = 0; i < testingUncorrectSecondParametr.length; i++){
            for(int j = 0; j < testingCorrectThirdParametr.length; j++) {
                assertEquals(outputUncorrectValues1[(i * 3) + j], timeFunction.FoundBusTime(
                        testingCorrectFirstParametr,
                        testingUncorrectSecondParametr[i],
                        testingCorrectThirdParametr[j]
                ));
            }
        }

        Integer[] testingUncorrectThirdParametr = {-1, 4};
        Integer[] outputUncorrectValues2 = {-2, -2, -2, -2, -2, -2};
        for(int i = 0; i < testingCorrectSecondParametr.length; i++){
            for(int j = 0; j < testingUncorrectThirdParametr.length; j++) {
                assertEquals(outputUncorrectValues2[(i * 2) + j], timeFunction.FoundBusTime(
                        testingCorrectFirstParametr,
                        testingCorrectSecondParametr[i],
                        testingUncorrectThirdParametr[j]
                ));
            }
        }

        ArrayList<Integer> testingUncorrectFirstParametr1 = new ArrayList<>();
        Integer[] outputUncorrectValues3 = {-3, -3, -3, -3, -3, -3, -3, -3, -3};
        for(int i = 0; i < testingCorrectSecondParametr.length; i++) {
            for (int j = 0; j < testingCorrectThirdParametr.length; j++) {
                assertEquals(outputUncorrectValues3[(i * 3) + j], timeFunction.FoundBusTime(
                        testingUncorrectFirstParametr1,
                        testingCorrectSecondParametr[i],
                        testingCorrectThirdParametr[j]
                ));
            }
        }
    }

}