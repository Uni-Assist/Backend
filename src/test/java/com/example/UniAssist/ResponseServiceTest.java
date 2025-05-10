package com.example.UniAssist;

import com.example.UniAssist.model.dto.StudentResponseRequest;
import com.example.UniAssist.model.dto.UpdateMarkRequest;
import com.example.UniAssist.model.entity.Solution;
import com.example.UniAssist.repository.ResponseRepository;
import com.example.UniAssist.service.ResponseService;
import com.example.UniAssist.type.SolutionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@DisplayName("Тесты для ResponseService")
@ExtendWith(MockitoExtension.class)
public class ResponseServiceTest {

    @Mock
    private ResponseRepository responseRepository;

    @InjectMocks
    private ResponseService responseService;

    private UUID studentId;
    private UUID taskId;
    private UUID responseId;

    @BeforeEach
    void setUp() {
        studentId = UUID.randomUUID();
        taskId = UUID.randomUUID();
        responseId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Успешная обработка ответа студента")
    void processStudentResponse_WhenValidRequest_ShouldReturnSuccess() {
        StudentResponseRequest request = new StudentResponseRequest();
        request.setTaskId(taskId);
        request.setBody("Ответ студента");
        request.setType(SolutionType.TEXT);

        String result = responseService.processStudentResponse(studentId, request);

        verify(responseRepository).saveResponse(studentId, taskId, "Ответ студента", SolutionType.TEXT);
        assertThat(result).isEqualTo("Success");
    }

    @Test
    @DisplayName("Обработка ошибки при сохранении ответа студента")
    void processStudentResponse_WhenRepositoryFails_ShouldThrowException() {
        StudentResponseRequest request = new StudentResponseRequest();
        request.setTaskId(taskId);
        request.setBody("Ответ студента");
        request.setType(SolutionType.TEXT);

        doThrow(new RuntimeException("Ошибка БД"))
                .when(responseRepository)
                .saveResponse(studentId, taskId, "Ответ студента", SolutionType.TEXT);

        assertThatThrownBy(() -> responseService.processStudentResponse(studentId, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ошибка БД");
    }

    @Test
    @DisplayName("Успешное обновление оценки")
    void updateResponseMark_WhenValidRequest_ShouldReturnSuccess() {
        UpdateMarkRequest request = new UpdateMarkRequest();
        request.setId(responseId);
        request.setMark(85);

        String result = responseService.updateResponseMark(request);

        verify(responseRepository).updateMark(responseId, 85);
        assertThat(result).isEqualTo("Success");
    }

    @Test
    @DisplayName("Обработка ошибки при обновлении оценки")
    void updateResponseMark_WhenRepositoryFails_ShouldThrowException() {
        UpdateMarkRequest request = new UpdateMarkRequest();
        request.setId(responseId);
        request.setMark(90);

        doThrow(new RuntimeException("Ошибка обновления"))
                .when(responseRepository)
                .updateMark(responseId, 90);

        assertThatThrownBy(() -> responseService.updateResponseMark(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ошибка обновления");
    }
}