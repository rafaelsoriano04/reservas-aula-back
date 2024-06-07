import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button } from 'react-bootstrap';
import "../styles/docentes.css";
import axios from "axios";
import Swal from "sweetalert2";

function Docentes() {
  const [selectedRow, setSelectedRow] = useState(null);
  const [docentes, setDocente] = useState([]);
  const [showContextMenu, setShowContextMenu] = useState(false);
  const [formData, setFormData] = useState({  id: '', nombre: '', cedula: '', apellido: '',telefono:''});
  const [isEditing, setIsEditing] = useState(false);
  const [contextMenuPosition, setContextMenuPosition] = useState({
    top: 0,
    left: 0,
  });
  const crearDocente = async () => {
    try {
      let docente = {
        cedula: formData.cedula,
        nombre: formData.nombre,
        apellido: formData.apellido,
        telefono: formData.telefono,
        direccion:"",
        tipo: "Docente"
      }
      const response = await axios.post(
        `http://localhost:8080/person`, docente
      );
      getDocentes();
            setFormData({  id: '', nombre: '', cedula: '', apellido: '',telefono:''}); 
            Swal.fire({
                title: "Guardado",
                text: "El Docente se guardo",
                icon: "success",
                confirmButtonText: 'OK'
            });
    } catch (error) {
      Swal.fire({
        title: "No encontrado",
        text: "Verifique que no existe e intentelo de nuevo",
        icon: "info",
      });
    }
  };

  const getDocentes = async () => {
    const url = `http://localhost:8080/person/docente`;
    try {
      const response = await axios.get(url);
      setDocente(Array.isArray(response.data) ? response.data : []);
    } catch (error) {
      setDocente([]); // Limpia los datos si la petición falla
    }
  };

  const eliminarDocentes= async (id) => {
    const url = `http://localhost:8080/person/docente${id}`
        try {
            const response = await axios.delete(url);
            getDocentes();
            Swal.fire({
                title: "Eliminado",
                text: "El docente ha sido eliminado correctamente",
                icon: "success",
                confirmButtonText: 'OK'
            });
        } catch (error) {
            Swal.fire({
                title: "Error",
                text: "No se pudo eliminar el docente. Por favor, intente de nuevo.",
                icon: "error",
                confirmButtonText: 'OK'
            });
        }
  }
  const editarDocente = async (id) => {
    const url = `http://localhost:8080/person`;
    let docente = {
      id: formData.id,
      cedula: formData.cedula,
      nombre: formData.nombre,
      apellido: formData.apellido,
      telefono: formData.telefono,
      direccion:"",
      tipo: "Docente"
     };
     console.log(docente);
     try {
       

       const response = await axios.post(url, docente);
       if (response.status === 200) {
         getDocentes();
         setIsEditing(false); 
         setFormData({ id: '', nombre: '', cedula: '', apellido: '',telefono:'' }); 
         Swal.fire({
             title: "Modificado",
             text: "El docente ha sido modificada correctamente",
             icon: "success",
             confirmButtonText: 'OK'
         }); 
       }
     } catch (error) {
         Swal.fire({
             title: "Error",
             text: "No se pudo modificar el docente. Por favor, intente de nuevo.",
             icon: "error",
             confirmButtonText: 'OK'
         });
     }
   };
  
const handleRowClick = (e, docente) => {
  e.stopPropagation();  
  setSelectedRow(docente.id);
  setContextMenuPosition({ top: e.pageY, left: e.pageX });
  setShowContextMenu(true);
  console.log(selectedRow);
  
};

  

  const handleDocumentClick = (e) => {
    if (!e.target.closest(".context-menu") && !e.target.closest("td")) {
      setSelectedRow(null);
      setShowContextMenu(false);
    }
  };

  useEffect(() => {
    document.addEventListener("click", handleDocumentClick);
    return () => {
      document.removeEventListener("click", handleDocumentClick);
    };
  }, []);

  useEffect(() => {
    getDocentes();
  }, []);

  return (
    <div className="container-fluid">
      <div className="content">
        <div className="header">
          <h2>Docentes</h2>
        </div>
        <div className="mt-4">
          <Form id="form-reservas">
            <div className="row">
              <div className="col-md-4">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="cedula">Cédula:</Form.Label>
                  <Form.Control type="text" id="cedula" className="form-control" name="cedula" value={formData.cedula}  
                                    onChange={e => setFormData({ ...formData, cedula: e.target.value })}  />

                </Form.Group>
              </div>
              <div className="col-md-4">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="nombre">Nombre:</Form.Label>
                  <Form.Control type="text" id="nombre" className="form-control" name="nombre" value={formData.nombre}  
                                    onChange={e => setFormData({ ...formData, nombre: e.target.value })}  />

                </Form.Group>
              </div>
              <div className="col-md-4">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="apellido">Apellido:</Form.Label>
                  <Form.Control type="text" id="apellido" className="form-control" name="apellido" value={formData.apellido}  
                                    onChange={e => setFormData({ ...formData, apellido: e.target.value })}  />

                </Form.Group>
                
              </div>
              <div className="col-md-4">
      <Form.Group className="form-group">
        <Form.Label htmlFor="telefono">Teléfono:</Form.Label>
        <Form.Control type="text" id="telefono" className="form-control" name="telefono" value={formData.telefono}  
                      onChange={e => setFormData({ ...formData, telefono: e.target.value })} />
      </Form.Group>
    </div>
            </div>
            <div className="button-group mt-4 text-center">
            {!isEditing ? (
                            <Button type="button" className="btn btn-custom" onClick={crearDocente}  >
                                Crear
                            </Button>
                        ) : (
                            <Button type="button" className="btn btn-custom" id="guardar-btn"onClick={editarDocente} >
                                Guardar
                            </Button>
                        )}
            </div>
          </Form>
          <table className="table table-bordered mt-4">
            <thead>
              <tr>
               
                <th>Cédula</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Teléfono</th>
              </tr>
            </thead>
            <tbody>
                      {docentes.map(docente => (
                                <tr key={docente.id} onClick={(e) => handleRowClick(e, docente)}>
                                <td>{docente.cedula}                             
                                </td>
                                <td>{docente.nombre}                             
                                </td>
                                <td>{docente.apellido}                             
                                </td>
                                <td>{docente.telefono}                             
                                </td>
                                </tr>
                            ))}
             
            </tbody>
          </table>
          <div
            className="context-menu"
            style={{
              display: showContextMenu && selectedRow !== null ? "block" : "none",
              top: contextMenuPosition.top,
              left: contextMenuPosition.left,
            }}
          >
            <Button variant="custom" id="editar-btn" onClick={() => {
  const selectedDocente = docentes.find(d => d.id === selectedRow);
  if (selectedDocente) {
    setFormData({
      id: selectedDocente.id,
      cedula: selectedDocente.cedula,
      nombre: selectedDocente.nombre,
      apellido: selectedDocente.apellido,
      telefono: selectedDocente.telefono
    });

    setShowContextMenu(false);  // Cierra el menú contextual
    setIsEditing(true); 
  }
}}>
  Editar
</Button>

                        <Button variant="custom" id="eliminar-btn" onClick={() => eliminarDocentes
                          (selectedRow)}>
                            Eliminar
                        </Button>
          </div>
        </div>
      </div>
    </div>
  );

 
}

export default Docentes;
