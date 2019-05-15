package edu.iis.mto.testreactor.exc2;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class WashingMachineTest {

    private Program program;
    private WashingMachine washingMachine;
    DirtDetector dirtDetector;
    Engine engine;
    WaterPump waterPump;

    @Before
    public void intialize() {
        this.dirtDetector = mock(DirtDetector.class);
        this.engine = mock(Engine.class);
        this.waterPump = mock(WaterPump.class);

        this.washingMachine = new WashingMachine(dirtDetector, engine, waterPump);

    }

    @Test
    public void itCompiles() {
        assertThat(true, Matchers.equalTo(true));
    }

}
