package com.raif.storage.sock.model;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SockRepositoryTest {

    @Autowired
    private SockRepository underTest;

    private static final Sock[] SOCKS_TO_SAVE = {
            new Sock(1, "red", 20, 5),
            new Sock(2, "red", 50, 2),
            new Sock(3, "red", 90, 120),
            new Sock(5, "yellow", 50, 1),
    };

    @BeforeAll
    public void setUp() {
        underTest.saveAll(Arrays.asList(SOCKS_TO_SAVE));
    }

    @AfterAll
    public void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByColorAndCottonPart() {
        // given
        String color = "red";
        int cottonPart = 50;

        // when
        var stored = underTest.findByColorAndCottonPart(color, cottonPart);

        // then
        assertTrue(stored.isPresent());
        assertEquals(color, stored.get().getColor());
        assertEquals(cottonPart, stored.get().getCottonPart());
    }

    @Test
    void findByColorAndSuchCottonPartTestFail() {
        // given
        String color = "yellow";
        int cottonPart = 80;

        // when
        var stored = underTest.findByColorAndCottonPart(color, cottonPart);

        // then
        assertFalse(stored.isPresent());
    }

    @Test
    void findBySuchColorAndCottonPartTestFail() {
        // given
        String color = "blue";
        int cottonPart = 20;

        // when
        var stored = underTest.findByColorAndCottonPart(color, cottonPart);

        // then
        assertFalse(stored.isPresent());
    }

    @Test
    void findAllByColorAndCottonPartGreaterThan() {
        // given ... SOCKS_TO_SAVE
        String color = "red";
        int minCottonPart = 30;
        var sock1 = SOCKS_TO_SAVE[1];
        var sock2 = SOCKS_TO_SAVE[2];

        // when
        var storedSocks = underTest.findAllByColorAndCottonPartGreaterThan(color, minCottonPart);

        // then
        assertEquals(2, storedSocks.size());
        for (var sock : storedSocks) {
            if (sock1.getQuantity() == sock.getQuantity()) {
                assertEquals(sock1.getColor(), sock.getColor());
                assertEquals(sock1.getCottonPart(), sock.getCottonPart());
            } else {
                assertEquals(sock2.getColor(), sock.getColor());
                assertEquals(sock2.getCottonPart(), sock.getCottonPart());
            }
        }
    }
}