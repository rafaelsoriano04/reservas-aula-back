import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button } from 'react-bootstrap';
import "../styles/materias.css";
import axios from "axios";
import Swal from "sweetalert2";

function Materias() {
    const [selectedRow, setSelectedRow] = useState(null);
    const [showContextMenu, setShowContextMenu] = useState(false);
    const [materias, setMateria] = useState([]);
    const [formData, setFormData] = useState({  id: '', nombre: '' });
    const [isEditing, setIsEditing] = useState(false);

    const [contextMenuPosition, setContextMenuPosition] = useState({
        top: 0,
        left: 0,
    });

    const getMaterias = async () => {
        const url = `http://localhost:8080/materia/todos`;
        try {
          const response = await axios.get(url);
          setMateria(Array.isArray(response.data) ? response.data : []);
        } catch (error) {
          setMateria([]); // Limpia los datos si la petición falla
        }
      };
      
    const eliminarMateria = async (id) =>{
        console.log("ID a eliminar:", id);
        const url = `http://localhost:8080/materia/${id}`
        try {
            const response = await axios.delete(url);
            getMaterias();
            Swal.fire({
                title: "Eliminado",
                text: "La materia ha sido eliminada correctamente",
                icon: "success",
                confirmButtonText: 'OK'
            });
        } catch (error) {
            Swal.fire({
                title: "Error",
                text: "No se pudo eliminar la materia. Por favor, intente de nuevo.",
                icon: "error",
                confirmButtonText: 'OK'
            });
        }
    
    }

    const guardarMateria = async () =>{
        const url = `http://localhost:8080/materia`
        try {
            let materia = {
                
                nombre: formData.nombre  
            };
            console.log(materia)
            const response = await axios.post(url, materia);
            getMaterias();
            setFormData({ id: '', nombre: '' }); 
            Swal.fire({
                title: "Guardado",
                text: "La materia se guardo",
                icon: "success",
                confirmButtonText: 'OK'
            });
        } catch (error) {
            Swal.fire({
                title: "Error",
                text: "No se pudo guardar, verifique e intentelo de nuevo.",
                icon: "error",
                confirmButtonText: 'OK'
            });
        }
    }
   
      const handleRowClick = (e, materia) => {
        e.stopPropagation();  
        setSelectedRow(materia.id);
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
    
    const editarMateria = async (id) => {
       const url = `http://localhost:8080/materia`
        try {
            let materia = {
                id: formData.id,  
                nombre: formData.nombre  
            };
            console.log(materia)

          const response = await axios.post(url, materia);
          if (response.status === 200) {
            getMaterias();  
            setIsEditing(false); 
            setFormData({ id: '', nombre: '' }); 
            Swal.fire({
                title: "Modificado",
                text: "La materia ha sido modificada correctamente",
                icon: "success",
                confirmButtonText: 'OK'
            }); 
          }
        } catch (error) {
            Swal.fire({
                title: "Error",
                text: "No se pudo modificar la materia. Por favor, intente de nuevo.",
                icon: "error",
                confirmButtonText: 'OK'
            });
        }
      };
    useEffect(() => {
        document.addEventListener("click", handleDocumentClick);
        return () => {
            document.removeEventListener("click", handleDocumentClick);
        };
    }, []);
    useEffect(() => {
        getMaterias();
      }, []);

    

    return (
        <div className="container-fluid">
            <div className="content">
                <div className="header">
                    <h2>Materias</h2>
                </div>
                <div className="mt-4">
                    <Form id="form-reservas">
                        <div className="row">
                            <div className="col-md-4">
                            <Form.Group className="form-group">
                
                
            </Form.Group>
                                <Form.Group className="form-group">
                                    <Form.Label htmlFor="nombre">Nombre:</Form.Label>
                                    <Form.Control type="text" id="nombre" className="form-control" name="nombre" value={formData.nombre}  
                                    onChange={e => setFormData({ ...formData, nombre: e.target.value })}  />

                                </Form.Group>
                            </div>
                            </div>
                                            <div className="button-group mt-4 text-center">
                        {!isEditing ? (
                            <Button type="button" className="btn btn-custom" onClick={()=> guardarMateria()} >
                                Crear
                            </Button>
                        ) : (
                            <Button type="button" className="btn btn-custom" id="guardar-btn" onClick={() => editarMateria(selectedRow)}>
                                Guardar
                            </Button>
                        )}
                    </div>

                    </Form>
                    <table className="table table-bordered mt-4">
                        <thead>
                        <tr>
                                
                                <th>Nombre</th>
                            </tr>
                        </thead>
                        <tbody>
                                {materias.map(materia => (
                                <tr key={materia.id} onClick={(e) => handleRowClick(e, materia)}>
                                
                                <td>{materia.nombre}
                                   
                                </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <div
                        className="context-menu"
                        id="context-menu"
                        style={{
                            display: showContextMenu && selectedRow !== null ? "block" : "none",
                            top: contextMenuPosition.top,
                            left: contextMenuPosition.left,
                        }}
                    >
                       <Button variant="custom" id="editar-btn" onClick={() => {
                        const selectedMateria = materias.find(m => m.id === selectedRow);
                        if (selectedMateria) {
                            setFormData({
                                id: selectedMateria.id,
                                nombre: selectedMateria.nombre
                            });
                           
                            setShowContextMenu(false);  // Cierra el menú contextual
                            setIsEditing(true); 
                        }
                        }}>
                        Editar
                        </Button>
                        <Button variant="custom" id="eliminar-btn" onClick={() => eliminarMateria(selectedRow)}>
                            Eliminar
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Materias;
