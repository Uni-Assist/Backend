package com.example.UniAssist;

import com.example.UniAssist.mapper.GroupMapper;
import com.example.UniAssist.model.dto.GroupDTO;
import com.example.UniAssist.model.entity.Group;
import com.example.UniAssist.repository.GroupRepository;
import com.example.UniAssist.service.GroupService;
import com.example.UniAssist.type.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Тесты для GroupServiceImpl")
@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupMapper groupMapper;

    @InjectMocks
    private GroupService groupService;

    private List<Group> testGroups;

    @BeforeEach
    public void setUp() {
        testGroups = createTestGroups();
    }

    private List<Group> createTestGroups() {
        return List.of(
                createGroup("42ebaa8f-d4c9-4b8e-8962-e2657871cc42", "ИВ-223", Department.CSE),
                createGroup("9f7706b2-ce24-4042-af4a-4302a23d521f", "ИВ-221", Department.CSE),
                createGroup("f4b6dabb-e19c-47d8-983d-0c0c482f1a36", "ИВ-222", Department.CSE)
        );
    }

    private Group createGroup(String id, String name, Department department) {
        Group group = new Group();
        group.setId(UUID.fromString(id));
        group.setName(name);
        group.setDepartment(department);
        return group;
    }

    @Test
    @DisplayName("Получение всех групп при наличии данных")
    void getAllGroups_WhenGroupsExist_ShouldReturnGroupList() {
        when(groupRepository.findAll()).thenReturn(testGroups);
        setupGroupMapperMock();

        List<GroupDTO> result = groupService.getAllGroups();

        assertThat(result)
                .hasSize(3)
                .extracting(GroupDTO::getName)
                .containsExactly("ИВ-223", "ИВ-221", "ИВ-222");
    }

    @Test
    @DisplayName("Получение пустого списка при отсутствии групп")
    void getAllGroups_WhenNoGroupsExist_ShouldReturnEmptyList() {
        when(groupRepository.findAll()).thenReturn(Collections.emptyList());

        List<GroupDTO> result = groupService.getAllGroups();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Проверка корректности преобразования Entity в DTO")
    void getAllGroups_ShouldReturnCorrectDTOStructure() {
        Group testGroup = testGroups.get(0);
        when(groupRepository.findAll()).thenReturn(List.of(testGroup));
        setupGroupMapperMock();

        GroupDTO result = groupService.getAllGroups().get(0);

        assertThat(result)
                .extracting(
                        GroupDTO::getId,
                        GroupDTO::getName
                )
                .containsExactly(
                        UUID.fromString("42ebaa8f-d4c9-4b8e-8962-e2657871cc42"),
                        "ИВ-223"
                );
    }

    @Test
    @DisplayName("Обработка ошибки при недоступности репозитория")
    void getAllGroups_WhenRepositoryFails_ShouldThrowException() {
        when(groupRepository.findAll()).thenThrow(new RuntimeException("Ошибка БД"));

        assertThatThrownBy(() -> groupService.getAllGroups())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ошибка БД");
    }

    private void setupGroupMapperMock() {
        when(groupMapper.toDTO(any(Group.class)))
                .thenAnswer(invocation -> {
                    Group g = invocation.getArgument(0);
                    GroupDTO dto = new GroupDTO();
                    dto.setId(g.getId());
                    dto.setName(g.getName());
                    return dto;
                });
    }
}