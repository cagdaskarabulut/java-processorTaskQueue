package com.karabulut.javaprocessortaskqueue;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JavaProcessorTaskQueueApplicationTests {

    @Mock
    JavaProcessorTaskQueueApplication javaProcessorTaskQueueApplication;

    @Test
    public void given6_whenGetActiveTaskIndexAtGivenCycle_thenReturn4() throws Exception {
        List<Integer> tasks = new ArrayList<>(List.of(2,6,7,1,4));
        assertThat(javaProcessorTaskQueueApplication.getActiveTaskIndexAtGivenCycle(tasks,6)).isEqualTo(4);
    }

    @Test
    public void given23_whenGetActiveTaskIndexAtGivenCycleWithDependencies_thenReturn7() throws Exception {
        List<Integer> tasks = new ArrayList<>(List.of(3,4,4,4,20,7,5));
        assertThat(javaProcessorTaskQueueApplication.getActiveTaskIndexAtGivenCycleWithDependencies(tasks,23)).isEqualTo(7);
    }
}
