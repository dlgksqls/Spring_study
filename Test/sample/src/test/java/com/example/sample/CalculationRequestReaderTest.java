package com.example.sample;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class CalculationRequestReaderTest {

    @Test
    public void System_in으로_데이터를_읽어들일_수_있다(){
        // given
        CalculationRequestReader calculationRequestReader = new CalculationRequestReader();
        System.setIn(new ByteArrayInputStream("2 + 3".getBytes()));


        // when
        CalculationRequest result = calculationRequestReader.read();

        // then
        assertEquals(2, result.getNum1());
        assertEquals("+", result.getOperator());
        assertEquals(3, result.getNum2());
    }

}