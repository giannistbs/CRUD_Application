import axios from 'axios';

const API_URL = '/api/resources';

export interface Resource {
  id?: string;
  name: string;
  description: string;
  quantity: number;
  unit: string;
  category: string;
  weight: number;
  location: string;
}

export const resourceService = {
  getAll: async (): Promise<Resource[]> => {
    const response = await axios.get(API_URL);
    return response.data;
  },

  getById: async (id: string): Promise<Resource> => {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
  },

  create: async (resource: Resource): Promise<Resource> => {
    const response = await axios.post(API_URL, resource);
    return response.data;
  },

  update: async (id: string, resource: Resource): Promise<Resource> => {
    const response = await axios.put(`${API_URL}/${id}`, resource);
    return response.data;
  },

  delete: async (id: string): Promise<void> => {
    await axios.delete(`${API_URL}/${id}`);
  }
}; 