package com.raif.storage.sock.validator;

import com.raif.storage.exception.SockValidationException;
import com.raif.storage.sock.model.Sock;
import com.raif.storage.sock.model.SockDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SockValidatorImplTest {

    private SockValidator underTest;

    @BeforeEach
    void setUp() {
        underTest = new SockValidatorImpl();
    }

    @Test
    void validateSockInPostRequest() {
        // given
        SockDto sock = new SockDto("green", 20, 17);

        // then
        assertDoesNotThrow(() -> underTest.validateSockInPostRequest(sock));
    }

    @Test
    void validateSockInPostRequestWithInvalidObject() {
        // given
        SockDto sock = null;

        // then
        assertThrows(SockValidationException.class, () -> underTest.validateSockInPostRequest(sock));
    }

    @Test
    void validateSockInPostRequestWithInvalidColor() {
        // given
        SockDto sock = new SockDto(null, 20, 17);

        // then
        assertThrows(SockValidationException.class, () -> underTest.validateSockInPostRequest(sock));
    }

    @Test
    void validateSockInPostRequestWithInvalidLargeCottonPart() {
        // given
        SockDto sock = new SockDto("blue", 120, 17);

        // then
        assertThrows(SockValidationException.class, () -> underTest.validateSockInPostRequest(sock));
    }

    @Test
    void validateSockInPostRequestWithInvalidLowCottonPart() {
        // given
        SockDto sock = new SockDto("blue", -1, 17);

        // then
        assertThrows(SockValidationException.class, () -> underTest.validateSockInPostRequest(sock));
    }

    @Test
    void validateSockInPostRequestWithInvalidQuantity() {
        // given
        SockDto sock = new SockDto("blue", 20, -1);

        // then
        assertThrows(SockValidationException.class, () -> underTest.validateSockInPostRequest(sock));
    }

    @Test
    void validateGetRequestForSockCount() {
        // given
        String color = "red";
        String[] operations = {"moreThan", "lessThan", "equal"};
        int cottonPart = 10;

        //then
        for (String op : operations) {
            assertDoesNotThrow(() -> underTest.validateGetRequestForSockCount(color, op, cottonPart));
        }
    }

    @Test
    void validateGetRequestForSockCountWithWrongOperation() {
        // given
        String color = "red";
        String op = "notEqual";
        int cottonPart = 10;

        //then
        assertThrows(SockValidationException.class, () -> underTest.validateGetRequestForSockCount(color, op, cottonPart));
    }

    @Test
    void validateSockForSaving() {
        // given
        Sock sock = new Sock(1, "green", 20, 17);

        // then
        assertDoesNotThrow(() -> underTest.validateSockForSaving(sock));
    }

    @Test
    void validateSockForSavingWithInvalidObject() {
        // given
        SockDto sock = null;

        // then
        assertThrows(SockValidationException.class, () -> underTest.validateSockInPostRequest(sock));
    }

    @Test
    void validateSockForSavingWithInvalidColor() {
        // given
        Sock sock = new Sock(1, null, 20, 17);

        // then
        assertThrows(SockValidationException.class, () -> underTest.validateSockForSaving(sock));
    }

    @Test
    void validateSockForSavingWithInvalidLargeCottonPart() {
        // given
        Sock sock = new Sock(1, "blue", 120, 17);

        // then
        assertThrows(SockValidationException.class, () -> underTest.validateSockForSaving(sock));
    }

    @Test
    void validateSockForSavingWithInvalidLowCottonPart() {
        // given
        Sock sock = new Sock(1, "blue", -1, 17);

        // then
        assertThrows(SockValidationException.class, () -> underTest.validateSockForSaving(sock));
    }

    @Test
    void validateSockForSavingWithInvalidQuantity() {
        // given
        Sock sock = new Sock(1, "blue", 20, -1);

        // then
        assertThrows(SockValidationException.class, () -> underTest.validateSockForSaving(sock));
    }
}