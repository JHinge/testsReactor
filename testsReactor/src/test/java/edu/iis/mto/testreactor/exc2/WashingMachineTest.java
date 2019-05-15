package edu.iis.mto.testreactor.exc2;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
                                        .withWeightKg(7)
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
    public void shouldReturnResultFailureIfWashingMachineIsOverweighted() {
        this.laundryBatch = LaundryBatch.builder()
                                        .withType(Material.COTTON)
                                        .withWeightKg(20)
                                        .build();
        LaundryStatus laundryStatus = washingMachine.start(laundryBatch, programConfiguration);
        assertThat(laundryStatus.getResult(), equalTo(Result.FAILURE));
    }

    @Test
    public void shouldReturnResultSuccesIfWashingMachineIsNotOverweighted() {

        Percentage percentage = new Percentage(20);
        when(dirtDetector.detectDirtDegree(any(LaundryBatch.class))).thenReturn(percentage);
        LaundryStatus laundryStatus = washingMachine.start(laundryBatch, programConfiguration);
        assertThat(laundryStatus.getResult(), equalTo(Result.SUCCESS));
    }

    @Test
    public void shouldBeRunnedLongProgramIfPercetageofDirtWasHigherThenAverage() {
        Percentage percentage = new Percentage(50);
        when(dirtDetector.detectDirtDegree(any(LaundryBatch.class))).thenReturn(percentage);
        LaundryStatus laundryStatus = washingMachine.start(laundryBatch, programConfiguration);
        assertThat(laundryStatus.getRunnedProgram(), equalTo(Program.LONG));
    }

    @Test
    public void shoulCallSpinMethodIfMaterialIsNotDelicate() {
        Percentage percentage = new Percentage(50);
        when(dirtDetector.detectDirtDegree(any(LaundryBatch.class))).thenReturn(percentage);
        LaundryStatus laundryStatus = washingMachine.start(laundryBatch, programConfiguration);
        verify(engine, times(1)).spin();
    }

    @Test
    public void shoulCallPourMethodOnceDuringLoundryProcess() {
        Percentage percentage = new Percentage(10);
        when(dirtDetector.detectDirtDegree(any(LaundryBatch.class))).thenReturn(percentage);
        LaundryStatus laundryStatus = washingMachine.start(laundryBatch, programConfiguration);
        verify(waterPump, times(1)).pour(laundryBatch.getWeightKg());
    }

    @Test
    public void itCompiles() {
        assertThat(true, Matchers.equalTo(true));
    }

}
