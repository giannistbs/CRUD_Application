package com.marsops.controller;

import com.marsops.model.Resource;
import com.marsops.service.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResourceControllerTest {

    @Mock
    private ResourceService resourceService;

    @InjectMocks
    private ResourceController resourceController;

    private Resource testResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testResource = new Resource();
        testResource.setId("1");
        testResource.setName("Water Supply");
        testResource.setDescription("Potable water storage");
        testResource.setQuantity(1000);
        testResource.setUnit("liters");
        testResource.setCategory("Life Support");
        testResource.setWeight(1000.0);
        testResource.setLocation("Storage Bay A");
    }

    @Test
    void getAllResources_ShouldReturnListOfResources() {
        List<Resource> resources = Arrays.asList(testResource);
        when(resourceService.getAllResources()).thenReturn(resources);

        ResponseEntity<List<Resource>> response = resourceController.getAllResources();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(resources, response.getBody());
        verify(resourceService).getAllResources();
    }

    @Test
    void getResourceById_WhenResourceExists_ShouldReturnResource() {
        when(resourceService.getResourceById("1")).thenReturn(Optional.of(testResource));

        ResponseEntity<Resource> response = resourceController.getResourceById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testResource, response.getBody());
        verify(resourceService).getResourceById("1");
    }

    @Test
    void getResourceById_WhenResourceDoesNotExist_ShouldReturnNotFound() {
        when(resourceService.getResourceById("1")).thenReturn(Optional.empty());

        ResponseEntity<Resource> response = resourceController.getResourceById("1");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(resourceService).getResourceById("1");
    }

    @Test
    void createResource_ShouldReturnCreatedResource() {
        when(resourceService.createResource(any(Resource.class))).thenReturn(testResource);

        ResponseEntity<Resource> response = resourceController.createResource(testResource);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testResource, response.getBody());
        verify(resourceService).createResource(testResource);
    }

    @Test
    void updateResource_WhenResourceExists_ShouldReturnUpdatedResource() {
        when(resourceService.updateResource("1", testResource)).thenReturn(testResource);

        ResponseEntity<Resource> response = resourceController.updateResource("1", testResource);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testResource, response.getBody());
        verify(resourceService).updateResource("1", testResource);
    }

    @Test
    void updateResource_WhenResourceDoesNotExist_ShouldReturnNotFound() {
        when(resourceService.updateResource("1", testResource)).thenReturn(null);

        ResponseEntity<Resource> response = resourceController.updateResource("1", testResource);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(resourceService).updateResource("1", testResource);
    }

    @Test
    void deleteResource_WhenResourceExists_ShouldReturnOk() {
        when(resourceService.deleteResource("1")).thenReturn(true);

        ResponseEntity<Void> response = resourceController.deleteResource("1");

        assertEquals(200, response.getStatusCodeValue());
        verify(resourceService).deleteResource("1");
    }

    @Test
    void deleteResource_WhenResourceDoesNotExist_ShouldReturnNotFound() {
        when(resourceService.deleteResource("1")).thenReturn(false);

        ResponseEntity<Void> response = resourceController.deleteResource("1");

        assertEquals(404, response.getStatusCodeValue());
        verify(resourceService).deleteResource("1");
    }
} 