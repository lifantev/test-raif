package com.raif.storage.sock.service;

import com.raif.storage.exception.SockValidationException;
import com.raif.storage.sock.model.Sock;
import com.raif.storage.sock.model.SockDto;
import com.raif.storage.sock.model.SockMapper;
import com.raif.storage.sock.model.SockRepository;
import com.raif.storage.sock.validator.SockValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SockServiceImplTest {

    @Mock
    private SockRepository sockRepository;
    @Mock
    private SockValidator sockValidator;
    @Mock
    private SockMapper sockMapper;
    @Captor
    private ArgumentCaptor<Sock> argumentCaptor;

    private SockService underTest;

    @BeforeEach
    void setUp() {
        underTest = new SockServiceImpl(sockRepository, sockValidator, sockMapper);
    }

    @Test
    void createSockIncomeForStoredOne() {
        // given
        var color = "red";
        var cottonPart = 50;
        var sockDto = new SockDto(color, cottonPart, 10);
        var storedOpt = Optional.of(new Sock(1, color, cottonPart, 20));
        var toStore = new Sock(1, color, cottonPart, 30);

        given(sockRepository.findByColorAndCottonPart(color, cottonPart)).willReturn(storedOpt);

        // when
        underTest.createSockIncome(sockDto);

        // then
        then(sockRepository).should().save(argumentCaptor.capture());
        assertEquals(toStore, argumentCaptor.getValue());
    }

    @Test
    void createSockIncomeForNewOne() {
        // given
        var color = "red";
        var cottonPart = 50;
        var sockDto = new SockDto(color, cottonPart, 10);
        var toStore = new Sock(1, color, cottonPart, 10);

        given(sockRepository.findByColorAndCottonPart(color, cottonPart)).willReturn(Optional.empty());
        given(sockMapper.toEntity(sockDto)).willReturn(toStore);

        // when
        underTest.createSockIncome(sockDto);

        // then
        then(sockRepository).should().save(argumentCaptor.capture());
        assertEquals(toStore, argumentCaptor.getValue());
    }

    @Test
    void createSockOutcomeFailWithInvalidOne() {
        // given
        var color = "red";
        var cottonPart = 50;
        var sockDto = new SockDto(color, cottonPart, 10);
        var storedOpt = Optional.of(new Sock(1, color, cottonPart, 5));

        given(sockRepository.findByColorAndCottonPart(color, cottonPart)).willReturn(storedOpt);
        willThrow(SockValidationException.class).given(sockValidator).validateSockForSaving(any());

        // when
        // then
        assertThrows(SockValidationException.class, () -> underTest.createSockOutcome(sockDto));
        verify(sockRepository, never()).save(any());
    }

    @Test
    void countSocks() {
    }
}