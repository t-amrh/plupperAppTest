package test;

import com.haw.main.AudioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioServiceTest {

    AudioService audioService;

    @BeforeEach
    void setUp() {
        audioService = new AudioService(true, 50005);
    }

    @Test
    void testMicSwitchFalse() {
        audioService.micSwitch();
        assertFalse(audioService.getMicOpen());
    }
}