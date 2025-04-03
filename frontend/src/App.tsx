import { useState, useEffect } from 'react';
import {
  Container,
  Typography,
  Box,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
} from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon, Add as AddIcon } from '@mui/icons-material';
import { Resource, resourceService } from './services/resourceService';

function App() {
  const [resources, setResources] = useState<Resource[]>([]);
  const [open, setOpen] = useState(false);
  const [editingResource, setEditingResource] = useState<Resource | null>(null);
  const [formData, setFormData] = useState<Resource>({
    name: '',
    description: '',
    quantity: 0,
    unit: '',
    category: '',
    weight: 0,
    location: '',
  });

  useEffect(() => {
    loadResources();
  }, []);

  const loadResources = async () => {
    try {
      const data = await resourceService.getAll();
      setResources(data);
    } catch (error) {
      console.error('Error loading resources:', error);
    }
  };

  const handleOpen = (resource?: Resource) => {
    if (resource) {
      setEditingResource(resource);
      setFormData(resource);
    } else {
      setEditingResource(null);
      setFormData({
        name: '',
        description: '',
        quantity: 0,
        unit: '',
        category: '',
        weight: 0,
        location: '',
      });
    }
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setEditingResource(null);
  };

  const handleSubmit = async () => {
    try {
      if (editingResource?.id) {
        await resourceService.update(editingResource.id, formData);
      } else {
        await resourceService.create(formData);
      }
      handleClose();
      loadResources();
    } catch (error) {
      console.error('Error saving resource:', error);
    }
  };

  const handleDelete = async (id: string) => {
    try {
      await resourceService.delete(id);
      loadResources();
    } catch (error) {
      console.error('Error deleting resource:', error);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'quantity' || name === 'weight' ? Number(value) : value,
    }));
  };

  return (
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
        <Typography variant="h4" component="h1">
          Mars Resource Management
        </Typography>
        <Button
          variant="contained"
          color="primary"
          startIcon={<AddIcon />}
          onClick={() => handleOpen()}
        >
          Add Resource
        </Button>
      </Box>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Name</TableCell>
              <TableCell>Description</TableCell>
              <TableCell>Quantity</TableCell>
              <TableCell>Unit</TableCell>
              <TableCell>Category</TableCell>
              <TableCell>Weight</TableCell>
              <TableCell>Location</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {resources.map((resource) => (
              <TableRow key={resource.id}>
                <TableCell>{resource.name}</TableCell>
                <TableCell>{resource.description}</TableCell>
                <TableCell>{resource.quantity}</TableCell>
                <TableCell>{resource.unit}</TableCell>
                <TableCell>{resource.category}</TableCell>
                <TableCell>{resource.weight}</TableCell>
                <TableCell>{resource.location}</TableCell>
                <TableCell>
                  <IconButton onClick={() => handleOpen(resource)} color="primary">
                    <EditIcon />
                  </IconButton>
                  <IconButton onClick={() => resource.id && handleDelete(resource.id)} color="error">
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
        <DialogTitle>{editingResource ? 'Edit Resource' : 'Add Resource'}</DialogTitle>
        <DialogContent>
          <Box display="flex" flexDirection="column" gap={2} sx={{ mt: 2 }}>
            <TextField
              name="name"
              label="Name"
              value={formData.name}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              name="description"
              label="Description"
              value={formData.description}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              name="quantity"
              label="Quantity"
              type="number"
              value={formData.quantity}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              name="unit"
              label="Unit"
              value={formData.unit}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              name="category"
              label="Category"
              value={formData.category}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              name="weight"
              label="Weight"
              type="number"
              value={formData.weight}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              name="location"
              label="Location"
              value={formData.location}
              onChange={handleChange}
              fullWidth
            />
          </Box>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSubmit} variant="contained" color="primary">
            {editingResource ? 'Update' : 'Create'}
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
}

export default App;
