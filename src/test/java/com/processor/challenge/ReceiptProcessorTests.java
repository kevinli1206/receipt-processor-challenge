package com.processor.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.processor.challenge.receipt.Item;
import com.processor.challenge.receipt.Receipt;
import com.processor.challenge.receipt.ReceiptProcessor;

@SpringBootTest(classes = ReceiptProcessor.class)
public class ReceiptProcessorTests {
    
    @Autowired
    private ReceiptProcessor processor;

    @Test
    void testCalculateAlphaNumeric() {
        String retailer1 = "Target";
        int points1 = processor.countAlphanumeric(retailer1);
        String retailer2 = "Target    ";
        int points2 = processor.countAlphanumeric(retailer2);
        String retailer3 = "!*^920AxDLf  }";
        int points3 = processor.countAlphanumeric(retailer3);
        String retailer4= "  ";
        int points4 = processor.countAlphanumeric(retailer4);
        assertEquals(6, points1);
        assertEquals(6, points2);
        assertEquals(8, points3);
        assertEquals(0, points4);
    }

    @Test
    void testCalculateIsRoundDollar() {
        String total1 = "35.25";
        String total2 = "0.00";
        String total3 = "20.00";
        String total4 = "36.99";
        assertFalse(processor.isRoundDollar(Double.parseDouble(total1)));
        assertTrue(processor.isRoundDollar(Double.parseDouble(total2)));
        assertTrue(processor.isRoundDollar(Double.parseDouble(total3)));
        assertFalse(processor.isRoundDollar(Double.parseDouble(total4)));
    }

    @Test
    void testCalculateIsPointTwoFiveMultiple() {
        String total1 = "35.25";
        String total2 = "0.00";
        String total3 = "20.50";
        String total4 = "36.75";
        String total5 = "0.24";
        String total6 = "1.52";
        String total7 = "36.76";
        assertTrue(processor.isPointTwoFiveMultiple(Double.parseDouble(total1)));
        assertTrue(processor.isPointTwoFiveMultiple(Double.parseDouble(total2)));
        assertTrue(processor.isPointTwoFiveMultiple(Double.parseDouble(total3)));
        assertTrue(processor.isPointTwoFiveMultiple(Double.parseDouble(total4)));
        assertFalse(processor.isPointTwoFiveMultiple(Double.parseDouble(total5)));
        assertFalse(processor.isPointTwoFiveMultiple(Double.parseDouble(total6)));
        assertFalse(processor.isPointTwoFiveMultiple(Double.parseDouble(total7)));
    }

    @Test
    void testCalculatePointsPerTwoItems() {
        Item i1 = new Item(null, null);
        Item i2 = new Item("a1", "3.25");
        Item i3 = new Item("a2", "0.00");
        Item i4 = new Item("a3", "2.25");
        Item i5 = new Item("a4", "3.24");
        List<Item> l1 = new ArrayList<>();
        List<Item> l2 = new ArrayList<>();
        l2.add(i1);
        List<Item> l3 = new ArrayList<>();
        l3.add(i1);
        l3.add(i2);
        List<Item> l4 = new ArrayList<>();
        l4.add(i1);
        l4.add(i2);
        l4.add(i3);
        List<Item> l5 = new ArrayList<>();
        l5.add(i1);
        l5.add(i2);
        l5.add(i3);
        l5.add(i4);
        List<Item> l6 = new ArrayList<>();
        l6.add(i1);
        l6.add(i2);
        l6.add(i3);
        l6.add(i4);
        l6.add(i5);
        assertEquals(0, processor.pointsPerTwoItems(l1));
        assertEquals(0, processor.pointsPerTwoItems(l2));
        assertEquals(5, processor.pointsPerTwoItems(l3));
        assertEquals(5, processor.pointsPerTwoItems(l4));
        assertEquals(10, processor.pointsPerTwoItems(l5));
        assertEquals(10, processor.pointsPerTwoItems(l6));
    }

    @Test
    void testCalculateTrimItemsDescriptionPoints() {
        Item i1 = new Item("Mountain Dew 12PK", "6.49");
        Item i2 = new Item("Emils Cheese Pizza", "12.25");
        Item i3 = new Item("Knorr Creamy Chicken", "1.26");
        Item i4 = new Item("Doritos Nacho Cheese", "3.35");
        Item i5 = new Item("   Klarbrunn 12-PK 12 FL OZ  ", "12.00");
        Item i6 = new Item("Doritos Nacho Cheeses", "3.35");
        List<Item> l1 = new ArrayList<>();
        l1.add(i1);
        l1.add(i2);
        l1.add(i3);
        l1.add(i4);
        l1.add(i4);
        l1.add(i5);
        List<Item> l2 = new ArrayList<>();
        l2.add(i1);
        l2.add(i2);
        l2.add(i3);
        l2.add(i4);
        l2.add(i4);
        l2.add(i5);
        l2.add(i6);
        assertEquals(6, processor.trimItemsDescriptionPoints(l1));
        assertEquals(7, processor.trimItemsDescriptionPoints(l2));
    }

    @Test
    void testCalculatePointsForLLM() {
        String l1 = "true";
        String l2 = null;
        String l3 = "false";
        String total1 = "10.00";
        String total2 = "9.99";
        String total3 = "10.01";
        assertEquals(5, processor.pointsForLLM(l1, Double.parseDouble(total3)));
        assertEquals(0, processor.pointsForLLM(l1, Double.parseDouble(total1)));
        assertEquals(0, processor.pointsForLLM(l1, Double.parseDouble(total2)));
        assertEquals(0, processor.pointsForLLM(l2, Double.parseDouble(total3)));
        assertEquals(0, processor.pointsForLLM(l2, Double.parseDouble(total1)));
        assertEquals(0, processor.pointsForLLM(l2, Double.parseDouble(total2)));
        assertEquals(0, processor.pointsForLLM(l3, Double.parseDouble(total3)));
        assertEquals(0, processor.pointsForLLM(l3, Double.parseDouble(total1)));
        assertEquals(0, processor.pointsForLLM(l3, Double.parseDouble(total2)));
    }

    @Test
    void testCalculatePointsForPurchaseDate() {
        String d1 = "2022-01-01";
        String d2 = "2000-12-30";
        String d3 = "2005-07-20";
        String d4 = "2010-08-31";
        assertEquals(6, processor.pointsForPurchaseDate(d1));
        assertEquals(0, processor.pointsForPurchaseDate(d2));
        assertEquals(0, processor.pointsForPurchaseDate(d3));
        assertEquals(6, processor.pointsForPurchaseDate(d4));
    }

    @Test
    void testCalculatePointsForPurchaseTime() {
        String t1 = "13:01";
        String t2 = "14:00";
        String t3 = "14:01";
        String t4 = "15:00";
        String t5 = "15:01";
        String t6 = "15:59";
        String t7 = "16:00";
        String t8 = "16:01";
        assertEquals(0, processor.pointsForPurchaseTime(t1));
        assertEquals(0, processor.pointsForPurchaseTime(t2));
        assertEquals(10, processor.pointsForPurchaseTime(t3));
        assertEquals(10, processor.pointsForPurchaseTime(t4));
        assertEquals(10, processor.pointsForPurchaseTime(t5));
        assertEquals(10, processor.pointsForPurchaseTime(t6));
        assertEquals(0, processor.pointsForPurchaseTime(t7));
        assertEquals(0, processor.pointsForPurchaseTime(t8));
    }

    @Test
    void testCalculatePoints1() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Mountain Dew 12PK", "6.49"));
        items.add(new Item("Emils Cheese Pizza", "12.25"));
        items.add(new Item("Knorr Creamy Chicken", "1.26"));
        items.add(new Item("Doritos Nacho Cheese", "3.35"));
        items.add(new Item("   Klarbrunn 12-PK 12 FL OZ  ", "12.00"));
        Receipt receipt = new Receipt("Target", "2022-01-01", "13:01", items, "35.35");
        int points = processor.calculatePoints(receipt);
        assertEquals(28, points);
    }

    @Test
    void testCalculatePoints2() {
        List<Item> items = new ArrayList<>();
        Item i1 = new Item("Gatorade", "2.25");
        items.add(i1);
        items.add(i1);
        items.add(i1);
        items.add(i1);
        Receipt receipt = new Receipt("M&M Corner Market", "2022-03-20", "14:33", items, "9.00");
        int points = processor.calculatePoints(receipt);
        assertEquals(109, points);
    }

    @Test
    void testCalculatePoints3() {
        List<Item> items = new ArrayList<>();
        Item i1 = new Item(" Dozen Whole Eggs ", "10.00");
        Item i2 = new Item("Amazon Gift Card", "50.00");
        items.add(i1);
        items.add(i1);
        items.add(i2);
        Receipt receipt = new Receipt("Woodmans", "2025-03-30", "15:59", items, "true", "70.00");
        int points = processor.calculatePoints(receipt);
        assertEquals(103, points);
    }
}
