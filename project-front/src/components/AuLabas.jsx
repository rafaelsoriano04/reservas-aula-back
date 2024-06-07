import React,{useEffect, useState} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button } from 'react-bootstrap';
import "../styles/AulaLabs.css";
import axios from "axios";
import Swal from "sweetalert2";

function AuLabs() {

  const [bloques, setBloques] = useState([]);
  const [selectedBloque, setSelectedBloque] = useState(1);
  const [selectedTipo, setSelectedTipo] = useState("Aula");
  const [selectedAulaLab, setSelectedAulaLab] = useState("");
  const [aulasLabs, setAulasLabs] = useState([]);
  
  const getBloques = async () => {
    try {
      const resp = await axios.get("http://localhost:8080/bloque");
      setBloques(resp.data);
    } catch (error) {
      const { message } = error.response.data;
     
    }
  };
  const handleTipoChange = event => {
    setSelectedTipo(event.target.value);
    console.log(selectedTipo);
  };
  
  const fetchAulasLabs = async () => {
    const url = `http://localhost:8080/espacio/bloque/${selectedBloque}`;
    try {
      const response = await axios.get(url);
      //Aqui se debe controlar que se llene de acuerdo al tipo
      let filteredData = [];
      if (selectedTipo == "Aula") {
        filteredData = response.data.filter(item => item.tipo === "Aula");
      } else {
        filteredData = response.data.filter(
          item => item.tipo === "Laboratorio"
        );
      }
      setAulasLabs(filteredData);
    } catch (error) {
      const { message } = error.response.data;
      if (message === "No hay espacios en este bloque") {
        Swal.fire({
          title: "Oops...",
          html: `<i>${message}</i>`,
          icon: "error",
        });
      } else {
        Swal.fire({
          title: "Oops...",
          html: `<i>Error al conectar con el servidor</i>`,
          icon: "error",
        });
      }
      setAulasLabs([]); // Limpia los datos si la petición falla
    }
  };
  useEffect(() => {
    if (selectedBloque && selectedTipo) {
      fetchAulasLabs();
    }
  }, [selectedBloque, selectedTipo]);
  useEffect(() => {
    getBloques();
  }, []);

  
  //Predefinido para aulabs
  
  useEffect(() => {
    if (aulasLabs.length > 0) {
      setSelectedAulaLab(aulasLabs[0].id);
    }
  }, [aulasLabs]);

  return (
    <div className="container-fluid">
      <div className="content">
        <div className="header">
          <h2>Aulas/Laboratorios</h2>
        </div>
        <div className="mt-4">
          <Form id="form-reservas">
            <div className="row">
              <div className="col-md-6">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="bloque">Bloque:</Form.Label>
                  <Form.Control as="select" id="bloque" className="form-control">
                  {bloques.map(bloque => (
                      <option key={bloque.id} value={bloque.id}>
                        {bloque.nombre}
                      </option>
                    ))}
                  </Form.Control>
                </Form.Group>
              </div>
              <div className="col-md-6">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="aula">Aulas-Laboratorios:</Form.Label>
                  <Form.Control
                    as="select"
                    id="aula"
                    className="form-control"
                    value={selectedTipo}
                    onChange={handleTipoChange}
                    name="aula"
                  >
                    <option value="Aulas">Aulas</option>
                    <option value="Laboratorio">Laboratorios</option>
                  </Form.Control>
                </Form.Group>
              </div>
            </div>
            <div className="row">
              <div className="col-md-6">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="nombre">Nombre:</Form.Label>
                  <Form.Control type="text" id="nombre" className="form-control" name="nombre" />
                </Form.Group>
              </div>
              <div className="col-md-6">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="piso">Piso:</Form.Label>
                  <Form.Control as="select" id="piso" className="form-control" name="piso">
                    <option value="1">Piso 1</option>
                    <option value="2">Piso 2</option>
                    <option value="3">Piso 3</option>
                    <option value="3">Piso 4</option>
                    <option value="3">Piso 5</option>
                  </Form.Control>
                </Form.Group>
              </div>
              <div className="col-md-6">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="capacidad">Capacidad:</Form.Label>
                  <Form.Control type="number" id="capacidad" className="form-control" name="capacidad" min="1" max="100" />
                </Form.Group>
              </div>
            </div>
            <div className="button-group mt-4 text-center">
              <Button type="button" className="btn btn-custom">Crear</Button>
              <Button type="button" className="btn btn-custom" id="guardar-btn" style={{ display: 'none' }}>
                Guardar
              </Button>
            </div>
          </Form>
          <table className="table table-bordered mt-4">
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Piso</th>
                <th>Capacidad</th>
              </tr>
            </thead>
            <tbody>
                  {aulasLabs.map(aulaLab => (
          <tr key={aulaLab.id}>
            <td>{aulaLab.nombre}</td>
            <td>{aulaLab.piso}</td>
            <td>{aulaLab.capacidad}</td>
      
    </tr>
  ))}
                
            </tbody>
          </table>
          <div className="context-menu" style={{ display: 'none' }}>
            <button type="button">Editar</button>
            <button type="button">Eliminar</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AuLabs;
