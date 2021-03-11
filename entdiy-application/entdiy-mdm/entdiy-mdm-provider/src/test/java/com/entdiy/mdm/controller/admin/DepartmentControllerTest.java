package com.entdiy.mdm.controller.admin;

import com.entdiy.common.test.SpringBootMVCTest;
import com.entdiy.common.test.support.MockEntityUtils;
import com.entdiy.mdm.entity.Department;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @see DepartmentController
 */
@Slf4j
public class DepartmentControllerTest extends SpringBootMVCTest {

    @Test
    public void curd() throws Exception {
        ResultActions resultActions = null;
        String response = null;

        resultActions = mockMvc.perform(
                get("/mdm/admin/department/edit")
                        .contentType(MediaType.APPLICATION_JSON));
        response = getContentString(resultActions);
        log.debug("/mdm/admin/department/edit Response 0: {}", response);

        Department departmentEditDto = MockEntityUtils.buildMockObject(Department.class);
        departmentEditDto.setEstablishDate(LocalDate.now().plusDays(1));
        resultActions = mockMvc.perform(
                post("/mdm/admin/department/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(departmentEditDto)));
        response = getContentString(resultActions);
        log.debug("/mdm/admin/department/edit Response 1: {}", response);
        resultActions.andExpect(jsonPath("$.success").exists()).andExpect(jsonPath("$.code").exists());


        Department editDto = fromJson(response, Department.class);
        Long id = editDto.getId();
        log.debug("/mdm/admin/department/edit Response id: {}", id);

        editDto.setEstablishDate(LocalDate.now().minusDays(1));
        resultActions = mockMvc.perform(
                post("/mdm/admin/department/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(editDto)));
        response = getContentString(resultActions);
        log.debug("/mdm/admin/department/edit Response 2: {}", response);

        resultActions = mockMvc.perform(
                get("/mdm/admin/department/query")
                        .contentType(MediaType.APPLICATION_JSON));
        response = getContentString(resultActions);
        log.debug("/mdm/admin/department/query Response: {}", response);

        resultActions = mockMvc.perform(
                post("/mdm/admin/department/delete")
                        .param("id", String.valueOf(id))
                        .param("id", String.valueOf(123))
                        .param("id", String.valueOf(234))
                        .contentType(MediaType.APPLICATION_JSON));

        response = getContentString(resultActions);
        log.debug("/mdm/admin/department/delete Response: {}", response);



    }

}
