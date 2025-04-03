package com.marsops.service;

import com.marsops.model.Resource;
import com.marsops.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourceService resourceService;

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
        when(resourceRepository.findAll()).thenReturn(resources);

        List<Resource> result = resourceService.getAllResources();

        assertEquals(resources, result);
        verify(resourceRepository).findAll();
    }

    @Test
    void getResourceById_WhenResourceExists_ShouldReturnResource() {
        when(resourceRepository.findById("1")).thenReturn(Optional.of(testResource));

        Optional<Resource> result = resourceService.getResourceById("1");

        assertTrue(result.isPresent());
        assertEquals(testResource, result.get());
        verify(resourceRepository).findById("1");
    }

    @Test
    void getResourceById_WhenResourceDoesNotExist_ShouldReturnEmpty() {
        when(resourceRepository.findById("1")).thenReturn(Optional.empty());

        Optional<Resource> result = resourceService.getResourceById("1");

        assertFalse(result.isPresent());
        verify(resourceRepository).findById("1");
    }

    @Test
    void createResource_ShouldReturnCreatedResource() {
        when(resourceRepository.save(any(Resource.class))).thenReturn(testResource);

        Resource result = resourceService.createResource(testResource);

        assertEquals(testResource, result);
        verify(resourceRepository).save(testResource);
    }

    @Test
    void updateResource_WhenResourceExists_ShouldReturnUpdatedResource() {
        when(resourceRepository.existsById("1")).thenReturn(true);
        when(resourceRepository.save(any(Resource.class))).thenReturn(testResource);

        Resource result = resourceService.updateResource("1", testResource);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals(testResource, result);
        verify(resourceRepository).existsById("1");
        verify(resourceRepository).save(testResource);
    }

    @Test
    void updateResource_WhenResourceDoesNotExist_ShouldReturnNull() {
        when(resourceRepository.existsById("1")).thenReturn(false);

        Resource result = resourceService.updateResource("1", testResource);

        assertNull(result);
        verify(resourceRepository).existsById("1");
        verify(resourceRepository, never()).save(any(Resource.class));
    }

    @Test
    void deleteResource_WhenResourceExists_ShouldReturnTrue() {
        when(resourceRepository.existsById("1")).thenReturn(true);

        boolean result = resourceService.deleteResource("1");

        assertTrue(result);
        verify(resourceRepository).existsById("1");
        verify(resourceRepository).deleteById("1");
    }

    @Test
    void deleteResource_WhenResourceDoesNotExist_ShouldReturnFalse() {
        when(resourceRepository.existsById("1")).thenReturn(false);

        boolean result = resourceService.deleteResource("1");

        assertFalse(result);
        verify(resourceRepository).existsById("1");
        verify(resourceRepository, never()).deleteById(anyString());
    }
} 