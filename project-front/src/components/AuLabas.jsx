import React from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button } from 'react-bootstrap';
import "../styles/AulaLabs.css";

function AuLabs() {
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
                    <option selected>Bloque 1</option>
                    <option>Bloque 2</option>
                    <option>Bloque 3</option>
                  </Form.Control>
                </Form.Group>
              </div>
              <div className="col-md-6">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="aula">Aulas-Laboratorios:</Form.Label>
                  <Form.Control as="select" id="aula" className="form-control">
                    <option selected>Aulas</option>
                    <option>Laboratorios</option>
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
                <th>id</th>
                <th>Nombre</th>
                <th>Piso</th>
                <th>Capacidad</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                <td>Audiovisuales</td>
                <td>1</td>
                <td>48</td>
              </tr>
              <tr>
                <td>2</td>
                <td>Computación</td>
                <td>2</td>
                <td>30</td>
              </tr>
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
