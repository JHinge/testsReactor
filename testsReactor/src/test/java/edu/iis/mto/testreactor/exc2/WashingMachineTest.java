package edu.iis.mto.testreactor.exc2;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class WashingMachineTest {

    private Program program;
    private WashingMachine washingMachine;
    private DirtDetector dirtDetector;
    private Engine engine;
    private WaterPump waterPump;
    private LaundryBatch laundryBatch;
    private ProgramConfiguration programConfiguration;

    @Before
    public void intialize() {
        this.dirtDetector = mock(DirtDetector.class);
        this.engine = mock(Engine.class);
        this.waterPump = mock(WaterPump.class);
        this.laundryBatch = LaundryBatch.builder()
                                        .withType(Material.COTTON)
                                        .withWeightKg(20)
                                        .build();
        this.programConfiguration = ProgramConfiguration.builder()
                                                        .withProgram(Program.AUTODETECT)
                                                        .withSpin(true)
                                                        .build();

        this.washingMachine = new WashingMachine(dirtDetector, engine, waterPump);

    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIdDirtDerectorIsNull() {
        new WashingMachine(null, engine, waterPump);
    }

    @Test
    public void shouldReturnResultFailureIfWashingMachineOverweighted() {
        LaundryStatus laundryStatus = washingMachine.start(laundryBatch, programConfiguration);
        assertThat(laundryStatus.getResult(), equalTo(Result.FAILURE));
    }

    @Test
    public void itCompiles() {
        assertThat(true, Matchers.equalTo(true));
    }

}
